import javax.swing.*;
import java.awt.*;

public abstract class Board extends JPanel
{
  protected Hollow [] hollows;
  protected int sideLength;

  // constructors

  public Board()
  {
    this(6);   // constructor delegation
  }
  public Board( int sideLength )
  {
    this(sideLength, 0);
  }
  public Board(int s, int pieces)
  {
    if (s < 2)
      sideLength = 2;
    else if ( s % 2 != 0)
      sideLength = s - 1;
    else
      sideLength = s;

    hollows = new Hollow[sideLength*2+2];          // middle hollows and two end hollows

    for (int i = 0; i < hollows.length; i++){
      if (i == 0) {
        hollows[i] = new Hollow(0, -1);
      }
      else if ( i == sideLength+1) {
        hollows[i] = new Hollow(0, 1);
      }
      else {
        hollows[i] = new Hollow(pieces, 0);
      }
      hollows[i].setOpaque(false);
      hollows[i].setContentAreaFilled(false);
      hollows[i].setBorderPainted(false);
    }
    // negative peices handled in Hollow constructor
  }

  public int buttonClicked(){
    for(int i = 0; i < hollows.length; i++){
      if ( hollows[i].isClicked()) return i;
    }
    return -1;
  }

  // prompts which buttons the player can click, based on player side
  public void setClickable(Player p)
  {
    if ( p.getHome() == 1){
      for( int i = 1; i < hollows.length; i++){
        if ( i < sideLength + 1){
          if(hollows[i].empty())
            hollows[i].setEnabled(false);
          else
            hollows[i].setEnabled(true);
        }
        else
          hollows[i].setEnabled(false);
      }
    }
    else{
      for( int i = 1; i < hollows.length; i++){
        if ( i > sideLength+1){
          if(hollows[i].empty())
            hollows[i].setEnabled(false);
          else
            hollows[i].setEnabled(true);
        }
        else
          hollows[i].setEnabled(false);
      }
    }
  }
  public void disableAll(){   // all buttons
    for( Hollow h : hollows ){
      h.setEnabled(false);
    }
  }
  public void cleanUpBoard(){
    int x = 0;
    for( int i = 1; i < hollows.length; i++) {
      if( i == sideLength+1){
        hollows[sideLength+1].add(x);
        x = 0;
      }
      else
        x += hollows[i].take();
    }
    hollows[0].add(x);
  }
  public abstract boolean movePieces(Player p, int spot);

  public boolean emptySide(){ // this will indicate if a playing side of the board is empty
    boolean empty = true;

    for(int i = 1; i < hollowsLength(); i++){
      if (i == sideLength + 1){
        if ( empty )
          return true;
        empty = true;
      }
      else
        empty = empty && hollows[i].empty();
    }
    return empty;
  }
  public int getSideLength(){
    return sideLength;
  }
  public int hollowsLength(){
    return hollows.length;
  }
  public int numPieces(){
    int count =0;
    for ( Hollow h : hollows)
      count += h.getCount();

    return count;
  }
  public Hollow getHollow(int i)
  {
    return hollows[i % hollows.length];
  }
  public Hollow getHome(Player p){
    if (p.getHome() == -1 )
      return hollows[0];
    if (p.getHome() == 1)
      return hollows[sideLength+1];

    return new Hollow();
  }
  public Hollow oppositeHollow(int i ){
    if ( i > hollows.length)
      i = i % hollows.length;

    int distance = i - sideLength - 1;
    return hollows[(sideLength + 1 - distance)];
  }

  public boolean playerSide(Player p, int i){
    i = i % hollows.length;
    if ( i > sideLength + 1)
      return p.getHome() == -1;

    return p.getHome() == 1;
  }
  public int isWinner(Player p) // positive for winner, negative for loser, also gives the count
  {
    if(p.getHome() == 1)
      return hollows[sideLength+1].getCount() - hollows[0].getCount();

    return hollows[0].getCount() - hollows[sideLength+1].getCount();
  }

  public int canPlayHome(Player p){
    // will play home with closes peices
    int start = hollows.length;
    if( p.getHome() == 1)
      start = start - sideLength - 1;

    for( int i = start - 1; i > start - sideLength - 1; i--){
      if( i + getHollow(i).getCount() == start)
        return i;
    }
    return -1;
  }

