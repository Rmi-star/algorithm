package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
/*
 마법사 상어와 비바라기
 */
public class Boj_21610 {
    static class Cloud {
        int x;
        int y;

        public Cloud (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int n, m, ret;
    static int[][] map;


    static boolean visit[][];
    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1}; // ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    static ArrayList<Cloud> cloudList;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken()); // 격자
        m = Integer.parseInt(st.nextToken()); // 이동횟수
        map = new int[n][n];

        for(int i=0; i<n; i++) { // map 생성
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        cloudList = new ArrayList<Cloud>();
        cloudList.add(new Cloud(n-2, 0));
        cloudList.add(new Cloud(n-1, 0));
        cloudList.add(new Cloud(n-2, 1));
        cloudList.add(new Cloud(n-1, 1));

        for(int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int dr = Integer.parseInt(st.nextToken()); // 이동방향
            int mc = Integer.parseInt(st.nextToken()); // 이동횟수

            visit = new boolean[n][n];
            moveCloud(dr, mc);
            waterAdd();
            removeWater();
        }

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                ret += map[i][j];
            }
        }
        System.out.println(ret);

    }

    static void removeWater() {
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(!visit[i][j] && map[i][j] >= 2) {
                    map[i][j] -= 2;
                    cloudList.add(new Cloud(i, j));
                }
            }
        }
    }

    static void waterAdd() {
        for(Cloud c : cloudList) {
           int cnt = 0;
           int nx = -1;
           int ny = -1;

           for(int i=1; i<8; i+=2) { // 1 3 5 7
               nx = c.x + dx[i];
               ny = c.y + dy[i];

               if (canMove(nx, ny) && map[nx][ny] > 0) {
                   cnt++;
               }
           }
           map[c.x][c.y] += cnt;
        }
        cloudList.clear();
    }

    static boolean canMove(int x, int y) {
        return (x>=0 && y>=0 && x<n && y<n);
    }

    static void moveCloud(int dr, int mc) {
        for(Cloud c : cloudList) {
            int nx = (c.x + n + dx[dr-1] * mc % n) % n;
            int ny = (c.y + n + dy[dr-1] * mc % n) % n;

            visit[nx][ny] = true;
            map[nx][ny] += 1; // 물의 양 1 증가
            c.x = nx;
            c.y = ny;
        }
    }
}
