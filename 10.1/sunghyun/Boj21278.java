package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Boj21278 {
    static int minDis = Integer.MAX_VALUE;
    static int[] answer = new int[2];
    static int[][] graph;
    static boolean[] selected;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // X의 접근성: 건물 X에서 가장 가까운 치킨집을 왕복하는 최단거리.
        // 왕복값이 가장 짧은거리. 왕복을 굳이 구할 필요는 없다. 거리를 계산하는 것 뿐.
        // 왕복거리가 같다면, 건물 번호가 작을수록 좋다.

        // 결국 한점에서 한점까의 최단거리를 구한다.
        // 모든 점에서 모든 점을 구하는 문제는 플로이드 워셜이며,
        // 이때 거리를 구한 것을 활용하여, 건물을 고르고 왕복거리를 계산해서 비교한다. 이때 비교는 오름차순으로
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int m = input[1];
        selected = new boolean[n+1];

        graph = new int[n+1][n+1];

        for (int i = 0; i < n+1; i++) {
            Arrays.fill(graph[i], 101);
		}

        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < n+1; j++) {
                if (i == j) graph[i][j] = 0;
            }
        }

        for (int i = 0; i < m; i++) {
            int[] data = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int a = data[0];
            int b = data[1];
            graph[a][b] = 1;
            graph[b][a] = 1;
        }

        for (int k = 1; k < n+1; k++) {
            for (int a = 1; a < n+1; a++) {
                for (int b = 1; b < n+1; b++) {
                    graph[a][b] = Math.min(graph[a][b], graph[a][k] + graph[k][b]);
                }
            }
        }

        dfs(n, 0, 0, 0);

        System.out.println(answer[0] + " " + answer[1] + " " + minDis);
    }

    private static void dfs(int n, int cnt, int a, int b) {
        if (cnt == 2) {
            int result = calc(n, a, b);
            if (minDis > result) {
                answer[0] = a;
                answer[1] = b;
                minDis = result;
            }
            return;
        }

        for (int i = 1; i < n+1; i++) {
            if (selected[i]) continue;

            selected[i] = true;

            if (cnt == 0) {
                dfs(n, cnt+1, i, b);
            } else {
                dfs(n, cnt+1, a, i);
            }

            selected[i] = false;
        }
    }

    private static int calc(int n, int a, int b) {
        int result = 0;

        for (int i = 1; i < n+1; i++) {
            if (i == a || i == b) continue;
            result += Math.min(graph[i][a], graph[i][b]);
        }

        return result * 2;
    }
}
