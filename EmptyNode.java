import java.awt.Point;

public class EmptyNode implements QuadNode {

    public boolean isLeaf() {
        return true;
    }


    private EmptyNode() {
    };

    private static EmptyNode flyweight = new EmptyNode();

    public static EmptyNode getInstance() {
        return flyweight;
    }


    public QuadNode insert(
        Coordinates value,
        Point topLeft,
        Point bottomRight) {
// System.out.println("Empty node");
        QuadLeafNode k = new QuadLeafNode();
        return (k.insert(value, topLeft, bottomRight));
    }


    public void traverse(Point topLeft, Point bottomRight) {
        int n = (1024 / (bottomRight.x - topLeft.x) - 1);
        if (n != 0)
            System.out.format("%1$" + n + "s", "");
        System.out.print("Node at " + topLeft.x + ", " + topLeft.y + ", "
            + (bottomRight.x - topLeft.x) + ":");
        System.out.println(" Empty");
    }


    public void search(Coordinates C, Point topLeft, Point bottomRight) {

    }


    public QuadNode remove(Coordinates C, Point topLeft, Point bottomRight) {
        return null;
    }


    public void regionsearch(
        int x,
        int y,
        int w,
        int h,
        Point topLeft,
        Point bottomRight,
        int[] nodeCount) {

        nodeCount[0]++;
    }

}
