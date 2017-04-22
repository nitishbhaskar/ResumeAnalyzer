import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakeshh91 on 4/22/2017.
 */
public class WordToTextConverter {

    public static void convertWordFiles(String filesPath, String destPath){

        final File folder = new File(filesPath);
        File[] files = listDOCXFilesFromFolder(folder);
            for (int i = 0; i < files.length; i++) {
                String desc = destPath;
                if (files[i].toString().endsWith(".docx")) {
                    desc = desc + files[i].getName().replace(".docx", ".txt");
                } else if(files[i].toString().endsWith(".DOCX")) {
                    desc = desc + files[i].getName().replace(".DOCX", ".txt");
                }else if(files[i].toString().endsWith(".DOC")) {
                    desc = desc + files[i].getName().replace(".DOC", ".txt");
                }else if(files[i].toString().endsWith(".doc")) {
                    desc = desc + files[i].getName().replace(".doc", ".txt");
                }
                convertWordToText(files[i].toString(), desc);
        }
        System.out.println("Conversion complete");
    }

    public static File[] listDOCXFilesFromFolder(final File folder) {
        List<File> files= new ArrayList<File>();
        for (final File fileEntry : folder.listFiles())
        {
            if (fileEntry.toString().endsWith(".docx") || fileEntry.toString().endsWith(".DOCX") || (fileEntry.toString().endsWith(".doc") || fileEntry.toString().endsWith(".DOC")))  {
                files.add(fileEntry);
            }

        }
        File[] filesArr = new File[files.size()];
        filesArr = files.toArray(filesArr);
        return filesArr;
    }

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
