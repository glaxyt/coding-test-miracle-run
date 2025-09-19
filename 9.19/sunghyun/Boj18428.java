import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static boolean answer = false;
    static boolean[] used;
    static char[][] grid;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 장애물로 막히기 전까지 상화좌우 방향으로 '거리' 상관없이 볼 수 있다.
        // 선생 T, 학생 S, 장애물 O
        // 좌표는 1부터 시작한다.
        // 목표는 장애물 3개 설치하여, 모든 학생이 안보여야한다.
        // 그리디는 힘들고 백트래킹으로 간다.

        // 최대 6 * 6 격자이며, 36(선생1, 학생1, 빈칸 34) 구성이더라도 34C3이기 때문에 문제없다.
        int n = Integer.parseInt(br.readLine());
        grid = new char[n][n];
        List<Pos> teachers = new ArrayList<>();
        List<Pos> blanks = new ArrayList<>();

        for (int x = 0; x < n; x++) {
            String line = br.readLine().replace(" ", "");
            for (int y = 0; y < n; y++) {
                char cur = line.charAt(y);
                if (cur == 'T') {
                    teachers.add(new Pos(x, y));
                } else if (cur == 'X') {
                    blanks.add(new Pos(x, y));
                }
                grid[x][y] = cur;
            }
        }
        used = new boolean[blanks.size()];
        dfs(n, 0, 0, blanks, teachers);
        if (answer) System.out.println("YES");
        else System.out.println("NO");
    }

    public static void dfs(int n, int cnt, int startIdx, List<Pos> blanks, List<Pos> teachers) {
        if (cnt == 3) {
            if (isAllStudentSafe(n, teachers)) answer = true;
            return;
        }

        for (int idx = startIdx; idx < blanks.size(); idx++) {
            Pos blank = blanks.get(idx);
            grid[blank.x][blank.y] = 'O';
            dfs(n, cnt + 1, idx + 1, blanks, teachers);
            grid[blank.x][blank.y] = 'X';
        }
    }

    private static boolean isAllStudentSafe(int n, List<Pos> teachers) {
        for (int idx = 0; idx < teachers.size(); idx++) {
            Pos curTeacherPos = teachers.get(idx);
            for (int x = curTeacherPos.x; x < n; x++) {
                char cur = grid[x][curTeacherPos.y];
                if (cur == 'S') return false;
                if (cur == 'O') break;
            }
            for (int x = curTeacherPos.x; x > -1; x--) {
                char cur = grid[x][curTeacherPos.y];
                if (cur == 'S') return false;
                if (cur == 'O') break;
            }
            for (int y = curTeacherPos.y; y < n; y++) {
                char cur = grid[curTeacherPos.x][y];
                if (cur == 'S') return false;
                if (cur == 'O') break;
            }
            for (int y = curTeacherPos.y; y > -1; y--) {
                char cur = grid[curTeacherPos.x][y];
                if (cur == 'S') return false;
                if (cur == 'O') break;
            }
        }
        return true;
    }
}

class Pos {
    int x;
    int y;

    public Pos(int x, int y) {
        this.x= x;
        this.y = y;
    }
}