package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 11시 25분 시작.
public class Boj13164 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 키 순서대로 일렬로 세우고 나서 K개 조로 나누려고한다.
        // 조마다 티셔츠를 맞추는 비용은 조에서 가장 키가 큰 원생과 가장 키가 작은 원생의 키 차이만큼 든다.
        // K개의 조에 대해 티셔츠 만드는 비용의 합을 최소로 만들자.
        // 그리디하게 풀 수 있는가? 조의 최대 키와 최소 키의 차이가 클수록 비용은 증가한다.
        // 지금 드는 생각은 딱히 없음.
        // 원생 수는 300,000명으로 백트래킹으로 풀기에는 불가능하다.
        // o(n)으로 푸는게 제일 무난해보임.
        // 줄은 왼쪽에서 오른쪽 순서이다. (키 순서대로 입력값이 주어짐)
        // 사실상 맨 앞과 맨 끝만 궁금하다. 그 사이는 궁금하지않다.
        // 5개의 조가 나와야하고 조의 사람 수는 같지않아도 된다.
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0];
        int k = input[1];
        int[] child = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 조 1 1 1 1 나머지도 가능.
        // 이분법으로 제일 적은 애들을 계속 구하면 될 것 같지않은가?
        // 뇌가 뭉개지네 이거 무슨 아이디어가 필요할까.
        // 윈도우? 이분법?
        // 1 2 3 4 19
        // 1 2 19 51 52
        // 이 경우에는 19를 따로 분리하는게 좋다. 이걸 가능하게 하는 기준이 있는가?
        // 1 3 5 6 10
        // 이건 어떻게 계산했을까?
        // 3조로 나누어야한다.
        // idx 기준으로 나누는가?
        // 1 - 3까지가 아닌 5까지 안가는 이유가 뭘까
        // 아 각 거리를 구하는게 좋을듯?
        // 한 학생에서 다른 점까지의 거리
        // 거리를 계산해야하는 문제라면 어떻게 풀어야하는지 고민이된다.
        // 한점에서의 거리를 계산하고 1 - 2 그 거리가 곧 비용이다.
        // 그렇다면 거리를 나열한다음에, 어떻게 조 별로 나눌 것인가가 문제이다.
        // 나눌 수 있는 방법은 조 별로 분리해서 계산한다.
        List<int[]> interval = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            interval.add(new int[]{Math.abs(child[i] - child[i + 1]), i});
        }

        interval.sort((int[] a, int[] b) -> {
            int val = Integer.compare(b[0], a[0]);
            if (val == 0) {
                return Integer.compare(a[1], b[1]);
            }
            return val;
        });

        int answer = 0;
        int leftIdx = 0;
        int rightIdx = 0;
        for (int i = 0; i < k - 1; i++) {
            rightIdx = interval.get(i)[1];
            answer += child[rightIdx] - child[leftIdx];
            leftIdx = rightIdx + 1;
        }
        answer += child[n-1] - child[leftIdx];

        System.out.println(answer);
    }
}