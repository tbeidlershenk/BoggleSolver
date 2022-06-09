import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentAdapter;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;
import java.util.HashMap;

public class MainMenu extends JFrame implements ActionListener {

    private HashMap<String, Integer> settings;
    private int height, width;
    private JLabel title;
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
        this.setBackground(new Color(100,100,100));
        this.setResizable(false);

        addComponents();
        this.setVisible(true);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
              System.out.println("Resized to " + e.getComponent().getSize());
            }
            @Override
            public void componentMoved(ComponentEvent e) {
              System.out.println("Moved to " + e.getComponent().getLocation());
            }
          });
        
    
    }

    private void addComponents () {
        int buttonWidth = 200;
        int buttonHeight = 80;
        int fontSize = 40;

        title = new JLabel("Boggle");
        title.setFont(new Font("Serif", Font.PLAIN, fontSize));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds((width-buttonWidth)/2, height/6, buttonWidth, buttonHeight);
        playGame = new JButton("Play Game");
        playGame.addActionListener(this);
        playGame.setBounds(title.getX(), title.getY()+buttonWidth/2, buttonWidth, buttonHeight);
        viewSettings = new JButton("Settings");
        viewSettings.addActionListener(this);
        viewSettings.setBounds(playGame.getX(), playGame.getY()+buttonWidth/2, buttonWidth, buttonHeight);

        this.add(title);
        this.add(playGame);
        this.add(viewSettings);

    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("pressed");
        if (e.getSource() == playGame) {
            this.dispose();
            Boggle bog = new Boggle(6, 3);
            BoggleGUI game = new BoggleGUI(bog);
        } else if (e.getSource() == viewSettings) {
            this.dispose();
            Settings ops = new Settings();
        }
    }  

}
