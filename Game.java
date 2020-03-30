import javax.swing.*;

public class Game {
    private Board gameBoard;
    private Player [] list = new Player[2];
    // add other objects such as players

    public Game(){
        gameBoard = new Board(12, 6);
        list[0] = new HumanPlayer("left");
        list[1] = new HumanPlayer("right");

        JFrame frame = new JFrame("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameBoard);
        frame.setSize(600, 900);
        frame.setVisible(true);
    }
    public static void main(String [] args){
        Game match = new Game();
    }
    public void playGame(){
        for ( int i = 0; !gameBoard.emptySide(); i++)
        {
            list[i%2].takeTurn(gameBoard);
        }
    }
}
