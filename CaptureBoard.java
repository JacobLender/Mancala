public class CaptureBoard extends Board {
    public CaptureBoard(){
        super();
    }
    public CaptureBoard(int sideLength){
        super(sideLength);
    }
    public CaptureBoard(int sideLength, int pieces){
        super(sideLength, pieces);
    }

    public boolean movePieces(Player p, int spot){
        int x = getHollow(spot).take();
        Hollow h = new Hollow();
        try{
            for (int i = 0; i < x; i++) {
                h = getHollow(spot + 1 + i);
                if (h.getSide() != p.oppHome()) {
                    h.increment();
                    repaint();
                    Thread.sleep(650);
                }
                else
                    x++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (p.getHome() == h.getSide()){
            return true;
        }
        else if( h.getCount() == 1 && h.getSide() == 0 && playerSide(p, spot+x)){
            if (oppositeHollow((spot+x)).getCount()!= 0) {
                // player ends on their side and with opposite hollow has pieces
                Hollow home = getHome(p);

                int y = h.take();
                int z = oppositeHollow(spot + x).take();

                home.add(y + z);
                repaint();
                return true;
            }
        }
        return false;
    }
}
