
/*
 * HomeworkMapper.java
 *
 * Created on Oct 22, 2012, 5:41:50 PM
 */


import java.io.IOException;
// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.io.Text;

/**
 *
 * @author mac
 */
public class HomeworkMapper extends Mapper<Text,Text,Text,Text> {
    // The Karmasphere Studio Workflow Log displays logging from Apache Commons Logging, for example:
    // private static final Log LOG = LogFactory.getLog("org.smaple.HomeworkMapper");

    @Override
    protected void map(Text key, Text value, Context context)
                    throws IOException, InterruptedException {
    	// TODO: please implement your mapper code here
    }
}
