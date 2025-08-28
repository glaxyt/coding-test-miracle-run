import java.io.*;
import java.util.*;

public class Boj10819 {
    static int[] nums;
    static boolean[] visited;
    static int answer = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        visited = new boolean[nums.length];
        dfs(n, 0, new ArrayList<Integer>());
        System.out.println(answer);
    }

    private static void dfs(int n, int cnt, List<Integer> result) {
        if (cnt == n) {
            answer = Math.max(answer, calculate(result));
        }

        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            result.add(nums[i]);
            dfs(n, cnt + 1, result);
            result.remove(result.size() - 1);
            visited[i] = false;
        }
    }

    private static int calculate(List<Integer> arr) {
        int result = 0;
        for (int i = 0; i < arr.size() - 1; i++) {
            result += Math.abs(arr.get(i) - arr.get(i + 1));
        }
        return result;
    }
}
