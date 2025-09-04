package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Boj20115 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        /*
            가장 많은 양의 에너지 드링크는 항상 보존을 해야한다.
            가장 적은 양의 에너지 드링크를 붓기만하면 될듯?
            2 3 6 9 10
            2, 3 -> 4
            4, 6 -> 8
            8, 9 -> 13
            10, 13 -> 18

            이게 아니면 양끝에서부터인가?
            2, 10 -> 11
            3, 11 -> 12.5
            6, 12 -> 15.5
            9, 15 -> 20

            1, 9, 27, 39, 45, 65, 77, 80, 100, 495

         */
        int n = Integer.parseInt(br.readLine());
        double[] nums = Arrays.stream(br.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
        Arrays.sort(nums);

        double answer = nums[n - 1];
        for (int i = 0; i < n - 1; i++) {
            answer = answer + (nums[i] / 2);
        }

        System.out.println(answer);
    }
}