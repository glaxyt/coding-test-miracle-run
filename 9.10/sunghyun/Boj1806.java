package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// 10 시 44분
public class Boj1806 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 누적할 수 있다.
        // 값이 누적함에 따라 계산을 할 수 있음.
        // 일단 목표값을 right를 늘려서 만든다.
        // 그리고 left를 안될때까지 줄여본다. 다시 right를 늘려본다. 이과정

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int s = input[1];

        int[] seq = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int result = seq[0];
        int left = 0;
        int right = 0;
        int answer = Integer.MAX_VALUE;

        while (left < n || right < n) {
            while (result >= s && left <= right) {
                answer = Math.min(answer, right - left + 1);
                result -= seq[left++];
            }

            right++;
            if (right >= n) break;

            result += seq[right];
        }

        if (answer == Integer.MAX_VALUE) System.out.println(0);
        else System.out.println(answer);
    }
}