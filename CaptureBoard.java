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
                currentHollow = getHollow(playSpot + 1 + i);        // next spot on board
                if (currentHollow.getSide() != currentPlayer.oppHome()) {      // increment if not opponent home
                    currentHollow.increment();
                    System.out.println("Increment done");
                    repaint();
                } else {
                    pieceCount++;                                           // if opponent home, we need to go additional spot
                    System.out.print("Pieces is incremented");
                }
                if (i < pieceCount - 1)       // counter boundary
                    i++;
                else {
                    pieceMover.stop();
                    moved = true;
                    System.out.println("Moved set to true...");

                    if (currentPlayer.getHome() == currentHollow.getSide()) {
                        System.out.println("User scored!");
                        retry = true;
                    } else if (currentHollow.getCount() == 1 && currentHollow.getSide() == 0 && playerSide(currentPlayer, playSpot + pieceCount)
                            && oppositeHollow((playSpot + pieceCount)).getCount() != 0) {

                        Hollow home = getHome(currentPlayer);
                        int y = currentHollow.take();
                        int z = oppositeHollow(playSpot + pieceCount).take();
                        home.add(y + z);
                        repaint();
                        retry = true;
                    }
                }
            }
        });
    }
}
