package acmicpc.gu;

import java.io.*;
import java.util.*;

/*
 어른 상어
 */
public class Boj_19237 {

    static int N, M, k;
    static int[][] resttime; // 각 칸마다 냄새가 없어지기까지 남은 시간
    static int[][] smell; // 각 칸에의 냄새를 뿌린 상어의 번호(냄새가 없으면 0)
    static int[][][] priority; // 상어마다 현재 방향에서의 우선순위
    static Shark[] shark;
    static int[] dr = { 0, -1, 1, 0, 0 };
    static int[] dc = { 0, 0, 0, -1, 1 };

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        k = Integer.parseInt(input[2]);

        resttime = new int[N + 1][N + 1];
        smell = new int[N + 1][N + 1];
        priority = new int[M + 1][5][4]; // ex) priority[m][1][0] : m번 상어의 현재 방향이 위쪽방향(1)일 때, 0번째 우선순위에 해당하는 방향
        shark = new Shark[M + 1]; // 1번부터 M번까지 각 상어의 위치(r,c)와 방향(d) 정보

        for (int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");
            for (int j = 1; j <= N; j++) {
                int n = Integer.parseInt(input[j - 1]);

                if (n > 0) {
                    shark[n] = new Shark(i, j, 0);
                    resttime[i][j] = k;
                    smell[i][j] = n;
                }
            }
        }
        input = br.readLine().split(" ");
        for (int i = 1; i <= M; i++)
            shark[i].d = Integer.parseInt(input[i - 1]); // 상어 방향 정보

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= 4; j++) {
                input = br.readLine().split(" ");
                for (int k = 0; k < 4; k++) {
                    priority[i][j][k] = Integer.parseInt(input[k]); // 상어 우선순위 방향
                }
            }
        }

        bw.write(solve() + "\n");
        bw.flush();

    }

    public static int solve() {

        int time = 0;

        while (true) {

            int count = 0;
            for (int m = 1; m <= M; m++) {
                if (shark[m] != null)
                    count++; // 상어 개수 세기
            }

            if (count == 1 && shark[1] != null) { // 1번 상어 혼자 남은 경우
                return time;
            }

            if (time >= 1000) // 시간 초과 나면 끝
                return -1;

            int[][] tmp = new int[N + 1][N + 1];

            for (int m = 1; m <= M; m++) {
                if (shark[m] != null) { // 상어가 경계 안에 있다면
                    moveShark(tmp, m);
                }
            }

            // 냄새 유효기간 하나씩 줄이기
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (resttime[i][j] > 0)
                        resttime[i][j]--;

                    if (resttime[i][j] == 0) // 유효시간 끝 = 0
                        smell[i][j] = 0;
                }
            }

            // 이동후의 상어 위치의 냄새 정보와 유효기간 초기화하기
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (tmp[i][j] > 0) { // 0보다 크다는 것은 상어가 (i,j)에 새롭게 이동했음을 의미
                        resttime[i][j] = k;
                        smell[i][j] = tmp[i][j];
                    }
                }
            }
            time++;
        }

    }

    public static void moveShark(int[][] tmp, int m) {

        int nr = 0;
        int nc = 0;
        int d = 0;

        boolean flag = false;

        // 1. 상하좌우 중에서 상어의 새로운 이동 위치(nr, nc)를 찾는다.
        // 1-1. 높은 우선순위부터 차례대로 탐색
        for (int i = 0; i < 4; i++) {

            d = priority[m][shark[m].d][i];
            nr = shark[m].r + dr[d];
            nc = shark[m].c + dc[d];

            // 경계를 벗어나지 않으면서, 냄새가 없는 곳을 찾으면 break로 빠져나옴
            if ((1 <= nr && nr <= N) && (1 <= nc && nc <= N) && smell[nr][nc] == 0) {
                flag = true; // 탐색 도중, 냄새가 없고 인접한 위치를 찾으면 탐색을 종료한다.
                break;
            }
        }

        // 1-2. 냄새가 없는 곳이 없는 경우
        if (!flag) {
            // 만약 모두 탐색했는데 냄새가 없는 곳이 없다면, 자기 자신의 냄새가 있는 인접한 곳을 찾는다.
            for (int i = 0; i < 4; i++) {

                d = priority[m][shark[m].d][i];
                nr = shark[m].r + dr[d];
                nc = shark[m].c + dc[d];

                if ((1 <= nr && nr <= N) && (1 <= nc && nc <= N) && smell[nr][nc] == m)
                    break;
            }
        }

        // tmp라는 임시 2차원 배열에는 현재 상어보다 이전에 움직인 상어가 이동한 결과가 들어있다.
        // 만약 현재 상어가 4번이고, 이 직전에 3번 상어가 (2, 4)로 이동을 한 상태라면 tmp[2][4]에는 3번 상어의 번호인 3이 들어있다.
        //
        if (tmp[nr][nc] == 0) { // 이전에 (nr, nc)로 이동한 상어가 없다면,
            tmp[nr][nc] = m; // 현제 상어의 위치와 방향 정보를 변경
            shark[m].r = nr;
            shark[m].c = nc;
            shark[m].d = d;
        } else { // 이전에 (nr, nc)로 이동한 상어가 존재
            // 이전 상어의 번호는 현재 상어보다 작다.
            // 즉, 현재 상어가 힘이 더 약하기 때문에 현재 상어는 문제 조건에 따라 경계 밖으로 쫓겨나게 된다.
            shark[m] = null;
        }

    }

    static class Shark {

        int r, c, d;

        Shark(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

}