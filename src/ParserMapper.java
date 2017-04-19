/**
 * Created by Nitish on 4/14/2017.
 */

import java.io.IOException;
import java.util.regex.Matcher;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.oro.text.regex.Util;

public class ParserMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] valueArray = Utility.splitWithSpacesAndTabs(value.toString());

        if(!isNewFile(context)){
            Utility.incrementLineCountOfThisFile();
        }

        for (String eachValue : valueArray) {
            if (!eachValue.equals("")) {
                parseRequirements(context, eachValue);
            }
        }
    }

    private void parseRequirements(Context context, String eachValue)
            throws IOException, InterruptedException {

        findEmail(context, eachValue);
        findLinks(context, eachValue);
        findMatchingSkills(context, eachValue);
        findPhoneNumber(context, eachValue);
        findGPA(context, eachValue);
        findLocation(context, eachValue);
    }

    private void findEmail(Context context, String eachValue) throws IOException, InterruptedException {
        Matcher emailMatcher = Utility.VALID_EMAIL_ADDRESS_REGEX.matcher(eachValue);
        if (emailMatcher.find()) { //Check for email address regex pattern
            context.write(new Text(Utility.currentFile), new Text("email:" + eachValue));
        }
    }

    private void findLinks(Context context, String eachValue) throws IOException, InterruptedException {
        Matcher urlMatcher = Utility.VALID_URL.matcher(eachValue);
        if (urlMatcher.find()) { //Check for URL regex pattern
            context.write(new Text(Utility.currentFile), new Text("link:" + eachValue));
        }
    }

    private void findMatchingSkills(Context context, String eachValue) throws IOException, InterruptedException {
        if (isMatchingSkill(eachValue)) { //Check if the word matches any of the required skill sets.
            context.write(new Text(Utility.currentFile), new Text("skill:" + eachValue));
        }
    }

    private void findPhoneNumber(Context context, String eachValue) throws IOException, InterruptedException {
        if (eachValue.matches(Utility.VALID_PHONENUMBER)) {
            context.write(new Text(Utility.currentFile), new Text("PhoneNumber :" + eachValue));
        }
    }

    private boolean isMatchingSkill(String word) {
        return Utility.requiredSkills.containsKey(word.toLowerCase());
    }

    private void findGPA(Context context, String eachValue) throws IOException, InterruptedException {
        if(eachValue.contains("\\")|| eachValue.contains("//")){
            Matcher gpaMatcher = Utility.VALID_GPA.matcher(eachValue);
            if (gpaMatcher.find()) {
                context.write(new Text(Utility.currentFile), new Text("GPA: " + gpaMatcher.group(2)));
            }
        }else{
            Matcher gpaMatcher = Utility.VALID_GPA_SEPARATOR.matcher(eachValue);
            if (gpaMatcher.find()) {
                context.write(new Text(Utility.currentFile), new Text("GPA: " + gpaMatcher.group(1)));
            }
        }

    }


    private void findLocation(Context context, String eachValue) throws IOException, InterruptedException {
        if(Utility.currentLineCount > Utility.LOCATION_DEPTH)
            return;
        String city, state, zip;

    }

    private boolean isNewFile(Context context){
        FileSplit fileSplit = (FileSplit)context.getInputSplit();
        String filename = fileSplit.getPath().getName();
        if(filename.matches(Utility.currentFile))
            return false;
        Utility.currentFile = filename;
        Utility.currentLineCount = 0;
        return true;
    }
}
