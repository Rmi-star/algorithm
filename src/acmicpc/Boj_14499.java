package acmicpc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj_14499 {
    static int N, M; // 지도 크기
    static int[] dx = {0, 1, -1, 0, 0}; // 우좌상하 → 동서북남
    static int[] dy = {0, 0, 0, -1, 1};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken()); // 가로
        M = Integer.parseInt(st.nextToken()); // 세로
        int y = Integer.parseInt(st.nextToken()); // 주사위 좌표
        int x = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken()); // 명령의 개수

        int[][] map = new int[N][M];
        int[] command = new int[k];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine(), " ");
        for(int i = 0; i < k; i++) {
            command[i] = Integer.parseInt(st.nextToken());
        } // input end

        /**
         *   2
         * 4 1 3
         *   5
         *   6
         * 출력값 항상 1번의 위치!
         * 바닥 항상 6번 위치!
         * 굴리는 방향마다 어떤 식으로 변하는지만 파악하면 끝인 것 같다
         * 아래는 각 방향 별 인덱스 스왑의 결과
         * 동쪽 ( 4 -> 1, 1 -> 3, 3 -> 6, 6 -> 4 )
         *   2
         * 6 4 1
         *   5
         *   3
         * 서쪽 (1 -> 4, 3 -> 1, 6 -> 3, 4->6)
         *   2
         * 4 1 3
         *   5
         *   6
         * 남쪽 ( 2 -> 1, 1 -> 5, 5 -> 6, 6->2 )
         *   6
         * 4 2 3
         *   1
         *   5
         * 북쪽 ( 1 -> 2, 2 -> 6, 6 -> 5, 5 -> 1 )
         *   2
         * 4 1 3
         *   5
         *   6
         * */
        int[] dice = new int[7];
        for(int i = 0; i < command.length; i++) {
            int next_y = y + dy[command[i]];
            int next_x = x + dx[command[i]];
            if(!isRange(next_y, next_x)) {
                y = next_y;
                x = next_x;
                roll_the_dice(command[i], dice, map, next_y, next_x);
            }
        }
    }


    /** 각 명령 별로 주사위를 굴리고 출력을 하자 */
    private static void roll_the_dice(int command, int[] dice, int[][] map, int next_y, int next_x) {
        switch(command) {
            case 1: // 동쪽
                roll_east(map, dice, next_y, next_x);
                break;
            case 2: // 서쪽
                roll_west(map, dice, next_y, next_x);
                break;
            case 3: // 북쪽
                roll_north(map, dice, next_y, next_x);
                break;
            case 4: // 남쪽
                roll_south(map, dice, next_y, next_x);
                break;
        }
    }

    /** 북쪽 굴림*/
    private static void roll_north(int[][] map, int[] dice, int next_y, int next_x) {
        int[] tmpdice = new int[7];
        for(int i = 1; i < dice.length; i++) {
            tmpdice[i] = dice[i];
        }
        dice[2] = tmpdice[1];
        dice[6] = tmpdice[2];
        dice[5] = tmpdice[6];
        dice[1] = tmpdice[5];

        change_map_dice(map, dice, next_y, next_x);
    }


    /** 남쪽 굴림 */
    private static void roll_south(int[][] map, int[] dice, int next_y, int next_x) {
        int[] tmpdice = new int[7];
        for(int i = 1; i < dice.length; i++) {
            tmpdice[i] = dice[i];
        }
        dice[2] = tmpdice[6];
        dice[6] = tmpdice[5];
        dice[5] = tmpdice[1];
        dice[1] = tmpdice[2];

        change_map_dice(map, dice, next_y, next_x);
    }


    /** 서쪽 굴림 */
    private static void roll_west(int[][] map, int[] dice, int next_y, int next_x) {
        int[] tmpdice = new int[7];
        for(int i = 1; i < dice.length; i++) {
            tmpdice[i] = dice[i];
        }
        dice[4] = tmpdice[1];
        dice[3] = tmpdice[6];
        dice[6] = tmpdice[4];
        dice[1] = tmpdice[3];

        change_map_dice(map, dice, next_y, next_x);
    }


    /** 동쪽 굴림 */
    private static void roll_east(int[][] map, int[] dice, int next_y, int next_x) {
        int[] tmpdice = new int[7];
        for(int i = 1; i < dice.length; i++) {
            tmpdice[i] = dice[i];
        }
        dice[3] = tmpdice[1];
        dice[6] = tmpdice[3];
        dice[4] = tmpdice[6];
        dice[1] = tmpdice[4];

        change_map_dice(map, dice, next_y, next_x);
    }

    /** 지도와 주사위의 바닥 값 변경 */
    private static void change_map_dice(int[][] map, int[] dice, int next_y, int next_x) {
        if(map[next_y][next_x] != 0) {
            dice[6] = map[next_y][next_x];
            map[next_y][next_x] = 0;
        } else { // 지도 바닥 0인 경우
            map[next_y][next_x] = dice[6];
        }
        System.out.println(dice[1]);
    }

    /** 범위 체크 */
    private static boolean isRange(int y, int x) {
        return y >= N || y < 0 || x >= M || x < 0;
    }
}
