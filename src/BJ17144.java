import java.io.*;
import java.util.*;

public class BJ17144 {
    private static int R, C, T;
    private static int[][] grid;
    private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    private static boolean inRange(int x, int y) {
        return 0 <= x && x < R && 0 <= y && y < C;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        grid = new int[R][C];
        for(int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < C; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}