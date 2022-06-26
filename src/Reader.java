import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;
import java.lang.StringBuilder;

public class Reader {

    private static String words = "files/oxford_dict.txt";
    private static String frequencies = "files/frequencies.txt";
    private static String options = "files/options.txt";
    private static String allowed = "n. —n. v. —v. adj. —adj. adv. —adv. prep. —prep. var. —var. pl. -pl.";
    private static FileReader fr;
    private static BufferedReader br;

    public static HashMap<String, String> readWords(int minLength) {
        HashMap<String, String> dict = new HashMap<>();
        try {
            fr = new FileReader(words);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {

                //System.out.println(line);
                String[] splitLine = line.split("  ");
                //System.out.println(splitLine.length);
                String word = splitLine[0].toLowerCase();;
                boolean include = true;

                // iterate through the word (make sure it is a single word)
                for (int i = 0; i < word.length(); i++) {
                    
                    // current character is between '0' and '9'
                    if (Reader.isDigit(word.charAt(i))) {
                        splitLine[0] = word.substring(0,i);
                        word = word.substring(0,i);
                        break;
                    }
                    // current character is not between 'a' and 'z'
                    if (!Reader.isLetter(word.charAt(i))) {
                        include = false;
                        break;
                    }
                }
                if (splitLine.length > 1) {
                    
                    boolean legalType = allowed.contains(splitLine[1].split(" ")[0].toLowerCase());
                    boolean legalSize = splitLine[0].length() >= minLength;

                    if (legalType && legalSize && include) {
                        dict.put(word, splitLine[1]);
                    }
                }
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error parsing file");
        }
        return dict;
        

    }
    // Read frequencies.txt to return an array of chars as the board of size * size
    public static char[] readFrequencies (int size) {
        
        char [] result = new char [size * size]; 
        Random rand = new Random();
        
        try {

            int count = 0;
            fr = new FileReader(frequencies);
            br = new BufferedReader(fr);
            String line = br.readLine();
            
            while (count < result.length) {
                
                String[] split = line.split(",");
                int num = rand.nextInt(0, split.length);
                result[count] = split[num].charAt(0);
                
                // If end of dice, restart
                if (count > 14) {
                    fr = new FileReader(frequencies);
                    br = new BufferedReader(fr);
                }
                
                line = br.readLine();
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        return result;
    }

    public static HashMap<String, Integer> readOptions () {
        
        HashMap settings = new HashMap<String, Integer>();

        try {
            
            fr = new FileReader(options);
            br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
                
                String[] args = line.split("=");
                String key = args[0];
                Integer value = Integer.parseInt(args[1]);
                System.out.println(key + "=" + value);
                settings.put(key, value);
                line = br.readLine();
            }
        
        } catch (IOException e) {
            System.out.println("Error opening file");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing String -> Integer");
        }
        return settings;
    }

    public static String getType (String abreviation) {
        if (abreviation.equals("n.") || abreviation.equals("—n.")) {
            return "noun";
        } else if (abreviation.equals("v.") || abreviation.equals("—v.")) {
            return "verb";
        } else if (abreviation.equals("adj.") || abreviation.equals("—adj.")) {
            return "adjective";
        } else if (abreviation.equals("adv.") || abreviation.equals("—adv.")) {
            return "adverb";
        } else if (abreviation.equals("prep.") || abreviation.equals("—prep.")) {
            return "preposition";
        } else return "";
    }

    public static boolean isDigit (char c) {
        if (c > 48 && c < 58) {
            return true;
        }
        return false;
    }

    public static boolean isLetter (char c) {
        if (c >= 97 && c <= 122) {
            return true;
        }
        return false;
    }
}
