import java.io.*;
import java.util.*;

public class BJ14503 {
    private static int cleaned = 0;
    private static int N, M;
    private static final int[] dx = {-1, 0, 1, 0};
    private static final int[] dy = {0, 1, 0, -1};
    private static int[][] grid;

    private static boolean inRange(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < M;
    }

    private static class Point{
        int x, y, d;
        public Point(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        grid = new int[N][M];
        boolean[][] visited = new boolean[N][M];

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Deque<Point> deque = new ArrayDeque<>();
        if(grid[r][c] == 1) {
            System.out.println(cleaned);
            return;
        }
        deque.offerLast(new Point(r, c, d));

        while(!deque.isEmpty()) {
            Point curr = deque.pollLast();

            if(!visited[curr.x][curr.y]) {
                visited[curr.x][curr.y] = true;
                cleaned++;
            }

            boolean canClean = false;
            for(int i = 1; i <= 4; i++) {
                int nextDir = (curr.d - i + 4) % 4;
                int nextX = curr.x + dx[nextDir];
                int nextY = curr.y + dy[nextDir];
                if(inRange(nextX, nextY) && grid[nextX][nextY] == 0 && !visited[nextX][nextY]) {
                    canClean = true;
                    deque.offerLast(new Point(nextX, nextY, nextDir));
                    break;
                }
            }

            if(!canClean) {
                int nextDir = (curr.d + 2) % 4;
                int nextX = curr.x + dx[nextDir], nextY = curr.y + dy[nextDir];

                if (inRange(nextX, nextY) && grid[nextX][nextY] != 1) {
                    deque.offerLast(new Point(nextX, nextY, curr.d));
                } else {
                    break;
                }
            }
        }

        System.out.println(cleaned);
    }
}
