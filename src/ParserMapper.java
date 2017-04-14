/**
 * Created by Nitish on 4/14/2017.
 */

import java.io.IOException;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
public class ParserMapper extends Mapper<Text, Text, Text, Text>  {

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {

    }
}
