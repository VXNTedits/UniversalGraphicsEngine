import java.util.ArrayList;

public class MeshObj3D {
    private ArrayList<Triangle3D> mesh;

    public MeshObj3D() {
        mesh = new ArrayList<Triangle3D>();
    }

    public void addTriangle3D(Triangle3D t) {
        mesh.add(t);
    }

    public ArrayList<Triangle3D> getMesh() {
        return mesh;
    }
}
