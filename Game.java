import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class Game extends JPanel{
    private Board gameBoard;
    private JTextPane statusbar;
    private Player [] list = new Player[2];

    public static int CAPTURE_MODE = 0;
    public static int AVALANCHE_MODE = 1;

    public static int HUMAN_PLAYER = 0;
    public static int EASY_COMPUTER = 1;
    public static int MEDIUM_COMPUTER = 2;

    public Game(){
        this(CAPTURE_MODE, HUMAN_PLAYER, HUMAN_PLAYER);
    }
    public Game(int game_mode, int player_1, int player_2){
        this(game_mode, player_1, player_2, 6, 4);
    }
    public Game(int game_mode, int player_1, int player_2, int length, int pieces){
        gameBoard = new CaptureBoard(length, pieces);

        if(player_1 == EASY_COMPUTER)
            list[0] = new EasyComputer();
        else
            list[0] = new HumanPlayer();

        list[0].setHome(-1);

        if(player_2 == EASY_COMPUTER)
            list[1] = new EasyComputer();
        else
            list[1] = new HumanPlayer();

        list[1].setHome(1);


        setLayout(new BorderLayout());
        statusbar = new JTextPane();

        // set up status bar
        StyledDocument doc = statusbar.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        statusbar.setFont(new Font("Serif", Font.PLAIN, 20));
        statusbar.setText("Setting up game...");
        statusbar.setEditable(false);

        add(statusbar, BorderLayout.NORTH);
        add(gameBoard, BorderLayout.CENTER);
    }
    public static void main(String [] args){

        JFrame frame = new JFrame("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 900);
        frame.setVisible(true);
        // set up with static constants
        Game match = new Game(Game.CAPTURE_MODE, Game.HUMAN_PLAYER, Game.EASY_COMPUTER);
        // add to whatever frame you need it to be
        frame.add(match);
        // game has started
        match.playGame();
    }
    public void playGame(){
        for ( int i = 0; !gameBoard.emptySide(); i++)
        {
            offerTurn(list[i%2]);
        }

        gameBoard.cleanUpBoard();
        if( gameBoard.isWinner(list[0]) > 0)
            statusbar.setText("Game over! Player 1 is the winner");
        else if (gameBoard.isWinner(list[1]) > 0)
            statusbar.setText("Game over! Player 2 is the winner");
        else
            statusbar.setText("Game over! Tie Game.");
    }
    public void offerTurn(Player p){
        if(p.getHome() == -1 )
            statusbar.setText("Left player, choose your move.");
        else
            statusbar.setText("Right player, choose your move.");


        Hollow h = new Hollow();
        boolean retry;
        int spot;
        do {
            spot = p.takeTurn(gameBoard);
            gameBoard.repaint();
            try {
                Thread.sleep(400);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            gameBoard.repaint();
            gameBoard.getHollow(spot).unClick();        // deselects hollow
            statusbar.setText("Moving pieces...");
            retry = gameBoard.movePieces(p, spot);

            if (retry)
            {
                if(p.getHome() == -1 )
                    statusbar.setText("Left player, take another turn!");
                else
                    statusbar.setText("Right player, take another turn!");
            }
        }while ( retry && !gameBoard.emptySide());
    }

}

// this mode is not finished yet... code will look similar to capture mode
//class AvalancheMode extends Game{
//    public AvalancheMode(){
//        super();
//    }
//    public void offerTurn(Player p){
//
//        Hollow h = new Hollow();
//        int spot;
//        do {
//            do {
//                spot = gameBoard.buttonClicked();
//            }while(spot == -1);
//            int x = gameBoard.getHollow(spot).take();
//
//            try{
//                for (int i = 0; i < x; i++) {
//                    h = gameBoard.getHollow((spot + 1 + i) % gameBoard.hollowsLength());
//                    if (h.getSide() != p.oppHome()) {
//                        h.increment();
//                        gameBoard.repaint();
//                        Thread.sleep(750);
//                    }
//                    if ( i == x - 1 && !gameBoard.getHollow((spot + 2 + i)%gameBoard.hollowsLength()).empty() ){
//                        x = x + gameBoard.getHollow((spot + 2 + i)%gameBoard.hollowsLength()).take();
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }while (h.getSide() == p.getHome() );
//    }
//}
