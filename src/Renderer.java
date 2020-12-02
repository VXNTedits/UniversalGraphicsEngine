import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;

public class Renderer {

    private int width;
    private int height;
    private int[] p;
    private SceneComputer sc;
    //TODO: Camera vector is temporary. Implement actual camera next.
    private V3D vCam;

    public Renderer(Window window, int width, int height) {
        vCam = new V3D(0,0,0);
        sc = new SceneComputer(0.1,1000,90, height, width,1,1,1);
        this.width = width;
        this.height = height;
        p = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
    }

    public void setPixel(int x, int y, int color_int) {
        if ( x < 0 || x >= width || y < 0 || y >= height ) {
            return; //check if the requested pixel is within the possible drawable area
        }
        p[x+(y*width)] = color_int;
    }

    public void drawLine(V2D v0, V2D v1, int color_int) { //type might be wrong here. check when you need to go to int from double
        double x0 = v0.getX();
        double y0 = v0.getY();
        double x1 = v1.getX();
        double y1 = v1.getY();

        int dx = (int)Math.abs(x1 - x0);
        int dy = (int)Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx-dy;
        int e2;
        int currentX = (int)x0;
        int currentY = (int)y0;

        while(true) {
            setPixel(currentX,currentY,color_int);
            if(currentX == x1 && currentY == y1) {
                break;
            }
            e2 = 2*err;
            if(e2 > -1 * dy) {
                err = err - dy;
                currentX = currentX + sx;
            }
            if(e2 < dx) {
                err = err + dx;
                currentY = currentY + sy;
            }
        }
    }
    public void drawLine(int x0, int y0, int x1, int y1, int color) {

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx-dy;
        int e2;
        int currentX = x0;
        int currentY = y0;

        while(true) {
            setPixel(currentX,currentY,color);
            if(currentX == x1 && currentY == y1) {
                break;
            }
            e2 = 2*err;
            if(e2 > -1 * dy) {
                err = err - dy;
                currentX = currentX + sx;
            }
            if(e2 < dx) {
                err = err + dx;
                currentY = currentY + sy;
            }
        }
    }
    public void draw2dShape(ArrayList<V2D> shape, int color_int) {    // shapes are drawn 2 indices at a time line(0,1), line(1,2) etc.
        for (int i = 0; i < shape.size()-1; i++) {
            drawLine(shape.get(i),shape.get(i+1), color_int);
        }
        drawLine(shape.get(shape.size()-1),shape.get(0),color_int);
    }

    public void drawRect(int offX, int offY, int width, int height, int color) {
        for (int y = 0; y <=height ; y++) {
            setPixel(offX,y+offY,color);
            setPixel(offX+width,y+offY,color);
        }
        for (int x = 0; x <=height ; x++) {
            setPixel(x+offX,offY,color);
            setPixel(x+offX,offY+height,color);
        }
    }
    public void drawTriangle(Triangle3D triangle, int color) {

        drawLine( (int)(triangle.getTriangle()[0].getX()), (int)(triangle.getTriangle()[0].getY()),
                  (int)(triangle.getTriangle()[1].getX()), (int)(triangle.getTriangle()[1].getY()),color );

        drawLine( (int)(triangle.getTriangle()[1].getX()), (int)(triangle.getTriangle()[1].getY()),
                  (int)(triangle.getTriangle()[2].getX()), (int)(triangle.getTriangle()[2].getY()),color );

        drawLine( (int)(triangle.getTriangle()[0].getX()), (int)(triangle.getTriangle()[0].getY()),
                  (int)(triangle.getTriangle()[2].getX()), (int)(triangle.getTriangle()[2].getY()),color );
    }

