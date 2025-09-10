import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Boj17425 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 최대값까지 미리 계산
        final int MAX_N = 1_000_000;
        long[] f = new long[MAX_N + 1]; // f[i] = i의 약수의 합
        long[] g = new long[MAX_N + 1]; // g[i] = f[1] + f[2] + ... + f[i]

        // 에라토스테네스 체 방식으로 모든 약수의 합 계산
        // i의 배수들(i, 2i, 3i, ...)에 i를 더해줌
        for (int i = 1; i <= MAX_N; i++) {
            for (int j = i; j <= MAX_N; j += i) {
                f[j] += i; // j의 약수 중 하나인 i를 더함
            }
        }

        // 누적합 계산 (g[x] = f[1] + f[2] + ... + f[x])
        for (int i = 1; i <= MAX_N; i++) {
            g[i] = g[i-1] + f[i];
        }

        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            sb.append(g[n]).append('\n');
        }

        System.out.print(sb);
    }
}