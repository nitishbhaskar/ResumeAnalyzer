import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by rakeshh91 on 4/22/2017.
 */
public class WordToTextConverter {
    public static void convertWordToText(String src, String desc){
        try{
            //create file inputstream object to read data from file
            FileInputStream fs=new FileInputStream(src);
            //create document object to wrap the file inputstream object
            XWPFDocument docx=new XWPFDocument(fs);
            //create text extractor object to extract text from the document
            XWPFWordExtractor extractor=new XWPFWordExtractor(docx);
            //create file writer object to write text to the output file
            FileWriter fw=new FileWriter(desc);
            //write text to the output file
            fw.write(extractor.getText());
            //clear data from memory
            fw.flush();
            //close inputstream and file writer
            fs.close();
            fw.close();

        }catch(IOException e){e.printStackTrace();}
    }
}
