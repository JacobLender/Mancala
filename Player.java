public abstract class Player {
    protected int pieces;
    protected String side;
    Player(){
        this("none");
    }
    Player( String s ){
        pieces = 0;
        side = s;
    }
    public abstract void takeTurn();

    //accessors
    public int getPieces()
    { return pieces; }

}
class HumanPlayer extends Player{
    public void takeTurn(){

    }
}
