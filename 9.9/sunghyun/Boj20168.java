import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Boj20168 {
    static int[][] graph;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 최대 요금을 최소화 해야한다.
        // A -> B 까지 가는데 C원을 소지하고 있음.
        // C원을 유지한채로 이동하되, 가중치의 합의 최솟값이 아닌, 한 경로의 가중치들의 최댓값 중에서 가장 최솟값을 찾으려한다.
        // 이동할 수 없다면 -1을 출력, 가는 길이 없다거나, C원으로는 부족한 경우
        // Node 안에서 이동할 때 C원을 차감해야한다.
        // 가중치가 존재하기 때문에 다익스트라가 수행되어야한다.
        // 가중치가 작은 값으로만 다익스트라에서 계산하게 되는데 이것이 중요한가?
        // 이전까지 거쳐온 길 중에서 가장 큰 값을 지불한 골목의 최댓값 중에서 가장 최솟값으로 계산하는게 좋지않을까?
        // 내가 어떤 골목을 지나가는데 고려하는건 '최소의 수치심' 가장 적은 골목이 중요한다.
        // 1 에서 2를 거쳐 가는데 8이 들더라도 수치심의 합이 아니다.
        // 교차로가 노드다.

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int m = input[1];
        int start = input[2];
        int end = input[3];
        int c = input[4];
        graph = new int[n+1][n+1];
        for (int i = 1; i < m+1; i++) {
            int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int a = line[0];
            int b = line[1];
            int cost = line[2];
            graph[a][b] = cost;
            graph[b][a] = cost;
        }

        System.out.println(dijkstra(n, c, start, end));
    }

    private static int dijkstra(int n, int c, int start, int end) {
        // 수치심이 작은 순서로
        int[] dis = new int[n+1];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>((Node a, Node b) -> Integer.compare(a.maxCost, b.maxCost));
        for (int nextNodeId = 1; nextNodeId <= n; nextNodeId++) {
            int cost = graph[start][nextNodeId];
            if (cost == 0 || cost > c) continue;
            pq.offer(new Node(nextNodeId, cost, cost));
            dis[nextNodeId] = cost;
        }

        while (!pq.isEmpty()) {
            Node curNode = pq.poll();
            if (dis[curNode.nodeId] < curNode.maxCost) continue;

            // 61 -> 88 까지는 해결됨.
            if (curNode.nodeId == end) return curNode.maxCost;

            for (int nextNodeId = 1; nextNodeId <= n; nextNodeId++) {
                int cost = graph[curNode.nodeId][nextNodeId];
                // 갈 수 있는 골목이 없으면 스킵
                if (cost == 0) continue;

                int newTotalCost = curNode.totalCost + cost;
                // 현재 가지고 있는 돈만으로 갈 수 없다면 스킵
                if (newTotalCost > c) continue;

                // 가중치의 최댓값이 제일 최솟값인 경우
                int comparedMinCost = Math.max(curNode.maxCost, cost);
                if (dis[nextNodeId] >= comparedMinCost) {
                    dis[nextNodeId] = comparedMinCost;
                    pq.offer(new Node(nextNodeId, newTotalCost, comparedMinCost));
                }
            }
        }

        if (dis[end] == Integer.MAX_VALUE) return -1;
        return dis[end];
    }
    // 출발할 경우 dis를 어떻게 잡냐가 문제가 됨.
}

class Node {
    int nodeId;
    int totalCost;
    int maxCost;

    public Node(int nodeId, int totalCost, int maxCost) {
        this.nodeId = nodeId;
        this.totalCost = totalCost;
        this.maxCost = maxCost;
    }
}