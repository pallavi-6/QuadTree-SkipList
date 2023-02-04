import java.util.ArrayList;
import java.awt.Point;

/**
 * 
 * @author Pallavi Padmanabha
 *         Asmita Hanchate
 * 
 * @version 2021-10-21
 *          Test class for testing the SkipList class.
 * 
 */
public class SkipListTest extends student.TestCase {
    // Fields
    private SkipList<String, Point> skipList;
    private Point point;
    private KVPair<String, Point> pair;

    /**
     * Reference - OpenDSA -> Writing JUnit Tests with student.TestCase
     * This method Runs before each test method
     */

    public void setUp() {
        skipList = new SkipList<String, Point>();
    }


    /**
     * This method tests the search method of the SkipList class which should
     * return us an array of key value pairs of the searched key.
     * 
     */
    public void testSearch() {
        ArrayList<KVPair<String, Point>> arrList = skipList.search("point1");
        assertEquals(0, arrList.size());

        point = new Point(1, 2);
        pair = new KVPair<String, Point>("point1", point);
        skipList.insert(pair);
        arrList = skipList.search(pair.getKey());
        assertEquals(1, arrList.size());

        point = new Point(3, 4);
        pair = new KVPair<String, Point>("point2", point);
        skipList.insert(pair);
        arrList = skipList.search(pair.getKey());
        assertEquals(1, arrList.size());

        point = new Point(5, 6);
        pair = new KVPair<String, Point>("point3", point);
        skipList.insert(pair);
        arrList = skipList.search(pair.getKey());
        assertEquals(1, arrList.size());

        arrList = skipList.search("dope");
        assertEquals(0, arrList.size());

        point = new Point(7, 8);
        pair = new KVPair<String, Point>("point4", point);
        skipList.insert(pair);
        arrList = skipList.search(pair.getKey());
        assertEquals(2, arrList.size());

    }


    /**
     * This method tests the remove function of the SkipList class which takes
     * the name of the Point as it's parameter.
     * 
     */
    public void testRemove() {
        skipList.remove("lol");

        point = new Point(1, 2);
        pair = new KVPair<String, Point>("point1", point);
        skipList.insert(pair);
        assertEquals(1, skipList.size());

        point = new Point(5, 6);
        pair = new KVPair<String, Point>("point2", point);
        skipList.insert(pair);
        assertEquals(2, skipList.size());

        point = new Point(7, 8);
        pair = new KVPair<String, Point>("point3", point);
        skipList.insert(pair);
        assertEquals(3, skipList.size());

        skipList.remove("point1");
        assertEquals(2, skipList.size());

        skipList.remove("point2");
        assertEquals(1, skipList.size());

        point = new Point(9, 10);
        pair = new KVPair<String, Point>("point4", point);
        skipList.insert(pair);

        point = new Point(11, 12);
        pair = new KVPair<String, Point>("point5", point);
        skipList.insert(pair);

        skipList.remove("point2");
        assertEquals(3, skipList.size());

        skipList.remove("point3");
        assertEquals(2, skipList.size());

    }


    /**
     * This method tests the removeByValue method of the SkipList class which
     * takes the Point values as an argument.
     * 
     */
    public void testRemoveValue() {
        point = new Point(1, 2);
        pair = new KVPair<String, Point>("point1", point);
        skipList.removeByValue(pair);

        point = new Point(3, 4);
        pair = new KVPair<String, Point>("point2", point);
        skipList.insert(pair);
        assertEquals(1, skipList.size());

        skipList.removeByValue(pair);
        assertEquals(0, skipList.size());

        point = new Point(5, 6);
        pair = new KVPair<String, Point>("point3", point);
        skipList.insert(pair);
        assertEquals(1, skipList.size());

        point = new Point(7, 8);
        pair = new KVPair<String, Point>("point4", point);
        skipList.insert(pair);
        assertEquals(2, skipList.size());

        point = new Point(11, 12);
        pair = new KVPair<String, Point>("point5", point);
        skipList.insert(pair);
        assertEquals(3, skipList.size());

        skipList.removeByValue(pair);
        assertEquals(2, skipList.size());
    }


    /**
     * This method is a test method for the SkipList dump which prints out the
     * entire SkipList with all it's key and value pairs along with the depth of
     * the nodes.
     * 
     */
    public void testDump() {
        skipList.dump();
        assertEquals(0, skipList.size());

        point = new Point(1, 2);
        pair = new KVPair<String, Point>("point1", point);
        skipList.insert(pair);
        assertEquals(1, skipList.size());

        point = new Point(3, 4);
        pair = new KVPair<String, Point>("point2", point);
        skipList.insert(pair);
        assertEquals(2, skipList.size());

        point = new Point(5, 6);
        pair = new KVPair<String, Point>("point3", point);
        skipList.insert(pair);
        assertEquals(3, skipList.size());

        skipList.dump();
        assertEquals(3, skipList.size());

    }

}
