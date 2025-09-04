package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// 10시 34분 시작
public class Boj3980 {
    static final int PLAYER_NUM = 11;
    static boolean[] deployedPlayer;
    static int answer;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        /*
         11명은 미리 뽑았다. 이제 배치를 어떻게 하냐가 중요하다.
         11개의 자리에 배치를 하려고한다.
         각 선수의 포지션마다의 능력치를 데이터화했다.
         어떤 포지션에 0인 능력치를 가진 선수는 해당 자리에 배치될 수 없다.
         자리 배치를 마친 뒤 모든 포지션에서의 선수의 능력치의 최댓합을 구하려고한다.
         포지션 별로 애들 선수 능력치 값을 모두 집어넣는다. 대신 0은 제외
         백트래킹 실행, 크기가 크지않아서 실행 가능
        */

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            answer = 0;
            int[][] position = new int[PLAYER_NUM][PLAYER_NUM];

            deployedPlayer = new boolean[PLAYER_NUM];
            for (int i = 0; i < PLAYER_NUM; i++) {
                int[] player_info = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                for (int j = 0; j < PLAYER_NUM; j++) {
                    position[j][i] = player_info[j];
                }
            }

            dfs(position, 0, 0);
            System.out.println(answer);
        }
    }

    private static void dfs(int[][] position, int result, int curPosIdx) {
        if (curPosIdx == PLAYER_NUM) {
            answer = Math.max(answer, result);
            return;
        }

        for (int playerIdx = 0; playerIdx < PLAYER_NUM; playerIdx++) {
            int ability = position[curPosIdx][playerIdx];
            if (ability == 0) continue;
            if (deployedPlayer[playerIdx]) continue;
            deployedPlayer[playerIdx] = true;
            dfs(position, result + ability,  curPosIdx + 1);
            deployedPlayer[playerIdx] = false;
        }
    }
}
