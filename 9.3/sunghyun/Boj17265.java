package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// 10시 18분 시작
public class Boj17265 {
    static char[][] grid;
    static final int[][] DXYS = {{0, 1}, {1, 0}};
    static int maxResult = Integer.MIN_VALUE;
    static int minResult = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        /* 연산자 우선순위는 고려하지않는다.
         * (1, 1)부터 시작이니 -1을 고려해준다.
         * 오른쪽과 아래로만 이동해서 도착지인 (N, N)까지 가야한다.
         * 최댓값과 최솟값을 구해야한다.
         * 지도의 크기는 그렇게 크지않다.
         */

        int n = Integer.parseInt(br.readLine());

        grid = new char[n][n];
        for (int i = 0; i < n; i++) {
            char[] line = br.readLine().replaceAll(" ", "").toCharArray();
            for (int j = 0; j < n; j++) {
                grid[i][j] = line[j];
            }
        }

        List<Character> maxResultArr = new ArrayList<>();
        maxResultArr.add(grid[0][0]);
        findMaxResult(n, 0, 0, maxResultArr);

        List<Character> minResultArr = new ArrayList<>();
        minResultArr.add(grid[0][0]);
        findMinResult(n, 0, 0, minResultArr);

        System.out.println(maxResult + " " + minResult);
    }

    private static void findMaxResult(int n, int x, int y, List<Character> resultArr) {
        if (x == n - 1 && y == n - 1) {
            maxResult = Math.max(maxResult, calculate(resultArr));
            return;
        }

        for (int[] dxy : DXYS) {
            int nx = x + dxy[0];
            int ny = y + dxy[1];
            if (!canMove(n, nx, ny)) continue;
            resultArr.add(grid[nx][ny]);
            findMaxResult(n, nx, ny, resultArr);
            resultArr.remove(resultArr.size() - 1);
        }
    }

    private static void findMinResult(int n, int x, int y, List<Character> resultArr) {
        if (x == n - 1 && y == n - 1) {
            minResult = Math.min(minResult, calculate(resultArr));
            return;
        }

        for (int[] dxy : DXYS) {
            int nx = x + dxy[0];
            int ny = y + dxy[1];
            if (!canMove(n, nx, ny)) continue;
            resultArr.add(grid[nx][ny]);
            findMinResult(n, nx, ny, resultArr);
            resultArr.remove(resultArr.size() - 1);
        }
    }

    private static int calculate(List<Character> resultArr) {
        int result = 0;
        char exOps = '+';
        for (int i = 0; i < resultArr.size(); i++) {
            // character라 연산 주의
            // 연산자는 (‘+’, ‘-’, ‘*’) 만 주어진다.
            char value = resultArr.get(i);
            if (value == '+' || value == '-' || value == '*') exOps = value;
            else {
                result = opsWithNum(result, exOps, value - '0');
            }
        }
        return result;
    }

    private static int opsWithNum(int result, char exOps, int value) {
        if (exOps == '+') return result + value;
        else if (exOps == '-') return result - value;
        else return result * value;
    }

    private static boolean canMove(int n, int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }
}