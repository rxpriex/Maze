import javax.swing.*;
import java.awt.*;

public class Pixel extends JLabel {
    private int XPositionInMaze;
    private int YPositionInMaze;

    public Pixel(int x, int y, int width, int height, Color c){
        setBounds(x,y,width,height);
        setBackground(c);
        setOpaque(true);
    }

    public int getXPositionInMaze(){
        return this.XPositionInMaze;
    }

    public void setXPositionInMaze(int i){
        this.XPositionInMaze = i;
    }

    public int getYPositionInMaze(){
        return this.YPositionInMaze;
    }

    public void setYPositionInMaze(int i){
        this.YPositionInMaze = i;
    }
}
