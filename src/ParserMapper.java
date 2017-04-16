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
        findEmailAndLinks(key, value, context);
        findPhoneNumber(key, value, context);
    }

    private void findEmailAndLinks(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] valueArray = Utility.splitWithSpacesAndTabs(value.toString());
        for (String eachValue : valueArray) {
            if (!eachValue.equals("")) {
                Matcher emailMatcher = Utility.VALID_EMAIL_ADDRESS_REGEX.matcher(eachValue);
                Matcher urlMatcher = Utility.VALID_URL.matcher(eachValue);
                if (emailMatcher.find()) { //Check for email address regex pattern
                    context.write(new Text(Utility.currentFile), new Text("email:" + eachValue));
                }
                if (urlMatcher.find()) { //Check for URL regex pattern
                    context.write(new Text(Utility.currentFile), new Text("link:" + eachValue));
                }
                if (isMatchingSkill(eachValue)) { //Check if the word matches any of the required skill sets.
                    context.write(new Text(Utility.currentFile), new Text("skill:" + eachValue));
                }
            }
        }
    }

    private void findPhoneNumber(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // split with spaces and tabs
        String[] valueArray = Utility.splitWithSpacesAndTabs(value.toString());
        for (Object phoneNumberData : valueArray) {
            String phoneNumber = (String) phoneNumberData;
            if (phoneNumber.matches(Utility.VALID_PHONENUMBER)) {
                context.write(new Text(Utility.currentFile), new Text("PhoneNumber :" + phoneNumber));
            }
        }
    }

    private boolean isMatchingSkill(String word) {
        return Utility.requiredSkills.containsKey(word.toLowerCase());
    }

}
