import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;

public class Reader {

    private static String words = "files/oxford_dict.txt";
    private static String frequencies = "files/frequencies.txt";
    private static String options = "files/options.txt";
    private static String allowed = "n. —n. v. —v. adj. —adj. adv. —adv. prep. —prep. var. —var. colloq. —colloq.";
    private static FileReader fr;
    private static BufferedReader br;

    public static HashMap<String, String> readWords(int minLength) {
        HashMap<String, String> dict = new HashMap<>();
        try {
            fr = new FileReader(words);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                String[] def = line.split("  ", 2);
                String word = def[0].toLowerCase();
                boolean include = true;
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) > 48 && word.charAt(i) < 58) {
                        System.out.println(word);
                        word = word.substring(0, i).toLowerCase();
                        System.out.println(word);
                        break;
                    }
                    if (word.charAt(i) > 122 || word.charAt(i) < 97) {
                        include = false;
                        break;
                    }
                }
                if (def.length > 1) {
                    //System.out.println(def[1].split(" ")[0].toLowerCase());
                    if (allowed.contains(def[1].split(" ")[0].toLowerCase()) && def[0].length() >= minLength && include) {
                        dict.put(word, def[1]);
                    }
                }
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error");
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
}
