package SW_Expert;

import java.util.Queue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
 모든 셀에서 BFS를 수행해서 조건에 부합하는 경우를 찾으면 되는 완전탐색 문제

 참고 : https://velog.io/@hyeon930/SWEA-2117-%ED%99%88-%EB%B0%A9%EB%B2%94-%EC%84%9C%EB%B9%84%EC%8A%A4-Java
 */
public class sw8_홈방범서비스 {
    static class Node {
        int x,y;
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static Queue<Node> q;
    static boolean[][] visited;
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int[][] map;
    static int N, M, T, ans;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        T = Integer.parseInt(br.readLine());

        for(int t = 1 ; t <= T ; ++t) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            ans = 0;

            q = new LinkedList<>();
            map = new int[N][N];
            visited = new boolean[N][N];

            for(int r = 0 ; r < N ; ++r) {
                st = new StringTokenizer(br.readLine());
                for(int c = 0 ; c < N ; ++c) {
                    map[r][c] = Integer.parseInt(st.nextToken());
                }
            }

            for(int r = 0 ; r < N ; ++r) {
                for(int c = 0 ; c < N ; ++c) {
                    init();
                    bfs(r, c);
                }
            }

            System.out.println("#" + t + " " + ans);
        }




    }

    private static void bfs(int r, int c) {
        q.offer(new Node(r, c));
        visited[r][c] = true;

        int K = 1;
        int house = map[r][c] == 1 ? 1 : 0;

        if(getOperationCost(K) <= house * M) { // K가 수익을 낼수있는 범위안에 있으면
            ans = K > ans ? K : ans; // 서비스 면적 크기 변경
        }

        while(!q.isEmpty()) {
            int size = q.size();
            K++;

            for(int i = 0 ; i < size ; ++i) {
                Node cur = q.poll();

                for(int d = 0 ; d < 4 ; ++d) {
                    int nx = cur.x + dir[d][0];
                    int ny = cur.y + dir[d][1];

                    if(nx < 0 || nx >= N || ny < 0 || ny >= N || visited[nx][ny]) continue;

                    if(map[nx][ny] == 1) house++;

                    q.offer(new Node(nx, ny));
                    visited[nx][ny] = true;
                }
            }
            if(getOperationCost(K) <= house * M) {
                ans = house > ans ? house : ans;
            }
        }
    }

    private static int getOperationCost(int k) {
        return k * k + (k - 1) * (k - 1);
    }

    private static void init() {
        q.clear();
        for(int r = 0 ; r < N ; ++r) {
            for(int c = 0 ; c < N ; ++c) {
                visited[r][c] = false;
            }
        }
    }
}
