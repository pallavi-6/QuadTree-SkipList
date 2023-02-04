import java.awt.Point;

/**
 * This class is for testing the KVPair class.
 * 
 * @author Pallavi Padmanabha
 * 
 * @version 2021-10-21
 *
 */

public class KVPairTest extends student.TestCase {

    /**
     * pair is an object of the class KVPair which has attributes key
     * and value
     */
    private KVPair<String, Point> pair;

    /**
     * Reference - OpenDSA -> Writing JUnit Tests with student.TestCase
     * This method Runs before each test method
     */

    public void setUp() {
        /**
         * point is an object of the Rectangle class which contains coordinates
         * (x,y).
         */

        Point point = new Point(5, 10);

        pair = new KVPair<String, Point>("point1", point);

    }


    /**
     * This method checks if the key is getting assigned properly in the KVPair
     * pair.
     * 
     */

    public void testGetKey() {
        assertEquals("point1", pair.getKey());

    }


    /**
     * 
     * This method checks if the value is getting assigned properly in the
     * KVPair pair.
     */
    public void testGetValue() {
        assertEquals("5, 10", (int)pair.getValue().getX() + ", " + (int)pair
            .getValue().getY());

    }


    /**
     * This method tests our overridden CompareTo method which compares two
     * pairs.
     * 
     */
    public void testCompareTo() {

        Point point1 = new Point(5, 10);
        KVPair<String, Point> pair1 = new KVPair<String, Point>("point2",
            point1);
        assertTrue(pair.compareTo(pair1) < 0);

        assertTrue(pair1.compareTo(pair) > 0);

        assertEquals(0, pair.compareTo(pair));
    }


    /**
     * This method tests the toString method of the KVPair class.
     * 
     */
    public void testToString() {

        assertEquals("(" + pair.getKey().toString() + ", " + pair.getValue()
            .toString() + ")", pair.toString());
    }
}
