public class Board
{
  private Hollow [] hollows;
  /*
  This could be one of the advanced functionality, where the user can choose
  how many spots are on the board... may have to limit this for graphics
  purposes.

  For now, lets not worry too much about this. We will use the standard
  size (12).

  */

  // hollows at each end
  private Hollow bottom = new Hollow();
  private Hollow top = new Hollow();

  /* we are going to need a standard way to label the board
  Here is what I am thinking:

      top
     (6) (5)
     (7) (4)
     (8) (3)
     (9) (2)
     (10) (1)
     (11) (0)
     bottom

  */

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

  public boolean isEmpty()
  {
    /*
    This will be an indicator for when the game ends
    Technically a game board is empty even if the end hollows have pieces
    */
    for ( Hollow h : hollows )
    {
      if ( !h.empty() ) return false;
    }
    return true;
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
