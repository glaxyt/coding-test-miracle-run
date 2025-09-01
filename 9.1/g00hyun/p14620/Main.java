package p14620;

import java.util.*;

/**
 * 씨앗이 3개
 *
 * 죽는 조건
 * 1. 꽃잎이 겹치면
 * 2. 격자 밖으로 나가면
 *
 * 만개하면
 * 5칸을 할당
 * 동서남북중심
 *
 * 그냥 3중포문 돌림
 */

public class Main {
	static int n;
	static int[][] map = new int[10][10];
	static boolean[][] visited = new boolean[10][10];
	static int result = Integer.MAX_VALUE;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		n = sc.nextInt();
		for(int i = 0; i<n; i++)
			for(int j = 0; j<n; j++)
				map[i][j] = sc.nextInt();

		// O(N^2^3) => 1백만?
		for(int i1 = 1; i1<n-1; i1++)
			for(int j1 = 1; j1<n-1; j1++)
				for(int i2 = 1; i2<n-1; i2++)
					for(int j2 = 1; j2<n-1; j2++)
						for(int i3 = 1; i3<n-1; i3++)
							for(int j3 = 1; j3<n-1; j3++) {
								int sum = calculate(i1,j1,i2,j2,i3,j3);
								if(result > sum) {
									result = sum;
									// System.out.println("[Seed] => ("+i1+","+j1+"), ("+i2+","+j2+"), ("+i3+","+j3+")");
								}
							}

		System.out.println(result);
	}

	private static int calculate(int a, int b, int c, int d, int e, int f) {
		initVisited();
		int result = 0;
		int val;

		val = oneYearAgoFee(a,b);
		if(val == -1) return Integer.MAX_VALUE;
		result += val;

		val = oneYearAgoFee(c,d);
		if(val == -1) return Integer.MAX_VALUE;
		result += val;

		val = oneYearAgoFee(e,f);
		if(val == -1) return Integer.MAX_VALUE;
		result += val;

		return result;
	}

	private static int oneYearAgoFee(int x, int y) {
		if(visited[x][y]) return -1;
		if(visited[x-1][y]) return -1;
		if(visited[x][y-1]) return -1;
		if(visited[x+1][y]) return -1;
		if(visited[x][y+1]) return -1;

		visited[x][y] = visited[x-1][y] = visited[x][y-1] = visited[x+1][y] = visited[x][y+1] = true;

		return map[x][y] + map[x-1][y] + map[x][y-1] + map[x+1][y] + map[x][y+1];
	}

	private static void initVisited() {
		for(int i = 0; i<n; i++)
			for(int j = 0; j<n; j++)
				visited[i][j] = false;
	}
}
