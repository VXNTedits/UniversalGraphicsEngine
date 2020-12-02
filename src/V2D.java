public class V2D {
    private double x;
    private double y;

    public V2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public V2D() {
        x = 0;
        y = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
