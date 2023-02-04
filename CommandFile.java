import java.awt.*;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project specification.
 * 
 * @author Pallavi Padmanabha
 * 
 * @version 2021-10-12
 */
public class CommandFile {

    // the database object to manipulate the
    // commands that the command processor
    // feeds to it
    private Database data;

    /**
     * This creates a KVPair object of type String, Rectangle
     */
    private KVPair<String, Point> keyvalue;

    /**
     * The constructor for the command processor requires a database instance to
     * exist, so the only constructor takes a database class object to feed
     * commands to.
     * 
     */
    public CommandFile() {
        data = new Database();
    }


    /**
     * This method identifies keywords in the line and calls methods in the
     * database as required. Each line command will be specified by one of the
     * keywords to perform the actions within the database required. These
     * actions are performed on specified objects and include insert, remove,
     * regionsearch, search, duplicates, and dump. If the command in the file
     * line is not
     * one of these, an appropriate message will be written in the console. This
     * processor method is called for each line in the file. Note that the
     * methods called will themselves write to the console, this method does
     * not, only calling methods that do.
     * 
     * @param line
     *            a single line from the text file
     */

    public void processor(String line) {

        // System.out.println(line);

        if (line.contains("insert")) {
            Coordinates coordinates = new Coordinates();
            String[] arrayToSend = cleanLine(line);
            coordinates.key = arrayToSend[1];
            coordinates.x = Integer.valueOf(arrayToSend[2]);
            coordinates.y = Integer.valueOf(arrayToSend[3]);

            if (coordinates.x < 0 || coordinates.y < 0 || coordinates.x > 1024
                || coordinates.y > 1024) {
                System.out.println("Point rejected: (" + coordinates.key + ", "
                    + coordinates.x + ", " + coordinates.y + ")");
            }
            else {
                data.insert(coordinates);
            }
        }
        // The functionality remains the same.
        else if (line.contains("regionsearch")) {

            String[] arrayToSend = cleanLine(line);
            int x = Integer.valueOf(arrayToSend[1]);
            int y = Integer.valueOf(arrayToSend[2]);
            int w = Integer.valueOf(arrayToSend[3]);
            int h = Integer.valueOf(arrayToSend[4]);

            if (h <= 0 || w <= 0) {
                System.out.println("Rectangle rejected: (" + x + ", " + y + ", "
                    + w + ", " + h + ")");
            }
            else {
                data.regionsearch(x, y, w, h); // only PRQuadtree
            }
        }
 
        else if (line.contains("search")) {
            String[] arrayToSend = cleanLine(line);
            String pointName = arrayToSend[1];
            data.search(pointName);
            // SkipLiat search remains the same for name.
            // After name search it has to be searched in the tree with
            // coordinates so change that in the database.

        }

        else if (line.contains("remove")) {
            String[] arrayToSend = cleanLine(line);
            String pointName = arrayToSend[1];
            if (arrayToSend.length > 2) {
                int x = Integer.valueOf(arrayToSend[1]);
                int y = Integer.valueOf(arrayToSend[2]);

                if (x < 0 || y < 0 || x > 1024 || y > 1024) {

                    System.out.println("Point Rejected: (" + x + ", " + y
                        + ")");
                    return;
                }

                data.remove(x, y);
                // PRQuadTree remove and then remove it from the skipList by
                // name
            }

            else {
                data.remove(pointName);
                // SkipList remove by name
                // Then call the PRQuadTree remove
            }
        }
// else if (line.contains("duplicates")) {
// data.duplictaes();
//
// }
 
        else

        {
            data.dump(); // Both
        }

    }


    /**
     * This method cleans up the given line and splits it up into a String Array
     * 
     * @param line
     *            The line to be formatted
     * 
     * @return returns a string array
     */
    public String[] cleanLine(String line) {
        // Trim the line from the sides
        line = line.trim();
        // Split the string and store all the values in a string array.
        String[] arr = line.split("\\s+");
        // System.out.println(Arrays.toString(arr));
        return arr;

    }

}
