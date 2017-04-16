/**
 * Created by Nitish on 4/14/2017.
 */

import java.io.IOException;
import java.util.regex.Matcher;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;

public class ParserMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] valueArray = Utility.splitWithSpacesAndTabs(value.toString());

        for (String eachValue : valueArray) {
            if (!eachValue.equals("")) {
                findEmail(key, value, context, eachValue);
                findLinks(key, value, context, eachValue);
                findMatchingSkills(key, value, context, eachValue);
                findPhoneNumber(key, value, context, eachValue);
                findGPA(key, value, context, eachValue);
            }
        }
    }

    private void findEmail(LongWritable key, Text value, Context context, String eachValue) throws IOException, InterruptedException {
        Matcher emailMatcher = Utility.VALID_EMAIL_ADDRESS_REGEX.matcher(eachValue);
        if (emailMatcher.find()) { //Check for email address regex pattern
            context.write(new Text(Utility.currentFile), new Text("email:" + eachValue));
        }
    }

    private void findLinks(LongWritable key, Text value, Context context, String eachValue) throws IOException, InterruptedException {
        Matcher urlMatcher = Utility.VALID_URL.matcher(eachValue);
        if (urlMatcher.find()) { //Check for URL regex pattern
            context.write(new Text(Utility.currentFile), new Text("link:" + eachValue));
        }
    }

    private void findMatchingSkills(LongWritable key, Text value, Context context, String eachValue) throws IOException, InterruptedException {
        if (isMatchingSkill(eachValue)) { //Check if the word matches any of the required skill sets.
            context.write(new Text(Utility.currentFile), new Text("skill:" + eachValue));
        }
    }

    private void findPhoneNumber(LongWritable key, Text value, Context context, String eachValue) throws IOException, InterruptedException {
        if (eachValue.matches(Utility.VALID_PHONENUMBER)) {
            context.write(new Text(Utility.currentFile), new Text("PhoneNumber :" + eachValue));
        }
    }

    private boolean isMatchingSkill(String word) {
        return Utility.requiredSkills.containsKey(word.toLowerCase());
    }

    private void findGPA(LongWritable key, Text value, Context context, String eachValue) throws IOException, InterruptedException {
        Matcher gpaMatcher = Utility.VALID_GPA.matcher(eachValue);
        if (gpaMatcher.find()) {
            context.write(new Text(Utility.currentFile), new Text("GPA: " + gpaMatcher.group(2)));
        }
    }
}
