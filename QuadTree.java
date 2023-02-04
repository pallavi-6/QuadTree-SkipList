import java.awt.Point;

public class QuadTree {

    private QuadNode root = EmptyNode.getInstance();
    private Point topLeft = new Point(0, 0);
    private Point bottomRight = new Point(1024, 1024);
    private int[] nodeCount = { 0 };

    public void insert(Coordinates C) {
        root = root.insert(C, topLeft, bottomRight);
    }


    public void dump() {
        root.traverse(topLeft, bottomRight);
    }


    public void search(Coordinates C) {
        root.search(C, topLeft, bottomRight);
    }


    public void remove(Coordinates C) {
        root.remove(C, topLeft, bottomRight);
    }


    public void regionsearch(int x, int y, int w, int h) {
        root.regionsearch(x, y, w, h, topLeft, bottomRight, nodeCount);
        System.out.println(nodeCount[0] + " QuadTree Nodes Visited");
    }
}
