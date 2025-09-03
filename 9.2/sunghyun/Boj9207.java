package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// 10시 35분 시작.
public class Boj9207 {
    static char[][] grid;
    static int maxPinMoved;
    static final int[][] DXYS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static final int GRID_WIDTH = 9;
    static final int GRID_HEIGHT = 5;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 이차원 좌표에서 구멍이 주어져있다.(구멍이 없을 수도 았음) 구멍에는 핀을 한개를 꽂을 수 있다.
        // 핀의 이동이 가능하다. 상하좌우 방향으로 인접한 핀이 있고, 그 핀을 넘어서로 다음칸으로만 이동 가능
        // 이동한 칸은 비어있어하며 이동한 이후에는 인접했던 핀을 제거해야한다.
        // '.'는 빈 칸, 'o'는 핀이 꽂혀있는 칸, '#'는 구멍이 없는 칸
        // 목표는 판에 남아있는 핀의 개수를 최소로 만드는 것
        // 예시까지 봐야 이해가 잘 될 것 같다.
        // 여기서 인접한 칸이라는 것은 거리가 1인 핀을 뜻한다.
        // 그리디하게 생각할 수 있는가? 아니다 모든 경우를 봐야할 것 같다.
        // 격자는 항상 9*5이기에 백트래킹으로 가도 큰 무리는 없어보인다.
        // 종결조건문은 딱히 필요없다. 더 이상 인접한 핀이 없으면 종료되기 때문.
        // 매번마다 없애 핀의 개수를 계산한다. 가장 많이 핀을 없앨 수 있는 상황을 찾는 것이 푸는 방법
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            grid = initializeGrid();
            maxPinMoved = 0;
            int pinCnt = 0;
            for (int i = 0; i < GRID_HEIGHT; i++) {
                char[] line = br.readLine().toCharArray();
                for (int j = 0; j < GRID_WIDTH; j++) {
                    if (line[j] == 'o') pinCnt++;
                    grid[i][j] = line[j];
                }
            }

            dfs(0, 0);
            System.out.println((pinCnt - maxPinMoved) + " " + maxPinMoved);

            if (t != 0) {
                String empty = br.readLine();
            }
        }
    }

    private static void dfs(int removedPinCnt, int moveCnt) {
        maxPinMoved = Math.max(maxPinMoved, moveCnt);

        for (int x = 0; x < GRID_HEIGHT; x++) {
            for (int y = 0; y < GRID_WIDTH; y++) {
                if (grid[x][y] == 'o') {
                    for (int[] dxy : DXYS) {
                        int nx = x + dxy[0];
                        int ny = y + dxy[1];
                        if (!canMove(nx, ny)) continue;
                        if (grid[nx][ny] != 'o') continue;
                        int landingX = nx + dxy[0];
                        int landingY = ny + dxy[1];
                        if (!canMove(landingX, landingY)) continue;
                        if (grid[landingX][landingY] != '.') continue;
                        grid[nx][ny] = '.';
                        grid[x][y] = '.';
                        grid[landingX][landingY] = 'o';
                        dfs(removedPinCnt + 1, moveCnt + 1);
                        grid[landingX][landingY] = '.';
                        grid[x][y] = 'o';
                        grid[nx][ny] = 'o';
                    }
                }
            }
        }
    }

    private static char[][] initializeGrid() {
        return new char[GRID_HEIGHT][GRID_WIDTH];
    }

    private static boolean canMove(int x, int y) {
        return 0 <= x && x < GRID_HEIGHT && 0 <= y && y < GRID_WIDTH;
    }
}