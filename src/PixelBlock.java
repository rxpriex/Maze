public class PixelBlock {
    Pixel[] pixel;

    private int XLocation;

    private int YLocation;

    PixelBlock(Pixel[] pixels, int x, int y){
        this.pixel = pixels;
        this.XLocation = x;
        this.YLocation = y;
    }

    public Pixel[] getNativePixels(){
        return this.pixel;
    }

    public int getYLocation() {
        return YLocation;
    }

    public int getXLocation() {
        return XLocation;
    }
}
