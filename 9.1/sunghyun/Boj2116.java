import java.io.*;
import java.util.*;

public class Boj2116 {
    static int[][] dices;
    static Map<Integer, Integer> reverseDiceIdx = new HashMap<>();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주사위는 평범한 주사위가 아닌 면의 값이 자유롭게 설정되어있다.
        // 주사위를 쌓아올리는 것을 상상해라.
        // 주사위들끼리 맞닿는 면은 서로 같은 수여야한다.
        // 주사위를 쌓아서 직각 기둥이 이루어지는데 이 직각기둥의 면의 총합의 최대를 계산해야한다.
        // 각 주사위의 옆면들 중의 최댓값을 계산해서 더하면 되겠구나라고 생각
        // 주사위를 쌓는 것부터해서 결국에 원하는 것은 옆면의 최댓값을 구하는 것이다.
        // 결국 옆면이 최댓값에 가깝게 나오도록하는 방법으로 주사위를 맞닿게 해야한다.
        // ABCDEF의 규칙성을 파악해야겠다는 생각이 들었음.
        // A의 반대는 F
        // A는 0, F는 5
        // 둘의 간격은 2이다.
        // 거꾸로는 5이다.
        // B의 반대는 D
        // B는 1, D는 3
        // C의 반대는 E
        // 그리디한 방법은 없는가? 그리디한 방법이 생각이 안난다.
        // 그럼 백트래킹으로 해도 되는가? 주사위 개수는 10,000개 이다. 터진다.
        // 옆면을 최대한 6이 나올 수 있게만 한다면?
        // 1번의 배치에 따라 모든 값은 확정적이다. 변할 수 없다.
        // 따라서 1번의 배치를 4번만 진행하면 최댓값을 뽑을 수 있을 것 같다.
        // 그럼 어떻게 값을 계산할 것 인가.
        // 우선 아래 깔릴 주사위 면을 고르면 반대 면을 알아야한다. 이건 아까 얘기한 %로 계산하면 될 것 같고,
        // 옆면 계산은 어떻게 하는가? 그냥 반대면이랑 아랫면을 제외한 가장 큰값을 미리 더하면서 계산하면 될듯

        int n = Integer.parseInt(br.readLine());
        dices = new int[n][6];

        reverseDiceIdx.put(0, 5);
        reverseDiceIdx.put(1, 3);
        reverseDiceIdx.put(2, 4);
        reverseDiceIdx.put(3, 1);
        reverseDiceIdx.put(4, 2);
        reverseDiceIdx.put(5, 0);

        for (int i = 0; i < n; i++) {
            int[] diceInfo = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < diceInfo.length; j++) {
                dices[i][j] = diceInfo[j];
            }
        }

        int answer = 0;
        // 아래 깔릴면을 고른다.
        for (int i = 0; i < 6; i++) {
            int lowerLayer = dices[0][i];
            int upperLayer = dices[0][reverseDiceIdx.get(i)];
            int maxSideLayer = 0;
            for (int k = 0; k < 6; k++) {
                if (upperLayer == dices[0][k] || dices[0][k] == lowerLayer) continue;
                maxSideLayer = Math.max(maxSideLayer, dices[0][k]);
            }
            answer = Math.max(answer, maxSideLayer + stackDice(n, 1, upperLayer));
        }

        System.out.println(answer);
    }

    private static int stackDice(int n, int idx, int kissingLayer) {
        if (idx == n) {
            return 0;
        }

        int[] curDice = dices[idx];
        int maxSideLayer = 0;
        int upperLayer = 0;
        for (int j = 0; j < 6; j++) {
            if (curDice[j] == kissingLayer) {
                upperLayer = curDice[reverseDiceIdx.get(j)];
                break;
            }
        }

        for (int k = 0; k < 6; k++) {
            if (upperLayer == curDice[k] || curDice[k] == kissingLayer) continue;
            maxSideLayer = Math.max(maxSideLayer, curDice[k]);
        }

        return maxSideLayer + stackDice(n, idx+1, upperLayer);
    }
}