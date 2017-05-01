/**
 * Created by Nitish on 4/14/2017.
 */

import java.io.IOException;
import java.util.regex.Matcher;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class ParserMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] valueArray = Utility.splitWithSpacesAndTabs(value.toString());

        if(!isNewFile(context)){
            Utility.incrementLineCountOfThisFile();
        }

        for (String eachValue : valueArray) {
            if (!eachValue.equals("")) {
                parseRequirements(context, eachValue, valueArray);
            }
        }
    }

    private void parseRequirements(Context context, String eachValue, String[] valueArray)
            throws IOException, InterruptedException {


        if(!Utility.nameFound) findName(context, eachValue, valueArray);
        if(!Utility.locationFound) findLocation(context, eachValue);
        findEmail(context, eachValue);
        findLinks(context, eachValue);
        findMatchingSkills(context, eachValue);
        findPhoneNumber(context, eachValue);
        findGPA(context, eachValue);
        findDegrees(context,eachValue);
    }

    private void findEmail(Context context, String eachValue) throws IOException, InterruptedException {
        Matcher emailMatcher = Utility.VALID_EMAIL_ADDRESS_REGEX.matcher(eachValue);
        if (emailMatcher.find()) { //Check for email address regex pattern
            context.write(new Text(Utility.currentFile), new Text("Email;" + eachValue));
        }
    }

    private void findLinks(Context context, String eachValue) throws IOException, InterruptedException {
        Matcher urlMatcher = Utility.VALID_URL.matcher(eachValue);
        if (urlMatcher.find()) { //Check for URL regex pattern
            context.write(new Text(Utility.currentFile), new Text("Link;" + eachValue));
        }
    }

    private void findMatchingSkills(Context context, String eachValue) throws IOException, InterruptedException {
        if (isMatchingSkill(eachValue)) { //Check if the word matches any of the required skill sets.
            context.write(new Text(Utility.currentFile), new Text("Skill;" + eachValue));
        }
    }

    private void findDegrees(Context context,String eachValue) throws IOException, InterruptedException{
        if(isMatchingDegree(eachValue)){
            context.write(new Text(Utility.currentFile),new Text("Degree;"+Utility.degrees.get(eachValue)));
        }
    }

    private void findPhoneNumber(Context context, String eachValue) throws IOException, InterruptedException {
        if (eachValue.matches(Utility.VALID_PHONENUMBER)) {
            context.write(new Text(Utility.currentFile), new Text("Phone;" + eachValue));
        }
    }

    private boolean isMatchingSkill(String word) {
        return Utility.requiredSkills.containsKey(word.toLowerCase());
    }

    private boolean isMatchingDegree(String word){
        return Utility.degrees.containsKey(word);
    }


    private void findGPA(Context context, String eachValue) throws IOException, InterruptedException {
        if(eachValue.contains("\\")|| eachValue.contains("//")){
            Matcher gpaMatcher = Utility.VALID_GPA.matcher(eachValue);
            if (gpaMatcher.find()) {
                context.write(new Text(Utility.currentFile), new Text("GPA;" + gpaMatcher.group(2)));
            }
        }else{
            Matcher gpaMatcher = Utility.VALID_GPA_SEPARATOR.matcher(eachValue);
            if (gpaMatcher.find()) {
                context.write(new Text(Utility.currentFile), new Text("GPA;" + gpaMatcher.group(1)));
            }
        }

    }


    private void findLocation(Context context, String eachValue) throws IOException, InterruptedException {

        String[] locationList = eachValue.split("[\\s@&.?$+-]+");
        String location = "";
        for(String eachLocationProbab : locationList){
            if(Utility.USStatesAcronyms.contains(eachLocationProbab) || Utility.USStatesFullNames.contains(eachLocationProbab)) {
                Utility.locationFound = true;
                location = eachLocationProbab;
                if(Utility.mapOfUSStates.containsKey(location))
                    location = Utility.mapOfUSStates.get(location);
                context.write(new Text(Utility.currentFile), new Text("Location;" + location));
            }
        }
    }

    private void findName(Context context, String eachValue, String[] valueArray) throws IOException, InterruptedException {

        String name = "";

        for(String eachString : valueArray){
            String[] namesList = eachString.split("[\\s@&.?$+:-]+");
            for(String eachNameProbab : namesList){
                // keep on checking for words which are not in dictionary and keep add it to names string
                if(!Dictionary.contains(eachNameProbab)){
                    name = name + eachNameProbab + " ";
                }
            }
        }

        if(name.matches("")){
            // Could not detect name in our parsing rule
            context.write(new Text(Utility.currentFile), new Text("Name;" + "UNKNOWN"));
        }
        else
            context.write(new Text(Utility.currentFile), new Text("Name;" + name.trim()));

        Utility.nameFound = true;
    }

    private boolean isNewFile(Context context){
        FileSplit fileSplit = (FileSplit)context.getInputSplit();
        String filename = fileSplit.getPath().getName();
        if(filename.matches(Utility.currentFile))
            return false;
        Utility.currentFile = filename;
        Utility.currentLineCount = 1;
        Utility.nameFound = false;
        Utility.locationFound = false;
        return true;
    }
}
