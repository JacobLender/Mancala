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
    public HumanPlayer(int h){
        super(h);
    }
    public int takeTurn(Board b){

        return 0;
    }
}
