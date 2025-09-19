package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Boj1996 {
    static final int[][] DXYS = {{0 ,1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 빈칸에는 0이 들어가있다.
        // 인접한 지뢰를(상화좌우대각) 나타내는 숫자가 존재한다.
        // 지뢰가 한 칸에 여러 개가 있을 수 있다.

        int n = Integer.parseInt(br.readLine());
        int[][] answer = new int[n][n];
        char[][] grid = new char[n][n];

        for (int x = 0; x < n; x++) {
            String line = br.readLine();
            for (int y = 0; y < n; y++) {
                char cur = line.charAt(y);
                if (cur != '.') {
                    for (int[] dxy : DXYS) {
                        int nx = x + dxy[0];
                        int ny = y + dxy[1];
                        if (!canMove(n, nx, ny)) continue;
                        answer[nx][ny] += (cur - '0');
                    }
                }
                grid[x][y] = cur;
            }
        }

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (grid[x][y] != '.') {
                    System.out.print('*');
                } else {
                    if (answer[x][y] >= 10) {
                        System.out.print('M');
                    } else {
                        System.out.print(answer[x][y]);
                    }
                }
            }
            System.out.println();
        }
    }

    private static boolean canMove(int n, int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }
}