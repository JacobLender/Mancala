import java.util.Random;

public abstract class Player{
    protected int home;
    protected String name;
    protected boolean bot;
    public Player(){
        this(0);
    }
    public Player(int h){
        home = h;
        if(home == -1)
            name = "Player 1";
        else
            name = "Player 2";
    }

    public void setHome(int h){
        home = h;
    }

    //accessors
    public int getHome()
    { return home; }
    public String getName(){
        return name;
    }
    public boolean isBot(){
        return bot;
    }
    public int oppHome(){
        return home * -1;
    }

    public int canPlayHome(Board b){
        // will play home with closes peices
        int start = b.hollowsLength();
        if( home == 1)
            start = start - b.getSideLength() - 1;

        for( int i = start - 1; i > start - b.getSideLength() - 1; i--){
            if( i + b.getHollow(i).getCount() == start)
                return i;
        }
        return -1;
    }

    public abstract void takeTurn(Board b);
}

class HumanPlayer extends Player{
    public HumanPlayer(){
        super();
    }
    public HumanPlayer(int h){
        super(h);
        bot = false;
    }
    public void takeTurn(Board b) {

    }
}

class EasyComputer extends Player{
    public EasyComputer(){
        super();
    }
    public EasyComputer(int h){
        super(h);
        bot = true;
    }
    public void takeTurn(Board b){
        int spot;

        if(canPlayHome(b) != -1)
            spot = canPlayHome(b);
        else {
            Random rand = new Random();
            do {
                spot = rand.nextInt(b.getSideLength()) + 1;
                if (home == Board.BOTTOM)
                    spot += b.getSideLength() + 1;
            } while (b.getHollow(spot).getCount() == 0);
        }
        System.out.println("I picked " + spot + "!");
        b.getHollow(spot).click();
        b.disableAll();

    }
}
class MediumComputer extends Player{
    public MediumComputer(int h){
        super(h);
    }
    public void takeTurn(Board b){

    }
}
