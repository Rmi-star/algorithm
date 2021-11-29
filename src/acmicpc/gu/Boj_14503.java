package acmicpc.gu;

import java.io.*;
import java.util.StringTokenizer;

/*
 로봇청소기
 1. 현재 위치를 청소한다.
 2. 반시계 방향으로 돌면서 주변을 청소할 수 있는지 확인한다.
    - 청소할 수 있는 공간이면 1번을 수행한다. (왼쪽 위치를 파라미터로 넘겨줌)
 3. 주변이 모두 청소되어 있거나 벽인 경우
    - 바라보는 방향을 유지한 채 1번을 수행한다. (뒤쪽 위치를 파라미터로 넘겨줌)

    북 → 0 , 동 → 1, 남 → 2, 서 → 3
    왼쪽 : 0 → 3 / 1 → 0 / 2 → 1 / 3 → 2 = (d+3) % 4
    뒷쪽 : 0 → 2 / 1 → 3 / 2 → 0 / 3 → 1 = (d+2) % 4

 */
public class Boj_14503 {
    public static int N, M;
    public static int[][] map;
    public static int cnt = 0;
    public static int[] dr = {-1, 0, 1, 0}; // 북,동,남,서
    public static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken()); // 방향

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        clean(r, c, d);

        bw.write(cnt + "\n");
        br.close();
        bw.flush();
        bw.close();
    }

    public static void clean(int row, int col, int direction) {
        // 1. 현재 위치를 청소한다.
        if (map[row][col] == 0) {
            map[row][col] = 2;
            cnt++;
        }

        // 2. 왼쪽방향부터 차례대로 탐색을 진행한다.
        boolean flag = false;
        int origin = direction;
        for (int i = 0; i < 4; i++) {
            int next_d = (direction + 3) % 4;
            int next_r = row + dr[next_d];
            int next_c = col + dc[next_d];

            if (next_r > 0 && next_c > 0 && next_r < N && next_c < M) {
                if (map[next_r][next_c] == 0) {   // 아직 청소하지 않은 공간이라면
                    clean(next_r, next_c, next_d);
                    flag = true;
                    break;
                }
            }
            direction = (direction + 3) % 4;
        }

        // 네 방향 모두 청소가 되어있거나 벽인 경우
        if (!flag) {
            int next_d = (origin + 2) % 4;
            int next_br = row + dr[next_d];
            int next_bc = col + dc[next_d];

            if (next_br > 0 && next_bc > 0 && next_br < N && next_bc < M) {
                if (map[next_br][next_bc] != 1) {
                    clean(next_br, next_bc, origin); // 바라보는 방향 유지한 채 후진
                }
            }
        }
    }
}
