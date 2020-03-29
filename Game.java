import javax.swing.*;

public class Game {
    private Board GameBoard;
    private Player [] list = new Player[2];
    // add other objects such as players

    public Game(){
        GameBoard = new Board();
        list[0] = new HumanPlayer("left");
        list[1] = new HumanPlayer("right");
    }
    public static void main(String [] args){
        JFrame frame = new JFrame("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new BoardPanel());
        frame.setSize(600, 900);
        frame.setVisible(true);
    }
}
