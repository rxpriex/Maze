import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Gui extends PixelUtils{
    Frame frame = new Frame(new Dimension(1000,600));

    Panel panel = new Panel();

    static ArrayList<Pixel[]> pixelArrayList;

    int PIXEL_AMOUNT = 0;

    int PIXEL_SIZE;

    int PIXELBLOCK_SIZE;

    static ArrayList<PixelBlock> pixelBlocks;

    static ArrayList<PixelBlockCoordinates> pixelBlockCoordinatesArrayList = new ArrayList<>();

    static ArrayList<PixelBlock> visited = new ArrayList<>();

    static boolean running = true;

    public Gui(int pixelWidth, int pixelHeight, Color defaultColor, int space, int blocksize) {
        PIXEL_SIZE = pixelHeight;
        frame.add(panel);
        pixelArrayList = GeneratePixels(pixelHeight,pixelWidth,defaultColor,space);
        LoadUpPixelsFrom(pixelArrayList, false, panel);
        pixelBlocks = LoadPixelsIntoArray(blocksize);
        frame.setSize(frame.getWidth() + PIXEL_SIZE, frame.getHeight() + 37);
        frame.setVisible(true);
        GenerateMaze(5,5);
    }

    private void GenerateMaze(int StartX, int StartY){
        PixelBlock px = getPixelBlockAt(StartX,StartY);
        visited.add(px);
        running = true;
        while(running){
            try{
                px = connectToNextFreeBlock(px);
                visited.add(px);
            }catch (IndexOutOfBoundsException e){
                px = connectToNextFreeBlock(px);
                visited.add(px);
            }
        }
    }

    public static void TerminateMazeGeneration(){
        running = false;
        pixelBlockCoordinatesArrayList.clear();
        visited.clear();
    }

    private PixelBlock MakePixelArray(int X, int Y, int size){
        Pixel[] pixelsBlock = new Pixel[size * size];
        int pixelcounter = 0;
        for (int y = Y * size; y < size * Y + 3; y++) {
            for (int x = X * size; x < size * X + 3; x++) {
                pixelsBlock[pixelcounter] = getPixelAt(x,y);
                pixelcounter++;
            }
        }
        return new PixelBlock(pixelsBlock,X,Y);
    }

    private ArrayList<PixelBlock> LoadPixelsIntoArray(int size){
        ArrayList<PixelBlock> pixels = new ArrayList<>();
        for (int Y = 0; Y < ((frame.getHeight() / PIXEL_SIZE) / size); Y++) {
            for (int X = 0; X < ((frame.getWidth() / PIXEL_SIZE) / size); X++) {
                pixels.add(MakePixelArray(X,Y,size));
            }
        }
        PIXELBLOCK_SIZE = size;
        return pixels;
    }

    private void LoadUpPixelsFrom(ArrayList<Pixel[]> from, boolean defaultColor, JPanel jPanel) {
        for (int i = 0; i < from.size(); i++) {
            for (int j = 0; j < from.get(i).length; j++) {
                jPanel.add(from.get(i)[j]);
                if (defaultColor)
                    from.get(i)[j].setBackground(Color.white);
            }
        }
    }

    private ArrayList<Pixel[]> GeneratePixels(int pixelHeight, int pixelWidth, Color defaultColor, int space){
        ArrayList<Pixel[]> temp = new ArrayList<>();
        int x = 0;
        int y = 0;
        for (int Y = 0; Y < frame.getHeight(); Y += pixelHeight + space) {
            Pixel[] px = new Pixel[frame.getWidth() / pixelWidth];
            int pxcounter = 0;
            for (int X = 0; X < frame.getWidth(); X += pixelWidth + space) {
                px[pxcounter] = new Pixel(X,Y,pixelWidth,pixelHeight,defaultColor);
                px[pxcounter].setXPositionInMaze(x);
                px[pxcounter].setYPositionInMaze(y);
                pxcounter++;
                PIXEL_AMOUNT++;
                x++;
            }
            temp.add(px);
            x = 0;
            y++;
        }
        return temp;
    }
}