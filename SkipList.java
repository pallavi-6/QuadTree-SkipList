import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author Pallavi Padmanabha
 *         Asmita Hanchate
 * 
 * @version 2021-10-14
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V>
    implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element of the top level
    private int size; // number of entries in the Skip List
    private int level;

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 0);
        size = 0;
        level = -1;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode
     * 
     * @return a random level number
     * 
     *         Reference - SkipLists -> OpenDSA
     */
    int randomLevel() {
        int lev;
        Random value = new Random();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }
 

    /**
     * Searches for the KVPair using the key which is a Comparable object.
     * 
     * Reference - SkipLists -> OpenDSA
     * 
     * @param key
     *            key to be searched for
     * 
     * @return arrList
     *         Returns a list of arrays containing all the rectangles matching
     *         the search.
     */
    public ArrayList<KVPair<K, V>> search(K key) {

        ArrayList<KVPair<K, V>> arrList = new ArrayList<KVPair<K, V>>();
        // This returns a list of points when we search for a point by name.
        SkipNode x = head; // Dummy header node
        // moved out of the loop

        for (int i = level; i >= 0; i--) { // For each level...
            while ((x.forward[i] != null) && (x.forward[i].element().getKey()
                .compareTo(key) < 0)) { // go forward
                x = x.forward[i]; // Go one last step
            }
        }
        x = x.forward[0]; // Move to actual record, if it exists

        if ((x != null) && (x.element().getKey().compareTo(key) == 0))
        // Create a new array list for saving all the searched rectangles
        {
            arrList.add(x.element());
            // move to the next element on 0th level to see if it is the same
            x = x.forward[0];
            while (x != null && (x.element().getKey().compareTo(key) == 0)) {
                arrList.add(x.element());
                x = x.forward[0];
            }
        }

        return arrList;

    }


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     * 
     * Reference - SkipLists -> OpenDSA
     * 
     * @param it
     *            the KVPair to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) { // If new node is deeper
            adjustHead(newLevel); // adjust the header
        }
        // Track end of level
        SkipNode[] update = (SkipNode[])Array.newInstance(
            SkipList.SkipNode.class, level + 1);
        SkipNode x = head; // Start at header node
        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.forward[i] != null) && (x.forward[i].element().getKey()
                .compareTo(it.getKey()) < 0)) {
                x = x.forward[i];
            }
            update[i] = x; // Track end at level i
        }
        x = new SkipNode(it, newLevel);
        for (int i = 0; i <= newLevel; i++) { // Splice into list
            x.forward[i] = update[i].forward[i]; // Who x points to
            update[i].forward[i] = x; // Who points to x
        }
        size++; // Increment dictionary size
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     * 
     * Reference - SkipLists -> OpenDSA
     * 
     * @param newLevel
     *            the number of levels to be added to head
     */
    // @SuppressWarnings("unchecked")
    private void adjustHead(int newLevel) {
        SkipNode temp = head;
        head = new SkipNode(null, newLevel);
        for (int i = 0; i <= level; i++) {
            head.forward[i] = temp.forward[i];
        }
        level = newLevel;
    }


    /**
     * Removes the KVPair that is passed in as a parameter and returns true if
     * the pair was valid and false if not.
     * 
     * @param key
     *            The key which is to be removed from the SkipList.
     * 
     * @return returns the removed pair if the pair was valid.
     */

    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        SkipNode[] update = (SkipNode[])Array.newInstance(
            SkipList.SkipNode.class, level + 1);
        SkipNode x = head;
        // To search the element for deletion
        for (int i = level; i >= 0; i--) {
            while ((x.forward[i] != null) && (x.forward[i].element().getKey()
                .compareTo(key) < 0)) {
                x = x.forward[i];
            }
            update[i] = x;
        }
        x = x.forward[0];

        if (x != null && x.element().getKey().compareTo(key) == 0) {
            
            int i = 0;
            while (i <= level) {
                if (update[i].forward[i] == x) {
                    update[i].forward[i] = x.forward[i];
                }
                else {
                    break;
                }
                i++;
            }
            size--;
        }
        // To avoid null pointer exception
        if (x != null) {
            return x.element();

        }
        KVPair<K, V> nullPair = null;
        return nullPair;

    }

 /**
 * Removes a KVPair with the specified value.
 *
 * @param pair
 * The pair which is to be removed.
 */
 public void removeByValue(KVPair<K, V> pair) {

 @SuppressWarnings("unchecked")
 SkipNode[] update = (SkipNode[])Array.newInstance(
 SkipList.SkipNode.class, level + 1);
 SkipNode x = head;
 // To search the element for deletion
 for (int i = level; i >= 0; i--) {
 while ((x.forward[i] != null) && (x.forward[i].element().getKey()
 .compareTo(pair.getKey()) <= 0)) {
 V value = x.forward[i].element().getValue();
 V value1 = pair.getValue();
 if (value.toString().compareTo(value1.toString()) == 0) {
 break;
 }
 else {
 x = x.forward[i];
 }

 }
 update[i] = x; // Track end at level i
 }
 x = x.forward[0];

 if (x != null) {
 int i = 0;
 while (i <= level) {
 if (update[i].forward[i] != x) {
 break;
 }
 else {
 update[i].forward[i] = x.forward[i];
 }
 i++;
 }
 size--;
 }

 }



    /**
     * Prints out the SkipList in a human readable format to the console.
     */
    public void dump() {
        System.out.println("SkipList Dump:");
        SkipNode x = head;
        int depth = 0;
        int headDepth = 1;
        String[] arr = new String[size + 1];
        // Might give an exception. Confirm i<this.size statement.
        for (int i = 1; i <= this.size(); i++) {

            depth = x.forward.length;
            if (headDepth < depth) {
                headDepth = depth;
            }
            arr[i] = (Integer.toString(depth) + "," + x.forward[0].element()
                .getKey() + "," + x.forward[0].element().getValue());
            x = x.forward[0];

        }
        arr[0] = Integer.toString(headDepth);
        System.out.println("Node has depth " + arr[0] + ", Value (null)");
        for (int i = 1; i < arr.length; i++) {
            String arrayString = arr[i].toString();
            String[] dumpArray = arrayString.replaceAll(" ", "").split(",");
            int dumpArrayDepth = Integer.parseInt(dumpArray[0]);
            String name = dumpArray[1];
            int xCoordinate = Integer.parseInt(dumpArray[2]);
            int yCoordinate = Integer.parseInt(dumpArray[3]);

            System.out.println("Node has depth " + dumpArrayDepth + ", Value ("
                + name + ", " + xCoordinate + ", " + yCoordinate + ")");
        }
        System.out.println("SkipList size is: " + this.size());
    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     * 
     * @author Pallavi Padmanabha
     *         Asmita Hanchate
     * 
     * @version 2021-10-14
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // what is this
        private SkipNode[] forward;
        // the number of levels

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            for (int i = 0; i < level; i++) {
                forward[i] = null;
            }

        }


        /**
         * Returns the KVPair stored in the SkipList.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }


    /**
     * 
     * 
     * @author Pallavi Padmanabha
     *         Asmita Hanchate
     * 
     *         Reference - GeeksForGeeks Iterators
     *
     */

    
    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipNode current;

        public SkipListIterator() {
            current = head;
        }


        @Override
        public boolean hasNext() {
            return current.forward[0] != null;
        }


        // @SuppressWarnings("unchecked")
        @Override
        public KVPair<K, V> next() {

            current = current.forward[0];
            return current.element();

        }

    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {

        return new SkipListIterator();
    }

}