    public void fillTriangle(Triangle3D triangle, int color) {
        double d1 = Math.sqrt( Math.pow(triangle.getTriangle()[1].getY() - triangle.getTriangle()[0].getY(),2) +
                               Math.pow(triangle.getTriangle()[1].getX() - triangle.getTriangle()[0].getX(),2)
        );
        double d2 = Math.sqrt( Math.pow(triangle.getTriangle()[2].getY() - triangle.getTriangle()[1].getY(),2) +
                               Math.pow(triangle.getTriangle()[2].getX() - triangle.getTriangle()[1].getX(),2)
        );
        double d3 = Math.sqrt( Math.pow(triangle.getTriangle()[0].getY() - triangle.getTriangle()[2].getY(),2) +
                               Math.pow(triangle.getTriangle()[0].getX() - triangle.getTriangle()[2].getX(),2)
        );
        if ( d1<=d2 ) {
            double tx = triangle.getTriangle()[0].getX();
            double ty = triangle.getTriangle()[0].getY();
            double vx = (triangle.getTriangle()[1].getX()-triangle.getTriangle()[0].getX())/d1;
            double vy = (triangle.getTriangle()[1].getY()-triangle.getTriangle()[0].getY())/d1;
            for (int i = 0; i < (int)d1; i++) {
                drawLine((int)triangle.getTriangle()[2].getX(),(int)triangle.getTriangle()[2].getY(),(int)tx,(int)ty,65280);//green
                tx+=vx;
                ty+=vy;
            }
        } else if ( d2<=d3 ) {
            double tx = triangle.getTriangle()[1].getX();
            double ty = triangle.getTriangle()[1].getY();
            double vx = (triangle.getTriangle()[2].getX()-triangle.getTriangle()[1].getX())/d2;
            double vy = (triangle.getTriangle()[2].getY()-triangle.getTriangle()[1].getY())/d2;
            for (int i = 0; i < (int)d2; i++) {
                drawLine((int)triangle.getTriangle()[1].getX(),(int)triangle.getTriangle()[1].getY(),(int)tx,(int)ty,16711680);//red
                tx+=vx;
                ty+=vy;
            }
        } else {
            double tx = triangle.getTriangle()[2].getX();
            double ty = triangle.getTriangle()[2].getY();
            double vx = (triangle.getTriangle()[0].getX()-triangle.getTriangle()[2].getX())/d2;
            double vy = (triangle.getTriangle()[0].getY()-triangle.getTriangle()[2].getY())/d2;
            for (int i = 0; i < (int)d3; i++) {
                drawLine((int)triangle.getTriangle()[1].getX(),(int)triangle.getTriangle()[1].getY(),(int)tx,(int)ty,16711680);//???
                tx+=vx;
                ty+=vy;
            }
        }
    }
    /*
    public void fillTriangle(Triangle3D triangle, int color) {
        //TODO: http://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html

        // 1. sort vertices by y coordinate ascending
        Triangle3D sortedTri = sc.sortTriangleByAscendingY(triangle);

        // 2. check for the trivial case of a bottom-flat triangle
        if( sortedTri.getTriangle()[1].getY() == sortedTri.getTriangle()[2].getY() ) {
            fillBottomFlatTriangle(sortedTri,color);
        }
        // 3. check for the trivial case of a top-flat triangle
        else if( sortedTri.getTriangle()[0].getY() == sortedTri.getTriangle()[1].getY() ) {
            fillTopFlatTriangle(triangle,color);
        }
        // 4. general case - split the triangle into a top-flat and bottom-flat

        else {
            V3D v4 = new V3D();
            V3D v0 = sortedTri.getTriangle()[0];
            V3D v1 = sortedTri.getTriangle()[1];
            V3D v2 = sortedTri.getTriangle()[2];

            double x4 = (v0.getX() + ( (v1.getY() - v0.getY()) / (v2.getY() - v0.getY()) ) * (v2.getX() - v0.getX()));

            v4.setX(x4);
            v4.setY(v1.getY());
            Triangle3D tBot = new Triangle3D(v0,v1,v4);
            Triangle3D tTop = new Triangle3D(v1,v4,v2);

            fillBottomFlatTriangle(tBot,color);
            fillTopFlatTriangle(tTop,color);
        }
    }
    private void fillBottomFlatTriangle(Triangle3D t, int color) {
        double invslope1 = (t.getTriangle()[1].getX() - t.getTriangle()[0].getX())
                         / (t.getTriangle()[1].getY() - t.getTriangle()[0].getY());
        double invslope2 = (t.getTriangle()[2].getX() - t.getTriangle()[0].getX())
                         / (t.getTriangle()[2].getY() - t.getTriangle()[0].getY());

        double curx1 = t.getTriangle()[0].getX();
        double curx2 = t.getTriangle()[0].getX();

        for (int scanlineY = (int)t.getTriangle()[0].getY(); scanlineY <= t.getTriangle()[1].getY(); scanlineY++) {
            drawLine((int)curx1,scanlineY,(int)curx2,scanlineY,65280); //TODO green [debug]
            curx1 += invslope1;
            curx2 += invslope2;
        }
        //System.out.println("fillBottomFlatTriangle();");
    }
    private void fillTopFlatTriangle(Triangle3D t, int color) {
        double invslope1 = (t.getTriangle()[2].getX() - t.getTriangle()[0].getX())
                         / (t.getTriangle()[2].getY() - t.getTriangle()[0].getY());
        double invslope2 = (t.getTriangle()[2].getX() - t.getTriangle()[1].getX())
                         / (t.getTriangle()[2].getY() - t.getTriangle()[1].getY());

        double curx1 = t.getTriangle()[2].getX();
        double curx2 = t.getTriangle()[2].getX();

        for (int scanlineY = (int)t.getTriangle()[2].getY(); scanlineY > t.getTriangle()[0].getY(); scanlineY--) {
            drawLine((int)curx1,scanlineY,(int)curx2,scanlineY,16711680); //TODO red [debug]
            curx1 -= invslope1;
            curx2 -= invslope2;
        }
        //System.out.println("fillTopFlatTriangle();");


    }
    */
    public void clear() {
        // black
        Arrays.fill(p, 3289650);
    }
    /**
     * TODO: Refactor for general use.
     */
    public void drawMesh(MeshObj3D mesh, int color_int) {

        for (int i = 0; i < mesh.getMesh().size(); i++) { // For every triangle in the mesh: mesh.getMesh().size()

            // 1. rotate
            sc.initMatRotX(0.1, sc.getRotX());
            sc.initMatRotY(0.1, sc.getRotY());
            sc.initMatRotZ(0.1, sc.getRotZ());

            Triangle3D triRot = new Triangle3D(
                    sc.multiplyVectorMatrix(mesh.getMesh().get(i).getTriangle()[0], sc.getMatRotZ(sc.getRotZ())),
                    sc.multiplyVectorMatrix(mesh.getMesh().get(i).getTriangle()[1], sc.getMatRotZ(sc.getRotZ())),
                    sc.multiplyVectorMatrix(mesh.getMesh().get(i).getTriangle()[2], sc.getMatRotZ(sc.getRotZ()))
            );
            triRot.overwrite(
                    sc.multiplyVectorMatrix(triRot.getTriangle()[0], sc.getMatRotY()),
                    sc.multiplyVectorMatrix(triRot.getTriangle()[1], sc.getMatRotY()),
                    sc.multiplyVectorMatrix(triRot.getTriangle()[2], sc.getMatRotY())
            );
            triRot.overwrite(
                    sc.multiplyVectorMatrix(triRot.getTriangle()[0], sc.getMatRotX()),
                    sc.multiplyVectorMatrix(triRot.getTriangle()[1], sc.getMatRotX()),
                    sc.multiplyVectorMatrix(triRot.getTriangle()[2], sc.getMatRotX())
            );

            // 2. translate
            Triangle3D triTrans = new Triangle3D(triRot.getTriangle()[0],
                                                 triRot.getTriangle()[1],
                                                 triRot.getTriangle()[2]
            );

            triTrans.getTriangle()[0].initTrans(0,0,5);
            triTrans.getTriangle()[1].initTrans(0,0,5);
            triTrans.getTriangle()[2].initTrans(0,0,5);

            // 3. solidify
            V3D ray = sc.computeDistanceVector(triTrans.getTriangle()[0],vCam);
            if ( sc.computeDotProd( sc.computeNorm(triTrans),ray ) < 0 ) {

                // 4. project
                Triangle3D triProj = new Triangle3D(
                        sc.multiplyVectorMatrix( triTrans.getTriangle()[0], sc.getMatProj() ),
                        sc.multiplyVectorMatrix( triTrans.getTriangle()[1], sc.getMatProj() ),
                        sc.multiplyVectorMatrix( triTrans.getTriangle()[2], sc.getMatProj() )
                );
                // 5. scale
                triProj.getTriangle()[0].setX(triProj.getTriangle()[0].getX()+1.0);
                triProj.getTriangle()[0].setY(triProj.getTriangle()[0].getY()+1.0);

                triProj.getTriangle()[1].setX(triProj.getTriangle()[1].getX()+1.0);
                triProj.getTriangle()[1].setY(triProj.getTriangle()[1].getY()+1.0);

                triProj.getTriangle()[2].setX(triProj.getTriangle()[2].getX()+1.0);
                triProj.getTriangle()[2].setY(triProj.getTriangle()[2].getY()+1.0);

                triProj.getTriangle()[0].setX(triProj.getTriangle()[0].getX()*0.45*(double)(width));
                triProj.getTriangle()[0].setY(triProj.getTriangle()[0].getY()*0.45*(double)(height));

                triProj.getTriangle()[1].setX(triProj.getTriangle()[1].getX()*0.45*(double)(width));
                triProj.getTriangle()[1].setY(triProj.getTriangle()[1].getY()*0.45*(double)(height));

                triProj.getTriangle()[2].setX(triProj.getTriangle()[2].getX()*0.45*(double)(width));
                triProj.getTriangle()[2].setY(triProj.getTriangle()[2].getY()*0.45*(double)(height));
                // 5. draw
                drawTriangle(triProj,triProj.getColor());
                fillTriangle(triProj,triProj.getColor()); //TODO: Replace with fill triangle method
            }


        }
    }

    public SceneComputer getSc() {
        return sc;
    }

    public void update(int ticksElapsed) {
        sc.update(ticksElapsed);
    }
}
