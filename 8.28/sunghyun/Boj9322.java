import java.io.*;
import java.util.*;

public class Boj9322 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer, Integer> map = new HashMap<>();
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            String[] publicKeyOne = Arrays.stream(br.readLine().split(" ")).toArray(String[]::new);
            String[] publicKeyTwo = Arrays.stream(br.readLine().split(" ")).toArray(String[]::new);
            String[] originalPassword = Arrays.stream(br.readLine().split(" ")).toArray(String[]::new);

            for (int i = 0; i < n; i++) {
                String word = publicKeyOne[i];
                for (int j = 0; j < n; j++) {
                    if (word.equals(publicKeyTwo[j])) {
                        map.put(j, i);
                    }
                }
            }

            String[] encryptedPassword = new String[n];
            for (int i = 0; i < n; i++) {
                int relocateIdx = map.get(i);
                encryptedPassword[relocateIdx] = originalPassword[i];
            }

            for (int i = 0; i < n; i++) {
                System.out.print(encryptedPassword[i] + " ");
            }
        }
    }
}
