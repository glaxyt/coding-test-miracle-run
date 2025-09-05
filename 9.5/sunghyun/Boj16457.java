package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 10시 18분 시작.
public class Boj16457 {
    static int answer = 0;
    static boolean[] keySetting;
    static boolean[][] needSkillPerQuestList;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 리유나, 라가
        // 리유나 225, 라가 202
        // 라가가 리유나를 질투해서 키보드 자판을 n개만 남기고 다 부셔버림
        // 2n의 스킬을 n에 배치하는 것이 목표.
        // m개의 퀘스트에서 각각 k개의 깨는데 필요한 퀘스트가 주어진다.
        // 어떤 배치를 해야 가장 많은 퀘스트를 깰 수 있는가?

        // 입력값이 어떻게 주어지려나?
        // n개의 키가 주어진다. 이 뜻은 내가 가지고 있는 스킬은 2n개라는 뜻이다.
        // n의 범위는 10 이하이기 때문에 여유롭다.
        // 20개중에서 10개를 선택하는 문제다.
        // 그리고 나서 비교를 어떻게 할 것인가.?
        // 어떤 비교가 제일 쉬우려나.
        // 집합 비교가 제일먼저 생각난다. 다중 값 처리에 효율적이다. 근데 자바로는 잘 다룰 줄 모르긴한다. 그래도 해본다.
        // 순차탐색이 들어가도 되려나.. Set으로 묶어서 처리한다면 이게 추후에 제거도 가능.

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int m = input[1];
        int k = input[2];

        keySetting = new boolean[2*n + 1];
        needSkillPerQuestList = new boolean[m][2*n + 1];

        for (int i = 0; i < m; i++) {
            int[] skills = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < k; j++) {
                int skillIdx = skills[j];
                needSkillPerQuestList[i][skillIdx] = true;
            }
        }

        findBestSkillSet(n, m,0, 1);
        System.out.println(answer);
    }

    private static void findBestSkillSet(int n, int m, int cnt, int curSkillIdx) {
        if (cnt == n) {
            answer = Math.max(answer, getClearQuest(n, m));
            return;
        }

        for (int i = curSkillIdx; i < 2*n + 1; i++) {
            keySetting[curSkillIdx] = true;
            findBestSkillSet(n, m,cnt + 1, i + 1);
            keySetting[curSkillIdx] = false;
        }
    }

    private static int getClearQuest(int n, int m) {
        int result = 0;

        for (int i = 0; i < m; i++) {
            boolean flag = true;
            for (int j = 1; j < 2*n + 1; j++) {
                if (needSkillPerQuestList[i][j]) {
                    if (keySetting[j] != needSkillPerQuestList[i][j]) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) result++;
        }

        return result;
    }
}

class Node {
    int nodeId;
    int weight;
    List<Integer> path = new ArrayList<>();

    public Node(int nodeId, int weight) {
        this.nodeId = nodeId;
        this.weight = weight;
    }
}