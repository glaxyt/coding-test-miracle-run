import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Boj17144 {
    static int[][] grid;
    static final int[][] DXYS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int r = input[0];
        int c = input[1];
        int t = input[2];
        grid = new int[r][c];
        Queue<int[]> que = new LinkedList<>();
        int[] airConditioner = new int[4];
        boolean find = false;

        for (int i = 0; i < r; i++) {
            int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < c; j++) {
                if (line[j] == -1 && !find) {
                    airConditioner[0] = i;
                    airConditioner[1] = 0;
                    airConditioner[2] = i+1;
                    airConditioner[3] = 0;
                    find = true;
                } else if (line[j] >= 5) {
                    que.offer(new int[]{i, j});
                }
                grid[i][j] = line[j];
            }
        }

        // 방문처리 안해줄거임, 미세먼지끼리 뭉칠 수 있기 때문.
        // 같은 시간대에 같은 미세먼지 자리에서 2번 확산되는 문제 발생
        // 인접한 미세먼지의 확산된 값으로 확산하는 문제 발생

        while (t-- > 0) {
            int[][] spread = new int[r][c];

            while (!que.isEmpty()) {
                int[] curPos = que.poll();
                int cx = curPos[0];
                int cy = curPos[1];

                int spreadAmount = grid[cx][cy] / 5;
                if (spreadAmount == 0) {
                    continue;
                }

                for (int[] dxy : DXYS) {
                    int nx = cx + dxy[0];
                    int ny = cy + dxy[1];

                    if (!canMove(r, c, nx, ny)) continue;

                    if (grid[nx][ny] == -1) continue;

                    grid[cx][cy] -= spreadAmount;
                    spread[nx][ny] += spreadAmount;
                }
            }

            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    grid[i][j] += spread[i][j];
                }
            }

            cleanUpperDust(r, c, airConditioner[0], airConditioner[1]);
            cleanLowerDust(r, c, airConditioner[2], airConditioner[3]);

            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (grid[i][j] >= 5) {
                        que.offer(new int[]{i, j});
                    }
                }
            }
        }

        System.out.println(printAnswer(r, c, airConditioner));
    }

    private static void cleanUpperDust(int r, int c, int x, int y) {
        int spread = grid[x][1];
        grid[x][1] = 0;
        int temp;
        for (int i = 2; i < c; i++) {
            temp = grid[x][i];
            grid[x][i] = spread;
            spread = temp;
        }

        for (int i = x-1; i >= 0; i--) {
            temp = grid[i][c-1];
            grid[i][c-1] = spread;
            spread = temp;
        }

        for (int i = c-2; i >= 0; i--) {
            temp = grid[0][i];
            grid[0][i] = spread;
            spread = temp;
        }

        for (int i = 1; i <= x-1; i++) {
            temp = grid[i][0];
            grid[i][0] = spread;
            spread = temp;
        }
    }

    private static void cleanLowerDust(int r, int c, int x, int y) {
        int spread = grid[x][1];
        grid[x][1] = 0;
        int temp;
        for (int i = 2; i < c; i++) {
            temp = grid[x][i];
            grid[x][i] = spread;
            spread = temp;
        }

        for (int i = x+1; i <= r-1; i++) {
            temp = grid[i][c-1];
            grid[i][c-1] = spread;
            spread = temp;
        }

        for (int i = c-2; i >= 0; i--) {
            temp = grid[r-1][i];
            grid[r-1][i] = spread;
            spread = temp;
        }

        for (int i = r-2; i >= x+1; i--) {
            temp = grid[i][0];
            grid[i][0] = spread;
            spread = temp;
        }
    }

    private static boolean canMove(int r, int c, int x, int y) {
        return 0 <= x && x < r && 0 <= y && y < c;
    }

    private static int printAnswer(int r, int c, int[] airConditioner) {
        int answer = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == -1) continue;
                answer += grid[i][j];
            }
        }
        return answer;
    }
}
