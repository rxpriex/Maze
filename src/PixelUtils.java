import com.rxpriex.Util.array;

import java.awt.*;
import java.util.Random;

public class PixelUtils {

    private int PixelsAroundExist(PixelBlock e) {
        int x = e.getXLocation();
        int y = e.getYLocation();
        int PixelCount = 0;
        if (PixelBlockExists(getPixelBlockAt(x + 1, y))) {
            PixelCount++;
        }
        if (PixelBlockExists(getPixelBlockAt(x - 1, y))) {
            PixelCount++;
        }
        if (PixelBlockExists(getPixelBlockAt(x, y + 1))) {
            PixelCount++;
        }
        if (PixelBlockExists(getPixelBlockAt(x, y - 1))) {
            PixelCount++;
        }
        return PixelCount;
    }

    private boolean isVisited(PixelBlock e) {
        for (int i = 0; i < Gui.visited.size(); i++) {
            if (comparePixelBlocks(Gui.visited.get(i), e))
                return true;
        }
        return false;
    }

    private boolean PixelBlockExists(PixelBlock e) {
        for (int i = 0; i < Gui.pixelBlocks.size(); i++) {
            if (comparePixelBlocks(Gui.pixelBlocks.get(i), e) && !isVisited(e))
                return true;
        }
        return false;
    }

    public PixelBlock getPixelBlockAt(int x, int y) {
        for (int index = 0; index < Gui.pixelBlocks.size(); index++) {
            if (comparePixelBlocks(Gui.pixelBlocks.get(index), x, y)) {
                return Gui.pixelBlocks.get(index);
            }
        }
        return null;
    }

    private void ConnectPixelBlocks(PixelBlock a, PixelBlock b) {
        Pixel[] aArray = a.getNativePixels();
        Pixel[] bArray = b.getNativePixels();
        int[] XValues = new int[aArray.length];
        int[] YValues = new int[aArray.length];
        array rxp_a = new array();
        int A_X;
        int A_Y;
        int B_X;
        int B_Y;
        for (int i = 0; i < XValues.length; i++) {
            XValues[i] = aArray[i].getXPositionInMaze();
            YValues[i] = aArray[i].getYPositionInMaze();
        }
        A_X = XValues[XValues.length / 2];
        YValues = rxp_a.parseToIntArray(rxp_a.Bubblesort(rxp_a.parseToIntegerArray(YValues)));
        rxp_a.printArray(YValues);
        A_Y = YValues.length % 2 == 0 ? YValues[YValues.length / 2] : YValues[(YValues.length / 2) + 1];

        XValues = new int[bArray.length];
        YValues = new int[bArray.length];

        for (int i = 0; i < XValues.length; i++) {
            XValues[i] = bArray[i].getXPositionInMaze();
            YValues[i] = bArray[i].getYPositionInMaze();
        }
        B_X = XValues[XValues.length / 2];
        YValues = rxp_a.parseToIntArray(rxp_a.Bubblesort(rxp_a.parseToIntegerArray(YValues)));
        rxp_a.printArray(YValues);
        B_Y = YValues.length % 2 == 0 ? YValues[(YValues.length / 2)] : YValues[(YValues.length / 2) + 1];

        drawline(getPixelAt(A_X, A_Y), getPixelAt(B_X, B_Y));
    }

