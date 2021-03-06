import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class menu implements ActionListener,  ChangeListener, java.io.Serializable{
    private JFrame frame;
    private Game match;
    static private menu men;
    private String gameMode;
    private String playerOption;
    private static int playCount;
    private static int gamesWon;
    private static double winPercentage;
    private int colorChooser;
    private Color[] colorNames = { Color.BLACK, Color.BLUE, Color.CYAN,
            Color.GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE,
            Color.PINK, Color.RED, Color.YELLOW };
    ButtonGroup gamemodeGroup;
    ButtonGroup playerGroup;
    private String currentFrame;
    int numofHollow;
    int numofMarbles;


    public menu() {
        createAndShowGUI("Menu");
    }

    public static void main(String[] args) {        //Call menu and read and Statistics from file
        men = new menu();
        ReadStats();
    }

    private void addAButton(JButton buttName, Container container) {            //Add Buttons
        buttName.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttName.setFont(new Font("Verdana", Font.PLAIN, 50));
        buttName.setPreferredSize(new Dimension(400, 75));
        container.add(buttName);
    }

    private static void addRadioButton(JRadioButton radio, Container container) {      //Add Radio Buttons
        radio.setAlignmentX(Component.CENTER_ALIGNMENT);
        radio.setFont(new Font("Verdana", Font.PLAIN, 20));
        radio.setPreferredSize(new Dimension(50, 75));
        container.add(radio);
    }

    private static void addlabel(String text, Container container, int size, Color col) {      //Add Labels
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Verdana", Font.BOLD, size));
        label.setForeground(col);
        container.add(label);
    }

    private static void addlabelforStats(String text, double measure, Container container, int size, Color col) {
        JLabel labelFStats = new JLabel(text + measure);
        labelFStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelFStats.setFont(new Font("Verdana", Font.BOLD, size));
        labelFStats.setForeground(col);
        container.add(labelFStats);
    }

    public void Menu(Container pane) {                              //Main Menu
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        ButtonHandler buttonHandle = new ButtonHandler();

        addlabel("MANCALA", pane, 100, colorNames[colorChooser]);

        JButton playGame = new JButton("Play Game");
        addAButton(playGame, pane);
        playGame.addActionListener(buttonHandle);

        JButton stats = new JButton("Statistics");
        addAButton(stats, pane);
        stats.addActionListener(buttonHandle);

        JButton DoNotPress = new JButton("DO NOT PRESS!");
        addAButton(DoNotPress, pane);
        DoNotPress.addActionListener(buttonHandle);

    }

    public void Setup(Container pane) {                             //Setup game screen
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        CheckBoxHandler Players = new CheckBoxHandler();
        playerGroup = new ButtonGroup();

        addlabel("Choose Players", pane, 50, Color.BLACK);      //Player options
        JRadioButton PVP = new JRadioButton("Player vs Player", true );
        playerOption = "PVP";
        addRadioButton(PVP, pane);
        PVP.addActionListener(Players);
        playerGroup.add(PVP);

        JRadioButton PVCE = new JRadioButton("Player vs Computer (EASY)");
        addRadioButton(PVCE, pane);
        PVCE.addActionListener(Players);
        playerGroup.add(PVCE);

        JRadioButton PVCH = new JRadioButton("Player vs Computer (HARD)");
        addRadioButton(PVCH, pane);
        PVCH.addActionListener(Players);
        playerGroup.add(PVCH);

        JRadioButton CVC = new JRadioButton("Computer vs Computer");
        addRadioButton(CVC, pane);
        CVC.addActionListener(Players);
        playerGroup.add(CVC);
        gameMode = "Capture";                           //Why?

        CheckBoxHandler gameMode = new CheckBoxHandler();                     //Game Mode Options
        addlabel("Game Mode", pane, 50, Color.BLACK);
        gamemodeGroup = new ButtonGroup();

        JRadioButton capture = new JRadioButton("Capture", true);
        addRadioButton(capture, pane);
        capture.addActionListener(gameMode);
        gamemodeGroup.add(capture);

        JRadioButton avalanche = new JRadioButton("Avalanche");
        addRadioButton(avalanche, pane);
        avalanche.addActionListener(gameMode);
        gamemodeGroup.add(avalanche);
                                                                            //Sliders for Hollows and Marbles
        JLabel sliderLabelHollow = new JLabel("# of Hollows per Player", JLabel.CENTER);
        JLabel sliderLabelMarbles = new JLabel("Starting amount of marbles in Hollow",JLabel.CENTER);

        sliderLabelHollow.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderLabelMarbles.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSlider slideHollow = new JSlider(2,8,8);
        numofHollow = 8;
        slideHollow.setName("HollowSlider");

        JSlider slideMarbles = new JSlider(1,14,4);
        numofMarbles= 4;
        slideMarbles.setName("MarbleSlider");
        slideHollow.addChangeListener(this);
        slideHollow.setPaintTicks(true);
        slideHollow.setMajorTickSpacing(1);
        slideHollow.setPaintLabels(true);
        slideMarbles.addChangeListener(this);
        slideMarbles.setPaintTicks(true);
        slideMarbles.setMajorTickSpacing(1);
        slideMarbles.setPaintLabels(true);


        pane.add(sliderLabelHollow);
        pane.add(slideHollow);
        pane.add(sliderLabelMarbles);
        pane.add(slideMarbles);

        ButtonHandler buttonHandle = new ButtonHandler();           //Start Game
        JButton startGame = new JButton("Start Game");
        addAButton(startGame, pane);
        startGame.addActionListener(buttonHandle);

        JButton back = new JButton("Back");                     //Back Button
        addAButton(back, pane);
        back.addActionListener(buttonHandle);
    }

    public void Statistics(Container pane) {                            //Get statistics for game
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));


        addlabel("Statistics", pane, 80, Color.RED);
        addlabelforStats("Games Played: ", playCount, pane, 40, Color.RED);
        addlabelforStats("Games Won: ", gamesWon, pane, 40, Color.ORANGE);
        addlabelforStats("Win Percentage: ", winPercentage, pane, 40, Color.RED);

        ButtonHandler buttonHandle = new ButtonHandler();
        JButton back = new JButton("Back");
        addAButton(back, pane);
        back.addActionListener(buttonHandle);

        JButton reset = new JButton("Reset Stats");
        addAButton(reset, pane);
        reset.addActionListener(buttonHandle);

    }

    public void captureRules(Container pane) {                  //Rules fro Capture mode

            String str = ("<html><span>1. Each Player has a store on one side of the board.<br><br>" +
                    "2.Players take turns choosing a pile from one of the holes. Moving<br>" +
                    "counter-clockwise, stones from the selected pile are deposited in each<br>" +
                    "of the following hole until you run out of stones.<br><br>" +
                    "3. If you drop the last stone into your store - you get a free turn.<br><br>" +
                    "4. If you drop the last stone into an empty hole on your side of the board" +
                    "- you can capture stones from the hole on the opposite side.<br><br>" +
                    "5. The game ends when all six holes on either side of the board are empty." +
                    "If a player has any stones on their side of the board when the game ends -" +
                    "he will capture all of those stones.<br><br><br>" +
                    "GOAL: PLAYER WITH THE MOST STONES IN THEIR STORE WINS<br></span></html>");



        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        addlabel("Rules of the Game", pane, 50, Color.RED);
        addlabel(str, pane, 15, Color.BLACK);

        ButtonHandler buttonHandle = new ButtonHandler();
        JButton Begin = new JButton("Begin!");
        addAButton(Begin, pane);
        Begin.addActionListener(buttonHandle);

        JButton back = new JButton("Back");
        addAButton(back, pane);
        back.addActionListener(buttonHandle);

    }

    public void avalancheRules(Container pane) {            //Rules for Avalanche mode

        String str = ("<html><span>Rules:<br> 1. Each Player has a store on one side of the board.<br><br>" +
                "2. Players take turns choosing a pile from one of the holes. Moving counter-clockwise, stones from " +
                "the selected pile are deposited in each of the following hole.<br><br>" +
                "3. If you drop the last stone into an unempty hole, you will pick up the stones from that hole and " +
                "continue depositing them counter-clockwise.<br><br>" +
                "4. Your turn is over when you drop the last stone into an empty hole. <br><br>" +
                "5. If you drop the last stone into your store - you get a free turn.<br><br>" +
                "6. The game ends when all six holes on either side of the board are empty.<br><br>" +
                "Goal: Player with most stones in their stones wins.</span></html>");



        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        addlabel("Rules of the Game", pane, 50, Color.RED);
        addlabel(str, pane, 15, Color.BLACK);

        ButtonHandler buttonHandle = new ButtonHandler();
        JButton Begin = new JButton("Begin!");
        addAButton(Begin, pane);
        Begin.addActionListener(buttonHandle);

        JButton back = new JButton("Back");
        addAButton(back, pane);
        back.addActionListener(buttonHandle);

    }
    public void startGame(){                    //Process setup choices and start the game
        frame = new JFrame("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);

        if( gameMode.equals("Capture")) {
            if(playerOption.equals("PVP"))
                match = new Game(Game.CAPTURE_MODE, Game.HUMAN_PLAYER, Game.HUMAN_PLAYER, numofHollow, numofMarbles);
            else if(playerOption.equals("PVCE"))
                match = new Game(Game.CAPTURE_MODE, Game.HUMAN_PLAYER, Game.EASY_COMPUTER, numofHollow, numofMarbles);
            else if(playerOption.equals("PVCH"))
                match = new Game(Game.CAPTURE_MODE, Game.HUMAN_PLAYER, Game.HARD_COMPUTER, numofHollow, numofMarbles);
            else
                match = new Game(Game.CAPTURE_MODE, Game.EASY_COMPUTER, Game.EASY_COMPUTER, numofHollow, numofMarbles);
        }
        else{
            if(playerOption.equals("PVP"))
                match = new Game(Game.AVALANCHE_MODE, Game.HUMAN_PLAYER, Game.HUMAN_PLAYER, numofHollow, numofMarbles);
            else if(playerOption.equals("PVCE"))
                match = new Game(Game.AVALANCHE_MODE, Game.HUMAN_PLAYER, Game.EASY_COMPUTER, numofHollow, numofMarbles);
            else if(playerOption.equals("PVCH"))
                match = new Game(Game.AVALANCHE_MODE, Game.HUMAN_PLAYER, Game.HARD_COMPUTER, numofHollow, numofMarbles);
            else
                match = new Game(Game.AVALANCHE_MODE, Game.EASY_COMPUTER, Game.EASY_COMPUTER, numofHollow, numofMarbles);
        }


        match.sourceMenu(this); // match lets menu know when game ends
        frame.add(match);


    }

    public void actionPerformed(ActionEvent e ){    // check if the game ends...
        if(match.isCompleted()){
            // do post match code here
            endofGame();
        }
    }

    private void endofGame() {          //If end of game, display these options
        Object[] options = {"Play Again",
                "Main Menu",
                "Quit"};
        int n = JOptionPane.showOptionDialog(frame,
                "What would you like to do next?",
                "End of Game",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
        if (n == 0) {
            frame.setVisible(false);
            startGame();
        } else if (n == 1) {
            SaveStats();
            frame.setVisible(false);
            createAndShowGUI("Menu");
        } else {
            SaveStats();
            System.exit(0);
        }


    }

    private void createAndShowGUI(String frameName) {               //Decide which GUI function to call
        frame = new JFrame(frameName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (frameName.equals("Menu"))
            Menu(frame.getContentPane());
        else if (frameName.equals("Setup"))
            Setup(frame.getContentPane());
        else if (frameName.equals("Statistics"))
            Statistics(frame.getContentPane());
        else if(frameName.equals("captureRules"))
            captureRules(frame.getContentPane());
        else if(frameName.equals("avalancheRules"))
            avalancheRules(frame.getContentPane());
        else if(frameName.equals("GAME"))
            startGame();

        frame.pack();
        frame.setSize(600, 750);
        frame.setVisible(true);
    }

    private class CheckBoxHandler implements ActionListener{    // respond to checkbox events
        public void actionPerformed(ActionEvent event) {
            String str = event.getActionCommand();
            // process bold checkbox events
            if (str.equals("Player vs Player")) {
                playerOption = "PVP";

            } else if (str.equals("Player vs Computer (EASY)")) {
                playerOption = "PVCE";

            } else if (str.equals("Player vs Computer (HARD)")) {
                playerOption = "PVCH";

            }
            else if (str.equals("Computer vs Computer")) {
                playerOption = "CVC";

            }

            if (str.equals("Capture")) {
                gameMode = "Capture";

            } else if (str.equals("Avalanche")) {
                gameMode = "Avalanche";

            }

        } // end method itemStateChanged
    }
    // end private inner class CheckBoxHandler

    private class ButtonHandler implements ActionListener {     //Responds to button-clicked events
        public void actionPerformed(ActionEvent event) {

            String buttonName = event.getActionCommand();
            if (buttonName.equals("Play Game")) {
                currentFrame = "Setup";
                frame.setVisible(false);
                createAndShowGUI("Setup");
            } else if (buttonName.equals("Statistics")) {
                currentFrame = "Statistics";
                frame.setVisible(false);
                createAndShowGUI("Statistics");
            } else if (buttonName.equals("DO NOT PRESS!")) {
                frame.setVisible(false);
                if (colorChooser == 9)
                    colorChooser = 0;
                else
                    colorChooser++;

                createAndShowGUI("Menu");
            }else if(buttonName.equals("Start Game")){
                frame.setVisible(false);

                if(gameMode.equals("Capture")) {
                    currentFrame = "captureRules";
                    createAndShowGUI("captureRules");
                }else if(gameMode.equals("Avalanche")){
                    currentFrame = "avalancheRules";
                    createAndShowGUI("avalancheRules");
                }
            }else if(buttonName.equals("Back")){
                frame.setVisible(false);
                if(currentFrame.equals("Statistics") || currentFrame.equals("Setup")){
                    createAndShowGUI("Menu");
                }else if(currentFrame.equals("captureRules") || currentFrame.equals("avalancheRules")){
                    createAndShowGUI("Setup");
                    currentFrame = "Setup";
                }

            }else if(buttonName.equals("Reset Stats")){
                playCount = 0;
                gamesWon = 0;
                winPercentage = 0;
                frame.setVisible(false);
                createAndShowGUI("Statistics");
            }
            else if(buttonName.equals("Begin!")){

                frame.setVisible(false);
                createAndShowGUI("GAME");

            }
        } // end method itemStateChanged

    } // end private inner class CheckBoxHandler


    public void stateChanged(ChangeEvent event){            //Responds to slider events
        Object S = event.getSource();
        JSlider tempSlide = (JSlider)S;
        String sliderName = tempSlide.getName();

        if(sliderName.equals("HollowSlider"))
            numofHollow = tempSlide.getValue();
        else if(sliderName.equals("MarbleSlider"))
            numofMarbles = tempSlide.getValue();

    }

    public void SaveStats(){                             //Write stats to a txt file
        try {
            File file = new File("save.txt");
            FileWriter writer = new FileWriter(file);
            String Percentage = String.format("%.2f",winPercentage);
            writer.write(playCount + "");
            writer.write(" ");
            writer.write(gamesWon + "");
            writer.write(" ");
            writer.write(Percentage);
            writer.close();
        }catch(IOException e){
            System.out.println("file not found");
        }
    }

   static public void ReadStats(){                          //Read the stats from txt file
        try{
            File f = new File("save.txt");
            Scanner scan = new Scanner(f);
            if(scan.hasNextInt())
                playCount = scan.nextInt();
            if(scan.hasNextInt())
                gamesWon = scan.nextInt();
            if(scan.hasNextDouble())
                winPercentage = scan.nextDouble();

        }catch(FileNotFoundException e){
            System.out.println("file not found");
        }
    }


    public void updateStats(){                                  //Adds to play count and calculates Win %
        playCount++;
        winPercentage = ((double)gamesWon / playCount) * 100;

    }

    public void incrementWin(){                                //Increments Games Won
        gamesWon++;
    }



}
