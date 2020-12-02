import java.math.BigDecimal;
import java.math.RoundingMode;

public class SceneComputer {

    //projection matrix constants
    private double fNear;
    private double fFar;
    private double fov_deg;
    private double aspectRatio;
    private double fFovRad;
    private double[][] matProj; // rows x cols
    private double[][] matRotX;
    private double[][] matRotY;
    private double[][] matRotZ;
    private double fTheta;
    private double rotX, rotY, rotZ;
    private double kx,ky,kz;

    public SceneComputer(double fNear, double fFar, double fov_deg, int height, int width, double kx, double ky, double kz) {
        fTheta = 0.0;
        rotX = 0.0;
        rotY = 0.0;
        rotZ = 0.0;
        this.kx = kx;
        this.ky = ky;
        this.kz = kz;
        this.fNear = fNear;
        this.fFar = fFar;
        this.fov_deg = fov_deg;
        fFovRad = 1.0/Math.tan(0.5*Math.toRadians(fov_deg));
        aspectRatio = (double)(height)/(double)(width);
        initMatProj();
        initMatRotX(kx,rotX);
        initMatRotY(ky,rotY);
        initMatRotZ(kz,rotZ);
    }

    public void initMatProj() {
        matProj = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matProj[i][j] = 0.0;
            }
        }
        matProj[0][0] = aspectRatio * fFovRad;
        matProj[1][1] = fFovRad;
        matProj[2][2] = fFar / (fFar - fNear);
        matProj[3][2] = (-fFar * fNear) / (fFar - fNear);
        matProj[2][3] = 1.0;
        matProj[3][3] = 0.0;

        System.out.println("matProj initialized");
    }

    public void initMatRotX(double kx, double rotX) {
        matRotX = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matRotX[i][j] = 0.0;
            }
        }
        matRotX[0][0] = 1.0;
        matRotX[1][1] = Math.cos(rotX * kx);
        matRotX[1][2] = Math.sin(rotX * kx);
        matRotX[2][1] = (-1.0)*Math.sin(rotX * kx);
        matRotX[2][2] = Math.cos(rotX * kx);
        matRotX[3][3] = 1.0;
    }

    public void initMatRotY(double ky, double rotY) {
        matRotY = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matRotY[i][j] = 0.0;
            }
        }
        matRotY[0][0] = Math.cos(rotY * ky);
        matRotY[2][0] = Math.sin(rotY * ky);
        matRotY[1][1] = 1.0;
        matRotY[0][2] = (-0.1)*Math.sin(rotY * ky);
        matRotY[2][2] = Math.cos(rotY * ky);
        matRotY[3][3] = 1.0; //TODO ???
    }

    public void initMatRotZ(double kz, double rotZ) {
        matRotZ = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matRotZ[i][j] = 0.0;
            }
        }
        matRotZ[0][0] = Math.cos(rotZ * kz);
        matRotZ[0][1] = Math.sin(rotZ * kz);
        matRotZ[1][0] = (-1.0)*Math.sin(rotZ * kz);
        matRotZ[1][1] = Math.cos(rotZ * kz);
        matRotZ[2][2] = 1.0;
        matRotZ[3][3] = 1.0;
    }

    public V3D multiplyVectorMatrix(V3D vIn, double[][] matrix) {
        V3D vOut = new V3D();
        vOut.setX( vIn.getX() * matrix[0][0] + vIn.getY() * matrix[1][0] + vIn.getZ() * matrix[2][0] + matrix[3][0] );
        vOut.setY( vIn.getX() * matrix[0][1] + vIn.getY() * matrix[1][1] + vIn.getZ() * matrix[2][1] + matrix[3][1] );
        vOut.setZ( vIn.getX() * matrix[0][2] + vIn.getY() * matrix[1][2] + vIn.getZ() * matrix[2][2] + matrix[3][2] );
        double w = vIn.getX() * matrix[0][3] + vIn.getY() * matrix[1][3] + vIn.getZ() * matrix[2][3] + matrix[3][3];
        if ( w != 0.0 ) {
            vOut.setX( vOut.getX() / w );
            vOut.setY( vOut.getY() / w );
            vOut.setZ( vOut.getZ() / w );
        }
        return vOut;
    }

    public V3D computeNorm(Triangle3D triangle) {
        V3D l1 = new V3D();
        l1.setX( triangle.getTriangle()[1].getX() - triangle.getTriangle()[0].getX() );
        l1.setY( triangle.getTriangle()[1].getY() - triangle.getTriangle()[0].getY() );
        l1.setZ( triangle.getTriangle()[1].getZ() - triangle.getTriangle()[0].getZ() );

        V3D l2 = new V3D();
        l2.setX( triangle.getTriangle()[2].getX() - triangle.getTriangle()[0].getX() );
        l2.setY( triangle.getTriangle()[2].getY() - triangle.getTriangle()[0].getY() );
        l2.setZ( triangle.getTriangle()[2].getZ() - triangle.getTriangle()[0].getZ() );

        V3D norm = new V3D();
        norm.setX( l1.getY()*l2.getZ() - l1.getZ()*l2.getY() );
        norm.setY( l1.getZ()*l2.getX() - l1.getX()*l2.getZ() );
        norm.setZ( l1.getX()*l2.getY() - l1.getY()*l2.getX() );
        norm.normalize();

        return norm;
    }

    public double computeDotProd(V3D u, V3D v) {
        double dotProd = u.getX()*v.getX() + u.getY()*v.getY() + u.getZ()*v.getZ();
        return dotProd;
    }

    public V3D computeDistanceVector(V3D u, V3D v) {
        V3D d = new V3D(u.getX() - v.getX(),u.getY() - v.getY(),u.getZ() - v.getZ() );
        return d;
    }

    //TODO: This method is obselete I think
    public V3D normalize(V3D v) {
        double lvl = v.getLength();
        V3D vNorm = new V3D( v.getX()/lvl, v.getY()/lvl, v.getZ()/lvl );
        return vNorm;
    }

    public Triangle3D sortTriangleByAscendingY( Triangle3D triUnsorted) {
        Triangle3D newTri = triUnsorted;
        boolean sorted = false;
        double temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < newTri.getTriangle().length - 1; i++) {
                if (newTri.getTriangle()[i].getY() > newTri.getTriangle()[i+1].getY()) {
                    temp = newTri.getTriangle()[i].getY();
                    newTri.getTriangle()[i].setY( newTri.getTriangle()[i+1].getY() );
                    newTri.getTriangle()[i+1].setY( temp );
                    sorted = false;
                }
            }
        }
//        System.out.println(
//                  "v0.y=" + round(newTri.getTriangle()[0].getY(),1) +
//                ", v1.y=" + round(newTri.getTriangle()[1].getY(),1) +
//                ", v2.y=" + round(newTri.getTriangle()[2].getY(),1));
        return newTri;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public double[][] getMatProj() {
        return matProj;
    }

    public double[][] getMatRotX() {
        return matRotX;
    }

    public double[][] getMatRotY() {
        return matRotY;
    }

    public double[][] getMatRotZ() {
        return matRotZ;
    }

    public double[][] getMatRotZ(double rotZ) {
        initMatRotZ(kz,rotZ);
        return matRotZ;
    }

    public double getRotX() {
        return rotX;
    }

    public void setRotX(double rotX) {
        this.rotX = rotX;
    }

    public double getRotY() {
        return rotY;
    }

    public void setRotY(double rotY) {
        this.rotY = rotY;
    }

    public double getRotZ() {
        return rotZ;
    }

    public void setRotZ(double rotZ) {
        this.rotZ = rotZ;
    }

    public void update(int ticksElapsed) {
        //fTheta += 0.05;
        //TODO
    }
}
