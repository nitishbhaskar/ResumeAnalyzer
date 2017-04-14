/**
 * Created by Nitish on 4/14/2017.
 */
import java.io.IOException;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
public class ParserReducer extends Reducer<Text, Text, Text, Text>  {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // TODO: please implement your reducer here
        StringBuilder concatValues = new StringBuilder();
        for (Text text : values) {
            concatValues.append(" ");
            concatValues.append(text.toString());
        }
        context.write(key, new Text(concatValues.toString().trim()));
    }
}
