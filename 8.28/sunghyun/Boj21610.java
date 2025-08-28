import java.io.*;
import java.util.*;

public class Boj21610 {
    static int m;
    static int[][] bucketGrid;
    static boolean[][] cloudGrid;
    static final int[][] DIRS = {{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        m = input[1];
        bucketGrid = new int[n][n];
        cloudGrid = new boolean[n][n];
        cloudGrid[n-1][0] = true;
        cloudGrid[n-2][0] = true;
        cloudGrid[n-1][1] = true;
        cloudGrid[n-2][1] = true;

        for (int i = 0; i < n; i++) {
            int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < n; j++) {
                bucketGrid[i][j] = line[j];
            }
        }

        for (int i = 0; i < m; i++) {
            int[] dirAndMove = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int dir = dirAndMove[0] - 1;
            int move = dirAndMove[1];
            startRain(n, dir, move);
        }
        // 비를 담을 수 있는 바구니가 존재한다.
        // (1, 1)부터 시작하기 때문에 -1을 해줘야한다.
        // 회전이 가능하다. % 가 들어가야한다.
        // 방향은 대각, 상하좌우가 가능한다.
        // 비바라기를 시전하면 (N, 1), (N, 2), (N-1, 1), (N-1, 2)에 비구름이 생긴다. 바구니는 1이 증가한다. 비는 한번 내리면 끝 사라진다.
        // 이건 구름이 있었던 곳만 해당: 물복사 버그 대각선 1거리에 물이 담긴 바구니 개수 만큼 물이 증가한다. 이때는 '회전'이 되지않음.
        // 물이 2 이상 되면 구름이 생기며, 이에 따른 물이 2 감소한다. 그리고 구름의 혜택을 받은 칸은 구름이 안생긴다.
        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                answer += bucketGrid[i][j];
            }
        }

        System.out.println(answer);
    }

    public static void startRain(int n, int dir, int move) {
        boolean[][] cloudMoves = new boolean[n][n];
        int dx = DIRS[dir][0];
        int dy = DIRS[dir][1];
        for (int cx = 0; cx < n; cx++) {
            for (int cy = 0; cy < n; cy++) {
                if (cloudGrid[cx][cy]) {
                    int nx = ((cx + (dx * move)) + (n * 51)) % n;
                    int ny = ((cy + (dy * move)) + (n * 51)) % n;
                    cloudMoves[nx][ny] = true;
                    cloudGrid[cx][cy] = false;
                }
            }
        }

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (cloudMoves[x][y]) {
                    bucketGrid[x][y] += 1;
                }
            }
        }

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (cloudMoves[x][y]) {
                    bucketGrid[x][y] += countBucketHoldRain(n, x, y);
                }
            }
        }

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (bucketGrid[x][y] >= 2 && !cloudMoves[x][y]) {
                    cloudGrid[x][y] = true;
                    bucketGrid[x][y] -= 2;
                }
            }
        }
    }

    private static int countBucketHoldRain(int n, int x, int y) {
        int cnt = 0;
        int[][] dirs = {{-1, 1}, {1, 1}, {-1, -1}, {1, -1}};

        for (int[] dir : dirs) {
            int nx = (x + dir[0]);
            int ny = (y + dir[1]);
            if (!canMove(n, nx, ny)) continue;
            if (bucketGrid[nx][ny] == 0) continue;
            cnt++;
        }

        return cnt;
    }

    private static boolean canMove(int n, int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

}
