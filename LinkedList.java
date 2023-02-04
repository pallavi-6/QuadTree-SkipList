import java.util.Iterator;

public class LinkedList {

    Node head = new Node(); // head of list
    int size;
    // Linked list Node.
    // This inner class is made static
    // so that main() can access it

    public LinkedList() {
        size = 0;
    }

    static class Node {

        Coordinates data;
        Node next;

        // Constructor
        Node() {

        }


        Node(Coordinates d) {
            data = d;
            next = null;

        }
    }

    // Method to insert a new node
    public LinkedList insert(LinkedList list, Coordinates data) {
        // Create a new node with given data
        Node new_node = new Node(data);
        new_node.next = null;

        // If the Linked List is empty,
        // then make the new node as head
        if (list.head == null) {
            list.head = new_node;
        }
        else {
            // Else traverse till the last node
            // and insert the new_node there
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }

            // Insert the new_node at last node
            last.next = new_node;
        }

        // Return the list by head
        size++;
        return list;
    }


    // Method to get the size of the LinkedList.
    public int size() {
        return size;
    }


    // Method to print the LinkedList.
    public void printList(LinkedList list) {
        Node currNode = list.head;

        System.out.print("LinkedList: ");

        // Traverse through the LinkedList
        while (currNode != null) {
            // Print the data at current node
            System.out.print(currNode.data + " ");

            // Go to next node
            currNode = currNode.next;
        }
    }


    // Changed static method. Ask why it was kept static.
    public LinkedList deleteByKey(LinkedList list, Coordinates key) {
        // Store head node
        Node currNode = list.head.next, prev = list.head;

        while (currNode != null && !(currNode.data.equals(key))) {
            // If currNode does not hold key
            // continue to next node
            prev = currNode;
            currNode = currNode.next;
        }

        if (currNode != null) {
            prev.next = currNode.next;

            // Display the message
            System.out.println(key + " found and deleted");
            size--;

        }

        else if (currNode == null) {
            // Display the message
            System.out.println(key + " not found");
        }

        // return the List

        return list;
    }


    boolean duplicates(LinkedList list, Coordinates data) {
        Node currNode = list.head.next;
        while (currNode != null) {

            if (currNode.data.x == data.x && currNode.data.y == data.y)
                return true;

            currNode = currNode.next;
        }
        return false;
    }


    // Method to get the coordinates from the LinkedList.
    public Coordinates get(int i) {
        Node currNode = head;
        int x = 0;
        while (currNode != null) {
            if (x == i) {
                break;
            }
            currNode = currNode.next;
            x++;
        }
        return currNode.data;
    }

    private class LinkedListIterator implements Iterator<Coordinates> {
        private Node current;

        public LinkedListIterator() {
            current = head;
        }


        @Override
        public boolean hasNext() {
            if (current == null)
                return false;

            return current.next != null;
        }


        @Override
        public Coordinates next() {
            if (current.next == null) {
                return null;
            }

            else {

                current = current.next;
                return current.data;
            }

        }

    }

    public Iterator<Coordinates> iterator() {

        return new LinkedListIterator();
    }

}
