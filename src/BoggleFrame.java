import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author tobiasbeidlershenk
 */
public class BoggleFrame extends  JFrame {

    private JButton shuffleButton, gameStatsButton;
    private JProgressBar progressBar;
    
    private JLabel playerListLabel, computerListLabel;
    private JLabel playerWordsLabel, computerWordsLabel;
    private JLabel playerPointsLabel, computerPointsLabel;
    private JLabel percentageLabel;
    private JLabel timerLabel;
    private JLabel definitionLabel;

    private JList<String> playerList, computerList;
    private JScrollPane playerListScrollPane, computerListScrollPane;
    private DefaultListModel playerListModel, computerListModel;

    private JPanel topPanel, gridPanel, parallelListsPanel, bottomPanel;

    private JToggleButton[][] grid;
    private Boggle bog;
    private String currentWord = "";
    private int minSize;
    private int gridSize;

    public BoggleFrame(int gridSize, int minSize) {
        this.gridSize = gridSize;
        this.minSize = minSize;
        bog = new Boggle(gridSize, minSize);

        this.setResizable(false);
        initComponents(gridSize);
        runGame(gridSize);
    }

    @SuppressWarnings("unchecked")
    private void initComponents(int gridSize) {

        // panels
        topPanel = new JPanel();
        gridPanel = new JPanel();
        parallelListsPanel = new JPanel();
        bottomPanel = new JPanel();

        // buttons/progress bar
        shuffleButton = new JButton();
        shuffleButton.setText("Shuffle");
        shuffleButton.setToolTipText("");
        // action listener code: shuffleButton();
        shuffleButton.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(java.awt.event.ActionEvent evt) { shuffleButton(evt);}});

        gameStatsButton = new JButton();
        gameStatsButton.setText("Game Stats");
        // action listener code: statsButton();
        gameStatsButton.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(java.awt.event.ActionEvent evt) { statsButton(evt); }});

        progressBar = new JProgressBar();
        
        // player word list components
        playerListScrollPane = new JScrollPane();
        playerListModel = new DefaultListModel();
        
        playerList = new JList<>(playerListModel);
        playerList.addListSelectionListener(new javax.swing.event.ListSelectionListener() { public void valueChanged(javax.swing.event.ListSelectionEvent e) { wordSelected(e);}});
        playerListScrollPane.setViewportView(playerList);
        
        playerListLabel = new JLabel();
        playerListLabel.setBackground(new java.awt.Color(51, 51, 255));
        playerListLabel.setFont(new java.awt.Font("Krungthep", 0, 18)); // NOI18N
        playerListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerListLabel.setText("My Words");
        playerListLabel.setToolTipText("");

        playerWordsLabel = new JLabel();
        playerWordsLabel.setText("Words: 0");

        playerPointsLabel = new JLabel();
        playerPointsLabel.setText("Points: 0");

        // computer word list components
        computerListScrollPane = new JScrollPane();
        computerListModel = new DefaultListModel();
        
        computerList = new JList<>(computerListModel);
        computerList.addListSelectionListener(new javax.swing.event.ListSelectionListener() { public void valueChanged(javax.swing.event.ListSelectionEvent e) { wordSelected(e);}});
        computerListScrollPane.setViewportView(computerList);
        
        computerListLabel = new JLabel();
        computerListLabel.setFont(new java.awt.Font("Krungthep", 0, 18)); // NOI18N
        computerListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        computerListLabel.setText("All Words");

        computerWordsLabel = new JLabel();
        computerWordsLabel.setText("Words: 0");

        computerPointsLabel = new JLabel();
        computerPointsLabel.setText("Points: 0");

        percentageLabel = new JLabel();
        percentageLabel.setText("Word Percentage: 0%");

        
        // definitionLabel: word + definition
        definitionLabel = new JLabel("",  SwingConstants.LEFT);
        definitionLabel.setVerticalAlignment( SwingConstants.TOP);
        definitionLabel.setFont(new java.awt.Font("Krungthep", 0, 12)); // NOI18N

        // timer label
        timerLabel = new JLabel();
        timerLabel.setText("Time Left: 0 min 0 sec");

        initGameGrid();
        setLayouts();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 255));

        pack();
    }// </editor-fold>                        

    // sets the layouts
    private void setLayouts () {
        GroupLayout topPanelLayout = new GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addComponent(shuffleButton)
                .addGap(24, 24, 24)
                .addComponent(progressBar,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addComponent(shuffleButton,  GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressBar,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridPanel.setBorder(new javax.swing.border.MatteBorder(null));
        gridPanel.setPreferredSize(new java.awt.Dimension(400, 400));
        int count = 0; // testing
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j].setText(""+count);
                grid[i][j].setPreferredSize(new java.awt.Dimension(96, 96));
                grid[i][j].addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        letterToggled(evt);
                    }
                });
                count++;
            }
        }

        GroupLayout gridPanelLayout = new   GroupLayout(gridPanel);
        gridPanel.setLayout(gridPanelLayout);
        gridPanelLayout.setHorizontalGroup(
            gridPanelLayout.createParallelGroup(  GroupLayout.Alignment.LEADING)
            .addGroup(gridPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(gridPanelLayout.createParallelGroup(  GroupLayout.Alignment.TRAILING)
                    .addComponent(grid[3][0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[2][0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[1][0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[0][0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gridPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(gridPanelLayout.createSequentialGroup()
                        .addComponent(grid[0][1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid[0][2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid[0][3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gridPanelLayout.createSequentialGroup()
                        .addComponent(grid[1][1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid[1][2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid[1][3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gridPanelLayout.createSequentialGroup()
                        .addComponent(grid[2][1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid[2][2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid[2][3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gridPanelLayout.createSequentialGroup()
                        .addComponent(grid[3][1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid[3][2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid[3][3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        gridPanelLayout.setVerticalGroup(
            gridPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gridPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(gridPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(grid[0][0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[0][1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[0][2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[0][3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gridPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(grid[1][0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[1][1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[1][2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[1][3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gridPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(grid[2][0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[2][1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[2][2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[2][3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gridPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(grid[3][0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[3][1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[3][2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(grid[3][3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout parallelListsPanelLayout = new javax.swing.GroupLayout(parallelListsPanel);
        parallelListsPanel.setLayout(parallelListsPanelLayout);
        parallelListsPanelLayout.setHorizontalGroup(
            parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(parallelListsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(percentageLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(parallelListsPanelLayout.createSequentialGroup()
                        .addGroup(parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(playerPointsLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(playerWordsLabel, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(computerWordsLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(computerPointsLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(GroupLayout.Alignment.TRAILING, parallelListsPanelLayout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addComponent(playerListScrollPane, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(computerListScrollPane, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(GroupLayout.Alignment.TRAILING, parallelListsPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(playerListLabel, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(computerListLabel, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)))
                .addContainerGap())
        );
        
        parallelListsPanelLayout.setVerticalGroup(
            parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(parallelListsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(playerListLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(computerListLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(computerListScrollPane, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                    .addComponent(playerListScrollPane))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(computerWordsLabel)
                    .addComponent(playerWordsLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelListsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(playerPointsLabel)
                    .addComponent(computerPointsLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(percentageLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bottomPanel.setBorder(BorderFactory.createEtchedBorder());


        GroupLayout bottomPanelLayout = new GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(definitionLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timerLabel, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gameStatsButton)
                .addGap(12, 12, 12))
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(definitionLabel, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(gameStatsButton)
                    .addComponent(timerLabel))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(bottomPanel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(topPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(37, 37, 37))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gridPanel, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(playerListLabel, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(computerListLabel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(parallelListsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(25, 25, 25))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(300, 300, 300)
                    .addGap(300, 300, 300)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(computerListLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(playerListLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(parallelListsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(gridPanel, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(222, 222, 222)
                    .addContainerGap(292, Short.MAX_VALUE)))
        );

        gridPanel.getAccessibleContext().setAccessibleName("");

    }
    
    // inits the game grid
    private void initGameGrid () {
        grid = new  JToggleButton[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new  JToggleButton();
            }
        }
    }
    
    // Handle Shuffle button
    private void shuffleButton(java.awt.event.ActionEvent evt) { 
        bog = new Boggle(gridSize, minSize); 
        initComponents(gridSize);
        runGame(gridSize);
        setUpValues();    
        // TODO fix method (does not work)                            
    }                                        

    // Handle Stats button
    private void statsButton(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here
    }                                        

    // Handle Letter toggling
    private void letterToggled(java.awt.event.ActionEvent evt) {
        String sourceText = (evt.getSource().toString());
        //System.out.println(sourceText);
        String letter = sourceText.substring(sourceText.length()-2, sourceText.length()-1);
        System.out.println(letter);
        //if (currentWord.length() >= 3)
        JToggleButton copy = (JToggleButton) evt.getSource();
        if (copy.isSelected()) {
            currentWord += letter;
        } else {
            currentWord = currentWord.substring(0, currentWord.length()-1);
        }
        System.out.println(currentWord);
        if (currentWord.length() >= minSize && bog.checkWord(currentWord)) {
            playerListModel.addElement(currentWord);
            updateValues(currentWord);

        }
    }  
    
    // Handle word selection from either word list
    private void wordSelected(javax.swing.event.ListSelectionEvent e) {
        System.out.println("new event\n"+e.getSource().hashCode());
        String fullText;
        if (e.getSource().hashCode() == playerList.hashCode()) {
            String[] definition = bog.getDefinition(playerList.getSelectedValue());
            fullText = "<html>"+playerList.getSelectedValue() + ": " + definition[0]+"<br>"+definition[1]+"<html>";
        } else {
            String[] definition = bog.getDefinition(computerList.getSelectedValue());
            fullText = "<html>"+computerList.getSelectedValue() + ": " + definition[0]+"<br>"+definition[1]+"<html>";
        }
        definitionLabel.setText(fullText);
    }   
    
    // Update player word count, points, percentage
    private void updateValues (String word) {
        int words = Integer.parseInt(playerWordsLabel.getText().split(" ")[1]) + 1;
        int points = Integer.parseInt(playerPointsLabel.getText().split(" ")[1]) + bog.addPoints(word);;
        double percentage = 100 * ((double) words) / bog.getWords().getLength();
        playerWordsLabel.setText("Words: " + words);
        playerPointsLabel.setText("Points: " + points);
        percentageLabel.setText("Word Percentage: " + (int) percentage);
    }

    // Set up computer word count and points
    private void setUpValues () {
        computerWordsLabel.setText("Words: " + bog.getWords().getLength());
        computerPointsLabel.setText("Points: " + bog.countPoints());
    }

    // adds new scramble to game grid
    private void addLetters () {
        char[][] letterGrid = bog.getGrid();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j].setText(""+letterGrid[i][j]);
                grid[i][j].setFont(new java.awt.Font("Krungthep", 0, 18)); // NOI18N
            }
        }
    }

    // add found words to computer list
    private void addComputerWords () {
        WordList words = bog.getWords();
        String[] arr = words.toArray();
        for (int i = 0; i < bog.getWords().getLength(); i++) {
            computerListModel.addElement(arr[i]);
            setUpValues();
        }
    }

    // runs the game
    private void runGame(int gridSize) {        
        
        bog.rescramble();
        bog.printWords(); //
        bog.printSize(); //
        bog.removeImpossibleWords();
        bog.printSize(); //
        bog.findWords();
        bog.printSize(); //
        bog.printBoggle(); //

        addLetters();
        addComputerWords();
        
    }
    // I don't think I need this main method...
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BoggleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BoggleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BoggleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BoggleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BoggleFrame(4, 3).setVisible(true);
                
            }
        });
    }

}

