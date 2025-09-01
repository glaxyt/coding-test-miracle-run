import java.io.*;
import java.util.*;

public class Boj1449 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int l = input[1];

        // 자연수로만 주어짐.
        // 0.5라는 것을 제어하려면 까다롭기 때문에 자동으로 제어가 가능하게 구현이가 가능하지않을까?
        // 테이프는 자르지않고 구멍을 발견하면 무조건 붙인다.
        // 1, 2라면 l이 1이라면 모든 구멍을 막는데 2개가 필요하다.
        // 1, 2라면 l이 2라면 모든 구멍을 막는데 0.5 - 2.5 니까 1개면 충분하다.
        // 1, 3라면 l이 3이라면 모든 구멍을 막는데 0.5 - 3.5니까 1개면 충분하다.
        // 그렇다면 어떤 원리가 주어지냐면 구멍 간의 거리가 1이라면, 2여야하고
        // 3이라면 구멍을 3개나 막을 수 있다.
        int[] leakedHoles = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(leakedHoles);

        int blockedIdx = 0;
        int answer = 0;
        for (int i = 0; i < n; i++) {
            int holeIdx = leakedHoles[i];
            if (blockedIdx >= holeIdx) continue;
            blockedIdx = holeIdx + l - 1;
            answer++;
        }

        System.out.println(answer);
    }
}