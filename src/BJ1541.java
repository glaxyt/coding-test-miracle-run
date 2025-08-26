import java.io.*;
import java.util.*;
public class BJ1541 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = br.readLine().split("-");
        List<Integer> result = new ArrayList<>();
        for(String str : nums) {
            if(str.contains("+")) {
                String[] temp = str.split("\\+");
                int sum = 0;
                for(String t : temp) {
                    sum += Integer.parseInt(t);
                }
                result.add(sum);
            } else {
                result.add(Integer.parseInt(str));
            }
        }

        int output = result.get(0);
        for(int i = 1; i < result.size(); i++) {
            output -= result.get(i);
        }

        br.close();
        System.out.println(output);
    }
}
