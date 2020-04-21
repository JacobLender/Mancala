import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CaptureBoard extends Board{
    public CaptureBoard(){
        super();
    }
    public CaptureBoard(int sideLength){
        super(sideLength);
    }
    public CaptureBoard(int sideLength, int pieces){
        super(sideLength, pieces);
    }

    public void setUpMover(){
        pieceMover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentHollow = getHollow(playSpot + 1 + counter);        // next spot on board
                if (currentHollow.getSide() != currentPlayer.oppHome()) {      // increment if not opponent home
                    currentHollow.addPiece(movingPieces.firstElement());
                    movingPieces.remove(0);
                    System.out.println("Increment done");
                    repaint();
                } else {
                    pieceCount++;                             // if opponent home, we need to go additional spot
                }
                if (counter < pieceCount - 1)       // counter boundary
                    counter++;
                else {
                    pieceMover.stop();
                    moved = true;

                    if (currentPlayer.getHome() == currentHollow.getSide()) {
                        System.out.println("User scored!");
                        retry = true;
                    } else if (currentHollow.getCount() == 1 && currentHollow.getSide() == 0 && playerSide(currentPlayer, playSpot + pieceCount)
                            && oppositeHollow((playSpot + pieceCount)).getCount() != 0) {

                        Hollow home = getHome(currentPlayer);
                        currentHollow.take(movingPieces);
                        oppositeHollow(playSpot + pieceCount).take(movingPieces);
                        home.dumpPieces(movingPieces);
                        repaint();
                        retry = true;
                    }
                }
            }
        });
    }
}
