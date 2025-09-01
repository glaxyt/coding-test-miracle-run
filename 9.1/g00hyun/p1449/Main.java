package p1449;

import java.util.*;

/**
 * 4 3
 * 3 5 7 8
 *
 * 1. 3에서 시작 -> 최대 땜빵길이는 5인지점까지
 * 2. 7에서 시작 -> 최대 땜빵길이는 9인 지점까지
 *
 * 최소 막대로 땜빵해라
 *
 * 투포인터 -> 단순히 끝나는 지점에서 count를 할려했는데 반례 -> 시작과 끝을 둘다 신경써야한다
 *
 * 시작하는 부분에서 땜빵할 수 있나?
 *
 *
 */

public class Main {
	static int n,l;
	static int[] leak = new int[1000];
	static int result = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		l = sc.nextInt();

		for(int i = 0; i<n; i++)
			leak[i] = sc.nextInt();

		Arrays.sort(leak, 0, n);

		int limit = leak[0] + l;
		result++;
		for(int i = 0; i<n; i++) {
			if(leak[i] < limit) continue;

			limit = leak[i] + l;
			result++;
		}

		System.out.println(result);
	}
}
