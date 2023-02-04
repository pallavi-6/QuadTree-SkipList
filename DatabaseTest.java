import student.testingsupport.PrintStreamWithHistory;

/**
 * 
 * @version 2021-09-15
 * 
 *          This class is used for the testing of the Database class and all of
 *          its methods.
 *
 */
public class DatabaseTest extends student.TestCase {

    private Database db;
    private KVPair<String, Rectangle> pair;
    private Rectangle rect;
    private PrintStreamWithHistory conHistory;

    /**
     * Reference - OpenDSA -> Writing JUnit Tests with student.TestCase
     * This method Runs before each test method
     */

    public void setUp() {
        db = new Database();
        conHistory = (PrintStreamWithHistory)System.out;
    }


    /**
     * The testRemoveValue method tests all possible scenarios one might
     * encounter while removing an element by passing in it's values i.e. the
     * rectangle coordinates,it's width and it's height.
     * 
     */
    public void testRemoveValue() {
        db.remove(0, 0, 0, 0);

        rect = new Rectangle(1, 2, 3, 4);
        pair = new KVPair<String, Rectangle>("lol", rect);
        db.insert(pair);

        rect = new Rectangle(4, 5, 3, 4);
        pair = new KVPair<String, Rectangle>("okok", rect);
        db.insert(pair);

        rect = new Rectangle(1, 2, 6, 8);
        pair = new KVPair<String, Rectangle>("kkkk", rect);
        db.insert(pair);

        conHistory.clearHistory();
        db.remove(1, 2, 6, 8);
        assertEquals("Rectangle removed: (kkkk, 1, 2, 6, 8)\n", conHistory
            .getHistory());

        conHistory.clearHistory();
        db.remove(3, 2, 6, 8);
        assertEquals("Rectangle not removed: (3, 2, 6, 8)\n", conHistory
            .getHistory());
    }


    /**
     * This method tests all possible conditions for a rectangle to be fully or
     * partially inside the given region.
     * 
     */
    public void testRegionSearch() {

        rect = new Rectangle(1, 2, 3, 4);
        pair = new KVPair<String, Rectangle>("lol", rect);
        db.insert(pair);

        rect = new Rectangle(4, 5, 3, 4);
        pair = new KVPair<String, Rectangle>("okok", rect);
        db.insert(pair);

        rect = new Rectangle(1, 2, 6, 8);
        pair = new KVPair<String, Rectangle>("kkkk", rect);
        db.insert(pair);

        conHistory.clearHistory();
        db.regionsearch(1, 2, 10, 100);
        assertEquals("Rectangles intersecting region (1, 2, 10, 100):\n"
            + "(kkkk, 1, 2, 6, 8)\n" + "(lol, 1, 2, 3, 4)\n"
            + "(okok, 4, 5, 3, 4)\n", conHistory.getHistory());

        rect = new Rectangle(4, 50, 38, 433);
        pair = new KVPair<String, Rectangle>("okok", rect);
        db.insert(pair);

        rect = new Rectangle(0, 20, 68, 80);
        pair = new KVPair<String, Rectangle>("kkkk", rect);
        db.insert(pair);

        conHistory.clearHistory();
        db.regionsearch(1, 0, 98, 739);
        assertEquals("Rectangles intersecting region (1, 0, 98, 739):\r\n"
            + "(kkkk, 0, 20, 68, 80)\r\n" + "(kkkk, 1, 2, 6, 8)\r\n"
            + "(lol, 1, 2, 3, 4)\r\n" + "(okok, 4, 50, 38, 433)\r\n"
            + "(okok, 4, 5, 3, 4)\n", conHistory.getHistory());

        conHistory.clearHistory();
        db.regionsearch(1, 10, 98, 739);
        assertEquals("Rectangles intersecting region (1, 10, 98, 739):\r\n"
            + "(kkkk, 0, 20, 68, 80)\r\n" + "(kkkk, 1, 2, 6, 8)\r\n"
            + "(okok, 4, 50, 38, 433)\n", conHistory.getHistory());
        conHistory.clearHistory();

        db.remove("lol");
        db.remove(4, 5, 3, 4);
        db.remove(1, 2, 6, 8);
        db.remove(4, 50, 38, 433);
        db.remove(0, 20, 68, 80);

        conHistory.clearHistory();
        Rectangle rect1 = new Rectangle(7, 7, 10, 10);
        KVPair<String, Rectangle> pair1 = new KVPair<String, Rectangle>("r3",
            rect1);
        rect = new Rectangle(7, 7, 8, 5);
        pair = new KVPair<String, Rectangle>("r1", rect);

        conHistory.clearHistory();
        rect = new Rectangle(10, 10, 5, 5);
        rect1 = new Rectangle(7, 7, 10, 10);
        pair = new KVPair<String, Rectangle>("r1", rect);
        pair1 = new KVPair<String, Rectangle>("r3", rect1);
        db.insert(pair);
        db.insert(pair1);
        conHistory.clearHistory();
        db.regionsearch(-5, -5, 20, 20);

        assertEquals("Rectangles intersecting region (-5, -5, 20, 20):\r\n"
            + "(r1, 10, 10, 5, 5)\r\n" + "(r3, 7, 7, 10, 10)\r\n", conHistory
                .getHistory());
        conHistory.clearHistory();

        rect = new Rectangle(15, 15, 10, 10);
        pair = new KVPair<String, Rectangle>("r4", rect);
        conHistory.clearHistory();

        db.regionsearch(15, 15, 10, 10);

        assertEquals("Rectangles intersecting region (15, 15, 10, 10):\r\n"
            + "(r1, 10, 10, 5, 5)\r\n" + "(r3, 7, 7, 10, 10)\r\n", conHistory
                .getHistory());
        conHistory.clearHistory();

    }


