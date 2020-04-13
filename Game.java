import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public abstract class Game {
    protected Board gameBoard;
    protected JPanel gamePanel;
    protected JTextPane statusbar;
    protected Player [] list = new Player[2];
    // add other objects such as players

    public Game(){
        gameBoard = new Board(6, 6) {
        };
        list[0] = new HumanPlayer(-1);  // home is bottom hollow (hollow they want to score on)
        list[1] = new HumanPlayer(1);   // home is top hollow

        JFrame frame = new JFrame("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        statusbar = new JTextPane();

        // set up status bar
        StyledDocument doc = statusbar.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        statusbar.setFont(new Font("Serif", Font.PLAIN, 20));
        statusbar.setText("Setting up game...");
        statusbar.setEditable(false);

        gamePanel.add(statusbar, BorderLayout.NORTH);
        gamePanel.add(gameBoard, BorderLayout.CENTER);

        frame.add(gamePanel);
        frame.setSize(600, 900);
        frame.setVisible(true);
    }
    public static void main(String [] args){
        Game match = new CaptureMode();
        match.playGame();
    }
    public void playGame(){
        for ( int i = 0; !gameBoard.emptySide(); i++)
        {
            movePieces(list[i%2]);
        }
        gameBoard.cleanUpBoard();
        if( gameBoard.isWinner(list[0]) > 0)
            statusbar.setText("Game over! Player 1 is the winner");
        else if (gameBoard.isWinner(list[1]) > 0)
            statusbar.setText("Game over! Player 2 is the winner");
        else
            statusbar.setText("Game over! Tie Game.");
    }
    public abstract void movePieces(Player p);      // decides when a turn ends

}

class CaptureMode extends Game{
    public CaptureMode(){
        super();
    }
    public void movePieces(Player p){
        if(p.getHome() == -1 )
            statusbar.setText("Left player, choose your move.");
        else
            statusbar.setText("Right player, choose your move.");


        Hollow h = new Hollow();
        boolean retry;
        int spot;
        do {
            gameBoard.setClickable(p);  // set clickable hollows based on who is playing
            do {
                spot = gameBoard.buttonClicked();
            }while(spot == -1);        // waits for player to click on hollow

            gameBoard.getHollow(spot).unClick();        // deselects hollow
            gameBoard.disableAll();                     // all hollows now unclickable
            retry = false;

            int x = gameBoard.getHollow(spot).take();
            try{
                statusbar.setText("Moving pieces...");
                for (int i = 0; i < x; i++) {
                    h = gameBoard.getHollow((spot + 1 + i) % gameBoard.hollowsLength());
                    if (h.getSide() != p.oppHome()) {
                        h.increment();
                        gameBoard.repaint();
                        Thread.sleep(650);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (p.getHome() == h.getSide())
            {
                retry = true;
            }
            else if( h.getCount() == 1 && h.getSide() == 0 && gameBoard.playerSide(p, spot+x)){
                if (gameBoard.oppositeHollow((spot+x)).getCount()!= 0) {

                    // player ends on their side and with opposite hollow has pieces
                    retry = true;
                    Hollow home = gameBoard.getHome(p);

                    int y = h.take();
                    int z = gameBoard.oppositeHollow(spot + x).take();

                    home.add(y + z);
                    gameBoard.repaint();
                }
            }
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
class AvalancheMode extends Game{
    public AvalancheMode(){
        super();
    }
    public void movePieces(Player p){

        Hollow h = new Hollow();
        int spot;
        do {
            do {
                spot = gameBoard.buttonClicked();
            }while(spot == -1);
            int x = gameBoard.getHollow(spot).take();

            try{
                for (int i = 0; i < x; i++) {
                    h = gameBoard.getHollow((spot + 1 + i) % gameBoard.hollowsLength());
                    if (h.getSide() != p.oppHome()) {
                        h.increment();
                        gameBoard.repaint();
                        Thread.sleep(750);
                    }
                    if ( i == x - 1 && !gameBoard.getHollow((spot + 2 + i)%gameBoard.hollowsLength()).empty() ){
                        x = x + gameBoard.getHollow((spot + 2 + i)%gameBoard.hollowsLength()).take();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (h.getSide() == p.getHome() );
    }
}
