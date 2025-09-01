package p2116;

import java.util.*;

/**
 * 40분?에 시작
 *
 * 그냥 일단 멘붕이였음
 *
 * 규칙 찾을려했고
 *
 * 첫번째는 마음대로 놓을수 있다 => 6가지가 나오겠구나
 *
 * 첫번째 -> 2 -> 3 -> ... -> n까지 결정됌
 * 첫번째의 바닥 -> 첫번째의 top 결정 -> 두번째의 bottom -> 두번째의 top -> 세번째의 bottom -> ... -> n번째의 top
 *
 * top, bottom만 결정 -> 옆 사이드는 마음대로 돌릴수있음 -> top,bottom을 제외한 사이드의 최댓값
 *
 * 한 옆면의 숫자의 합의 최댓값
 *
 *
 */

public class Main {
	static int n;
	static Dice[] dices = new Dice[10000];

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		for(int i = 0; i<n; i++)
			dices[i] = new Dice(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());

		int result = 0;
		// bottom => A ~ F
		for(int bottomD1 = 1; bottomD1<=6; bottomD1++) {
			int sum = 0;
			int curBottom = bottomD1;
			for(int d = 0; d<n; d++) {
				sum += dices[d].maxSideByBottom(curBottom);
				curBottom = dices[d].convert(curBottom);
			}
			result = Math.max(result, sum);
		}

		System.out.println(result);
	}
}

class Dice {
	int A,B,C,D,E,F;

	Dice(int a, int b, int c, int d, int e, int f) {
		this.A = a;
		this.B = b;
		this.C = c;
		this.D = d;
		this.E = e;
		this.F = f;
	}

	int maxSideByBottom(int bottom) {
		if (this.A == bottom || this.F == bottom)
			return Math.max(Math.max(Math.max(this.B, this.C), this.E), this.D);
		else if (this.B == bottom || this.D == bottom)
			return Math.max(Math.max(Math.max(this.A, this.C), this.E), this.F);
		else
			return Math.max(Math.max(Math.max(this.A, this.B), this.D), this.F);
	}

	// bottomToTop
	int convert(int val) {
		if (this.A == val) return this.F;
		else if (this.B == val) return this.D;
		else if (this.C == val) return this.E;
		else if (this.D == val) return this.B;
		else if (this.E == val) return this.C;
		else return this.A;
	}
}
