package Geometry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class ClosestPair {
    private static State closestPair(Point[] points) {
        Arrays.sort (points);
        double min = 1e9;
        int p1 = -1, p2 = -1;
        int left = 0;
        
        TreeSet <Point> curset = new TreeSet <Point>(
                new Comparator <Point> () {
                    public int compare (Point a, Point b) {
                        return (int)(Math.signum (a.y == b.y ? a.x - b.x : a.y - b.y));
                    }
                });
        
        for (Point p : points) {
            while (p.x - points [left].x > min)
                curset.remove (points [left++]);
            for (Point next : curset.subSet (
                    new Point (p.x, (int)(p.y - min), 0),
                    new Point (p.x, (int)(p.y + min), 0))) {
                double temp = dist (p, next);
                if (temp < min) {
                    min = temp;
                    p1 = p.idx; p2 = next.idx;
                }
            }
            curset.add (p);
        }
        
        return new State (p1, p2, min);
    }
    
    private static double dist (Point p1, Point p2) {
        return Math.sqrt ((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    static class State {
        public int p1, p2;
        public double dist;
        
        public State (int p1, int p2, double dist) {
            if (p2 < p1) {p2 ^= p1; p1 ^= p2; p2 ^= p1;}
            this.p1 = p1;
            this.p2 = p2;
            this.dist = dist;
        }
    }
}
