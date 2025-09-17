import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 8시 10분 시작.
public class Boj24435 {
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // BOB은 최소의 수 a를 골라내야하며,
        // ALICE는 a보다 작은 수중에서 최대의 수를 골라야한다.
        // ALICE는 백트래킹 사용.
        // BOB은 단순 비교.

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            String bob = br.readLine();
            String alice = br.readLine();
            visited = new boolean[n];
            int minBobNum = chooseBobNum(bob);
            int aliceNum = dfs(minBobNum, alice, 0, 0);
            System.out.println(aliceNum);
        }
    }

    private static int chooseBobNum(String nums) {
        int leftNum = 0;
        for (int i = 0; i < nums.length(); i++) {
            leftNum += (nums.charAt(i) - '0') * Math.pow(10, (i));
        }

        int rightNum = 0;
        for (int i = nums.length() - 1; i > -1; i--) {
            rightNum += (nums.charAt(i) - '0') * Math.pow(10, ((nums.length()-1)-i));
        }
        return Math.min(leftNum, rightNum);
    }

    private static int dfs(int bobNum, String aliceNums, int cur, int cnt) {
        int result = Integer.MIN_VALUE;

        if (bobNum > cur) result = Math.max(result, cur);

        for (int i = 0; i < aliceNums.length(); i++) {
            if (visited[i]) continue;

            visited[i] = true;
            result = Math.max(result, dfs(bobNum, aliceNums,  cur + (aliceNums.charAt(i) - '0') * (int) Math.pow(10, cnt), cnt + 1));
            visited[i] = false;
        }

        return result;
    }
}