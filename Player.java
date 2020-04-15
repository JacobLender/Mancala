import java.util.Random;

public abstract class Player {
    protected int pieces;
    protected int home;
    public Player(){
        this(0);
    }
    public Player(int h){
        pieces = 0;
        home = h;
    }

    public abstract int takeTurn(Board b);
    //mutators
    public void setHome(int h){
        home = h;
    }

    //accessors
    public int getPieces()
    { return pieces; }
    public int getHome()
    { return home; }
    public int oppHome(){
        return home * -1;
    }
}
class HumanPlayer extends Player{
    public HumanPlayer(){
        super();
    }
    public HumanPlayer(int h){
        super(h);
    }
    public int takeTurn(Board b){
        int spot = -1;
        b.setClickable(this);  // set clickable hollows based on who is playing
        do {
            spot = b.buttonClicked();
        }while(spot == -1);        // waits for player to click on hollow
        b.disableAll();                     // all hollows now unclickable
        return spot;
    }
}

class EasyComputer extends Player{
    public EasyComputer(){
        super();
    }
    public EasyComputer(int h){
        super(h);
    }
    public int takeTurn(Board b){
        int spot;

        if(b.canPlayHome(this) != -1)
            spot = b.canPlayHome(this);
        else {
            Random rand = new Random();
            do {
                spot = rand.nextInt(b.getSideLength()) + 1;
                if (home == -1)
                    spot += b.getSideLength() + 1;
            } while (b.getHollow(spot).getCount() == 0);
        }
        b.getHollow(spot).click();
        b.disableAll();
        return spot;
    }
}
class MediumComputer extends Player{
    public MediumComputer(int h){
        super(h);
    }
    public int takeTurn(Board b){

        return 0;
    }
}
