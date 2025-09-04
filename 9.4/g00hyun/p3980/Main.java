package p3980;

import java.util.*;
import java.io.*;

/**
 * 11명을 최대 능력치로 배치해라
 *
 * 11명의 능력치가 모두 11개를 갖고있으면 -> 시간초과
 *
 * 문제 1인당 최대 5개의 필드에 뛸수있게끔 설정
 *
 * 55 -> backtracking
 *
 * 일단 처음 그냥 해보자 -> 되겠지라는 생각 -> 해보니 되더라
 */

public class Main {
	static int c;
	static Person[] person = new Person[11];
	static boolean[] used = new boolean[11];
	static int result;
	// static int tempSum;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		c = Integer.parseInt(br.readLine());

		while(c-- > 0) {
			for(int i = 0; i<11; i++)
				person[i] = new Person(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray());

			result = 0;
			bt(0, 0);
			System.out.println(result);
		}
	}

	private static void bt(int posIdx, int sum) {
		if(posIdx == 11) {
			result = Math.max(result, sum);
			return;
		}

		for(int perIdx = 0; perIdx < 11; perIdx++) {
			int value = person[perIdx].retrieveValueOfAnyPos(posIdx);

			// O(11 * 5)
			if(value <= 0 || used[perIdx]) continue;

			used[perIdx] = true;
			bt(posIdx+1, sum + value);
			used[perIdx] = false;
		}

	}
}

class Person {
	int[] values = new int[11];

	Person(int[] v) {
		for(int i = 0; i<11; i++)
			values[i] = v[i];
	}

	int retrieveValueOfAnyPos(int pos) {
		return values[pos];
	}
}
