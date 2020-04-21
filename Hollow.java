import javax.swing.*;
import java.util.Random;
import java.util.Vector;

// these are the spots on the board
class Hollow extends JButton
{
    private int side;
    public Vector<Piece> pieces;

    boolean isClicked;  // indicate user input to board / game

    public Hollow()
    {
        this(0, 0);
    }
    public Hollow(int numPieces, int theside)
    {
        side = theside;
        isClicked = false;

        pieces = new Vector<Piece>();
        Random rand = new Random();
        for(int i = 0; i < numPieces;  i++){
            pieces.add(new Piece(rand.nextInt(10) + 1, rand.nextInt(10) + 1));
        }
    }

    // accessors
    public int getCount()   // mumber of peices
    { return pieces.size(); }
    public boolean empty()
    { return pieces.size() == 0; }
    public int getSide(){
        return side;
    }
    public boolean isClicked(){
        return isClicked;
    }
    public void click(){ isClicked = true;}
    public void unClick(){
        isClicked = false;
    }

    // mutators
    public void addPiece(Piece p){
        pieces.add(p);
    }
    public void dumpPieces(Vector<Piece> v){
        pieces.addAll(v);
        v.clear();
    }
    public void take(Vector<Piece> v)
    // simulates a player taking peices from a hollow
    {
        v.addAll(pieces);
        pieces.clear();
    }
}
