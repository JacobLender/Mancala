public abstract class Player {
    protected int pieces;
    protected String side;
    public Player(){
        this("none");
    }
    public Player( String s ){
        pieces = 0;
        side = s;
    }
    public abstract void takeTurn();

    //accessors
    public int getPieces()
    { return pieces; }

}
class HumanPlayer extends Player{
    public HumanPlayer(String s){
        super(s);
    }
    public void takeTurn(){

    }
}
