import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Nitish on 4/15/2017.
 */
public class Utility {


    // Data Section ----------------------------------------------------------------
    //
    public static String currentFile = "";
    public static int currentLineCount = 0;
    public static int LOCATION_DEPTH = 2;
    public static String currentResumeSection;
    public static String DICTIONARY = "./data/words.txt";
    public static boolean nameFound  = false;
    public static boolean locationFound  = false;
    public static HashMap<String, Integer> requiredSkills = new HashMap<>();
    public static HashMap<String,String> degrees = new HashMap<>();
    public static HashMap<String, Integer> USStates = new HashMap<>();
    public static DecimalFormat df = new DecimalFormat("#.00");

    public static Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Pattern VALID_URL = Pattern.compile("^(https?|ftp|file)://?|www[.][-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);

    public static String[] splitWithCustomized(String inputString, String criteria) {
        return inputString.toString().trim().split(criteria);
    }

    public static String[] splitWithSpacesAndTabs(String value) {
        return value.toString().replace(",","").trim().split("\\s+");
    }

    //
    static String VALID_PHONENUMBER = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]" +
            "|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1" +
            "|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1" +
            "|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";

    public static void addDegrees(JSONArray degreeJSON) throws JSONException {
        for (int i = 0; i < degreeJSON.length(); i++) {
            degrees.put(degreeJSON.get(i).toString(), checkDegree(degreeJSON.get(i).toString()) );
        }
    }

    public static String checkDegree(String degree){
        if(degree.startsWith("M")){
            return "Master's";
        }else if(degree.startsWith("B")){
            return "Bachelor's";
        }else if(degree.startsWith("D") || degree.toLowerCase().equals("phd")){
            return "Doctrate";
        }else{
            return "";
        }
    }

    public static Pattern VALID_GPA = Pattern.compile("([^ ]*)([0-3]\\.\\d?\\d|4\\.0)"); //format 3.78
    public static Pattern VALID_GPA_SEPARATOR = Pattern.compile("([0-3]\\.\\d?\\d|4\\.0)"); //format 3.5/4.0


    // Utility Functions --------------------------------------------------------------------------
    //
    public static void incrementLineCountOfThisFile(){
        currentLineCount++;
    }

    public static void addSkills(JSONArray skillsJSON) throws JSONException {
        for (int i = 0; i < skillsJSON.length(); i++) {
            requiredSkills.put(skillsJSON.get(i).toString().toLowerCase(),1);
        }
    }

    public static HashSet<String> USStatesAcronyms = new HashSet<>();
    public static HashSet<String> USStatesFullNames = new HashSet<>();
    public static void populateStatesData(){

        for(Map.Entry<String, String> entry : mapOfUSStates.entrySet()){
            USStatesAcronyms.add(entry.getKey());
            USStatesFullNames.add(entry.getValue());
        }
    }

    // US States Names
    public final static HashMap<String, String> mapOfUSStates = new HashMap<String, String>() {
        {
            put("AL", "Alabama");
            put("AK", "Alaska");
            put("AZ", "Arizona");
            put("AR", "Arkansas");
            put("CA", "California");
            put("CO", "Colorado");
            put("CT", "Connecticut");
            put("DE", "Delaware");
            put("DC", "Dist of Columbia");
            put("FL", "Florida");
            put("GA", "Georgia");
            put("HI", "Hawaii");
            put("ID", "Idaho");
            put("IL", "Illinois");
            put("IN", "Indiana");
            put("IA", "Iowa");
            put("KS", "Kansas");
            put("KY", "Kentucky");
            put("LA", "Louisiana");
            put("ME", "Maine");
            put("MD", "Maryland");
            put("MA", "Massachusetts");
            put("MI", "Michigan");
            put("MN", "Minnesota");
            put("MS", "Mississippi");
            put("MO", "Missouri");
            put("MT", "Montana");
            put("NE", "Nebraska");
            put("NV", "Nevada");
            put("NH", "New Hampshire");
            put("NJ", "New Jersey");
            put("NM", "New Mexico");
            put("NY", "New York");
            put("NC", "North Carolina");
            put("ND", "North Dakota");
            put("OH", "Ohio");
            put("OK", "Oklahoma");
            put("OR", "Oregon");
            put("PA", "Pennsylvania");
            put("RI", "Rhode Island");
            put("SC", "South Carolina");
            put("SD", "South Dakota");
            put("TN", "Tennessee");
            put("TX", "Texas");
            put("UT", "Utah");
            put("VT", "Vermont");
            put("VA", "Virginia");
            put("WA", "Washington");
            put("WV", "West Virginia");
            put("WI", "Wisconsin");
            put("WY", "Wyoming");
        }
    };



}
