import java.awt.*;
import java.util.Random;

public class Piece {
    private int x, y;
    public int xScale, yScale;
    private Color color;

    public static final Color [] colorList = {
            new Color(81, 165, 255, 214) ,          // light blue
            new Color(195, 43, 55, 200),            // light red
            new Color(254, 255, 38, 178),
            new Color(255, 126, 45),
            new Color(102, 195, 80, 225),
    };
    public Piece(){
        this(0,0);
    }
    public Piece(int xS, int yS){
        xScale = xS;
        yScale = yS;
        x= 0;
        y = 0;
        Random rand = new Random();
        color = colorList[rand.nextInt(colorList.length)];
    }
    public Piece(int xCord, int yCord, Color theColor){
        x = xCord;
        y = yCord;
        color = theColor;
    }

    public void setCord( int xCord, int yCord){
        x =xCord;
        y=yCord;
    }

    public int getX(){
        return x;
    }
    public int getY(){return y;}
    public Color getColor(){
        return color;
    }

}
