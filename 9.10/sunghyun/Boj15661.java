package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// 10 시 21분
public class Boj15661 {
    static int answer = Integer.MAX_VALUE;
    static int[][] membersPowerComb;
    static boolean[] teamStartMembers;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Sij 랑 Sji는 다를 수 있다.
        // 둘다 더해야한다. 결국 쌍으로 더해야하는 구조.

        // 인원수는 같지않아도 되고, 인원은 최소 1명 이상이다. 두팀의 능력치를 최소로 해야한다. 따라서 마지막 계산에서 선택을 계산을 수행해줘야한다.
        // 그리디하게 뽑을 수 있는 방법은 없어보인다.
        int n = Integer.parseInt(br.readLine());
        teamStartMembers = new boolean[n];
        membersPowerComb = new int[n][n];
        for (int i = 0; i < n; i++) {
            int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < n; j++) {
                membersPowerComb[i][j] = line[j];
            }
        }

        selectMember(n, 0, 0);

        System.out.println(answer);

    }

    private static void selectMember(int n, int startIdx, int cnt) {

        if (cnt > 0) answer = Math.min(answer, comparePower(n));

        for (int i = startIdx; i < n; i++) {
            teamStartMembers[i] = true;
            selectMember(n, i+1, cnt+1);
            teamStartMembers[i] = false;
        }
    }

    private static int comparePower(int n) {
        int teamStartPower = 0;
        int teamLinkPower = 0;
        boolean flag;

        for (int i = 0; i < n; i++) {
            if (teamStartMembers[i]) flag = true;
            else flag = false;

            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if (flag && teamStartMembers[j]) teamStartPower += membersPowerComb[i][j];
                else if (!flag && !teamStartMembers[j]) teamLinkPower += membersPowerComb[i][j];
            }
        }

        return Math.abs(teamStartPower - teamLinkPower);
    }
}