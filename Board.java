import javax.swing.*;
import java.awt.*;

public class Board extends JPanel
{
  private Hollow [] hollows;
  private Hollow bottom = new Hollow();
  private Hollow top = new Hollow();

  // constructors

  public Board()
  {
    this(12);   // constructor delegation
  }
  public Board( int spots )
  {
    this(spots, 0);
  }
  public Board(int spots, int pieces)
  {
    if ( spots % 2 != 0 || spots < 0 )
      hollows = new Hollow[12]; // standard number of "hollows" on board
    else
      hollows = new Hollow[spots];

    for (int i = 0; i < hollows.length; i++)
      hollows[i] = new Hollow(pieces);
    // negative peices handled in Hollow constructor
  }

  public boolean emptySide(){ // this will indicate if a playing side of the board is empty
    boolean empty = true;
    for(int i = 0; i < hollows.length/2; i++){
      if ( !hollows[i].empty()) empty = false;
    }
    if (empty)
      return empty;

    empty = true;
    for( int i = hollows.length/2; i < hollows.length; i++){
      if ( !hollows[i].empty())
        empty = false;
    }
    return empty;
  }

  // begin graphics

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
      g.setColor(new Color(152, 117, 84));
      g.fillOval(topBasketX + topBasketX /4, incrementLeft, circleLength, circleHeight);
      g.setColor(new Color(0, 0, 0));
      g.drawString(  hollows[i].getCount()+ "",topBasketX + topBasketX /4 + circleLength/2, incrementLeft + circleHeight/2);
      incrementLeft = incrementLeft + circleHeight;
    }

    int incrementRight = topBasketY + boardHeight/8;
    for(int i = 6; i < 12; i++){
      g.setColor(new Color(152, 117, 84));
      g.fillOval(bottomBasketX + topBasketX, incrementRight, circleLength, circleHeight);
      g.setColor(new Color(0, 0, 0));
      g.drawString( hollows[i].getCount()+"", bottomBasketX + topBasketX + circleLength/2, incrementRight + circleHeight/2);
      incrementRight = incrementRight + circleHeight;
    }

  }
}


// these are the spots on the board
class Hollow
{
  private int count;  // the number of peices in each hollow

  public Hollow()
  {
    this(0);
  }
  public Hollow(int pieces)
  {
    if ( pieces < 0 )
      count = 0;
    else
      count = pieces;
  }

  // accessors
  public int getCount()   // mumber of peices
  { return count; }
  public boolean empty()
  { return count == 0; }

  // mutators
  public void increment()
  { count++; }
  public int take()
  // this function may be handy during game play
  // simulates a player taking peices from a hollow
  {
    int x = count;
    count = 0;
    return x;
  }

}
