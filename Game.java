import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game extends JPanel implements ActionListener {
    private menu source;
    private Board gameBoard;
    private JTextPane statusbar;
    private Player [] list = new Player[2];
    private int turn;
    private boolean completed = false;
    private Timer playTimer;
    public Timer matchFinished;

    public final static int CAPTURE_MODE = 0;
    public final static int AVALANCHE_MODE = 1;

    public final static int HUMAN_PLAYER = 0;
    public final static int EASY_COMPUTER = 1;
    public final static int HARD_COMPUTER = 2;

    public Game(){
        this(CAPTURE_MODE, HUMAN_PLAYER, HUMAN_PLAYER);
    }
    public Game(int game_mode, int player_1, int player_2){
        this(game_mode, player_1, player_2, 6, 4);
    }
    public Game(int game_mode, int player_1, int player_2, int length, int pieces){

        if (game_mode == Game.AVALANCHE_MODE)
            gameBoard = new AvalancheBoard(length, pieces);
        else
            gameBoard = new CaptureBoard(length, pieces);

        for(int i = 0; i < gameBoard.hollowsLength(); i++)
            gameBoard.getHollow(i).addActionListener(this);

        gameBoard.pieceMover.addActionListener(this);


        if(player_1 == EASY_COMPUTER)
            list[0] = new EasyComputer(Board.BOTTOM);
        else
            list[0] = new HumanPlayer(Board.BOTTOM);

        if(player_2 == EASY_COMPUTER)
            list[1] = new EasyComputer(Board.TOP);
        else
            list[1] = new HumanPlayer(Board.TOP);

        Random rand = new Random();

        turn = rand.nextInt(2);
        if (list[turn].isBot())
            signalPlayer();
        else
            gameBoard.setClickable(list[turn]);


        setLayout(new BorderLayout());
        statusbar = new JTextPane();

        // set up status bar
        StyledDocument doc = statusbar.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        statusbar.setFont(new Font("Serif", Font.PLAIN, 20));
        statusbar.setText( list[turn].getName() +", choose your move.");
        statusbar.setEditable(false);
        add(statusbar, BorderLayout.NORTH);
        add(gameBoard, BorderLayout.CENTER);

        matchFinished = new Timer(0, null);
    }


    public void actionPerformed(ActionEvent e){
        if(e.getSource() == gameBoard.pieceMover){
            if(gameBoard.moved){
                System.out.println("Gameboard is moved!");
                gameBoard.moved = false;
                System.out.println("Retry == " + gameBoard.retry);
                if (!gameBoard.retry) {
                    nextPlayer();
                    statusbar.setText(list[turn].getName() + ", choose your move...");
                }
                else {
                    statusbar.setText(list[turn].getName() + ", choose another move!");
                }

                if (gameBoard.emptySide()) {
                    completed = true;
                    System.out.println("Game is finished");
                    gameBoard.cleanUpBoard();
                    matchFinished = new Timer(0, source);
                    matchFinished.setRepeats(false);
                    matchFinished.start();
                    statusbar.setText(gameBoard.decideWinner());
                    if(gameBoard.decideWinner().equals("Player One Wins!"))
                        source.incrementWinCount();
                }
                else if(list[turn].isBot()){
                    signalPlayer();
                }
                else
                    gameBoard.setClickable(list[turn]);
            }
        }
        else {
            gameBoard.disableAll();
            System.out.println(list[turn].getName() + " action performed:");
            if(e.getSource() == playTimer){
                list[turn].takeTurn(gameBoard);
            }
            else {
                Hollow h = (Hollow) e.getSource();
                h.click();
            }
            int spot = gameBoard.buttonClicked();
            repaint();
            Timer clickDelay = new Timer(300, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameBoard.getHollow(spot).unClick();
                    repaint();
                }
            });
            clickDelay.start();
            gameBoard.movePieces(list[turn], spot);
        }
    }

    public void nextPlayer(){
        turn = (turn + 1)% list.length;
    }

    public boolean isCompleted(){
        return completed;
    }

    public void sourceMenu(menu m){
        source = m;
    }

    public void signalPlayer(){
        playTimer = new Timer(300, this);
        playTimer.setRepeats(false);
        playTimer.start();
    }

    public int increment(){
        String str = gameBoard.decideWinner();
        if(str.equals("Player One Wins!"))
            return 1;
        else
            return 0;
    }

}