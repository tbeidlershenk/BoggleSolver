import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;
//test
public class Reader {

    private static String wordfile = "files/words.txt";
    private static String freqfile = "files/frequencies.txt";
    private static FileReader fr;
    private static BufferedReader br;

    // Read words.txt to return a WordList of words of minLength or greater
    public static WordList readWords (int minLength) {
        WordList list = new WordList();
        try {
            fr = new FileReader(wordfile);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                if (line.length() >= minLength) 
                    list.add(line.toLowerCase());
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
        return list;
    }
    
    // Read frequencies.txt to return an array of chars as the board of size * size
    public static char[] readFrequencies (int size) {
        
        char [] result = new char [size * size]; 
        Random rand = new Random();
        
        try {

            int count = 0;
            fr = new FileReader(freqfile);
            br = new BufferedReader(fr);
            String line = br.readLine();
            
            while (count < result.length) {
                
                String[] split = line.split(",");
                int num = rand.nextInt(0, split.length);
                result[count] = split[num].charAt(0);
                
                // If end of dice, restart
                if (count > 14) {
                    fr = new FileReader(freqfile);
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

    public static HashMap<String, String> readOptions () {
        HashMap settings = new HashMap<String, String>();
        return null;
    }
}
