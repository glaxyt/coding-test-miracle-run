import java.io.*;
import java.util.*;
public class BJ1890 {
    private static int N;
    private static final int[] dx = {1, 0}, dy = {0, 1};
    private static boolean inRange(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < N;
    }
    private static class Point{
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int[][][] grid = new int[N][N][2]; //grid[][][0] = 한번에 점프할 수 있는 수, grid[][][1] = 해당 위치까지 올 수 있는 경우의 수

        StringTokenizer st;
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                grid[i][j][0] = Integer.parseInt(st.nextToken());
            }
        }

        Deque<Point> dq = new ArrayDeque<>();
        dq.offerLast(new Point(0, 0));
        grid[0][0][1] = 1;

        while(!dq.isEmpty()) {
            Point curr = dq.pollFirst();
            int jump = grid[curr.x][curr.y][0];

            if(jump == 0) continue;

            for(int i = 0; i < 2; i++) {
                int nx = curr.x + jump * dx[i], ny = curr.y + jump * dy[i];

                if(inRange(nx, ny)) {
                    if(grid[nx][ny][1] == 0) dq.offerLast(new Point(nx, ny));
                    grid[nx][ny][1] += grid[curr.x][curr.y][1];
                }
            }
        }

        System.out.println(grid[N - 1][N - 1][1]);
    }
}
