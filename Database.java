import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;

/**
 * This class is responsible for interfacing between the command file,
 * the SkipList and the PRQuadTree. The responsibility of this class is to
 * further interpret variations of commands and do some error checking of those
 * commands. This class further interpreting the command means that types of
 * remove will call the PRQuadTree or the skipList depending on if we are
 * removing by
 * name or by coordinates. Many of these methods will simply call the
 * appropriate version of the SkipList and the PRQuadTree method after some
 * preparation.
 * 
 * 
 * @version 2021-10-14
 */
public class Database {
    // Fields
    /**
     * This is a Point object that we are using to use use the attributes of
     * the Point class for various operations.
     * 
     */
    private Point pt;
    private Rectangle rect;
    private KVPair<String, Point> keyvalue;
    // this is the SkipList object that we are using a string for the name of
    // the point and then a point object, these are stored in a KVPair, see the
    // KVPair class for more information
    private SkipList<String, Point> list;
    private QuadTree tree;
    // Regular expression used for checking if name is in correct format
    // private static final String NAME_REGEX = "(([a-zA-Z])+[a-zA-Z0-9_]*)";

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Point a its parameters.
     */
    public Database() {
        list = new SkipList<String, Point>();
        tree = new QuadTree();

    }


    /**
     * Inserts the KVPair in the SkipList if the point has valid coordinates,
     * that is that the coordinates are non-negative. This insert will
     * insert the KVPair specified into the sorted SkipList appropriately
     * 
     * @param keyvalue
     *            the KVPair to be inserted
     */

    public void insert(Coordinates point) {

        String name = point.key;
        Point p = new Point(point.x, point.y);
        keyvalue = new KVPair<String, Point>(name, p);
        pt = keyvalue.getValue();
        System.out.println("Point inserted: (" + name + ", " + p.x + ", " + p.y
            + ")");
        list.insert(keyvalue);
        // if (name.matches(NAME_REGEX)) {
        tree.insert(point);
        // }

    }


    /**
     * Removes a point with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the point to be removed
     */

    public void remove(String name) {

        if (list.search(name).isEmpty()) {
            System.out.println("Point not removed: " + "(" + name + ")");
        }
        else {

            KVPair<String, Point> pair = list.search(name).get(0);
            pt = pair.getValue();
            // quadtree.remove(pt);
            // Remove using pt.x and pt.y, comparison using name of the point
            System.out.println("Point removed: (" + name + ", " + pt.toString()
                + ")");
            list.remove(name);

        }

    }


    /**
     * Removes a point with the specified coordinates if available. If not,
     * an error message is printed to the console.
     *
     * @param x
     *            x-coordinate of the point to be removed
     * @param y
     *            x-coordinate of the point to be removed
     */
    public void remove(int x, int y) {

        // First search PRQuadTree if such value exists and the search returns a
        // list of key value pairs and then remove from the PRQuadTree and
        // remove from SkipList using the first point that search returns

        if (tree.search(x, y).isEmpty()) {
            System.out.println("Point not removed: " + "(" + x + y + ")");
        }
        else {

            KVPair<String, Point> pair = tree.search(x, y).get(0);
            ;
            list.removeByValue(pair);
            System.out.println("Point removed: " + "(" + pair.getKey() + ", "
                + x + ", " + y + ")");
            tree.remove(x, y);
        }
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region. You will need a SkipList
     * Iterator for this
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {

        System.out.println("Points intersecting region (" + x + ", " + y + ", "
            + w + ", " + h + "):");
        tree.regionsearch(x, y, w, h);

    }


    /**
     * Prints out all the points with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Point to be searched for
     */
    public void search(String name) {

        // Check assignment spec for specific instructions, but otherwise
        // modify by adding quadtree.search

        if (list.search(name).isEmpty()) {
            System.out.println("Point not found: " + name);
        }
        else {
            System.out.println("Points found:");
            ArrayList<KVPair<String, Point>> arrList = list.search(name);
            for (int i = 0; i < arrList.size(); i++) {
                KVPair<String, Point> pair = arrList.get(i);
                System.out.println("(" + pair.getKey() + ", " + pair.getValue()
                    .toString() + ")");
            }

        }
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
        // quadtree.dump();
    }


    public void duplicates() {
        // quadtree.duplicates()
    }

}
