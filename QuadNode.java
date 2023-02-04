import java.awt.Point;

public interface QuadNode {
    public boolean isLeaf();


    public QuadNode insert(Coordinates C, Point topLeft, Point bottomRight);


    public void traverse(Point topLeft, Point bottomRight);


    public void search(Coordinates C, Point topLeft, Point bottomRight);


    public QuadNode remove(Coordinates C, Point topLeft, Point bottomRight);


    public void regionsearch(
        int x,
        int y,
        int w,
        int h,
        Point topLeft,
        Point bottomRight,
        int[] nodeCount);

}
