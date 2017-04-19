/**
 * Created by goswa on 4/19/2017.
 */
import java.util.*;
import java.io.*;
public class Dictionary{
    private static HashSet <String> dicSet = new HashSet<String>();
    public static void populate() throws FileNotFoundException {

//read the dictionary file
        Scanner dicIN = new Scanner(new File(Utility.DICTIONARY));

//Loop through dictionary and store them into set. set all chars to lower case just in case because java is case sensitive
        while(dicIN.hasNext())
        {
            String dicWord = dicIN.next();
            dicSet.add(dicWord.toLowerCase());
        }
    }

    public static boolean contains(String word){
        if(dicSet.contains(word))
            return true;
        return false;
    }
}