    /**
     * This method tests all possible conditions for a rectangle to be fully or
     * partially in the given region.
     * 
     */
    public void testRegionSearch1() {

        conHistory.clearHistory();
        db.regionsearch(1, 2, 10, 100);
        assertEquals("Rectangles intersecting region (1, 2, 10, 100):\n",
            conHistory.getHistory());

        rect = new Rectangle(1, 2, 3, 4);
        pair = new KVPair<String, Rectangle>("lol", rect);
        db.insert(pair);

        rect = new Rectangle(2, 4, 8, 9);
        pair = new KVPair<String, Rectangle>("lol", rect);
        db.insert(pair);

        conHistory.clearHistory();
        db.regionsearch(1, 5, 7, 3);

        assertEquals("Rectangles intersecting region (1, 5, 7, 3):\r\n"
            + "(lol, 2, 4, 8, 9)\r\n" + "(lol, 1, 2, 3, 4)\n", conHistory
                .getHistory());
        conHistory.clearHistory();

        db.remove("lol");
        db.remove("lol");

    }


    /**
     * This method tests all possible conditions of two rectangles intersecting.
     */
    public void testIntersection() {

        conHistory.clearHistory();
        db.intersections();
        assertEquals("Intersection Pairs:\n", conHistory.getHistory());

        rect = new Rectangle(1, 2, 3, 4);
        pair = new KVPair<String, Rectangle>("lol", rect);
        db.insert(pair);

        rect = new Rectangle(4, 5, 3, 4);
        pair = new KVPair<String, Rectangle>("okok", rect);
        db.insert(pair);

        rect = new Rectangle(1, 2, 6, 8);
        pair = new KVPair<String, Rectangle>("kkkk", rect);
        db.insert(pair);

        conHistory.clearHistory();
        db.intersections();
        assertEquals("Intersection Pairs:\r\n"
            + "(kkkk, 1, 2, 6, 8 | lol, 1, 2, 3, 4)\n"
            + "(kkkk, 1, 2, 6, 8 | okok, 4, 5, 3, 4)\n"
            + "(lol, 1, 2, 3, 4 | kkkk, 1, 2, 6, 8)\n"
            + "(okok, 4, 5, 3, 4 | kkkk, 1, 2, 6, 8)\n", conHistory
                .getHistory());

    }

}
