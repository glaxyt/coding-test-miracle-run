package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Boj16948 {
    static final int[][] DXYS = {{-2, -1}, {-2, 1}, {0, -2}, {0, 2}, {2, -1}, {2, 1}};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // N * N 격자 안에서 특정 위치에서 최종 목적지까지 가는 최단거리를 계산해야한다.
        // BFS도 가능. 백트래킹도 가능

        int n = Integer.parseInt(br.readLine());
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Node startNode = new Node(input[0], input[1]);
        Node finishNode = new Node(input[2], input[3]);

        int answer = findShortestPath(n, startNode, finishNode);
        System.out.println(answer);
    }

    private static int findShortestPath(int n, Node startNode, Node finishNode) {

        Deque<Node> dq = new ArrayDeque<>();
        dq.offer(startNode);

        int[][] visited = new int[n][n];
        visited[startNode.x][startNode.y] = 1;

        while (!dq.isEmpty()) {
            Node curNode = dq.poll();

            if (curNode.x == finishNode.x && curNode.y == finishNode.y) break;

            for (int[] dxy : DXYS) {
                int nx = curNode.x + dxy[0];
                int ny = curNode.y + dxy[1];
                if (!canMove(n, nx, ny)) continue;
                if (visited[nx][ny] != 0) continue;

                visited[nx][ny] = visited[curNode.x][curNode.y] + 1;
                dq.offer(new Node(nx, ny));
            }
        }

        int shortestPathCost = visited[finishNode.x][finishNode.y];

        if (shortestPathCost == 0) return -1;
        return shortestPathCost - 1;
    }

    private static boolean canMove(int n, int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }
}

class Node {
    int x;
    int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}