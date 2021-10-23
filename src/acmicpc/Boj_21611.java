package acmicpc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 마법사 상어와 블리자드
 ①  d 방향으로 거리가 s 이하인 모든 칸의 구슬 파괴, 파괴된 칸은 빈 칸이 된다. (블리자드 마법)
 ② 구슬이 파괴된 후에는 빈 칸이 생겨 다음 인덱스의 구슬이 앞으로 당겨진다.
 ③ 같은 숫자로 4개 이상 연속되는 구슬들을 폭파시켜 없앤다. 과정 ②, ③을 폭파되는 구슬이 없어질 때까지 반복
 ④ 더 이상 폭발할 구슬이 없으면 구슬이 변화한다.
    - {1, 1} 은 1번 구슬이 2개 있다. → {2, 1}
    - {2} 은 2번 구슬이 1개 있다. → {1, 1}
    - {3} 은 3번 구슬이 1개 있다. → {1, 3}
    - 만약 {3, 3, 3}이 있다면 3번 구슬이 3개 있는 것이므로 {3, 3} 이 된다.
    - 주어진 구슬의 칸 전체 개수보다 구슬이 많아질 수 없다.
 ▶ 과정 ① ~ ④ 까지를 M번 실행한 후,
    → 1 × (폭발한 1번 구슬의 개수) + 2 × (폭발한 2번 구슬의 개수) + 3 × (폭발한 3번 구슬의 개수) 계산
 */
import java.io.*;
import java.util.*;

public class Boj_21611 {
    static int[] dx = { 0, -1, 1, 0, 0 }; // 칸 이동
    static int[] dy = { 0, 0, 0, -1, 1 };
    static int[] mx = { 0, 1, 0, -1 }; // 빈칸에 구슬 채울 때
    static int[] my = { -1, 0, 1, 0 };
    static int N, M; // 칸의 크기 , 마법 횟수
    static int[][] map, magicInfos; // 격자, 마법 정보
    static int[] sharkLoc = new int[2]; // 상어의 위치 (중앙)
    static int[] bombedMarbles = new int[4]; // 폭발한 1,2,3 번 구슬 개수 카운트

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        sharkLoc[0] = (N) / 2;
        sharkLoc[1] = (N) / 2;

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        map[sharkLoc[0]][sharkLoc[1]] = -2; // 상어 위치는 빈칸과 헷갈리지 않게 -2 로 바꾼다.

