import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setBackground(Color.WHITE);

        int boardLength = getWidth()/2;
        int boardHeight = getHeight()*3/4;

        g.setColor(new Color(205, 170, 125));
        g.fillRoundRect(getWidth()/4,getHeight()/10, boardLength, boardHeight, getWidth()/10, getHeight()/10);

        g.setColor(new Color(152, 117, 84));
        g.fillRoundRect(getWidth()/4 + boardLength/20, getHeight()/10 + boardHeight/45, boardLength - getWidth()/20, boardHeight/8, getWidth()/20, getHeight()/20);
        g.fillRoundRect(getWidth()/4 + boardLength/20, getHeight()*15/20 - boardHeight/45, boardLength - getWidth()/20, boardHeight/8, getWidth()/20, getHeight()/20);
    }
}
