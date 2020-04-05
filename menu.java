import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class menu extends JFrame{
//    private JLabel label1;
//    private JButton start;
//    private JButton Rules;
//    private JButton Options;
//    private JButton Stats;
    private static int playCount;
    private static int gamesWon;
    private static double winPercentage;



    private static void addAButton(String buttName, Container container){
        JButton button = new JButton(buttName);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Verdana", Font.PLAIN, 50));
        button.setPreferredSize(new Dimension(400, 75));
        container.add(button);
    }
    private static void addRadioButton(String text, Container container){
        JRadioButton radioButt = new JRadioButton(text);
        radioButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        radioButt.setFont(new Font("Verdana", Font.PLAIN, 20));
        radioButt.setPreferredSize(new Dimension(50, 75));
        container.add(radioButt);
    }

    private static void addlabel(String text, Container container, int size, Color col){
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Verdana", Font.BOLD, size));
        label.setForeground(col);
        container.add(label);
    }

    private static void addlabelforStats(String text, double measure, Container container, int size, Color col){
        JLabel labelFStats = new JLabel(text + measure);
        labelFStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelFStats.setFont(new Font("Verdana", Font.BOLD, size));
        labelFStats.setForeground(col);
        container.add(labelFStats);
    }

    public static void Menu(Container pane){
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addlabel("MANCALA", pane, 100, Color.BLUE);
        addAButton("Play Game", pane);
        addAButton("Statistics", pane);
        addAButton("DO NOT PRESS!!!", pane);

    }
    public static void Setup(Container pane){
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addlabel("Choose Players", pane, 50, Color.BLACK);
        addRadioButton("Player vs Player", pane);
        addRadioButton("Player vs Computer", pane);
        addRadioButton("Computer vs Computer", pane);

        addlabel("Game Mode", pane, 50, Color.BLACK);
        addRadioButton("Classic", pane);
        addRadioButton("Flash", pane);
        addRadioButton("Avalanche", pane);

        addAButton("Start Game", pane);


    }

    public static void Statistics(Container pane){
        JPanel panel = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addlabel("Statistics", pane, 80, Color.RED);
        addlabelforStats("Games Played: ", playCount, pane, 40, Color.RED);
        addlabelforStats("Games Won: ", gamesWon, pane, 40, Color.ORANGE);
        addlabelforStats("Winning Percentage: ", winPercentage, pane, 40, Color.RED);

    }

    public static void Rules(Container pane){
        String label = ("<html><span>1. Each Player has a store on one side of the board.<br><br>"+
                        "2.Players take turns choosing a pile from one of the holes. Moving<br>"+
                        "counter-clockwise, stones from the selected pile are deposited in each<br>"+
                        "of the following hole until you run out of stones.<br><br>"+
                        "3. If you drop the last stone into your store - you get a free turn.<br><br>"+
                        "4. If you drop the last stone into an empty hole on your side of the board"+
                        "- you can capture stones from the hole on the opposite side.<br><br>"+
                        "5. The game ends when all six holes on either side of the board are empty."+
                        "If a player has any stones on their side of the board when the game ends -"+
                        "he will capture all of those stones.<br><br><br>"+
                        "GOAL: PLAYER WITH THE MOST STONES IN THEIR STORE WINS<br></span></html>");

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        addlabel("Rules of the Game", pane, 50, Color.RED);
        addlabel(label, pane, 15, Color.BLACK);

    }

    private static void createAndShowGUIMenu(){
        JFrame frame = new JFrame("GAME MENU");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu(frame.getContentPane());

        frame.pack();
        frame.setSize( 600, 600 );
        frame.setVisible(true);
    }
    private static void createAndShowGUISetup() {
        JFrame frame = new JFrame("Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Setup(frame.getContentPane());

        frame.pack();
        frame.setSize( 600, 600 );
        frame.setVisible(true);

    }
    private static void createAndShowGUIRules() {
        JFrame frame = new JFrame("Rules");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Rules(frame.getContentPane());

        frame.pack();
        frame.setSize( 600, 500 );
        frame.setVisible(true);

    }
    private static void createAndShowGUIStats(){
        JFrame frame = new JFrame("Statistics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Statistics(frame.getContentPane());


        frame.pack();
        frame.setSize( 600, 600 );
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        createAndShowGUIMenu();
        createAndShowGUISetup();
        createAndShowGUIStats();
        createAndShowGUIRules();
    }

//    public menu(){
//        setLayout(new FlowLayout());
//
//        label1 = new JLabel("MANCALA");
//        label1.setFont(new Font("Verdana", Font.BOLD, 90));
//        label1.setForeground(Color.blue);
//        add(label1);
//
//        start = new JButton("Play Game");
//        start.setFont(new Font("Verdana", Font.PLAIN, 50));
//        start.setPreferredSize(new Dimension(400, 75));
//        add(start);
//
//        Rules = new JButton("Rules");
//        Rules.setFont(new Font("Verdana", Font.PLAIN, 50));
//        Rules.setPreferredSize(new Dimension(400, 75));
//        add(Rules);
//
//        Options = new JButton("Statistics");
//        Options.setFont(new Font("Verdana", Font.PLAIN, 50));
//        Options.setPreferredSize(new Dimension(400, 75));
//        add(Options);
//
//        Stats = new JButton("Settings");
//        Stats.setFont(new Font("Verdana", Font.PLAIN, 50));
//        Stats.setPreferredSize(new Dimension(400, 75));
//        add(Stats);
//    }

//
//        public void paintComponent(Graphics2D g) {
//            super.paintComponent(g);
//            int startX = getWidth();
//            int startY = getHeight();
//
//
//            g.setFont(new Font("TimesRoman", Font.BOLD, 100));
//            g.drawString("MANCALA", startX, startY);
//            g.setColor(Color.CYAN);
//            g.fill3DRect(startX / 200, startY / 7, startX, startY, true);
//            g.setColor(Color.BLACK);
//
//
//        }


}
