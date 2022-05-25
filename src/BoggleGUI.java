import java.util.HashMap;
import javax.swing.JFrame;

public class BoggleGUI extends JFrame {

    private Boggle game;
    private HashMap<String, Integer> settings;
    private int height, width;

    public BoggleGUI (Boggle game) {
        
        this.game = game;
        this.settings = Reader.readOptions();
        this.height = settings.get("height");
        this.width = settings.get("width");
        
        this.setSize(height, width);
        this.setTitle("Boggle Game");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    
}
