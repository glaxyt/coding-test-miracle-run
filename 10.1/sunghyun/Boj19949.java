package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Boj19949 {
    static final int QUIZ_NUM = 10;
    static final int CHOICE_NUM = 5;
    static int[] answerOfQuiz;
    static int cntOfOverFive = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 3개의 연속된 문제의 답은 같지않다.
        // 5점 이상일 확률
        // 3개의 연속된 문제의 답을 적지않는 모든 경우
        // 5점 이상이 나오는 경우

        answerOfQuiz = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        dfs(0, 0, -1, 0);
        System.out.println(cntOfOverFive);
    }

    private static void dfs(int quizIdx, int cnt, int exChoice, int combo) {
        if (quizIdx == QUIZ_NUM) {
            if (cnt >= 5) cntOfOverFive++;
            return;
        }

        for (int i = 1; i < CHOICE_NUM + 1; i++) {
            if (exChoice == i && combo == 2) continue;

            if (i == answerOfQuiz[quizIdx]) {
                if (exChoice == i) dfs(quizIdx + 1, cnt + 1, i, combo+1);
                else dfs(quizIdx + 1, cnt + 1, i, 1);
            } else {
                if (exChoice == i) dfs(quizIdx + 1, cnt, i, combo+1);
                else dfs(quizIdx + 1, cnt, i, 1);
            }
        }
    }
}
