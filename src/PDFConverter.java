import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by rakeshh91 on 4/22/2017.
 */
public class PDFConverter {

    public static void selectPDFFiles(){

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF","pdf");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File[] Files=chooser.getSelectedFiles();
            System.out.println("Please wait...");
            for( int i=0;i<Files.length;i++){
                convertPDFToText(Files[i].toString(),"textfrompdf"+i+".txt");
            }
            System.out.println("Conversion complete");
        }


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