  // begin graphics

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    setBackground(Color.WHITE);

    int boardLength = getWidth()/2;
    int boardHeight = getHeight()*3/4;
    int boardX = getWidth()/4;
    int boardY = getHeight()/10;

    g.drawString(numPieces()+"", 50, 50);

    g.setColor(new Color(205, 170, 125));
    g.fillRoundRect(boardX, boardY, boardLength, boardHeight, getWidth()/10, getHeight()/10);

    g.setColor(new Color(152, 117, 84));
    int topBasketX = getWidth()/4 + boardLength/20;
    int topBasketY = getHeight()/10 + boardHeight/45;
    int bottomBasketX = getWidth()/4 + boardLength/20;
    int bottomBasketY = getHeight()*15/20 - boardHeight/45;

    g.fillRoundRect(topBasketX, topBasketY, boardLength - getWidth()/20, boardHeight/8, getWidth()/20, getHeight()/20);
    g.fillRoundRect(bottomBasketX, bottomBasketY, boardLength - getWidth()/20, boardHeight/8, getWidth()/20, getHeight()/20);

    g.setColor(Color.BLACK);
    g.drawString(hollows[sideLength + 1].getCount()+"", getWidth()/2, topBasketY + topBasketY/2);
    g.drawString(hollows[0].getCount()+"", getWidth()/2, bottomBasketY + boardHeight/16);

    int circleLength = boardLength / 4;
    int circleHeight = boardHeight / 9;

    int incrementY = topBasketY + boardHeight/8;
    for(int i = sideLength + 2; i < hollows.length; i++) {
      //draw highlight if possible
      if(hollows[i].isEnabled() && hollows[i].getCount() != 0) {
        g.setColor(new Color(194, 192, 15));
        g.fillOval(topBasketX + topBasketX/4 - circleLength /30, incrementY - circleHeight/30,
                circleLength + circleLength /15, circleHeight + circleHeight /15);
      }
      if(hollows[i].isClicked()){
        g.setColor(new Color(54, 194, 70));
        g.fillOval(topBasketX + topBasketX/4 - circleLength /30, incrementY - circleHeight/30,
                circleLength + circleLength /15, circleHeight + circleHeight /15);
      }

      //draw hollow
      g.setColor(new Color(152, 117, 84));
      g.fillOval(topBasketX + topBasketX /4, incrementY, circleLength, circleHeight);
      g.setColor(new Color(0, 0, 0));
      g.drawString(  hollows[i].getCount()+ "",topBasketX + topBasketX /4 + circleLength/2, incrementY + circleHeight/2);
      hollows[i].setBounds(topBasketX + topBasketX/4, incrementY, circleLength, circleHeight);
      add(hollows[i]);

      incrementY += circleHeight;
    }

    incrementY = topBasketY + boardHeight/8;
    for(int i = sideLength; i > 0; i--){
      // draw highlight is possible
      if(hollows[i].isEnabled() && hollows[i].getCount() != 0) {
        g.setColor(new Color(194, 192, 15));
        g.fillOval(bottomBasketX + topBasketX - circleLength /30, incrementY - circleHeight/30,
                circleLength + circleLength /15, circleHeight + circleHeight /15);
      }
      if(hollows[i].isClicked()){
        g.setColor(new Color(54, 194, 70));
        g.fillOval(bottomBasketX + topBasketX - circleLength /30, incrementY - circleHeight/30,
                circleLength + circleLength /15, circleHeight + circleHeight /15);
      }
      // draw hollow
      g.setColor(new Color(152, 117, 84));
      g.fillOval(bottomBasketX + topBasketX, incrementY, circleLength, circleHeight);
      g.setColor(new Color(0, 0, 0));
      g.drawString( hollows[i].getCount()+"", bottomBasketX + topBasketX + circleLength/2, incrementY + circleHeight/2);
      hollows[i].setBounds(bottomBasketX + topBasketX, incrementY, circleLength, circleHeight);
      add(hollows[i]);
      incrementY = incrementY + circleHeight;
    }
  }
}
