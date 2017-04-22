import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakeshh91 on 4/22/2017.
 */
public class PDFConverter {

    public static void convertPDFFiles(String filesPath,String destPath){
        final File folder = new File(filesPath);
        File[] files = listPDFFilesFromFolder(folder);
            for( int i=0;i<files.length;i++) {
                String desc = destPath;
                    if (files[i].toString().endsWith(".pdf")) {
                        desc = desc + files[i].getName().replace(".pdf", ".txt");
                    } else {
                        desc = desc + files[i].getName().replace(".PDF", ".txt");
                    }
                    convertPDFToText(files[i].toString(), desc);
            }
            System.out.println("Conversion complete");
    }

    public static File[] listPDFFilesFromFolder(final File folder) {
        List<File> files= new ArrayList<File>();
        for (final File fileEntry : folder.listFiles())
        {
            if (fileEntry.toString().endsWith(".pdf") || fileEntry.toString().endsWith(".PDF")) {
                files.add(fileEntry);
            }

        }
        File[] filesArr = new File[files.size()];
        filesArr = files.toArray(filesArr);
        return filesArr;
    }



    public static void convertPDFToText(String src,String desc){
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
}
