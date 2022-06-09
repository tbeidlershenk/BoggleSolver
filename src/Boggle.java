import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;

public class Boggle {
    
    private int minLength = 3;
    private int size;
    private int squares;
    private char[][] grid;
    private char[] letters;
    private HashMap<String, String> dict;
    private WordList words;
    private int wordSize;
    private String strWords;

    public Boggle (int size, int wordSize) {
        this.size = size;
        this.wordSize = wordSize;
        squares = size * size;
        letters = new char[squares];
        grid = new char[size][size];
        dict = Reader.readWords(wordSize);
        words = new WordList();
        for (String key: dict.keySet()) {
            words.add(key);
        }
        System.out.println(words.getLength());
    }

    // First scanning of WordList
    // Stub method for WordList method removeImpossibleWords()
    public void removeImpossibleWords () {
        printWords();
        System.out.println(words.getLength());
        words.removeImpossibleWords(letters);
    }

    // Second scanning of list
    // Stub method for WordList method findWords()
    public void findWords () {
        words.findWords(grid);
        strWords = words.toString();
    }


    public void rescramble () {
        
        char[] scramble = Reader.readFrequencies(size); 
        
        for (int i = 0; i < scramble.length; i++) {
            letters[i] = scramble[i];
            grid[i / size][i % size] = scramble[i];
        }
    }

    public String getDefinition(String key) {
        System.out.println(dict.get(key).length());
        String definition = dict.get(key).split("2")[0]
            .substring(0, Math.min(60, dict.get(key).split("2")[0].length()));
        return definition;
    }

    public boolean checkWord(String key) {
        if (strWords.contains(key)) {
            return true;
        }
        return false;
    }

    public static char[] sortLetters (char[] board) {
        Arrays.sort(board);
        return board;
    }

    public void printBoggle () {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printWords () {
        char[] sorted = Boggle.sortLetters(letters);
        for (char c: sorted) {
            System.out.print(c + " ");
        }
    }

    public void printSize () {
        System.out.println(words.getLength());
    }

    public WordList getWords() {
        return words;
    }

    public char[] getLetters() {
        return letters;
    }

    public char[][] getGrid() {
        return grid;
    }
}
