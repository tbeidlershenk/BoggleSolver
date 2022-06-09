import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;

public class Settings extends JFrame {
    
    private int height;
    private int width;
    private HashMap<String, Integer> settings;
    private JLabel[] labels;
    private JTextField[] options;

    public Settings () {
        
        this.settings = Reader.readOptions();
        this.options = new JTextField[settings.size()];
        this.labels = new JLabel[settings.size()];
        this.height = settings.get("height");
        this.width = settings.get("width");
        
        this.setSize(height, width);
        this.setTitle("Boggle Game");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponents();
        this.setVisible(true);

    }

    public void addComponents() {

        int labelWidth = 100, labelHeight = 50;
        int textWidth = 50, textHeight = 50;
        int titleWidth = 200, titleHeight = 80;

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Serif", Font.PLAIN, 40));
        title.setBounds((width-titleWidth)/2, height/8, titleWidth, titleHeight);
        title.setHorizontalAlignment(JLabel.CENTER);
        this.add(title);
        
        Object[] keys = settings.keySet().toArray();
        String[] strs = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            strs[i] = (String) keys[i];
        }
        Arrays.sort(strs);
        int count = 1;
        for (int i = keys.length-1; i >= 0; i--) {
            count++;
            labels[i] = new JLabel(strs[i]);
            labels[i].setFont(new Font("Serif", Font.PLAIN, 20));
            labels[i].setBounds((width-labelWidth)/3, title.getY()+ labelHeight*(count), labelWidth, labelHeight);
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            this.add(labels[i]);
            options[i] = new JTextField(settings.get(keys[i]));
            System.out.println(settings.get(keys[i]));
            options[i].setFont(new Font("Serif", Font.PLAIN, 14));
            options[i].setBounds(2*(width-textWidth)/3, title.getY()+ textHeight*(count), textWidth, textHeight);
            System.out.println(options[i].getBounds().toString());
            this.add(options[i]);

        }

    }
}
