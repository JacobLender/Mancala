import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

public abstract class Board extends JPanel
{
  protected Hollow [] hollows;
  protected int sideLength;

  protected Hollow currentHollow;
  protected Vector<Piece> movingPieces;
  protected int spotPlayed;
  protected int pieceCount;
  protected int counter;

  public boolean retry;
  protected boolean moved = false;
  protected boolean pieceTimerSet = false;
  protected int playSpot = 0;

  public final static int TOP = 1;
  public final static int BOTTOM = -1;
  public Timer pieceMover;
  protected Player currentPlayer;



  // GUI Information
  private int width;
  private int height;
  private int circleLength;
  private int circleHeight;
  private int boardLength;
  private int boardHeight;
  private int topBasketX;
  private int topBasketY;
  private int bottomBasketX;
  private int bottomBasketY;
  private int basketHeight;
  private int basketWidth;
  private int boardY;
  private int boardX;
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
//    boardLength = getWidth()/2;
//    boardHeight = getHeight()*3/4;
//    circleLength = boardLength / 4;
//    circleHeight = boardHeight / 9;


    if (s < 2)
      sideLength = 2;
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
    System.out.println("Board completed...");
    // negative peices handled in Hollow constructor

    retry = false;
    pieceMover = new Timer(650, null);
    pieceMover.setRepeats(true);

