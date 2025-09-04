package p17140;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

/**
 * 100 넘으면 버려라 -> 그냥 고정해도 되겠다.
 * 길이를 len으로 못뽑음
 * 그래서 lenR, lenC - >길이를 동적으로 관리
 * -21억 ~ 21억 일때의 counting -> hashmap
 * 범위가 100개 -> 그냥 배열로 해도 상관없겠다
 *
 *
 */
public class Main {
	static int r,c,k;
	static int[][] map = new int[100][100];
	static int lenR, lenC;
	static int[] counts = new int[101];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		r = tmp[0] - 1;
		c = tmp[1] - 1;
		k = tmp[2];

		lenR = lenC = 3;
		for(int i = 0; i<3; i++) {
			tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			for(int j = 0; j<3; j++)
				map[i][j] = tmp[j];
		}

		System.out.println(Solution());
	}

	private static int Solution() {
		int time = 0;

		while(map[r][c] != k) {
			if (time++ >= 100) return -1;

			if(selectOperation())
				ROperation();
			else
				COperation();
		}

		return time;
	}

	private static boolean selectOperation() {
		return lenR >= lenC;
	}

	private static void ROperation() {
		int tempLenC = 0;
		// 2차원 배열 -> 내가 지금 연산해야하는 1차원 배열을 뽑음 -> 연산 후 2차원 배열로 넣음
		for(int i = 0; i<lenR; i++) {
			int[] arr = new int[lenC];

			for(int j = 0; j<lenC; j++) {
				arr[j] = map[i][j];
				map[i][j] = 0;
			}

			List<Pair> pairs = counting(arr, lenC);
			List<Pair> sortedPairs = pairs.stream().sorted((a,b) -> a.count == b.count ? a.number - b.number : a.count - b.count).collect(Collectors.toList());

			int limit = 0;
			for(Pair p : sortedPairs) {
				if(limit >= 50) break;
				map[i][limit++] = p.number;
				map[i][limit++] = p.count;
			}

			tempLenC = Math.max(tempLenC, limit);
		}
		lenC = tempLenC;
	}

	private static void COperation() {
		int tempLenR = 0;
		for(int j = 0; j<lenC; j++) {
			int[] arr = new int[lenR];

			for(int i = 0; i<lenR; i++) {
				arr[i] = map[i][j];
				map[i][j] = 0;
			}

			List<Pair> pairs = counting(arr, lenR);
			List<Pair> sortedPairs = pairs.stream().sorted((a,b) -> a.count == b.count ? a.number - b.number : a.count - b.count).collect(Collectors.toList());

			int limit = 0;
			for(Pair p : sortedPairs) {
				if(limit >= 50) break;
				map[limit++][j] = p.number;
				map[limit++][j] = p.count;
			}

			tempLenR = Math.max(tempLenR, limit);
		}

		lenR = tempLenR;
	}

	private static List<Pair> counting(int[] arr, int len) {
		// init count
		for(int i = 0; i<101; i++)
			counts[i] = 0;

		for(int i = 0; i<len; i++)
			counts[arr[i]]++;

		List<Pair> pairs = new ArrayList<>();

		for(int i = 1; i<101; i++)
			if(counts[i] > 0)
				pairs.add(new Pair(i, counts[i]));

		return pairs;
	}
}

class Pair {
	int number, count;

	Pair(int n, int c) {
		number = n;
		count = c;
	}
}