import java.io.*;
import java.util.*;
public class BJ15651 {
    private static int N, M;
    private static boolean[] visited;
    private static StringBuilder sb = new StringBuilder();
    private static void repeatedPermutation(int depth, int start, int[] result) {
        if(depth == M) {
            for(int r : result) {
                sb.append(r).append(" ");
            }
            sb.append("\n");
            return;
        }

        for(int i = 1; i <= N; i++) {
            result[depth] = i;
            repeatedPermutation(depth + 1, start, result);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new boolean[N + 1];
        repeatedPermutation(0, 0, new int[M]);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