    public Pixel getPixelAt(int x, int y) {
        try {
            return Gui.pixelArrayList.get(y)[x];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private void drawline(Pixel a, Pixel b) {
        Pixel[] p = getPixelsInDistance(a, b, calculateAxis(a, b));
        PixelBlockSetColour(p, Color.BLACK);
    }

    private char calculateAxis(Pixel a, Pixel b) {
        int x1 = a.getXPositionInMaze();
        int x2 = b.getXPositionInMaze();
        int y1 = a.getYPositionInMaze();
        int y2 = b.getYPositionInMaze();

        if (x1 == x2 && y1 != y2)
            return 'y';
        else if (x1 != x2 && y1 == y2)
            return 'x';
        else
            return 'd';
    }

    private Pixel[] getPixelsInDistance(Pixel a, Pixel b, char axis) {
        Pixel[] p = new Pixel[getDistance(a, b)];
        System.out.println(getDistance(a, b));
        int pixelcount = 0;
        switch (axis) {
            case 'x':
                if (a.getXPositionInMaze() > b.getXPositionInMaze()) {
                    a = b;
                }
                int XKoordinateToStart = a.getXPositionInMaze();
                final int YKoordinate = a.getYPositionInMaze();
                for (int xpixels = 0; xpixels < p.length; xpixels++) {
                    p[pixelcount] = Gui.pixelArrayList.get(YKoordinate)[XKoordinateToStart];
                    XKoordinateToStart++;
                    pixelcount++;
                }
                break;
            case 'y':
                if (a.getYPositionInMaze() > b.getYPositionInMaze()) {
                    a = b;
                }
                int YKoordinateToStart = a.getYPositionInMaze();
                final int XKoordinate = a.getXPositionInMaze();
                for (int ypixels = 0; ypixels < p.length; ypixels++) {
                    p[pixelcount] = Gui.pixelArrayList.get(YKoordinateToStart)[XKoordinate];
                    YKoordinateToStart++;
                    pixelcount++;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + axis);
        }
        return p;
    }

    private boolean comparePixelBlocks(PixelBlock a, PixelBlock b) {
        if (a == null)
            return false;
        if (b == null)
            return false;
        return a.getXLocation() == b.getXLocation() && a.getYLocation() == b.getYLocation();
    }

    private boolean comparePixelBlocks(PixelBlock a, int x, int y) {
        if (a == null)
            return false;
        return a.getXLocation() == x && a.getYLocation() == y;
    }

    private int getDistance(Pixel a, Pixel b) {
        if (a.getYPositionInMaze() == b.getYPositionInMaze()) {
            int x1 = a.getXPositionInMaze();
            int x2 = b.getXPositionInMaze();
            return Math.max(x1, x2) - Math.min(x1, x2) + 1;
        } else if (a.getXPositionInMaze() == b.getXPositionInMaze()) {
            int y1 = a.getYPositionInMaze();
            int y2 = b.getYPositionInMaze();
            return Math.max(y1, y2) - Math.min(y1, y2) + 1;
        } else
            return -1;
    }

    private void PixelBlockSetColour(Pixel[] pixels, Color color) {
        for (int i = 0; i < pixels.length; i++) {
            Gui.pixelArrayList.get(pixels[i].getYPositionInMaze())[pixels[i].getXPositionInMaze()].setBackground(color);
        }
    }

    public PixelBlock connectToNextFreeBlock(PixelBlock a) {
       try {
           System.out.println(PixelsAroundExist(a));
           PixelBlock[] pixelBlocks1 = new PixelBlock[PixelsAroundExist(a)];
           int pixelindex = 0;
           if (PixelBlockExists(getPixelBlockAt(a.getXLocation() + 1, a.getYLocation()))) {
               pixelBlocks1[pixelindex] = getPixelBlockAt(a.getXLocation() + 1, a.getYLocation());
               pixelindex++;
           }
           if (PixelBlockExists(getPixelBlockAt(a.getXLocation() - 1, a.getYLocation()))) {
               pixelBlocks1[pixelindex] = getPixelBlockAt(a.getXLocation() - 1, a.getYLocation());
               pixelindex++;
           }
           if (PixelBlockExists(getPixelBlockAt(a.getXLocation(), a.getYLocation() + 1))) {
               pixelBlocks1[pixelindex] = getPixelBlockAt(a.getXLocation(), a.getYLocation() + 1);
               pixelindex++;
           }
           if (PixelBlockExists(getPixelBlockAt(a.getXLocation(), a.getYLocation() - 1))) {
               pixelBlocks1[pixelindex] = getPixelBlockAt(a.getXLocation(), a.getYLocation() - 1);
               pixelindex++;
           }

           Random rand = new Random();
           try {
               PixelBlock temp = pixelBlocks1[rand.nextInt(pixelBlocks1.length)];
               ConnectPixelBlocks(a, temp);
               Gui.pixelBlockCoordinatesArrayList.add(new PixelBlockCoordinates(temp.getXLocation(), temp.getYLocation()));
               return temp;
           } catch (IllegalArgumentException e) {
               try {
                   Gui.visited.add(a);
                   Gui.pixelBlockCoordinatesArrayList.remove(Gui.pixelBlockCoordinatesArrayList.size() - 1);
                   PixelBlockCoordinates temp = Gui.pixelBlockCoordinatesArrayList.get(Gui.pixelBlockCoordinatesArrayList.size() - 1);
                   return getPixelBlockAt(temp.getX(), temp.getY());
               } catch (IndexOutOfBoundsException E) {
                   return null;
               }
           }
       }catch(NullPointerException e){
           Gui.TerminateMazeGeneration();
           return null;
       }
    }
}
