package Geometry;

import java.util.*;
import static Geometry.Utils.*;

/**
 * Representation of a Point
 * @author Lewin
 *
 */
public class Point implements Comparable <Point> {
    public double   x, y, angle;
    public int idx;
    
    public Point (double x, double y) {
        this.x = x;
        this.y = y;
        angle = 0;
    }
    
    public Point (double x, double y, int idx) {
        this.x = x;
        this.y = y;
        this.idx = idx;
    }
    
    public int compareTo (Point other) {
        double test = angle - other.angle;
        if (test < -EPS)
            return -1;
        if (test > EPS)
            return 1;
        return 0;
    }
}
