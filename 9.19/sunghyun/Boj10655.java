package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Boj10655 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 순서대로 방문해야한다.
        // 따라서 점과 점 사이에 간격거리가 있으며, 한 점을 스킵함으로써 거리를 줄일 수도 있다.
        // 그리디하게 할 수 있는가?
        // 그건 어려울 듯하다.
        // 그렇다면 백트래킹은?
        // 점의 개수는 100_000 개이다.
        // 그것보다. 한 칸앞의 점과의 거리를 비교해서 간격이 큰 점을 뛰어넘으면 되겠다는 생각이 들었다.
        int n = Integer.parseInt(br.readLine());
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int[] pointData = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            points.add(new Point(pointData[0], pointData[1]));
        }

        int maxInterval = Integer.MIN_VALUE;

        for (int i = 0; i < n - 2; i++) {
            Point start = points.get(i);
            Point skip = points.get(i+1);
            Point next = points.get(i+2);
            int interval = (calcPointDiff(start, skip) + calcPointDiff(skip, next)) - calcPointDiff(start, next);
            if (maxInterval < interval) {
                maxInterval = interval;
            }
        }

        int totalDis = 0;
        for (int i = 0; i < n - 1; i++) {
            totalDis += calcPointDiff(points.get(i), points.get(i+1));
        }

        System.out.println(totalDis - maxInterval);
    }

    private static int calcPointDiff(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}