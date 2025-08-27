import java.io.*;
import java.util.*;
public class BJ1063 {
    private static int[] dx = {1, -1, 0, 0, 1, -1, 1, -1}, dy = {0, 0, -1, 1, 1, 1, -1, -1};
    private static Map<String, Integer> commandMap = Map.of(
            "R", 0, "L", 1, "B", 2, "T", 3,
            "RT", 4, "LT", 5, "RB", 6, "LB", 7
    );
    private static boolean inRange(int x, int y) {
        return 0 <= x && x < 8 && 0 <= y && y < 8;
    }
    private static class Point{
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public String toPos() {
            return (char) (x + 'A') + "" + (y + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        String k = st.nextToken();
        Point king = new Point(k.charAt(0) - 'A', k.charAt(1) - '1');
        String s = st.nextToken();
        Point stone = new Point(s.charAt(0) - 'A', s.charAt(1) - '1');
        int ops = Integer.parseInt(st.nextToken());

        while(ops-- > 0) {

            String command = br.readLine();
            int op = commandMap.get(command);

            int nxk = king.x + dx[op];
            int nyk = king.y + dy[op];
            int nxs = stone.x + dx[op];
            int nys = stone.y + dy[op];

            if (!inRange(nxk, nyk)) {
                continue;
            }

            if (nxk == stone.x && nyk == stone.y) {
                if (!inRange(nxs, nys)) continue;
                king.x = nxk;
                king.y = nyk;
                stone.x = nxs;
                stone.y = nys;
            } else {
                king.x = nxk;
                king.y = nyk;
            }
        }

        System.out.println(king.toPos());
        System.out.println(stone.toPos());
        br.close();

    }
}
