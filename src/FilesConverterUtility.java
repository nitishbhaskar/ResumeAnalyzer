import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.*;


/**
 * Created by rakeshh91 on 4/22/2017.
 */
public class FilesConverterUtility {

    public static void convertFiles(String filesPath,String destPath){
        final File folder = new File(filesPath);
        for (final File fileEntry : folder.listFiles())
        {
            if (fileEntry.toString().endsWith(".pdf") || fileEntry.toString().endsWith(".PDF")) {
                convertPDFToText(fileEntry,destPath);
            }
            else  if (fileEntry.toString().endsWith(".docx") || fileEntry.toString().endsWith(".DOCX") || (fileEntry.toString().endsWith(".doc") || fileEntry.toString().endsWith(".DOC")))  {
                convertDocxToText(fileEntry,destPath);
            }
            else if(fileEntry.toString().endsWith(".txt")|| fileEntry.toString().endsWith(".TXT")){
                moveTxtToDest(fileEntry,destPath);
            }

        }
            System.out.println("Conversion complete");
    }


    private static void convertPDFToText(File file, String destPath){
        String desc = destPath;
        if (file.toString().endsWith(".pdf")) {
            desc = desc + file.getName().replace(".pdf", ".txt");
        } else {
            desc = desc + file.getName().replace(".PDF", ".txt");
        }
        writePdfToTextDest(file.toString(), desc);
    }

    private static void convertDocxToText(File file, String destPath){
        String desc = destPath;
        if (file.toString().endsWith(".docx")) {
            desc = desc + file.getName().replace(".docx", ".txt");
        } else if(file.toString().endsWith(".DOCX")) {
            desc = desc + file.getName().replace(".DOCX", ".txt");
        }else if(file.toString().endsWith(".DOC")) {
            desc = desc + file.getName().replace(".DOC", ".txt");
        }else if(file.toString().endsWith(".doc")) {
            desc = desc + file.getName().replace(".doc", ".txt");
        }
        writeWordToTextDest(file.toString(), desc);
    }



    private static void writePdfToTextDest(String src,String desc){
        try{
            //create file writer
            FileWriter fw=new FileWriter(desc);
            //create buffered writer
            BufferedWriter bw=new BufferedWriter(fw);
            //create pdf reader
            PdfReader pr=new PdfReader(src);
            //get the number of pages in the document
            int pNum=pr.getNumberOfPages();
            //extract text from each page and write it to the output text file
            for(int page=1;page<=pNum;page++){
                String text= PdfTextExtractor.getTextFromPage(pr, page);
                bw.write(text);
                bw.newLine();

            }
            bw.flush();
            bw.close();

        }catch(Exception e){e.printStackTrace();}

    }

    private static void writeWordToTextDest(String src, String desc){
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

    private static void moveTxtToDest(File file,String destPath){
        try {
            FileUtils.moveFileToDirectory(file,new File(destPath),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
