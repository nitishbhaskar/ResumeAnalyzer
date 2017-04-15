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

    }

}
