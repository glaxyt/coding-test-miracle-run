import java.io.*;
import java.util.*;

public class BJ15665 {
    private static int N, M;
    private static Set<String> set = new HashSet<>();
    private static StringBuilder output = new StringBuilder();
    private static void dfs(int depth, int[] nums, List<Integer> result) {
        if(depth == M) {
            StringBuilder temp = new StringBuilder();
            for(int r : result) {
                temp.append(r).append(" ");
            }
            if(set.contains(temp.toString())) return;
            else {
                set.add(temp.toString());
                for(int r : result) {
                    output.append(r).append(" ");
                }
            }
            output.append("\n");
            return;
        }

        for(int i = 0; i < N; i++) {
            result.add(nums[i]);
            dfs(depth + 1, nums, result);
            result.remove(result.size() - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //입력 후 정렬
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int[] nums = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(nums);

        dfs(0, nums, new ArrayList<>());

//        List<String> output = new ArrayList<>(set);
//        Collections.sort(output);

        br.close();
        System.out.println(output.toString());
    }
}
