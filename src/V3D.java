public class V3D {
    private double x;
    private double y;
    private double z;
    private boolean initTrans;

    public V3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        initTrans = false;
    }

    public V3D() {
        x = 0;
        y = 0;
        z = 0;
        initTrans = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() { return z; }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) { this.z = z; }

    public void initTrans(double dx, double dy, double dz) {
        if ( !initTrans ) {
            setX(x+dx);
            setY(y+dy);
            setZ(z+dz);
            initTrans = true;
        }

    }

    public void normalize() {
        double lvl = getLength();
        setX(getX()/lvl);
        setY(getY()/lvl);
        setZ(getZ()/lvl);
    }

    public double getLength() {
        double lvl = Math.sqrt( x*x + y*y + z*z );
        return lvl;
    }

    public V2D toV2D() {
        V2D vOut = new V2D(x,y);
        return vOut;
    }

    public void printCoords() {
        System.out.println("x = " + x + "; y = " + y + "; z = " + z);
    }
}
