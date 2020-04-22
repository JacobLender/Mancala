import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AvalancheBoard extends Board{
    public AvalancheBoard(){
        super();
    }
    public AvalancheBoard(int sideLength){
        super(sideLength);
    }
    public AvalancheBoard(int sideLength, int pieces){
        super(sideLength, pieces);
    }

    private boolean keepGoing = false;

    public void setUpMover(){
        pieceMover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(keepGoing){
                    currentHollow.take(movingPieces);
                    pieceCount += movingPieces.size();
                    //pieceCount++;
                    counter++;
                    keepGoing = false;
                }
                currentHollow = getHollow(playSpot + 1 + counter);        // next spot on board
                if (currentHollow.getSide() != currentPlayer.oppHome()) {      // increment if not opponent home
                    currentHollow.addPiece(movingPieces.firstElement());
                    movingPieces.remove(0);
                    repaint();
                }
                else {
                    pieceCount++;                               // if opponent home, we need to go additional spot
                }

                if (counter < pieceCount - 1)       // counter boundary
                    counter++;
                else
                {
                    if(currentHollow.getCount() != 1 && currentHollow.getSide() != currentPlayer.getHome()){
                        keepGoing = true;
                    }
                    else{
                        moved = true;
                        if (currentPlayer.getHome() == currentHollow.getSide()) {
                            retry = true;
                        }
                        pieceMover.stop();
                    }
                }
            }
        });
    }

}
