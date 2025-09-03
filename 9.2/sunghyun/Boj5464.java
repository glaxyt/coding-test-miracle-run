package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Boj5464 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 1부터 N까지의 값이 주어지니 -1을 해야한다. 혹은 범위를 고려해야한다.
        // 주차장 자리를 번호가 작은 순서부터 채운다.
        // 주차료 차량의 무게 * 자리의 단위 무게이다.
        // 차량들은 번호로 구분된다.
        // 차량이 들어오는 순서에 따라 주차장 자리의 번호를 작은순서대로 배치할 것이다.
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int m = input[1];

        Map<Integer, Integer> perWeightFee = new HashMap<>();
        for (int idx = 1; idx <= n; idx++) {
            int weight = Integer.parseInt(br.readLine());
            perWeightFee.put(idx, weight);
        }

        Map<Integer, Integer> carWeight = new HashMap<>();
        for (int idx = 1; idx <= m; idx++) {
            int weight = Integer.parseInt(br.readLine());
            carWeight.put(idx, weight);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            pq.offer(i);
        }

        int answer = 0;
        int[] carUsedLog = new int[m+1];
        Deque<Integer> dq = new ArrayDeque<>();

        for (int i = 0; i < 2*m; i++) {
            int carIdx = Integer.parseInt(br.readLine());
            // 먼저 들어간 값의 자리와 배치를 아는가? 현재 남은 빈자리가 어디인가를 알아야한다.
            // 들어갔을때는 상관없다. 다만 나중에 나올때 빈자리가 생기냐를 우리가 알야아한다.
            // 차라리 남는 자리를 미리 넣어두면 어떨까라고 생각이남
            if (carIdx > 0) {
                // 주차할 자리가 있다면.
                if (!pq.isEmpty()) {
                    int minParkingIdx = pq.poll();
                    answer += (perWeightFee.get(minParkingIdx) * carWeight.get(carIdx));
                    carUsedLog[carIdx] = minParkingIdx;
                } else { // 주차할 자리가 없다면
                    dq.offerLast(carIdx);
                }
            } else if (carIdx < 0) {
                int absCarIdx = Math.abs(carIdx);
                int parkingIdx = carUsedLog[absCarIdx];
                pq.offer(parkingIdx);
                if (!dq.isEmpty()) { // 기다리는 자동차가 있었다면 빈자리에 바로 주차 예정.
                    int minParkingIdx = pq.poll();
                    int waitingCarIdx = dq.pollFirst();
                    carUsedLog[waitingCarIdx] = minParkingIdx;
                    answer += (perWeightFee.get(minParkingIdx) * carWeight.get(waitingCarIdx));
                }
            }
        }

        while (!dq.isEmpty()) { // 기다리는 자동차가 있었다면 빈자리에 바로 주차 예정.
            int parkingIdx = pq.poll();
            int waitingCarIdx = dq.pollFirst();
            answer += (perWeightFee.get(parkingIdx) * carWeight.get(waitingCarIdx));
        }

        System.out.println(answer);
    }
}