    movingPieces = new Vector<Piece>();
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
    Vector<Piece> cleanUp = new Vector<Piece>();
    for( int i = 1; i < hollows.length; i++) {
      if( i == sideLength+1){
        hollows[sideLength+1].dumpPieces(cleanUp);
        cleanUp.clear();
      }
      else
        hollows[i].take(cleanUp);
    }
    hollows[0].dumpPieces(cleanUp);
  }
  public void movePieces(Player p, int spot){
    getHollow(spot).take(movingPieces);
    spotPlayed = spot;
    pieceCount = movingPieces.size();
    retry = false;
    counter = 0;
    playSpot = spot;
    currentPlayer = p;

    if( !pieceTimerSet) {
      pieceTimerSet = true;
      setUpMover();
    }
    pieceMover.start();
  }

  public abstract void setUpMover();
  public void setUpPieces(){
    Random rand = new Random();
    if ( circleLength/2 > 0 && circleHeight/2>0) {
      for (Hollow h : hollows) {
        for (Piece p : h.pieces) {
          p.setCord(circleLength / p.xScale /2 + circleLength/4, circleHeight / p.yScale / 3 + circleHeight/4);
        }
      }
    }
    for(Piece p : hollows[sideLength+1].pieces){
      p.setCord(basketWidth / p.xScale * 3  / 4  + basketWidth/8, basketHeight / p.yScale * 3 / 4 + basketHeight / 8);
    }
    for(Piece p : hollows[0].pieces){
      p.setCord(basketWidth / p.xScale * 3  / 4  + basketWidth/8, basketHeight / p.yScale * 3 / 4 + basketHeight / 8);
    }
  }

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
  public boolean getRetry(){
    return retry;
  }
  public Hollow getHollow(int i)
  {
    return hollows[i % hollows.length];
  }
  public boolean isHollow(Hollow x){
    for (Hollow h : hollows ){
      if( h == x) return true;
    }
    return false;
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


  // begin graphics

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    setBackground(Color.WHITE);
    setUpPieces();


//    //new board start here
//    boardLength = getWidth()/2;
//    boardX = getWidth()/2 - boardLength/2;
//
//    basketWidth = boardLength - getWidth()/20;
//    basketHeight = getHeight() / 20;
//
//    g.drawRoundRect();

    width = getWidth();
    height = getHeight();

    boardX = width / 4;
    boardY = height / 10;
    boardLength = width / 2;

    topBasketX = boardX + boardLength / 20;
    topBasketY = boardY;

    bottomBasketX = boardX + boardLength / 20;
    bottomBasketY = boardY + (circleHeight * (sideLength + 1));

    basketHeight = height / 9;
    basketWidth = boardLength - width/20;

    circleLength = boardLength / 4;
    circleHeight = height / 9;

    boardHeight = basketHeight * 2 + sideLength * circleHeight;

    g.drawString(numPieces()+"", 50, 50);

    drawBoard(g);

    g.setColor(new Color(152, 117, 84));
    g.fillRoundRect(topBasketX, topBasketY, basketWidth, basketHeight, getWidth()/20, getHeight()/20);
    for( int j = 0; j < hollows[sideLength+1].pieces.size(); j++){
      Piece p = hollows[sideLength+1].pieces.elementAt(j);
      g.setColor(p.getColor());
      g.fillOval(topBasketX + p.getX(), topBasketY + p.getY(), circleLength/3, circleHeight/2);
    }
    g.setColor(new Color(152, 117, 84));
    g.fillRoundRect(bottomBasketX, bottomBasketY, basketWidth, basketHeight, getWidth()/20, getHeight()/20);
    for( int j = 0; j < hollows[0].pieces.size(); j++){
      Piece p = hollows[0].pieces.elementAt(j);
      g.setColor(p.getColor());
      g.fillOval(bottomBasketX + p.getX(), bottomBasketY + p.getY(), circleLength/3, circleHeight/2);
    }

    g.setColor(Color.BLACK);
    g.drawString(hollows[sideLength + 1].getCount()+"", getWidth()/2, topBasketY + topBasketY/2);
    g.drawString(hollows[0].getCount()+"", getWidth()/2, bottomBasketY + boardHeight/16);

    int incrementX = topBasketX + topBasketX/4;
    int incrementY = topBasketY + basketHeight;
    for(int i = sideLength; i > 0; i--) { //int i = sideLength + 2; i < 3; i++
      //draw highlight if possible
      if(hollows[i].isEnabled() && hollows[i].getCount() != 0) {
        g.setColor(new Color(194, 192, 15));
        g.fillOval(incrementX - circleLength /30, incrementY - circleHeight/30,
                circleLength + circleLength /15, circleHeight + circleHeight /15);
      }
      if(hollows[i].isClicked()){
        g.setColor(new Color(54, 194, 70));
        g.fillOval(incrementX - circleLength /30, incrementY - circleHeight/30,
                circleLength + circleLength /15, circleHeight + circleHeight /15);
      }

      //draw hollow
      g.setColor(new Color(152, 117, 84));
      g.fillOval(incrementX, incrementY, circleLength, circleHeight);

      for( int j = 0; j < hollows[i].pieces.size(); j++){
        Piece p = hollows[i].pieces.elementAt(j);
        g.setColor(p.getColor());
        g.fillOval(incrementX + p.getX(), incrementY + p.getY(), circleLength/3, circleHeight/2);
      }

      g.setColor(Color.BLACK);
      g.drawString(  hollows[i].getCount()+ "",incrementX + circleLength/2, incrementY + circleHeight/2);
      hollows[i].setBounds(incrementX, incrementY, circleLength, circleHeight);
      add(hollows[i]);

      incrementY += circleHeight;
    }

    incrementX = bottomBasketX + topBasketX;
    incrementY = topBasketY + basketHeight;
    for(int i = sideLength; i > 0; i--){
      // draw highlight is possible
      if(hollows[i].isEnabled() && hollows[i].getCount() != 0) {
        g.setColor(new Color(194, 192, 15));
        g.fillOval(incrementX - circleLength /30, incrementY - circleHeight/30,
                circleLength + circleLength /15, circleHeight + circleHeight /15);
      }
      if(hollows[i].isClicked()){
        g.setColor(new Color(54, 194, 70));
        g.fillOval(incrementX - circleLength /30, incrementY - circleHeight/30,
                circleLength + circleLength /15, circleHeight + circleHeight /15);
      }
      // draw hollow
      g.setColor(new Color(152, 117, 84));
      g.fillOval(incrementX, incrementY, circleLength, circleHeight);

      for( int j = 0; j < hollows[i].pieces.size(); j++){
        Piece p = hollows[i].pieces.elementAt(j);
        g.setColor(p.getColor());
        g.fillOval(incrementX + p.getX(), incrementY + p.getY(), circleLength/3, circleHeight/2);
      }

      g.setColor(new Color(0, 0, 0));
      g.drawString( hollows[i].getCount()+"", incrementX + circleLength/2, incrementY + circleHeight/2);
      hollows[i].setBounds(incrementX, incrementY, circleLength, circleHeight);
      add(hollows[i]);
      incrementY = incrementY + circleHeight;
    }
  }

  private void drawBoard(Graphics g){
    g.setColor(new Color(205, 170, 125));
    g.fillRoundRect(boardX, boardY, boardLength, boardHeight, getWidth()/10, getHeight()/10);
  }
}
