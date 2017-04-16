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
        findGPA(key,value,context);
    }

    private void findEmailAndLinks(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] valueArray = Utility.splitWithSpace(value.toString());
        for (String eachValue : valueArray) {
            Matcher matcher = Utility.VALID_EMAIL_ADDRESS_REGEX.matcher(eachValue);
            Matcher urlMatcher = Utility.VALID_URL.matcher(eachValue);
            if (matcher.find()) {
                context.write(new Text("test"), new Text("email:"+eachValue));
            }
            if(urlMatcher.find()){
                context.write(new Text("test"), new Text("link:"+eachValue));
            }
        }
    }

    private void findPhoneNumber(LongWritable key, Text value, Context context) throws IOException, InterruptedException{

        // split with spaces and tabs
        String[] valueArray = Utility.splitWithSpacesAndTabs(value.toString());
        for(Object phoneNumberData : valueArray){
            String phoneNumber = (String)phoneNumberData;
            if (phoneNumber.matches(Utility.VALID_PHONENUMBER)){
                context.write(new Text("test"), new Text("PhoneNumber :" + phoneNumber));
            }
        }
    }

    private void findGPA(LongWritable key,Text value,Context context)throws IOException, InterruptedException{
        String[] valueArray = Utility.splitWithSpacesAndTabs(value.toString());
        for(String gpaData:valueArray){
            Matcher gpaMatcher = Utility.VALID_GPA.matcher(gpaData);
            if(gpaMatcher.find()){
                context.write(new Text("test"), new Text("GPA: "+gpaMatcher.group(2)));
            }
        }

    }

}
