import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// these are the spots on the board
class Hollow extends JButton implements ActionListener
{
    private int count;  // the number of peices in each hollow
    private int side;

    boolean isClicked;  // indicate user input to board / game

    public Hollow()
    {
        this(0, 0);
    }
    public Hollow(int pieces, int theside)
    {
        if ( pieces < 0 )
            count = 0;
        else
            count = pieces;

        side = theside;
        isClicked = false;
        addActionListener(this);
    }

    // accessors
    public int getCount()   // mumber of peices
    { return count; }
    public boolean empty()
    { return count == 0; }
    public int getSide(){
        return side;
    }
    public boolean isClicked(){
        return isClicked;
    }
    public void unClick(){
        isClicked = false;
    }

    // mutators
    public void increment()
    {
        add(1);
    }

    public void add(int i)
    {
        count += i;
    }

    public int take()
    // simulates a player taking peices from a hollow
    {
        int x = count;
        count = 0;
        return x;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        isClicked = true;
    }
}
