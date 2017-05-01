/**
 * Created by Nitish on 4/14/2017.
 */

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;

public class ParserReducer extends Reducer<Text, Text, Text, Text> {
    HashSet<String> matchedSkills;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        matchedSkills = new HashSet<>();
        // TODO: please implement your reducer here
        StringBuilder concatValues = new StringBuilder();
        for (Text text : values) {
            String[] valueArray = text.toString().trim().split(";");
            if (valueArray[0].toLowerCase().equals("skill")) {
                matchedSkills.add(valueArray[1]);
            }
            concatValues.append("|");
            concatValues.append(text.toString());
        }
        concatValues.append("|");
        concatValues.append("Score;" + calculateMatchSkillScore());
        context.write(key, new Text(concatValues.toString().trim()));
    }

    private double calculateMatchSkillScore() {
        Double score = (double) matchedSkills.size() / Utility.requiredSkills.keySet().size();
        return Double.parseDouble(Utility.df.format(score * 100));
    }
}
