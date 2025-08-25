import java.io.*;
import java.util.*;
public class BJ1890_DP {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] grid = new int[N][N];
        long[][] dp = new long[N][N];

        StringTokenizer st;
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp[0][0] = 1;

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (dp[i][j] == 0 || grid[i][j] == 0) continue; //끝이거나, 아직 방문한적 없으면 pass

                int jump = grid[i][j]; //현재 jump 할 수 있는 수

                if(i + jump < N) dp[i + jump][j] += dp[i][j]; //밑으로 가서 범위안에 있으면
                if(j + jump < N) dp[i][j + jump] += dp[i][j]; //오른쪽으로 가서 범위안에 있으면
            }
        }

        System.out.println(dp[N - 1][N - 1]);
    }
}
