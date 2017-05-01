/**
 * Created by Nitish on 4/14/2017.
 */

import java.io.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.poi.util.SystemOutLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class ResumeAnalyzerJob {
    public static void main(String[] args) throws Exception {
        FilesConverterUtility.convertFiles("data\\sourceFiles\\", args[0]); //converts all types of files to .txt format and puts in destination
        Dictionary.populate();
        Utility.initializeCategories();
        Utility.populateStatesData();
        Utility.learnCategories();
        readFromRequirementsJSON();
        readDegreeFromRequirementsJSON();

        Job job = new Job();
        /* Autogenerated initialization. */
        initJob(job);
        /* Custom initialization. */
        initCustom(job);
        /* Tell Task Tracker this is the main */
        job.setJarByClass(ResumeAnalyzerJob.class);
        /*Delete the output folder if it already exists*/
        File index = new File(args[1] + "_intermediate");
        deleteFolder(index);

		/* This is an example of how to set input and output. */
        FileInputFormat.setInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1] + "_intermediate"));

		/* And finally, we submit the job. */
        job.submit();

        boolean success = job.waitForCompletion(true);

        // Job completes here and then we write the output data to Excel File
        OutputProcessor op = new OutputProcessor(args[1], "output_intermediate/part-r-00000");
        op.writeresultsToFile();
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { // some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    public static void initCustom(Job job) {
    }

    public static void initJob(Job job) {
        job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.TextInputFormat.class);
        job.setMapperClass(ParserMapper.class);
        job.getConfiguration().set("mapred.mapper.new-api", "true");
        job.getConfiguration().set("mapred.map.tasks", "3");
        job.setMapOutputKeyClass(org.apache.hadoop.io.Text.class);
        job.setMapOutputValueClass(org.apache.hadoop.io.Text.class);
        job.setPartitionerClass(org.apache.hadoop.mapreduce.lib.partition.HashPartitioner.class);
        job.setReducerClass(ParserReducer.class);
        job.getConfiguration().set("mapred.reducer.new-api", "true");
        job.getConfiguration().set("mapred.reduce.tasks", "2");
        job.setOutputKeyClass(org.apache.hadoop.io.Text.class);
        job.setOutputValueClass(org.apache.hadoop.io.Text.class);
        job.setOutputFormatClass(org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class);
        job.getConfiguration().set("", "");
    }

    private static void readFromRequirementsJSON() {
        String fileContents = "";
        StringBuilder fileContentsBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data\\requirements.json"));
            String line;
            while ((line = reader.readLine()) != null) {
                fileContentsBuilder.append(line);
            }
            fileContents = fileContentsBuilder.toString();
            JSONObject jsonObject = new JSONObject(fileContents);
            Utility.addSkills(jsonObject.getJSONArray("reqSkills"));
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static void readDegreeFromRequirementsJSON() {
        String fileContents = "";
        StringBuilder fileContentsBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data\\requirements.json"));
            String line;
            while ((line = reader.readLine()) != null) {
                fileContentsBuilder.append(line);
            }
            fileContents = fileContentsBuilder.toString();
            JSONObject jsonObject = new JSONObject(fileContents);
            Utility.addDegrees(jsonObject.getJSONArray("degree"));
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
