package acmicpc.gu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Boj_2667 {
    static StringBuilder sb = new StringBuilder();

    static int N, group_cnt;
    static String[] a;
    static boolean[][] visit;
    static int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}}; // 인접한 객체 발리 찾기 위한 것
    static ArrayList<Integer> group;

    static void input() {
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        a = new String[N];
        for (int i = 0; i < N; i++) {
            a[i] = in.next();
        }

        visit = new boolean[N][N];
    }

    // x, y 를 갈 수 있다는 걸 알고 방문한 상태
    static void dfs(int x, int y) {
        // 단지에 속한 집의 개수 증가, visit 체크 하기
        group_cnt++;
        visit[x][y] = true;

        // 인전한 집으로 방문하기
        for(int k=0; k<4; k++) { // 격자무늬로 생각했을 때 인접한것 4부분이니까 for문 4번 돔
            // (x,y) -> dir[k]
            int nx = x + dir[k][0];
            int ny = y + dir[k][1];

            // 이동한 격자가 실제로 존재하는지 확인
            if(nx<0 || ny<0 || nx>=N || ny>=N) continue; // 칸을 벗어난다면 갈 필요 없음
            if(a[nx].charAt(ny) == '0') continue;
            if(visit[nx][ny]) continue;
            dfs(nx, ny);
        }
    }

    static void pro() {
        group = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visit[i][j] && a[i].charAt(j) == '1') {
                    // 갈 수 있는 칸인데, 이미 방문 처리된, 즉 새롭게 만난 단지인 경우!
                    group_cnt = 0;
                    dfs(i, j);
                    group.add(group_cnt);
                }
            }
        }

        // 찾은 단지의 정보 출력
        Collections.sort(group);
        sb.append(group.size()).append('\n');
        for(int cnt : group) sb.append(cnt).append('\n');
        System.out.println(sb);
    }

    public static void main(String[] args) {
        input();
        pro();
    }
}
