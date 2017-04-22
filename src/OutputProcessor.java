import org.apache.hadoop.util.hash.Hash;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by goswa on 4/19/2017.
 */
public class OutputProcessor {

    private final String outPutFileName;
    private final String inPutFileName;
    private String name, location, score, gpa;
    private List<String> degrees, links, skills;
    HashMap<String, Integer> excelLocation;
    public OutputProcessor(String outPutFileName, String inPutFileName){
        StringBuffer strBuf = new StringBuffer("./");
        strBuf.append(outPutFileName);
        strBuf.append("/Output.xlsx");
        String fileSpec = strBuf.toString();
        this.outPutFileName = fileSpec;
        this.inPutFileName = inPutFileName;
        System.out.println(this.outPutFileName);

        excelLocation = new HashMap<>();
        excelLocation.put("name", 0);
        excelLocation.put("location", 1);
        excelLocation.put("email", 2);
        excelLocation.put("phone", 3);
        excelLocation.put("score", 4);
        excelLocation.put("skill", 5);
        excelLocation.put("degree", 6);
        excelLocation.put("link", 7);
        excelLocation.put("gpa", 8);
    }

    public void writeresultsToFile() {

        try{

            File file = new File(outPutFileName);
            if (file.createNewFile()){
                System.out.println("File is created!");
            }else{
                System.out.println("File already exists.");
            }


            FileInputStream fisExcel = new FileInputStream(file);
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkbook = new XSSFWorkbook();
            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkbook.createSheet("Resume Data");
            int rownum = mySheet.getLastRowNum();

            BufferedReader br = new BufferedReader(new FileReader(inPutFileName));
            StringBuilder sb = new StringBuilder();


            Object[] extractedContent = new Object[Utility.EXTRACTED_VALUES_COUNT];

            HashSet<String> extractedLinks = new HashSet<>();
            HashSet<String> extractedSkills = new HashSet<>();
            HashSet<String> extractedDegrees = new HashSet<>();
            HashMap<String, HashSet<String>> multipleExtractedValues = new HashMap<>();
            multipleExtractedValues.put("link", extractedLinks);
            multipleExtractedValues.put("skill", extractedSkills);
            multipleExtractedValues.put("degree", extractedDegrees);
            String line = br.readLine();
            while (line != null) {
                System.out.println("Line Read-------------------------------------");

                sb.append(line);
                //line = br.readLine();
                if(line == null)
                    break;
                String[] resumeData = line.split("[|\\t]");
                for(String resumeString : resumeData)
                    System.out.print(resumeString + " ");
                System.out.print("\n");
                for(String eachResumeData : resumeData){
                    String[] eachContent = eachResumeData.split(":");  // Name:Satish Goswami
                    if(eachContent.length <2)                  // first (resume name)
                        continue;
                    String column = eachContent[0].toLowerCase().toString();   // Name
                    System.out.println(column);
                    // check if this is skills, degrees or links
                    if(multipleExtractedValues.containsKey(column))
                    {
                        multipleExtractedValues.get(column).add(eachContent[1].toString());
                        System.out.println("In Array Adding : " + eachContent[1].toString());
                        continue;
                    }

                    int index = excelLocation.get(column);
                    extractedContent[index] = eachContent[1].toString();
                }
                // after the line is read, also add the multiple values to Object array
                for(String eachKey: multipleExtractedValues.keySet()){
                    int index = excelLocation.get(eachKey);
                    if(multipleExtractedValues.get(eachKey) instanceof HashSet){
                        HashSet<String> values = new HashSet<>();
                        for(String eachVal : multipleExtractedValues.get(eachKey))
                            values.add(eachVal);
                       // values = multipleExtractedValues.get(eachKey);
                        extractedContent[index] = values;
                    }
                    else
                        extractedContent[index] = multipleExtractedValues.get(eachKey);
                    multipleExtractedValues.get(eachKey).clear();
                    System.out.println("Array Size ---  : " + ((HashSet)extractedContent[index]).size());
                }

                // put the above data exractedContent to the excel sheet
                Row row = mySheet.createRow(rownum++);
                populateExcel(extractedContent, row);
                // clear extractedContent
                for(int i = 0; i < extractedContent.length; i++)
                    extractedContent[i] = null;
                line = br.readLine();
            }

            String everything = sb.toString();

            // Finish the writing to workbook
            FileOutputStream fos = new FileOutputStream(outPutFileName);
            myWorkbook.write(fos);
            System.out.println("Output Written to Excel File");

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void populateExcel(Object[] extractedContent, Row row){

        // taking object array for a reason to parse later if on integer or string or boolean or double
        int cellNum = 0;
        for(int i = 0; i < extractedContent.length; i++){
            Cell cell = row.createCell(cellNum++);
            Object eachContent = extractedContent[i];
            String cellContent = "";
            if(eachContent != null){
                if(!(eachContent instanceof HashSet))
                    cellContent = (String)eachContent;
                else{
                    for(String eachSetValue : (HashSet<String>)eachContent){
                        cellContent += eachSetValue + ", ";
                        System.out.println(cellContent);
                    }
                }
            }
            cell.setCellValue(cellContent);
        }
    }


    public int getCellLocation(String key){
       return -1;
    }
}
