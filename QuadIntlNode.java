import java.awt.*;
import java.util.Iterator;

public class QuadIntlNode implements QuadNode {

    private QuadNode southEast;
    private QuadNode southWest;
    private QuadNode northEast;
    private QuadNode northWest;

    /*
     * QuadIntlNode(QuadNode topLeft, QuadNode topRight, QuadNode
     * bottomLeft, QuadNode bottomRight) { southEast = bottomRight;
     * southWest = bottomLeft; northEast = topRight; northWest = topLeft;
     * }
     */

    public QuadIntlNode() {
        southEast = EmptyNode.getInstance();
        southWest = EmptyNode.getInstance();
        northEast = EmptyNode.getInstance();
        northWest = EmptyNode.getInstance();
    }


    public boolean isLeaf() {
        return false;
    }


    public QuadNode southEast() {
        return southEast;
    }


    public QuadNode southWest() {
        return southWest;
    }


    public QuadNode northEast() {
        return northEast;
    }


    public QuadNode northWest() {
        return northWest;
    }


    public QuadNode insert(
        Coordinates value,
        Point topLeft,
        Point bottomRight) {
        Point tempBottomRight = new Point();
        Point tempTopLeft = new Point();

        int xPos = value.x;
        int yPos = value.y;
        // Quadrant 1(Northwest) check
        if (((xPos >= topLeft.x) && xPos < ((topLeft.x + bottomRight.x) / 2))
            && (yPos >= topLeft.y) && (yPos < ((topLeft.y + bottomRight.y)
                / 2))) {
// System.out.println("Inserting northwest");
            tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
            tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
            northWest = (northWest.insert(value, topLeft, tempBottomRight));
            return this;
        }

        // Quadrant 2(Northeast) check
        if (((xPos >= (topLeft.x + bottomRight.x) / 2)
            && (xPos < bottomRight.x)) && (yPos >= topLeft.y)
            && (yPos < ((topLeft.y + bottomRight.y) / 2))) {
// System.out.println("Inserting northeast");
            tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
            tempTopLeft.y = topLeft.y;
            tempBottomRight.x = bottomRight.x;
            tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
            northEast = (northEast.insert(value, tempTopLeft, tempBottomRight));
            return this;
        }

        // Quadrant 3(Southwest) check
        if (((xPos >= topLeft.x && xPos < ((topLeft.x + bottomRight.x) / 2))
            && (yPos >= ((topLeft.y + bottomRight.y) / 2))
            && (yPos < bottomRight.y))) {
            // System.out.println("Inserting southwest!!");
            tempTopLeft.x = topLeft.x;
            tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
            tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
            tempBottomRight.y = bottomRight.y;
            southWest = (southWest.insert(value, tempTopLeft, tempBottomRight));
            return this;
        }

        // Quadrant 4(Southeast) check)
        if (xPos >= ((topLeft.x + bottomRight.x) / 2) && xPos < bottomRight.x
            && yPos >= ((topLeft.y + bottomRight.y) / 2)
            && yPos < bottomRight.y) {
            // System.out.println("Inserting southeast!!");
            tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
            tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
            southEast = southEast.insert(value, tempTopLeft, bottomRight);
            return this;
        }

        return this;
    }


    public void traverse(Point topLeft, Point bottomRight) {
        Point tempBottomRight = new Point();
        Point tempTopLeft = new Point();
        int n = (1024 / (bottomRight.x - topLeft.x) - 1);
        if (n != 0)
            System.out.format("%1$" + n + "s", "");
        System.out.println("Node at " + topLeft.x + ", " + topLeft.y + ", "
            + (bottomRight.x - topLeft.x) + ": Internal");

        tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
        tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
        northWest.traverse(topLeft, tempBottomRight);

        tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
        tempTopLeft.y = topLeft.y;
        tempBottomRight.x = bottomRight.x;
        tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
        northEast.traverse(tempTopLeft, tempBottomRight);

        tempTopLeft.x = topLeft.x;
        tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
        tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
        tempBottomRight.y = bottomRight.y;
        southWest.traverse(tempTopLeft, tempBottomRight);

        tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
        tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
        southEast.traverse(tempTopLeft, bottomRight);

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
        Rectangle rect = new Rectangle(x, y, w, h);
        Point tempTopLeft = topLeft;
        Point tempBottomRight = bottomRight;
        int regionBoundaryX = tempTopLeft.x + (tempTopLeft.x
            + tempBottomRight.x) / 2;
        int regionBoundaryY = tempTopLeft.y + (tempTopLeft.y
            + tempBottomRight.y) / 2;

        if (regionBoundaryX > rect.x && regionBoundaryY > rect.y) {
            tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
            tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
            northWest.regionsearch(x, y, w, h, topLeft, tempBottomRight,
                nodeCount);
        }
        if (regionBoundaryX < (rect.x + rect.width)
            && regionBoundaryY > rect.y) {
            tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
            tempTopLeft.y = topLeft.y;
            tempBottomRight.x = bottomRight.x;
            tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
            northEast.regionsearch(x, y, w, h, tempTopLeft, tempBottomRight,
                nodeCount);
        }
        if (regionBoundaryX > rect.x && regionBoundaryY < (rect.y
            + rect.height)) {
            tempTopLeft.x = topLeft.x;
            tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
            tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
            tempBottomRight.y = bottomRight.y;
            southWest.regionsearch(x, y, w, h, tempTopLeft, tempBottomRight,
                nodeCount);
        }

        if (regionBoundaryX < (rect.x + rect.width) && regionBoundaryY < (rect.y
            + rect.height)) {
            tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
            tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
            southEast.regionsearch(x, y, w, h, tempTopLeft, bottomRight,
                nodeCount);
        }

    }


    
    public void search(Coordinates value, Point topLeft, Point bottomRight) {
        Point tempBottomRight = new Point();
        Point tempTopLeft = new Point();

        int xPos = value.x;
        int yPos = value.y;
        // Quadrant 1(Northwest) check
        if (((xPos >= topLeft.x) && xPos < ((topLeft.x + bottomRight.x) / 2))
            && (yPos >= topLeft.y) && (yPos < ((topLeft.y + bottomRight.y)
                / 2))) {
// System.out.println("Inserting northwest");
            tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
            tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
            northWest.search(value, topLeft, tempBottomRight);
        }

        // Quadrant 2(Northeast) check
        if (((xPos >= (topLeft.x + bottomRight.x) / 2)
            && (xPos < bottomRight.x)) && (yPos >= topLeft.y)
            && (yPos < ((topLeft.y + bottomRight.y) / 2))) {
// System.out.println("Inserting northeast");
            tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
            tempTopLeft.y = topLeft.y;
            tempBottomRight.x = bottomRight.x;
            tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
            northEast.search(value, tempTopLeft, tempBottomRight);
        }

        // Quadrant 3(Southwest) check
        if (((xPos >= topLeft.x && xPos < ((topLeft.x + bottomRight.x) / 2))
            && (yPos >= ((topLeft.y + bottomRight.y) / 2))
            && (yPos < bottomRight.y))) {
            // System.out.println("Inserting southwest!!");
            tempTopLeft.x = topLeft.x;
            tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
            tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
            tempBottomRight.y = bottomRight.y;
            southWest.search(value, tempTopLeft, tempBottomRight);
        }

        // Quadrant 4(Southeast) check)
        if (xPos >= ((topLeft.x + bottomRight.x) / 2) && xPos < bottomRight.x
            && yPos >= ((topLeft.y + bottomRight.y) / 2)
            && yPos < bottomRight.y) {
            // System.out.println("Inserting southeast!!");
            tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
            tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
            southEast.search(value, tempTopLeft, bottomRight);
        }

    }


