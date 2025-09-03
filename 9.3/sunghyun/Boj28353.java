package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// 10시 9분 시작.
public class Boj28353 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 고양이 카페에는 N마리의 고양이가 있다.
        // 고양이 무게는 w 이다.
        // 두 고양이의 무게가 k 가 넘으면 안됨.
        // 2, 4, 8, 11, 16
        // 2, 4, 8, 11까지 가능 굳이 양끝을 계산할 필요가 있는가?
        // 1, 2, 3, 4, 5, 19라면
        // 양끝에서부터 선택하는게 제일 좋다.
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int limitWeight = input[1];
        int[] catWeights =  Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(catWeights);

        int left = 0;
        int right = n-1;
        int answer = 0;
        while (left < right) {
            int leftW = catWeights[left];
            int rightW = catWeights[right];

            if (leftW + rightW > limitWeight) right--;
            else {
                left++;
                right--;
                answer++;
            }
        }

        System.out.println(answer);
    }
}