import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Boj23350 {
    static PriorityQueue<Container> storage;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 컨테이너를 적재한다.
        // 1에 가까울 수록 높은 우선수위이며, M에 가까울수록 낮은 우선순위를 갖는다.
        // 우선순위가 낮은 순서로 적재한다.
        // 만일 우선순위가 낮은 컨테이너가 아직 존재한다면 높은 우선순위의 컨테이너는 레일의 처음으로 보낸다.
        // 우선순위 큐는 오직 메타정보를 저장하는 용도이자 내가 찾는 컨테이너가 무엇인지를 알려주고,
        // 컨테이너 자체 구현은 큐로 구현한다
        // 낮은 우선순위의 컨테이너가 오면 무조건 적재한다.
        // 따라서 추후에 우선순위가 같지만 무게가 더 무겁다면,
        // 이 과정을, 가벼운 컨테이너가 모두 빠질 때까지 반복한다. 이 과정에서 컨테이너를 뺄 때와 적재될 때 컨테이너의 무게만큼 비용이 발생한다.
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int m = input[1];

        List<Integer> priorities = new ArrayList<>();
        Deque<Container> rail = new ArrayDeque<>();
        // 레일의 처음으로 보낼 때, 컨테이너의 무게만큼 비용이 발생한다.
        storage = new PriorityQueue<>((Container a, Container b) -> {
            int val = Integer.compare(a.priority, b.priority);
            if (val == 0) return Integer.compare(a.weight, b.weight);
            return val;
        });

        for (int i = 0; i < n; i++) {
            int[] containerData = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int priority = containerData[0];
            int weight = containerData[1];
            priorities.add(priority);
            rail.offer(new Container(priority, weight));
        }

        priorities.sort((a, b) -> Integer.compare(b, a));

        int answer = 0;
        for (int idx = 0; idx < priorities.size(); idx++) {
            int targetPriority = priorities.get(idx);

            while (true) {
                Container cont = rail.poll();
                int curContPriority = cont.priority;

                if (curContPriority == targetPriority) {
                    // 찾았지만 아직 안에 넣어도 되는지 모른다. 무게 비교 필요
                    if (!storage.isEmpty()) answer += moveLighterContainer(cont);
                    storage.offer(cont);
                    answer += cont.weight;
                    break;
                }
                answer += cont.weight;
                rail.offer(cont);
            }
        }
        System.out.println(answer);
    }

    private static int moveLighterContainer(Container container) {
        int result = 0;
        int curContainerPriority = container.priority;
        int curContainerWeight = container.weight;
        List<Container> tempStorage = new ArrayList<>();

        while (!storage.isEmpty()) {
            Container topContainer = storage.peek();
            if (topContainer.priority == curContainerPriority && topContainer.weight < curContainerWeight) {
                result += topContainer.weight;
                tempStorage.add(storage.poll());
            } else {
                break;
            }
        }

        for (Container cont : tempStorage) storage.offer(cont);

        return result * 2;
    }
}

class Container {
    int priority;
    int weight;

    public Container(int priority, int weight) {
        this.priority = priority;
        this.weight = weight;
    }
}