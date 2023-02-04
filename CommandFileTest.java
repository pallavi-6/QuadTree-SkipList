import student.testingsupport.PrintStreamWithHistory;

/**
 * 
 * @author Pallavi Padmanabha
 * @version 2021-10-19
 *          Test class for testing the command file.
 * 
 *
 */
public class CommandFileTest extends student.TestCase {
    // Fields
    private CommandFile testCmd;
    private PrintStreamWithHistory conHistory;

    /**
     * Reference - OpenDSA -> Writing JUnit Tests with student.TestCase
     * This method Runs before each test method
     */

    public void setUp() {
        testCmd = new CommandFile();
        conHistory = (PrintStreamWithHistory)System.out;
    }
 

    /**
     * This method tests if the command file properly reads the file and
     * upon encountering the 'insert' command, calls in the correct insert
     * method
     * from the database class for further processing.
     */
    public void testFileInsert() {

        testCmd.processor("insert          point1 8 10");
        assertEquals("Point inserted: (point1, 8, 10)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("  insert point2 -1 4");
        assertEquals("Point rejected: (point2, -1, 4)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("  insert point 1 -4");
        assertEquals("Point rejected: (point, 1, -4)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("  insert point3 10245 18888");
        assertEquals("Point rejected: (point3, 10245, 18888)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("  insert point4 1 19999");
        assertEquals("Point rejected: (point4, 1, 19999)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("  insert point5 12345 19999");
        assertEquals("Point rejected: (point5, 12345, 19999)\n", conHistory
            .getHistory());
        conHistory.clearHistory();
    }


    /**
     * This method tests if the command file properly reads the file and
     * upon encountering the 'remove' command, calls in the correct remove
     * method from the database class for further processing.
     */

    public void testProcessorRemove() {

        testCmd.processor("insert          point1 8 10");
        assertEquals("Point inserted: (point1, 8, 10)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove point1");
        assertEquals("Point removed: (point1, 8, 10)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove pointnonexistent");
        assertEquals("Point not removed: pointnonexistent\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove -1 -2");
        assertEquals("Point rejected: (-1, -2)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove -1 2");
        assertEquals("Point rejected: (-1, 2)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove 1 -2");
        assertEquals("Point rejected: (1, -2)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove 12345 2");
        assertEquals("Point rejected: (12345, -2)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove 1 24567");
        assertEquals("Point rejected: (1, 24567)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove 12345 23456");
        assertEquals("Point rejected: (12345, 23456)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove 9 10");
        assertEquals("Point not found: (9, 10)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert          point1 8 10");
        assertEquals("Point inserted: (point1, 8, 10)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("remove 8 10");
        assertEquals("Point removed: ( point1, 8, 10)\n", conHistory
            .getHistory());
        conHistory.clearHistory();
    }


    /**
     * This method tests if the command file properly reads the file and
     * upon encountering the 'search' command, calls in the correct search
     * method from the database class for further processing.
     */

    public void testProcessorSearch() {

        testCmd.processor("search point6");
        assertEquals("Point not found: point6\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert          a 1 1");
        assertEquals("Point inserted: (a, 1, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert  b 1 1");
        assertEquals("Point inserted: (b, 1, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert a1 2 1");
        assertEquals("Point inserted: (a1, 2, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert b1 551 1");
        assertEquals("Point inserted: (b1, 551, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert  b2 553 1");
        assertEquals("Point inserted: (b2, 553, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("search b1");
        assertEquals("Found (b1, 551, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("search a");
        assertEquals("Found (a, 551, 1)\n Found (a, 1, 1)", conHistory
            .getHistory());
        conHistory.clearHistory();

    }


    /**
     * This method tests if the command file properly reads the file and
     * upon encountering the 'regionsearch' command, calls in the correct
     * regionsearch method from the database class for further processing.
     */

    public void testProcessorRegionsearch() {

        testCmd.processor("regionsearch 20 30 40 50");
        assertEquals("Pointss intersecting region (20, 30, 40, 50):\n",
            conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("regionsearch 9 2 -5 6");
        assertEquals("Rectangle rejected: (9, 2, -5, 6)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

        testCmd.processor("regionsearch 9 2 5 -6");
        assertEquals("Rectangle rejected: (9, 2, 5, -6)\n", conHistory
            .getHistory());
        conHistory.clearHistory();

    }


    /**
     * This method tests if the command file properly reads the file and
     * upon encountering the 'dump' command, calls in the correct
     * dump method from the database class for further processing.
     */
    public void testProcessorDump() {
        testCmd.processor("dump");
        assertEquals("SkipList Dump:\n" + "Node has depth 1, Value (null)\n"
            + "SkipList size is: 0\n" + "" + "QuadTree dump:\n"
            + "Node at 0, 0, 1024:Empty\n" + "1 quadtree nodes printed\n" + "",
            conHistory.getHistory());
        conHistory.clearHistory();
    }


    /**
     * This method tests if the command file properly reads the file and
     * upon encountering the 'duplicates' command, calls in the correct
     * dump method from the database class for further processing.
     */
    public void testProcessorDuplicates() {

        testCmd.processor("duplicates");
        assertEquals("Duplicate Points:\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert          a 1 1");
        assertEquals("Point inserted: (a, 1, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert  b 1 1");
        assertEquals("Point inserted: (b, 1, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert a1 2 1");
        assertEquals("Point inserted: (a1, 2, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert b1 551 1");
        assertEquals("Point inserted: (b1, 551, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert  b2 553 1");
        assertEquals("Point inserted: (b2, 553, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("insert  a 551 1");
        assertEquals("Point inserted: (a, 551, 1)\n", conHistory.getHistory());
        conHistory.clearHistory();

        testCmd.processor("duplicates");
        assertEquals("Duplicate Points:\n" + "(1, 1)\n" + "(551, 1)\n" + "",
            conHistory.getHistory());
        conHistory.clearHistory();
    }
}
