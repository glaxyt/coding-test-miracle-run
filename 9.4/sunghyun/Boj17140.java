package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 10시 56분 시작
public class Boj17140 {
    static final int GRID_SIZE = 100;
    static final int INITIAL_GRID_SIZE = 3;
    static int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    static int gridMaxRowSize = 3;
    static int gridMaxColSize = 3;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        /*
         배열 인덱스는 1부터 시작하기 때문에 -1을 해주거나 범위를 늘려줘야한다.
         R 연산: 배열 A의 모든 행에 대해서 정렬을 수행한다. 행의 개수 ≥ 열의 개수인 경우에 적용된다.
         C 연산: 배열 A의 모든 열에 대해서 정렬을 수행한다. 행의 개수 < 열의 개수인 경우에 적용된다.
         수의 등장 횟수가 커지는 순으로, 그러한 것이 여러가지면 수가 커지는 순으로 정렬한다.

         정렬된 결과를 다시 배열에 넣을 것이고 이때는 (수, 등장횟수) 이렇게 페어링해서 넣어야한다.

         예를 들어, [3, 1, 1]에는 3이 1번, 1가 2번 등장한다. 따라서, 정렬된 결과는 [3, 1, 1, 2]가 된다.
         다시 이 배열에는 3이 1번, 1이 2번, 2가 1번 등장한다. 다시 정렬하면 [2, 1, 3, 1, 1, 2]가 된다.

         둘다 오름차순으로 제어하면된다.

         행과 열의 증가는 어떻게 제어해야하는가?
         미리 큰 사이즈를 만들어도 좋을 것 같다. 이거 계산할 엄두가 안나네 3X3이 주어진다면 이건 100초 뒤에는 최대 몇개인가?
         3^100이다. 몇인가? 흐음흐음 모르겠음 이거 int 배열 최대 크기를 몰라서 모르겠음.
         혹은 List를 사용해서 적용하는건? 메모리 부담없는 것이 제일 효율적으로 보이긴한다.
         List를 사용하면 열과 행의 최댓값을 항상 계산해서 조건식에 넣어줘야한다. 부담없긴함.

         결과적으로는 100이 넘어가면 제거를 하기 때문에 고정 100으로 둬도 될듯.
         대신 유효한 행과 열의 길이를 제어해줘야함.

         0은 어떻게 제어해야하는가?
         0은 자연수로도, 횟수로도 등장할 수 없다. 0이라면 무조건 지운다.

         행 또는 열의 크기가 100을 넘어가는 경우에는 처음 100개를 제외한 나머지는 버린다.

        결과적으로 A[r][c]에 들어있는 값이 k가 되기 위한 최소 시간을 구하는것이 목표
        */

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int r = input[0];
        int c = input[1];
        int k = input[2];

        grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < INITIAL_GRID_SIZE; i++) {
            int[] row = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < INITIAL_GRID_SIZE; j++) {
                grid[i][j] = row[j];
            }
        }

        int answer = 0;
        while (true) {

            if (grid[r-1][c-1] == k) {
                System.out.println(answer);
                break;
            }

            if (answer >= 100) {
                System.out.println(-1);
                break;
            }

            if (gridMaxRowSize >= gridMaxColSize) {
                // 모든 행에 대한 정렬
                for (int rowIdx = 0; rowIdx < gridMaxRowSize; rowIdx++) {
                    sortGridByRow(rowIdx);
                }
                // 정렬할때 0은 무시한다.
            } else {
                for (int colIdx = 0; colIdx < gridMaxColSize; colIdx++) {
                    sortGridByCol(colIdx);
                }
                // 모든 열에 대한 정렬
                // 정렬할 때 0을 무시한다.
            }
            answer++;
            // 100 이 넘어가는 수는 모두 지운다.
            // 행, 열 별로 길이를 측정해줘야 정렬하기 편할려나?

//            System.out.println("-----------------");
//            for (int rowIdx = 0; rowIdx < gridMaxRowSize; rowIdx++) {
//                for (int colIdx = 0; colIdx < gridMaxColSize; colIdx++) {
//                    System.out.print(grid[rowIdx][colIdx] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println("-----------------");

        }
    }


    private static void sortGridByRow(int rowIdx) {
        // 개수를 계산할 수 있고, 나중에 등장횟수와 수를 모두 저장할 수 있는 객체 선정 필요
        // Map으로 계산하고 나중에 keySet로 int[]로 저장한 다음 정렬
        Map<Integer, Integer> count = new HashMap<>();

        // 여기서 최대 길이도 갱신해주자.
        for (int i = 0; i < GRID_SIZE; i++) {
            int num = grid[rowIdx][i];
            if (num == 0) continue;
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        // 우선순위큐는 정렬할 수 없음.
        List<int[]> list = new ArrayList<>();
        for (int key : count.keySet()) {
            list.add(new int[]{key, count.get(key)});
        }

        // 등장횟수, 수 순서로 둘다 오름차순 기준
        list.sort((int[] a, int[] b) -> {
            int val = a[1] - b[1];
            if (val == 0) return a[0] - b[0];
            return val;
        });

        int gridColIdx = -2;
        for (int i = 0; i < list.size(); i++) {
            gridColIdx += 2;

            if (gridColIdx == 100) {
                break;
            }
            int[] numAndCountArr = list.get(i);
            grid[rowIdx][gridColIdx] = numAndCountArr[0];
            grid[rowIdx][gridColIdx+1] = numAndCountArr[1];
        }

        for (int idx = gridColIdx + 2; idx < GRID_SIZE; idx++) {
            grid[rowIdx][idx] = 0;
        }

        gridMaxColSize = Math.max(gridMaxColSize, gridColIdx + 2);
    }

    private static void sortGridByCol(int colIdx) {
        // 개수를 계산할 수 있고, 나중에 등장횟수와 수를 모두 저장할 수 있는 객체 선정 필요
        // Map으로 계산하고 나중에 keySet로 int[]로 저장한 다음 정렬
        Map<Integer, Integer> count = new HashMap<>();

        // 여기서 최대 길이도 갱신해주자.
        for (int i = 0; i < GRID_SIZE; i++) {
            int num = grid[i][colIdx];
            if (num == 0) continue;
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        // 우선순위큐는 정렬할 수 없음.
        List<int[]> list = new ArrayList<>();
        for (int key : count.keySet()) {
            list.add(new int[]{key, count.get(key)});
        }

        // 등장횟수, 수 순서로 둘다 오름차순 기준
        list.sort((int[] a, int[] b) -> {
            int val = a[1] - b[1];
            if (val == 0) return a[0] - b[0];
            return val;
        });

        int gridRowIdx = -2;
        for (int i = 0; i < list.size(); i++) {
            gridRowIdx += 2;

            if (gridRowIdx == 100) {
                break;
            }
            int[] numAndCountArr = list.get(i);
            grid[gridRowIdx][colIdx] = numAndCountArr[0];
            grid[gridRowIdx+1][colIdx] = numAndCountArr[1];
        }

        for (int idx = gridRowIdx + 2; idx < GRID_SIZE; idx++) {
            grid[idx][colIdx] = 0;
        }

        gridMaxRowSize = Math.max(gridMaxRowSize, gridRowIdx + 2);
    }
}