    public QuadNode remove(
        Coordinates value,
        Point topLeft,
        Point bottomRight) {
        Point tempBottomRight = new Point();
        Point tempTopLeft = new Point();
        int count = 0;

        int xPos = value.x;
        int yPos = value.y;
        QuadLeafNode k = new QuadLeafNode();

        // Quadrant 1(Northwest) check
        if (((xPos >= topLeft.x) && xPos < ((topLeft.x + bottomRight.x) / 2))
            && (yPos >= topLeft.y) && (yPos < ((topLeft.y + bottomRight.y)
                / 2))) {
// System.out.println("Inserting northwest");
            tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
            tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
            northWest = northWest.remove(value, topLeft, tempBottomRight);
        }

        // Quadrant 2(Northeast) check
        if (((xPos >= (topLeft.x + bottomRight.x) / 2)
            && (xPos < bottomRight.x)) && (yPos >= topLeft.y)
            && (yPos < ((topLeft.y + bottomRight.y) / 2))) {
// System.out.println("Inserting northeast");
            tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
            tempTopLeft.y = topLeft.y;
            tempBottomRight.x = bottomRight.x;
            tempBottomRight.y = (topLeft.y + bottomRight.y) / 2;
            northEast = northEast.remove(value, topLeft, tempBottomRight);
        }

        // Quadrant 3(Southwest) check
        if (((xPos >= topLeft.x && xPos < ((topLeft.x + bottomRight.x) / 2))
            && (yPos >= ((topLeft.y + bottomRight.y) / 2))
            && (yPos < bottomRight.y))) {
            // System.out.println("Inserting southwest!!");
            tempTopLeft.x = topLeft.x;
            tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
            tempBottomRight.x = (topLeft.x + bottomRight.x) / 2;
            tempBottomRight.y = bottomRight.y;
            southWest = southWest.remove(value, topLeft, tempBottomRight);
        }

        // Quadrant 4(Southeast) check)
        if (xPos >= ((topLeft.x + bottomRight.x) / 2) && xPos < bottomRight.x
            && yPos >= ((topLeft.y + bottomRight.y) / 2)
            && yPos < bottomRight.y) {
            // System.out.println("Inserting southeast!!");
            tempTopLeft.x = (topLeft.x + bottomRight.x) / 2;
            tempTopLeft.y = (topLeft.y + bottomRight.y) / 2;
            southEast = southEast.remove(value, topLeft, tempBottomRight);
        }

        QuadLeafNode l = new QuadLeafNode();
        QuadLeafNode m = new QuadLeafNode();
        QuadLeafNode n = new QuadLeafNode();
        k = (QuadLeafNode)southWest;
        l = (QuadLeafNode)southEast;
        m = (QuadLeafNode)northWest;
        n = (QuadLeafNode)southEast;

        LinkedList points = new LinkedList();
        if ((k.count + l.count + m.count + n.count) < 4) {
            QuadNode q = new QuadLeafNode();
            points = k.getdata();
            Iterator<Coordinates> listIterator = points.iterator();
            while (listIterator.hasNext()) {
                q = q.insert(listIterator.next(), topLeft, bottomRight);
            }
            points = l.getdata();
            listIterator = points.iterator();
            while (listIterator.hasNext()) {
                q = q.insert(listIterator.next(), topLeft, bottomRight);
            }
            points = m.getdata();
            listIterator = points.iterator();
            while (listIterator.hasNext()) {
                q = q.insert(listIterator.next(), topLeft, bottomRight);
            }
            points = n.getdata();
            listIterator = points.iterator();
            while (listIterator.hasNext()) {
                q = q.insert(listIterator.next(), topLeft, bottomRight);
            }
            return q;
        }
        return this;

    }

}
