package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Boj_14502 {
    static StringBuilder sb = new StringBuilder();

    static int N, M, B, ans;
    static int[][] A, blank;
    static boolean[][] visit;
    static int[][] dir = { {1,0}, {0,1}, {-1,0},{0,-1} };

    static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 지도크기
        M = Integer.parseInt(st.nextToken());

        A = new int[N+1][M+1];
        blank = new int[N*M+1][2];
        visit = new boolean[N+1][M+1];

        for(int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=M; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    // 바이러스 퍼뜨리기
    static void bfs() {
        Queue<Integer> Q = new LinkedList<>();

        // 모든 바이러스가 시작점으로 가능하니까, 전부 큐에 넣어준다.
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                visit[i][j] = false; // visit 배열 초기화
                if(A[i][j] == 2) { // 바이러스가 있다면
                    Q.add(i); // 행
                    Q.add(j); // 열
                    visit[i][j] = true;
                }
            }
        }

        // BFS 과정
        while(!Q.isEmpty()) {
            int x = Q.poll();
            int y = Q.poll();

            for(int k=0; k<4; k++) {
                int nx = x + dir[k][0];
                int ny = y + dir[k][1];

                if (nx < 1 || ny < 1 || nx > N || ny > M) continue;
                if(A[nx][ny] != 0) continue;
                if(visit[nx][ny]) continue;

                visit[nx][ny] = true;
                Q.add(nx);
                Q.add(ny);
            }
        }

        // 탐색이 종료된 시점이니, 안전 영역으로 넓이를 계산하고, 정답을 갱신
        int cnt = 0;
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                if (A[i][j] == 0 && !visit[i][j]) { // 안전영역이다
                    cnt++;
                }
            }
        }

        ans = Math.max(ans, cnt);
    }

    // idx 번째 빈 칸에 벽을 세울 지 말 지 결정해야 하고, 이전까지 selected_cnt 개의 벽을 세웠다.
    static void dfs(int idx, int selected_cnt) {
        if(selected_cnt == 3) { // 3개의 벽을 모두 세운 상태
            bfs();
            return;
        }
        if(idx > B) return; // 더 이상 세울 수 있는 벽이 없는 상태

        A[blank[idx][0]][blank[idx][1]] = 1;
        dfs(idx + 1, selected_cnt + 1);

        A[blank[idx][0]][blank[idx][1]] = 0;
        dfs(idx+1, selected_cnt);
    }

    static void pro() {
        // 모든 벽의 위치를 모아놓자.
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                if(A[i][j] == 0) { // 56빈 공간이라면
                    B++; // 벽의 개수 증가
                    blank[B][0] = i; // 벽의 위치 기록
                    blank[B][1] = j;
                }
            }
        }

        // 벽을 3개 세우는 모드 방법을 확인해보자!
        dfs(1, 0);

        System.out.println(ans);
    }

    public static void main(String[] args) throws IOException {
        input();
        pro();
    }
}
