package acmicpc;

import java.util.*;

public class Boj_1260_list {
    static StringBuilder sb = new StringBuilder();

    static int N, V, M;
    static boolean[] visit;
    static ArrayList<Integer>[] adj;

    public static void main(String[] args) {
        // 입력
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        M = in.nextInt();
        V = in.nextInt();

        adj = new ArrayList[N+1];

        for(int i=1; i<=N; i++) {
            adj[i] = new ArrayList<Integer>();
        }

        for(int i=1; i<=M; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            adj[x].add(y);
            adj[y].add(x);
        }

        for(int i=1; i<=N; i++) {
            Collections.sort(adj[i]);
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

        for(int y : adj[x]) {
            if(visit[y]) continue;
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

            for(int y : adj[x]) {
                if(visit[y]) continue;

                que.add(y);
                visit[y] = true;
            }
        }

    }
}
