package SW_Expert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
    5653번 줄기세포배양
    '배양 용기의 크기는 무한하다' 가 포인트
    → K 시간 동안 최대 얼마나 번져 나갈 수 있을지 계산
    → 세포의 생명력 수치가 1이라고 치면 1번 증식하는데 2시간이 걸림
    → 생명력 수치가 1인 세포가 가장 빨리 증식하게 되므로, K 시간 동안 최대 K/2만큼 증식
    → 따라서 배열의 크기를 여유롭게 N+K+2, M+K+2만큼 잡음

    참고 : https://leveloper.tistory.com/77
 */
public class sw1_줄기세포 {
    private static int n, m, k, nx, ny;
    private static int[][] map;
    private static boolean[][] visit;
    private static Queue<Cell> queue = new LinkedList<>();
    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};
    private static final short DEATH = 0, ACTIVE = 1, INACTIVE = 2;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            map = new int[n + k + 2][m + k + 2];
            visit = new boolean[n + k + 2][m + k + 2];
            queue.clear();

            int temp;
            for (int i = k / 2 + 1; i < n + k / 2 + 1; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = k / 2 + 1; j < m + k / 2 + 1; j++) {
                    temp = Integer.parseInt(st.nextToken());

                    if (temp != 0) {
                        map[i][j] = temp;
                        visit[i][j] = true;
                        queue.add(new Cell(i, j, temp));
                    }
                }
            }

            int answer = solution();
            System.out.println("#" + t + " " + answer);
        }
    }

    private static int solution() {
        int count = k;
        Cell cell;

        while (count-- > 0) {
            int len = queue.size();

            for (Cell c : queue) {
                if (c.status == ACTIVE) check(c); // 주변에 세포 value 정해줌
            } for (int t = 0; t < len; t++) {
                cell = queue.poll();

                if (cell.status == ACTIVE) { // 활성화 상태인 경우만 번식
                    for (int i = 0; i < 4; i++) { // 상하좌우
                        nx = cell.x + dx[i];
                        ny = cell.y + dy[i];

                        if (visit[nx][ny]) continue;

                        queue.add(new Cell(nx, ny, map[nx][ny])); // 번식된 세포 추가
                        visit[nx][ny] = true; // 방문 처리
                        }
                }

                cell.next(); // 세포 상태 변화

                if (cell.status == DEATH) continue; // 죽은 세포는 queue에서 제외

                queue.add(cell);
            }
        } return queue.size();
    }

    private static void check(Cell cell) {
        for (int i = 0; i < 4; i++) {
            nx = cell.x + dx[i];
            ny = cell.y + dy[i];

            if (visit[nx][ny]) continue;

            if (map[nx][ny] < cell.value)
                map[nx][ny] = cell.value;
        }
    }

    static class Cell {
        int x, y;
        int value, temp;
        short status;

        public Cell(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.temp = value;
            this.status = INACTIVE;
        }

        public void next() {
            switch (status) {
                case INACTIVE: // 비활성화 상태
                    if (--temp == 0) status = ACTIVE;
                    break;
                case ACTIVE: // 활성화 상태
                    if (++temp == value)
                        status = DEATH; break;
            }
        }
    }
}
