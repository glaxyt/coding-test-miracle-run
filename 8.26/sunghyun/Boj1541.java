import java.io.*;
import java.util.Arrays;

public class Boj1541 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] words = Arrays.stream(br.readLine().split("-")).map(String::toString).toArray(String[]::new);

        int result = 0;
        for (String num : words[0].split("\\+")) {
            result += Integer.parseInt(num);
        }

        for (String wordNeedSplit : Arrays.copyOfRange(words, 1, words.length)) {
            for (String num : wordNeedSplit.split("\\+")) {
                result -= Integer.parseInt(num);
            }
        }
        System.out.println(result);
    }
}
