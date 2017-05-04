/**
 * Created by Nitish on 4/14/2017.
 */

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import de.daslaboratorium.machinelearning.classifier.Classifier;

public class ParserReducer extends Reducer<Text, Text, Text, Text> {
    HashSet<String> matchedSkills;
    String[] keyArray;
    StringBuilder completeDocument;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        keyArray = key.toString().split(":");
        if (keyArray.length > 0 && keyArray[0].equals("classify")) {
            completeDocument = new StringBuilder();
            for (Text newLine : values) {
                completeDocument.append(newLine.toString().toLowerCase());
            }
            //classifyDocument(new Text(keyArray[1]),context);
        } else {
            matchedSkills = new HashSet<>();
            // TODO: please implement your reducer here
            StringBuilder concatValues = new StringBuilder();
            for (Text text : values) {
                String[] valueArray = text.toString().trim().split(";");
                if (valueArray[0].toLowerCase().equals("skill")) {
                    matchedSkills.add(valueArray[1].toLowerCase());
                }
                concatValues.append("|");
                concatValues.append(text.toString());
            }
            classifyDocument(key, context);
            concatValues.append("|");
            concatValues.append("Score;" + calculateMatchSkillScore());
            context.write(key, new Text(concatValues.toString().trim()));
        }
    }

    private double calculateMatchSkillScore() {
        Double score = (double) matchedSkills.size() / Utility.requiredSkills.keySet().size();
        return Double.parseDouble(Utility.df.format(score * 100));
    }

    private void classifyDocument(Text key, Context context) throws IOException, InterruptedException {
        //String category = Utility.bayes.classify(Arrays.asList(Utility.splitWithSpacesAndTabs(completeDocument.toString().toLowerCase()))).getCategory();
        String category = Utility.bayes.classify(Arrays.asList(this.matchedSkills.toArray(new String[matchedSkills.size()]))).getCategory();
        float probability = Utility.bayes.classify(Arrays.asList(this.matchedSkills.toArray(new String[matchedSkills.size()]))).getProbability();
        //Object test =((BayesClassifier<String, String>) Utility.bayes).classifyDetailed(Arrays.asList(this.matchedSkills.toArray(new String[matchedSkills.size()])));
        //context.write(new Text(keyArray[1]), new Text("category:" + category));
        context.write(key, new Text("category:" + category+ ", "+ probability*4));
    }

}
