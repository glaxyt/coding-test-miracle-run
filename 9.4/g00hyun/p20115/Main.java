package p20115;

import java.util.*;
import java.io.*;

public class Main {
	static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		Integer[] berverages = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);

		Arrays.sort(berverages, Collections.reverseOrder());

		double result = berverages[0];
		for(int i = 1; i<n; i++) {
			result += (double)berverages[i] / 2;
		}

		System.out.println(result);
	}
}