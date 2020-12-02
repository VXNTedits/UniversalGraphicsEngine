import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class Triangle3D {
    private V3D[] triangle = new V3D[3];
    private int color;
    private boolean initTrans;
    public Triangle3D(V3D v0, V3D v1, V3D v2) {
        color = 16777215;   // white - default
        triangle[0] = v0;
        triangle[1] = v1;
        triangle[2] = v2;
        initTrans = false;
    }

    public void initTrans(double dx, double dy, double dz) {
        if ( !initTrans ) {
            triangle[0].setX(triangle[0].getX()+dx);
            triangle[0].setY(triangle[0].getY()+dy);
            triangle[0].setZ(triangle[0].getZ()+dz);
            triangle[1].setX(triangle[1].getX()+dx);
            triangle[1].setY(triangle[1].getY()+dy);
            triangle[1].setZ(triangle[1].getZ()+dz);
            triangle[2].setX(triangle[2].getX()+dx);
            triangle[2].setY(triangle[2].getY()+dy);
            triangle[2].setZ(triangle[2].getZ()+dz);
            initTrans = true;
        }

    }

    public V3D[] getTriangle() {
        return triangle;
    }

    public int getColor() {
        return color;
    }

    public void overwrite(V3D v0, V3D v1, V3D v2) {
        triangle[0] = v0;
        triangle[1] = v1;
        triangle[2] = v2;
    }

}
