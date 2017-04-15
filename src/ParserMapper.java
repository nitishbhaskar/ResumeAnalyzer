/**
 * Created by Nitish on 4/14/2017.
 */

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;

public class ParserMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        findEmail(key, value, context);
    }

    private void findEmail(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Pattern valid_url = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);
        String[] valueArray = value.toString().trim().split(" ");
        for (String eachValue : valueArray) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(eachValue);
            Matcher urlMatcher = valid_url.matcher(eachValue);
            if (matcher.find()) {
                context.write(new Text("test"), new Text("email:"+eachValue));
            }
            if(urlMatcher.find()){
                context.write(new Text("test"), new Text("link:"+eachValue));
            }
        }
    }

    private void findPhoneNumber23(LongWritable key, Text value, Context context) throws IOException, InterruptedException{

    }

}