        magicInfos = new int[M][2];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            magicInfos[i][0] = Integer.parseInt(st.nextToken()); // 마법 방향
            magicInfos[i][1] = Integer.parseInt(st.nextToken()); // 마법 거리
        }

        for (int m = 0; m < M; m++) { // 마법 횟수
            // 1. 마법
            magic(m);

            // 2. 구슬 이동
            moveMarbles();

            // 3-1. 구슬 폭발, 3-2. 이동 반복
            while(bomb()) { // 폭발한 구슬이 있다면
                moveMarbles(); // 이동
            }

            // 4. 구슬 변하기
            changeBomb();
        }
        System.out.println(1 * bombedMarbles[1] + 2 * bombedMarbles[2] + 3 * bombedMarbles[3]);
    }

    // 1. 마법
    private static void magic(int m) {
        int nx = sharkLoc[0], ny = sharkLoc[1];
        for (int i = 0; i < magicInfos[m][1]; i++) {
            nx += dx[magicInfos[m][0]];
            ny += dy[magicInfos[m][0]];
            if (!isRange(nx, ny))
                break;
            map[nx][ny] = -1; // 구슬 파괴!
        }
    }

    // 2, 3-1. 구슬 이동
    private static void moveMarbles() {
        int n = 1, d = 0; // 이동 칸 수, 이동 방향
        int nx = sharkLoc[0], ny = sharkLoc[1]; // 현재 구슬 좌표
        int px = sharkLoc[0], py = sharkLoc[1]; // 이전 구슬 좌표
        boolean out = false, flag = false; // out: 범위를 벗어나면 끝내기 위한 플래그, flag: 파괴된 구슬을 발견하면 true
        while (true) {

            // 벽을 따라 이동하기 위해 n칸 이동 시마다 방향을 바꾸고, n칸을 2번 이동했을 땐 칸 수(n)를 1 UP 해야 한다.
            for (int i = 0; i < 2; i++) { // 2번 이동
                for (int j = 0; j < n; j++) { // n칸 이동
                    nx += mx[d % 4]; // d 방향으로 이동
                    ny += my[d % 4];
                    if (nx < 0 || ny < 0) { // (0,0)까지 이동 했다면 (한바퀴 돌았다면)
                        out = true; // 그만 이동!
                        break;
                    }
                    if (flag) { // 파괴된 구슬 이후의 모든 칸을 이동시켜야한다.
                        map[px][py] = map[nx][ny]; // 이전 구슬의 번호는 지금 구슬의 번호가 된다.
                    }
                    if (map[nx][ny] == -1) { // 파괴된 구슬 발견
                        flag = true;
                    }
                    px = nx; py = ny; // 지금 구슬은 이전 구슬이 된다.
                }
                if (out)
                    break;
                d++; // 방향 바꾸기
            }
            n++; // 이동 칸 수 증가
            if (out) { // 한바퀴 돌았을 때
                if (getEmptyCnt() == 0) { // 파괴된 구슬이 더 이상 없다면
                    break;
                } else { // 파괴된 구슬이 아직 남았다면
                    // 초기화
                    n = 1;
                    d = 0;
                    nx = sharkLoc[0];
                    ny = sharkLoc[1];
                    flag = false;
                    out = false;
                }
            }
        }
    }

    // 파괴된 구슬 개수 반환
    private static int getEmptyCnt() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == -1)
                    cnt++;
            }
        }
        return cnt;
    }

    // 3-1. 구슬 폭발
    private static boolean bomb() {
        Deque<int[]> bombMarbleList = new ArrayDeque<>(); // 같은 구슬 번호가 4개 이상 근접해있다면 폭발시키기 위해 만든 Deque
        int n = 1, d = 0; // 이동 칸 수, 이동 방향
        int nx = sharkLoc[0], ny = sharkLoc[1]; // 현재 구슬 좌표
        int px = sharkLoc[0], py = sharkLoc[1]; // 이전 구슬 좌표
        boolean out = false; // out: 범위를 벗어나면 끝내기 위한 플래그
        boolean isBombed = false; // 4개 이상이어서 폭발되면 true
        while (true) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < n; j++) {
                    nx += mx[d % 4];
                    ny += my[d % 4];
                    if (nx < 0 || ny < 0) {
                        out = true;
                        break;
                    }
                    if(map[nx][ny] != map[px][py]) { // 구슬 번호가 달라지면
                        // deque에 저장된 구슬(같은 번호를 가지고 있음) 수를 세서 4개 이상이면 -1로 바꾼다.
                        if(bombMarbleList.size() >= 4) {
                            while(!bombMarbleList.isEmpty()) {
                                int[] pos = bombMarbleList.poll();
                                bombedMarbles[map[pos[0]][pos[1]]]++; // 폭발한 구슬의 수 증가
                                map[pos[0]][pos[1]] = -1;
                                isBombed = true;
                            }
                        } else { // 4개 이상이 아니면 그냥 비운다.
                            while(!bombMarbleList.isEmpty()) {
                                bombMarbleList.poll();
                            }

                        }
                    }
                    bombMarbleList.add(new int[] {nx, ny}); // 현재 구슬을 담는다.
                    px = nx;
                    py = ny;
                }
                if(out) break;
                d++;
            }
            if(out) break;
            n++;
        }
        return isBombed;
    }

    // 4. 구슬 변하기
    private static void changeBomb() {
        Deque<Integer> newMarbles = new ArrayDeque<>(); // 새로 채울 구슬들
        Deque<int[]> marbleGroup = new ArrayDeque<>(); // 구슬 그룹
        int n = 1, d = 0;
        int nx = sharkLoc[0], ny = sharkLoc[1];
        int px = sharkLoc[0], py = sharkLoc[1];
        boolean out = false;
        while (true) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < n; j++) {
                    nx += mx[d % 4];
                    ny += my[d % 4];
                    if (nx < 0 || ny < 0) {
                        out = true;
                        break;
                    }
                    if(map[nx][ny] != map[px][py] && map[px][py] != -2) { // 구슬 번호가 바뀌었을 때 (단, 이전 좌표가 상어일 때 제외)
                        newMarbles.add(marbleGroup.size()); // 구슬 그룹 개수 (A)
                        newMarbles.add(map[px][py]); // 구슬 번호 (B)
                        while(!marbleGroup.isEmpty()) { // deque 비우기
                            marbleGroup.poll();
                        }
                    }
                    if(map[nx][ny] == 0) { // 모든 구슬들을 살펴봤다면
                        out = true;
                        break;
                    }
                    marbleGroup.add(new int[] {nx, ny}); // 현재 구슬 add
                    px = nx;
                    py = ny;
                }
                if(out) break;
                d++;
            }
            if(out) break;
            n++;
        }
        // 4-1. 구슬 채우기
        fillBomb(newMarbles);
    }

    // 4-1. 구슬 채우기
    private static void fillBomb(Deque<Integer> marbles) {
        int n = 1, d = 0;
        int nx = sharkLoc[0], ny = sharkLoc[1];
        boolean out = false;
        while (true) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < n; j++) {
                    nx += mx[d % 4];
                    ny += my[d % 4];
                    if (nx < 0 || ny < 0) {
                        out = true;
                        break;
                    }
                    if(!marbles.isEmpty()) { // 채울 구슬이 남아 있다면
                        map[nx][ny] = marbles.poll(); // 현재 위치에 구슬을 채운다.
                    }
                }
                if(out) break;
                d++;
            }
            if(out) break;
            n++;
        }
    }

    private static boolean isRange(int x, int y) {
        return x < 0 || x >= N || y < 0 || y >= N ? false : true;
    }
}
