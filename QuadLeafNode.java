import java.awt.Point;
import java.awt.Rectangle;
import java.lang.Object;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class QuadLeafNode implements QuadNode {
    private LinkedList points = new LinkedList();
    int count = 0;

    public boolean isLeaf() {
        return true;
    }

    public QuadNode insert(
        Coordinates value,
        Point topLeft,
        Point bottomRight) {

        //boolean flag = true;

        if (points.duplicates(points, value)) {
            points.insert(points, value);
            return this;
        }

        else {
            if (count < 3) {
                count++;
                points.insert(points, value);
                return this;
            }

            else {

                // System.out.println("making leafnode internal");
                QuadNode q = new QuadIntlNode();
                Iterator<Coordinates> listIterator = points.iterator();
                while (listIterator.hasNext()) {
                    q = q.insert(listIterator.next(), topLeft, bottomRight);
                }
                q = q.insert(value, topLeft, bottomRight);
                return q;
            }

        }

    }


    public void traverse(Point topLeft, Point bottomRight) {

        int n = (1024 / (bottomRight.x - topLeft.x) - 1);
        if (n != 0)
            System.out.format("%1$" + n + "s", "");
        System.out.println("Node at " + topLeft.x + ", " + topLeft.y + ", "
            + (bottomRight.x - topLeft.x) + ":");
        Coordinates temp = new Coordinates();
        Iterator<Coordinates> listIterator = points.iterator();
        while (listIterator.hasNext()) {
            temp = listIterator.next();
            if (n != 0)
                System.out.format("%1$" + n + "s", "");
// System.out.println("Rectangle topleft x " + topLeft.x + "y " + topLeft.y + "
// bottomright x: " + bottomRight.y + " y: "+ bottomRight.y);
            System.out.println(temp.toString());

        }
    }


    public void search(Coordinates C, Point topLeft, Point bottomRight) {
        Coordinates temp = new Coordinates();
        Iterator<Coordinates> listIterator = points.iterator();
        while (listIterator.hasNext()) {
            temp = listIterator.next();
            if (temp.equals(C)) {
                System.out.println("Found");
            }

        }

    }

    @Override
    public void regionsearch(
        int x,
        int y,
        int w,
        int h,
        Point topLeft,
        Point bottomRight,
        int[] nodeCount) {

        nodeCount[0]++;
        Rectangle rect = new Rectangle(x, y, w, h);

        for (int i = 0; i < points.size(); i++) {
            Coordinates current = points.get(i);
            if (rect.contains(current.x, current.y)) {
                System.out.println("Point found: " + "(" + current.key + ", "
                    + current.x + ", " + current.y + ")");
            }
        }

    }


    public QuadNode remove(Coordinates C, Point topLeft, Point bottomRight) {
        Coordinates temp = new Coordinates();
        Iterator<Coordinates> listIterator = points.iterator();
        while (listIterator.hasNext()) {
            temp = listIterator.next();
            if (temp.equals(C)) {
                points = points.deleteByKey(points, C);
                count--;
            }

        }
        return this;
    }


    public LinkedList getdata() {
        return points;
    }


 
    
}
