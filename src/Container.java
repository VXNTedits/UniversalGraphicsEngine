import javafx.scene.shape.Mesh;

import java.util.ArrayList;

public class Container {
    private ArrayList testSquare;
    private ArrayList<MeshObj3D> meshContainer;

    public Container() {
        //TODO: Initialize all geometry data
        meshContainer = new ArrayList<MeshObj3D>();
        initTestSquare();
        initUnitCube();
    }

    public void initTestSquare() {
        testSquare = new ArrayList<V2D>();
        V2D v1 = new V2D(50,50);
        V2D v2 = new V2D(100,50);
        V2D v3 = new V2D(100,100);
        V2D v4 = new V2D(50,100);
        testSquare.add(v1);
        testSquare.add(v2);
        testSquare.add(v3);
        testSquare.add(v4);
    }

    public ArrayList getTestSquare() {
        return testSquare;
    }

    public void initUnitCube() {
        MeshObj3D unitCube = new MeshObj3D();

        //south
        V3D v00 = new V3D(0,0,0);
        V3D v01 = new V3D(0,1,0);
        V3D v02 = new V3D(1,1,0);
        Triangle3D s0 = new Triangle3D(v00,v01,v02);

        V3D v03 = new V3D(0,0,0);
        V3D v04 = new V3D(1,1,0);
        V3D v05 = new V3D(1,0,0);
        Triangle3D s1 = new Triangle3D(v03,v04,v05);

        //east
        V3D v06 = new V3D(1,0,0);
        V3D v07 = new V3D(1,1,0);
        V3D v08 = new V3D(1,1,1);
        Triangle3D e0 = new Triangle3D(v06,v07,v08);

        V3D v09 = new V3D(1,0,0);
        V3D v10 = new V3D(1,1,1);
        V3D v11 = new V3D(1,0,1);
        Triangle3D e1 = new Triangle3D(v09,v10,v11);

        //north
        V3D v12 = new V3D(1,0,1);
        V3D v13 = new V3D(1,1,1);
        V3D v14 = new V3D(0,1,1);
        Triangle3D n0 = new Triangle3D(v12,v13,v14);

        V3D v15 = new V3D(1,0,1);
        V3D v16 = new V3D(0,1,1);
        V3D v17 = new V3D(0,0,1);
        Triangle3D n1 = new Triangle3D(v15,v16,v17);

        //west
        V3D v18 = new V3D(0,0,1);
        V3D v19 = new V3D(0,1,1);
        V3D v20 = new V3D(0,1,0);
        Triangle3D w0 = new Triangle3D(v18,v19,v20);

        V3D v21 = new V3D(0,0,1);
        V3D v22 = new V3D(0,1,0);
        V3D v23 = new V3D(0,0,0);
        Triangle3D w1 = new Triangle3D(v21,v22,v23);

        //top
        V3D v24 = new V3D(0,1,0);
        V3D v25 = new V3D(0,1,1);
        V3D v26 = new V3D(1,1,1);
        Triangle3D t0 = new Triangle3D(v24,v25,v26);

        V3D v27 = new V3D(0,1,0);
        V3D v28 = new V3D(1,1,1);
        V3D v29 = new V3D(1,1,0);
        Triangle3D t1 = new Triangle3D(v27,v28,v29);

        //bottom
        V3D v30 = new V3D(1,0,1);
        V3D v31 = new V3D(0,0,1);
        V3D v32 = new V3D(0,0,0);
        Triangle3D b0 = new Triangle3D(v30,v31,v32);

        V3D v33 = new V3D(1,0,1);
        V3D v34 = new V3D(0,0,0);
        V3D v35 = new V3D(1,0,0);
        Triangle3D b1 = new Triangle3D(v33,v34,v35);

        unitCube.addTriangle3D(s0);
        unitCube.addTriangle3D(s1);
        unitCube.addTriangle3D(e0);
        unitCube.addTriangle3D(e1);
        unitCube.addTriangle3D(n0);
        unitCube.addTriangle3D(n1);
        unitCube.addTriangle3D(w0);
        unitCube.addTriangle3D(w1);
        unitCube.addTriangle3D(t0);
        unitCube.addTriangle3D(t1);
        unitCube.addTriangle3D(b0);
        unitCube.addTriangle3D(b1);

        meshContainer.add(unitCube);
        System.out.println("mesh container size " + meshContainer.size());
        System.out.println("unit cube initialized");
    }

    public ArrayList<MeshObj3D> getMeshContainer() {
        return meshContainer;
    }
}
