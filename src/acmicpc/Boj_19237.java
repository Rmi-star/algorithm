package acmicpc;

import java.io.*;
import java.util.*;

/*
 어른 상어

 3차원 배열을 이용해서, map[y][x][0]에는 상어 정보, map[y][x][1]에는 남은 냄새 시간을 저장한다.
 상어가 M마리 있으므로 Shark 클래스를 만들고 Shark 클래스에는 위치정보와 방향정보, 상어마다 가지고있는 우선순위 정보를 저장한다.
 지도 입력을 받을 때, 각 상어의 위치를 저장하고, 해당 위치에는 상어의 냄새를 지정한다. 이 정보를 가지고 반복 구현을 한다.

 Step 01. 우선 모든 상어의 위치를 이동시킨다. 상어가 현재 바라보고있는 방향과, 상어가 가지고 있는 우선순위에 따라 적절히 갈 곳을 탐색해야한다.
          빈 곳이 없어서 어쩔수 없이 자신의 냄새나는 곳으로 가야 할 때를 잘 처리해야한다.
 Step 02. 지도상 모든 냄새를 -1 해준다.
 Step 03. Step 01에서 모든 상어가 이동을 완료했으므로, 자신보다 작은 상어와 겹치는 경우가 있는지 탐색한다. 만약 겹친다면 해당 상어는 앞으로의 동작에서 제외된다.
 Step 04. 현재 살아있는 모든 상어의 위치에 냄새를 남긴다.
 Step 05. Step 01 부터 Step 04까지의 동작을 상어가 한마리만 남을 때까지 반복한다. 한마리만 남았을때 수행한 시간이 정답이다!
 Step 05-1. 이 행동이 1000번이 넘어가면 -1을 출력한다.

 참고 : https://so-cute-danu-dev.tistory.com/92
 */
public class Boj_19237 {
    static class Shark { // 상어 정보
        int y, x, dir;
        int[][] pri_move = new int[5][5]; // 우선순위 저장

        public Shark(int y, int x) {
            super();
            this.y = y;
            this.x = x;
        }
    }

    private static int TIME;
    private static int[] dy = { 0, -1, 1, 0, 0 };
    private static int[] dx = { 0, 0, 0, -1, 1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stt = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(stt.nextToken()); // 지도 크기
        int M = Integer.parseInt(stt.nextToken()); // 상어 수
        int K = Integer.parseInt(stt.nextToken()); // 냄새 유지 이동

        int[][][] map = new int[N][N][2];
        Shark[] shark_arr = new Shark[M + 1];

        for (int i = 0; i < N; i++) {
            stt = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int input = Integer.parseInt(stt.nextToken());
                if (input != 0) {
                    shark_arr[input] = new Shark(i, j);
                    map[i][j][0] = input;
                    map[i][j][1] = K;
                }
            }
        } // 지도 정보 저장

        stt = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            shark_arr[i].dir = Integer.parseInt(stt.nextToken());
        } // 상어 초기 방향 지정

        for (int m = 1; m <= M; m++) {
            for (int i = 1; i <= 4; i++) {
                stt = new StringTokenizer(br.readLine());
                for (int j = 1; j <= 4; j++) {
                    shark_arr[m].pri_move[i][j] = Integer.parseInt(stt.nextToken());
                }
            }
        } // 전체 상어들의 이동 우선순위 지정( 1:↑ 2:↓ 3:← 4:→ )

        process(shark_arr, map, N, M, K);
        System.out.println(TIME);
    }

    // 상어들을 이동시키자!
    private static void process(Shark[] shark_arr, int[][][] map, int n, int m, int k) {
        int time = 0;
        boolean[] out = new boolean[m + 1]; // 밖으로 나간 상어 체크
        int out_count = 0;

        while (time <= 1000) { // 1000초가 지나면 탈출불가 -1 리턴
            time++;

            // step 01. 지정된 방향에 맞게 상어를 이동시킨다.
            // 모든 상어 이동
            for (int i = 1; i <= m; i++) {
                Shark cur_shark = shark_arr[i];
                int cur_x = cur_shark.x;
                int cur_y = cur_shark.y;
                int cur_dir = cur_shark.dir;
                int[] move_pri = cur_shark.pri_move[cur_dir];
                boolean flag = false;
                Shark tmp_shark = new Shark(0, 0);

                for (int d = 1; d <= 4; d++) {
                    int next_dir = move_pri[d];

                    int next_y = cur_y + dy[next_dir];
                    int next_x = cur_x + dx[next_dir];

                    // 범위 체크
                    if (rangeCheck(next_y, next_x, n))
                        continue;
                    // 냄새가 나..
                    if (map[next_y][next_x][1] != 0) {
                        // 근데 내 냄새는 아니야..! 못가!
                        if (map[next_y][next_x][0] != i) {
                            continue;
                        } else if (!flag) { // 내 냄새가 나서 갈 수 는 있어.. 우선순위가 제일 처음이야
                            flag = true;
                            tmp_shark.y = next_y;
                            tmp_shark.x = next_x;
                            tmp_shark.dir = next_dir;
                            continue; // 빈 공간 계속 탐색
                        }
                        continue;
                    }
                    // 갈 수 있다!
                    flag = false;
                    cur_shark.x = next_x;
                    cur_shark.y = next_y;
                    cur_shark.dir = next_dir;
                    break;
                } // 4방향 탐색 end
                if (flag) { // 갈곳이 없었다.. 내 냄새나는데로 가자
                    cur_shark.x = tmp_shark.x;
                    cur_shark.y = tmp_shark.y;
                    cur_shark.dir = tmp_shark.dir;
                }
            }

            // Step 02. 이동하기 때문에 지도에 모든 냄새는 -1이 된다.
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j][1] > 0) {
                        map[i][j][1]--;
                    }
                    // 냄새가 사라지면 상어 정보도 지워주자
                    if (map[i][j][1] == 0) {
                        map[i][j][0] = 0;
                    }
                }
            }

            // step 03.
            // 겹치는 상어 체크
            for (int i = m; i > 1; i--) {
                if (out[i])
                    continue;
                Shark mini_shark = shark_arr[i];
                for (int j = 1; j < i; j++) {
                    if (out[j])
                        continue;
                    Shark big_shark = shark_arr[j];
                    if (mini_shark.y == big_shark.y && mini_shark.x == big_shark.x) {
                        out_count++;
                        out[i] = true;
                        break;
                    }
                }
            }

            // step 04. 격자 안 모든 상어가 자신의 자리에 향기를 남긴다
            for (int i = 1; i <= m; i++) {
                if (out[i])
                    continue;
                Shark cur_shark = shark_arr[i];
                map[cur_shark.y][cur_shark.x][0] = i;
                map[cur_shark.y][cur_shark.x][1] = k;
            }

            // Step 05. 1마리 남을때까지 반복
            if (out_count == m-1) {
                TIME = time;
                return;
            }
        } // while end
        TIME = -1;
    }

    private static boolean rangeCheck(int next_y, int next_x, int n) {
        return next_y < 0 || next_y >= n || next_x < 0 || next_x >= n;
    }
}