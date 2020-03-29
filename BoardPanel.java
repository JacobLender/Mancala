import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setBackground(Color.WHITE);

        int boardLength = getWidth()/2;
        int boardHeight = getHeight()*3/4;
        int boardX = getWidth()/4;
        int boardY = getHeight()/10;

        g.setColor(new Color(205, 170, 125));
        g.fillRoundRect(boardX, boardY, boardLength, boardHeight, getWidth()/10, getHeight()/10);

        g.setColor(new Color(152, 117, 84));
        int topBasketX = getWidth()/4 + boardLength/20;
        int topBasketY = getHeight()/10 + boardHeight/45;
        int bottomBasketX = getWidth()/4 + boardLength/20;
        int bottomBasketY = getHeight()*15/20 - boardHeight/45;

        g.fillRoundRect(topBasketX, topBasketY, boardLength - getWidth()/20, boardHeight/8, getWidth()/20, getHeight()/20);
        g.fillRoundRect(bottomBasketX, bottomBasketY, boardLength - getWidth()/20, boardHeight/8, getWidth()/20, getHeight()/20);

        int circleLength = boardLength / 4;
        int circleHeight = boardHeight / 9;

        int incrementLeft = topBasketY + boardHeight/8;
        for(int i = 0; i < 6; i++) {
            g.fillOval(topBasketX + topBasketX /4, incrementLeft, circleLength, circleHeight);
            incrementLeft = incrementLeft + circleHeight;
        }

        int incrementRight = topBasketY + boardHeight/8;
        for(int i = 0; i < 6; i++){
            g.fillOval(bottomBasketX + topBasketX, incrementRight, circleLength, circleHeight);
            incrementRight = incrementRight + circleHeight;
        }

    }
}
