import java.io.*;
import java.util.*;
// 10시 26분 시작
public class Boj14620 {
    static int answer = Integer.MAX_VALUE;
    static final int[][] DXYS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int[][] grid;
    static boolean[][] visited;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        visited = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < n; j++) {
                grid[i][j] = line[j];
            }
        }
        dfs(n, 0, 0);
        // 꽃을 3개를 피어야한다.
        // 필요한 땅은 15개(5개, 5개, 5개) 이 격자의 좌표는 서로 겹치지않아야한다.
        // 그렇다면, 백트래킹이 먼저 생각난다. 좌표를 선택하고 4방향으로 여유로운지 확인, visited 체크를 통해서 처리할 수 있다. 해본다.
        System.out.println(answer);

    }

    private static void dfs(int n, int cnt, int cost) {
        if (cnt == 3) {
            answer = Math.min(answer, cost);
            return;
        }

        for (int x  = 0; x < n; x++) {
            for (int y  = 0; y < n; y++) {
                if (visited[x][y]) continue;
                if (!plantFlower(n, x, y)) continue;
                int estimateCostedCost = estimateCost(x, y);
                dfs(n, cnt + 1, cost + estimateCostedCost);
                removeFlower(x, y);
            }
        }
    }

    private static boolean plantFlower(int n, int x, int y) {
        for (int[] dxy : DXYS) {
            int nx = x + dxy[0];
            int ny = y + dxy[1];
            if (!canPlant(n, nx, ny)) return false;
            if (visited[nx][ny]) return false;
        }
        visited[x][y] = true;
        visited[x-1][y] = true;
        visited[x+1][y] = true;
        visited[x][y+1] = true;
        visited[x][y-1] = true;
        return true;
    }

    private static void removeFlower(int x, int y) {
        visited[x][y] = false;
        for (int[] dxy : DXYS) {
            int nx = x + dxy[0];
            int ny = y + dxy[1];
            visited[nx][ny] = false;
        }
    }

    private static boolean canPlant(int n, int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

    private static int estimateCost(int x, int y) {
        int cost = 0;
        cost += grid[x][y];
        for (int[] dxy : DXYS) {
            cost += grid[x+dxy[0]][y+dxy[1]];
        }
        return cost;
    }

}