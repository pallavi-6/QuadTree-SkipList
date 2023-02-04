import java.awt.Point;

public class Coordinates extends Point {

    int count;
    String key;

    public Coordinates() {
        count = 1;
    }

    public String toString() {
        return "(" + this.key + ", " + this.x + ", " + this.y + ")";
    }

}
