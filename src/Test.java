public class Test {
    
    public static void main (String [] args) {
        
        Boggle bog = new Boggle(4, 3);
        bog.rescramble();
        bog.printBoggle();
        bog.printWords();
        bog.printSize();
        bog.removeImpossibleWords();
        bog.printSize();
        bog.findWords();
        bog.printSize();
        bog.printBoggle();

        bog.getWords().printList();
        bog.printBoggle();
        System.out.println(bog.getWords().getLength());

    }
}