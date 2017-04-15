import java.util.regex.Pattern;

/**
 * Created by Nitish on 4/15/2017.
 */
public class Utility {
    public static Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Pattern VALID_URL = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);

    public static String[] splitWithSpace(String inputString) {
        return inputString.toString().trim().split(" ");
    }
}
