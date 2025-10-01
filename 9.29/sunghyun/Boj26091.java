package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// 10시 19분 시작.
public class Boj26091 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 팀원이 2명이며, 팀의 능력치가 M 이상이어야한다. 팀의 능력치는 팀원들의 능력치 합한 값이다.
        // 조건을 만족하는 팀을 최대한 많이 만들어야한다.

        // 정렬해서 곱하면 되지않겠나라고 생각.
        // 앞뒤 곱으로 구할 수도 있다.
        // 만일 M 이상이 나오지 못한다면, 앞의 포인터를 이동시킨다.
        // 3 3 5 5 6 7
        //  10을 만족해야한다면

        int answer = 0;
        int[] data = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = data[0];
        int m = data[1];

        int[] abilities = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(abilities);

        int left = 0;
        int right = n - 1;

        // 2명의 조합이 가능해야하니 =은 없다.
        while (left < right) {
            int teamPower = abilities[left] + abilities[right];

            if (teamPower >= m) {
                right--;
                answer++;
            }
            left++;

        }
        System.out.println(answer);
    }
}