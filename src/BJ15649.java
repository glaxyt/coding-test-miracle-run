import java.io.*;
import java.util.*;
public class BJ15649 {
    private static int N, M;
    private static boolean[] visited;
    private static void permutation(int depth, int start, int[] result) {
        if(depth == M) {
            for(int r : result) {
                System.out.print(r + " ");
            }
            System.out.println();
            return;
        }

        for(int i = 1; i <= N; i++) {
            if(visited[i]) continue;
            visited[i] = true;
            result[depth] = i;
            permutation(depth + 1, start, result);
            visited[i] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        visited = new boolean[N + 1];

        permutation(0, 0, new int[M]);
        br.close();

    }
}
