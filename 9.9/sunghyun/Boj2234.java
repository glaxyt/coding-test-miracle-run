import java.io.*;
import java.util.*;

// 11시 5분 시작.
public class Boj2234 {
    static int[][] grid;
    static boolean[][] visited;
    static final int[][] DXYS = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    static final int[] BIT_MASKING = {1, 2, 4, 8};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 성벽을 탐색 지나갈 수 있는 곳까지만 체크.
        // 대신 벽을 부실 수 있다면 부시고 체크.
        // 기본적으로 방의 개수를 확인해야함.
        // 비트마스킹도 필요하네 이거.
        // 기본은 BFS

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int m = input[1];

        // 벽을 묘사하는게 어렵네.
        // 일단 값을 집어넣는게 맞겠네.
        grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < n; j++) {
                grid[i][j] = line[j];
            }
        }

        visited = new boolean[m][n];
        int answer1 = 0;
        int answer2 = Integer.MIN_VALUE;
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                if (visited[x][y]) continue;
                visited[x][y] = true;
                answer2 = Math.max(answer2, bfs(m, n, x, y));
                answer1++;
            }
        }
        System.out.println(answer1);
        System.out.println(answer2);

        int answer3 = Integer.MIN_VALUE;
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                visited = new boolean[m][n];
                visited[x][y] = true;
                int basicResult = bfs(m, n, x, y);
                for (int idx = 0; idx < 4; idx++) {
                    int nx = x + DXYS[idx][0];
                    int ny = y + DXYS[idx][1];
                    if (!canMove(m, n, nx, ny)) continue;
                    if (isWallExist(x, y, idx) && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        answer3 = Math.max(answer3, basicResult + bfs(m, n, nx, ny));
                    } else {
                        answer3 = Math.max(answer3, basicResult);
                    }
                }
            }
        }
        System.out.println(answer3);
    }

    private static int bfs(int m, int n, int x, int y) {
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(x, y));
        visited[x][y] = true;
        int result = 1;

        while (!queue.isEmpty()) {
            Pos curPos = queue.poll();

            for (int idx = 0; idx < 4; idx++) {
                if (isWallExist(curPos.x, curPos.y, idx)) continue;

                int nx = curPos.x + DXYS[idx][0];
                int ny = curPos.y + DXYS[idx][1];

                if (!canMove(m, n, nx, ny)) continue;
                if (visited[nx][ny]) continue;
                visited[nx][ny] = true;
                result += 1;
                queue.offer(new Pos(nx, ny));
            }
        }
        return result;
    }

    private static boolean isWallExist(int x, int y, int idx) {
        return (grid[x][y] & BIT_MASKING[idx]) == BIT_MASKING[idx];
    }

    private static boolean canMove(int m, int n, int nx, int ny) {
        return 0 <= nx && nx < m && 0 <= ny && ny < n;
    }
}

class Pos {
    int x;
    int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}