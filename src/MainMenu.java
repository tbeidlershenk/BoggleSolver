import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.HashMap;

public class MainMenu extends JFrame implements ActionListener {

    private HashMap<String, Integer> settings;
    private int height, width;
    private JButton playGame;
    private JButton viewSettings;

    public MainMenu () {
                
        this.settings = Reader.readOptions();
        this.height = settings.get("height");
        this.width = settings.get("width");

        this.setSize(height, width);
        this.setTitle("Main Menu");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        addComponents();
        this.setVisible(true);
    }

    private void addComponents () {
        int buttonWidth = 200;
        int buttonHeight = 80;

        playGame = new JButton("Play Game");
        playGame.addActionListener(this);
        playGame.setBounds((width-buttonWidth)/2, (height-buttonHeight)/2, buttonWidth, buttonHeight);
        viewSettings = new JButton("Settings");
        viewSettings.addActionListener(this);
        viewSettings.setBounds(playGame.getX(), playGame.getY()+buttonWidth/2, buttonWidth, buttonHeight);

        this.add(playGame);
        this.add(viewSettings);

    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("pressed");
        if (e.getSource() == playGame) {
            this.dispose();
            Boggle bog = new Boggle(6, 3);
            BoggleGUI game = new BoggleGUI(bog);
        }
    }
    
}
