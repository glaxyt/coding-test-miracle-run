package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Boj16987 {
    static int answer = 0;
    static int[] durability;
    static int[] weight;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 계란 비교 문제
        // 계란끼리 부딪치면, 계란의 '내구도'는 서로의 계란의 '무게'만큼 깎인다.
        // 왼쪽부터 차례로 들어서 한 번씩만 다른 계란을 쳐 최대한 많은 계란을 깨는 문제
        // 인범이가 찾은답이 맞는지 검증해주면된다.
        // 이미 계란은 정렬이 되어있다.

        // 손에 들고 있는 계란으로 깨지지 않은 다른 계란 중에서 하나를 친다. <- 이게 깨지지않은 계란을 고르는 것이다.
        // 따라서 그리디하게 선택할 수 없다.

        int n = Integer.parseInt(br.readLine());
        durability = new int[n];
        weight = new int[n];

        for (int i = 0; i < n; i++) {
            int[] eggData = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int dur = eggData[0];
            int w = eggData[1];
            durability[i] = dur;
            weight[i] = w;
        }

        dfs(n,0);

        System.out.println(answer);
    }

    private static void dfs(int n, int l) {
        if (n == l) {
            int result = findBrokenEgg(n);
            answer = Math.max(answer, result);
            return;
        }

        // 왼쪽이 깨졌을 때도 패스가 필요하다.
        if (durability[l] <= 0) {
            dfs(n, l + 1);
            return;
        }

        int leftWeight = weight[l];

        boolean flag = false;
        for (int r = 0; r < n; r++) {
            if (r == l) {
                if (r == n - 1) dfs(n, l+1);
                continue;
            }
            if (durability[r] <= 0) continue;

            int rightWeight = weight[r];

            durability[l] -= rightWeight;
            durability[r] -= leftWeight;

            // 깰 수 있다.
            flag = true;

            dfs(n, l+1);

            durability[l] += rightWeight;
            durability[r] += leftWeight;
        }

        if (!flag) {
            dfs(n, l+1);
        }
    }

    private static int findBrokenEgg(int n) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (durability[i] <= 0) result++;
        }

        return result;
    }
}