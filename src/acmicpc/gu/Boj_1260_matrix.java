package acmicpc.gu;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Boj_1260_matrix {
    static StringBuilder sb = new StringBuilder();
    static int N, V, M;
    static boolean[] visit;
    static int[][] adj;

    public static void main(String[] args) {
        // 입력
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        M = in.nextInt();
        V = in.nextInt();

        adj = new int[N+1][N+1];

        for(int i=1; i<=M; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            adj[x][y] = 1;
            adj[y][x] = 1;
        }

    }

    static void pro() {
        visit = new boolean[N+1];
        dfs(V);
        sb.append('\n');
        for(int i=0; i<=N; i++) {
            visit[i] = false;
        }
        bfs(V);
        System.out.println(sb);
    }

    static void dfs(int x) {
        visit[x] = true;
        sb.append(x).append(' ');
        for(int y=1; y<=N; y++) {
            if(adj[x][y] == 0) continue;
            if(visit[y] == true) continue;
            dfs(y);
        }
    }

    static void bfs(int x) {
        Queue<Integer> que = new LinkedList<>();

        que.add(x);
        visit[x] = true;

        while (!que.isEmpty()) {
            x = que.poll();
            sb.append(x).append(' ');

            for(int y=1; y<=N; y++) {
                if(adj[x][y] == 0) continue;
                if(visit[y] == true) continue;

                que.add(y);
                visit[y] = true;
            }
        }

    }
}
