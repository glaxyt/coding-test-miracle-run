package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class BojTemp {
    static List<Pos> towns = new ArrayList<>();
    static List<List<Edge>> graph = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // A와 B로 건너갈 수 있는

        int[] homeToTarget = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Pos home = new Pos(homeToTarget[0], homeToTarget[1]);
        towns.add(home);
        towns.add(new Pos(homeToTarget[2], homeToTarget[3]));

        int n = Integer.parseInt(br.readLine());

        // 제일 짧은거리로의 이동. 최단거리이자 다익스트라 적용한 문제이지않을까? 근데 모든 간선이 존재한다. -> 모두 이동이 가능하다.
        for (int i = 0; i < n; i++) {
            int[] posData = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            towns.add(new Pos(posData[0], posData[1]));
        }

        for (int i = 0; i < towns.size(); i++) {
            List<Edge> edges = new ArrayList<>();
            Pos a = towns.get(i);

            for (int j = 0; j < towns.size(); j++) {
                if (i == j) continue;

                Pos b = towns.get(j);
                int curDis = calcDistance(a.x, a.y, b.x, b.y);

                if (isPrime(curDis)) {
                    edges.add(new Edge(j, curDis));
                }
            }
            graph.add(edges);
        }

        System.out.println(dijkstra());
    }

    private static int dijkstra() {
        int[] dis = new int[towns.size()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[0] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        pq.add(new Node(0, 0));

        while (!pq.isEmpty()) {

            Node curNode = pq.poll();
            int curId = curNode.id;
            int cost = curNode.cost;

            if (curId == 1) {
                break;
            }

            if (cost > dis[curId]) continue;

            for (Edge edge : graph.get(curId)) {

                int nextId = edge.id;
                int w = edge.cost;

                int newCost = w + cost;
                if (newCost < dis[nextId]) {
                    dis[nextId] = newCost;
                    pq.add(new Node(nextId, newCost));
                }
            }
        }

        if (dis[1] == Integer.MAX_VALUE) return -1;
        return dis[1];
    }

    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;

        for (int i = 2; i < Math.sqrt(n) + 1; i++) {
            if (n % i == 0) return false;
        }

        return true;
    }

    private static int calcDistance(int x1, int y1, int x2, int y2) {
        long dx = x1 - x2;
        long dy = y1 - y2;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return (int)Math.floor(dist);
    }
}

class Pos {
    int x;
    int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Node {

    int id;
    int cost;

    public Node(int id, int cost) {
        this.id = id;
        this.cost = cost;
    }
}

class Edge {
    int id;
    int cost;

    public Edge(int id, int cost) {
        this.id = id;
        this.cost = cost;
    }
}