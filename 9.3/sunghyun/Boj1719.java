package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

// 11시 1분 시작.
public class Boj1719 {
    static int[][] graph;
    static int[][] answer;
    static int[][] dis;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        /* 한 점에서 한 점으로의 최단거리 계산 시에 최솟값임을 알 수 있는 방법은 도착한 뒤다.
         * 따라서 제일 먼저 거치는 장소를 저장해두고 있어야한다.
         * 플로이드 워셜이 나을 수도. 결국 한점에서 모든 점의 거리를 계산해야하니
         * 정점은 1부터 주어지기 때문에 +1 해줘야한다. 그래프의 크기를 n+1로 처리
         * 양방향 그래프를 구현해준다.
         */

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int m = input[1];
        answer = new int[n+1][n+1];

        // 그래프 생성
        graph = new int[n+1][n+1];
        while (m-- > 0) {
            int[] data = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int a = data[0];
            int b = data[1];
            int w = data[2];
            graph[a][b] = w;
            graph[b][a] = w;
        }

        for (int i = 1; i < n+1; i++) {
            dijkstra(n, i);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    System.out.print("- ");
                } else {
                    System.out.print(answer[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private static void dijkstra(int n, int start) {
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((int[] a, int[] b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int curNode = current[0];
            int curDist = current[1];

            if (curDist > dist[curNode]) continue;

            for (int nextNode = 1; nextNode <= n; nextNode++) {
                if (graph[curNode][nextNode] == 0) continue;
                int newDist = dist[curNode] + graph[curNode][nextNode];

                // 첫 번째로 거쳐야 하는 정점 결정
                if (newDist < dist[nextNode]) {
                    dist[nextNode] = newDist;

                    // 첫 번째로 거쳐야 하는 정점 결정
                    if (curNode == start) {
                        // 시작점에서 직접 연결된 경우
                        answer[start][nextNode] = nextNode;
                    } else {
                        // 다른 정점을 거쳐서 가는 경우
                        answer[start][nextNode] = answer[start][curNode];
                    }

                    pq.offer(new int[]{nextNode, newDist});
                }
            }
        }
    }
}