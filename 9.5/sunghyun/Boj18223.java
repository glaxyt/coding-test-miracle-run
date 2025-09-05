package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 10시 50분 시작.
public class Boj18223 {
    static int shortestPathWeight = Integer.MAX_VALUE;
    static List<Set<Integer>> shortestPathList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 민준이가 가는 길에 건우가 있다면 도움을 받을 수 있다.
        // 출발지는 정점 1번, 도착지는 마산 v번. 인덱스가 1부터 시작한다.
        // 건우 위치도 주어진다.
        // 최단 거리를 거쳐온 경로를 저장하는 문제.

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int v = input[0];
        int e = input[1];
        int gwNode = input[2]; // 건우 노드

        int[][] graph = new int[v+1][v+1];

        for (int i = 0; i < e; i++) {
            int[] edgeData = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int a = edgeData[0];
            int b = edgeData[1];
            int w = edgeData[2];
            graph[a][b] = w;
            graph[b][a] = w;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(
                (Node a, Node b) -> (a.weight - b.weight)
        );

        Node startNode = new Node(1, 0);
        startNode.path.add(1);
        pq.offer(startNode);

        int[] dis = new int[v+1];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[1] = 0;

        while (!pq.isEmpty()) {
            Node curNode = pq.poll();

            if (curNode.weight > dis[curNode.nodeId]) continue;

            for (int nextNode = 1; nextNode < v+1; nextNode++) {
                int weight = graph[curNode.nodeId][nextNode];
                int newWeight = curNode.weight + weight;

                if (weight != 0 && newWeight <= dis[nextNode]) {
                    dis[nextNode] = newWeight;
                    Node newNode = new Node(nextNode, newWeight);

                    for (Integer node : curNode.path) newNode.path.add(node);
                    newNode.path.add(nextNode);

                    if (nextNode == v) updateShortestPath(newNode);
                    pq.offer(newNode);
                }
            }
        }

        String answer = "GOOD BYE";
        for (Set<Integer> set : shortestPathList) {
            if (set.contains(gwNode)) {
                answer = "SAVE HIM";
                break;
            }
        }

        System.out.println(answer);
    }

    private static void updateShortestPath(Node node) {
        if (node.weight > shortestPathWeight) return;

        if (node.weight < shortestPathWeight) shortestPathList.clear();

        shortestPathWeight = node.weight;
        shortestPathList.add(new HashSet<>(node.path));
    }
}

class Node {
    int nodeId;
    int weight;
    List<Integer> path = new ArrayList<>();

    public Node(int nodeId, int weight) {
        this.nodeId = nodeId;
        this.weight = weight;
    }
}