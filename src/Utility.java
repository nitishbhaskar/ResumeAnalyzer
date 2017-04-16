import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Nitish on 4/15/2017.
 */
public class Utility {
    public static String currentFile = "";
    public static HashMap<String, Integer> requiredSkills = new HashMap<>();

    public static Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Pattern VALID_URL = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);

    public static String[] splitWithSpace(String inputString) {
        return inputString.toString().trim().split(" ");
    }

    public static String[] splitWithSpacesAndTabs(String value) {
        return value.toString().replace(",","").trim().split("\\s+");
    }

    //
    static String VALID_PHONENUMBER = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]" +
            "|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1" +
            "|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1" +
            "|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";

    public static void addSkills(JSONArray skillsJSON) throws JSONException {
        for (int i = 0; i < skillsJSON.length(); i++) {
            requiredSkills.put(skillsJSON.get(i).toString().toLowerCase(),1);
        }
    }

    public static Pattern VALID_GPA = Pattern.compile("([^ ]*)([0-3]\\.\\d?\\d|4\\.0)");
}