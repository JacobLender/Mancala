import javax.swing.*;

public class Game {
    private Board GameBoard = new Board();
    // add other objects such as players
    // testing commit from IDE...
    public static void main(String [] args){
        JFrame frame = new JFrame("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new BoardPanel());
        frame.setSize(600, 900);
        frame.setVisible(true);
    }
}
