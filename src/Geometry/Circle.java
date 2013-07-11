package Geometry;


/**
 * Representation of a circle
 * @author Lewin
 *
 */
import static Geometry.Utils.*;

public class Circle {
    // circle centered at p with radius r
    public Point p;
    public double r;
    
    public Circle (double x, double y, double r) {
        p = new Point (x, y);
        this.r = r;
    }
    
    // finds unique circle from 3 points
    public Circle (Point a, Point b, Point c) {
        double da1 = 2 * (c.x - a.x);
        double da2 = 2 * (c.x - b.x);
        double db1 = 2 * (c.y - a.y);
        double db2 = 2 * (c.y - b.y);
        double dc1 = c.x * c.x - a.x * a.x + c.y * c.y - a.y * a.y;
        double dc2 = c.x * c.x - b.x * b.x + c.y * c.y - b.y * b.y;
        
        double x = 0, y = 0;
        if (Math.abs (da1) <= EPS) {
            y = dc1 / db1;
            x = (dc2 - db2 * y) / da2;
        } else {
            db2 -= db1 * da2 / da1;
            dc2 -= dc1 * da2 / da1;
            y = dc2 / db2;
            x = (dc1 - db1 * y) / da1;
        }
        p = new Point (x, y);
        r = distance (p, a);
    }
    
    public boolean contains (Point other) {
        return distance (other, p) < r;
    }
}
