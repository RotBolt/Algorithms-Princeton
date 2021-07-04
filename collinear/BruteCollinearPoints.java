/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MergeX;

import java.util.ArrayList;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        // verify input
        // 1. null check
        if (points == null)
            throw new IllegalArgumentException("Input is null.");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Input contains null.");
        }

        // copy input parameter to avoid direct modify
        Point[] copyPoints = points.clone();

        // sort local points to avoid mutate input
        MergeX.sort(copyPoints);

        // 2. duplicate check
        if (copyPoints.length > 1) {
            for (int m = 1; m < copyPoints.length; m++) {
                if (copyPoints[m].compareTo(copyPoints[m - 1]) == 0)
                    throw new IllegalArgumentException("Input contains duplicate.");
            }
        }
        MergeX.sort(copyPoints);
        ArrayList<LineSegment> collector = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < copyPoints.length; j++) {
                for (int k = j + 1; k < copyPoints.length; k++) {
                    for (int m = k + 1; m < copyPoints.length; m++) {
                        Point p = copyPoints[i];
                        Point q = copyPoints[j];
                        Point r = copyPoints[k];
                        Point s = copyPoints[m];

                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            LineSegment line = new LineSegment(p, s);
                            collector.add(line);
                        }
                    }
                }
            }
        }

        segments = collector.toArray(new LineSegment[collector.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return copy(segments);
    }

    private LineSegment[] copy(LineSegment[] arr) {
        LineSegment[] copy = new LineSegment[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }

        return copy;
    }

}
