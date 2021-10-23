package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 마법사 상어와 파이어스톰
 1. 2^n만큼 구역을 나눠주고 그 칸을 회전
 2. 전체 탐색하며 얼음을 녹임
 3. 1,2를 주어진 단계만큼 수행
 4. 제일 큰 덩어리와 얼음의 합을 출력
 */
public class Boj_20058 {

    static class Dot{
        int row;
        int col;
        Dot(int r, int c){
            this.row = r;
            this.col = c;
        }
    }

    static int n;
    static int[][] ice;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int N =  Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        n = (int) Math.pow(2, N);// 비트 연산 2의 제곱 연산!
        ice = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int cur = Integer.parseInt(st.nextToken());
                ice[i][j] = cur;
            }
        }

        int[] steps = new int[Q];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < Q; i++) {
            int cur = Integer.parseInt(st.nextToken());
            cur = 1 << cur;
            steps[i] = cur;
            ice = tornado(steps[i]);
            melt();
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(ice[i][j] > 0) sum += ice[i][j];
            }
        }
        System.out.println(sum);
        System.out.println(largeOne());
    }

    // 얼음 녹이는 부분
    public static void melt() {
        boolean[][] marked = new boolean[n][n];

        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cnt = 0;
                if(ice[i][j] == 0) continue;
                for (int dir = 0; dir < 4; dir++) {
                    int nextR = i + dirs[dir][0];
                    int nextC = j + dirs[dir][1];

                    if(nextR < 0 || nextC < 0 || nextR >=n || nextC >= n) continue;
                    if(ice[nextR][nextC] <= 0) continue;
                    cnt++;
                }
                if(cnt <3) marked[i][j] = true;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(marked[i][j]) ice[i][j]--;
            }
        }
    }

    public static int largeOne() {
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        boolean[][] marked = new boolean[n][n];
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(marked[i][j]) continue;
                int cnt = 0;
                Queue<Dot> queue = new LinkedList<Dot>();
                queue.add(new Dot(i, j));
                while(!queue.isEmpty()) {
                    Dot cur = queue.poll();

                    for (int k = 0; k < 4; k++) {
                        int nextR = dirs[k][0] + cur.row;
                        int nextC = dirs[k][1] + cur.col;

                        if(nextR < 0 || nextC < 0 || nextR >=n || nextC >= n) continue;
                        if(marked[nextR][nextC] || ice[nextR][nextC] <= 0) continue;
                        marked[nextR][nextC] = true;
                        cnt++;
                        queue.add(new Dot(nextR, nextC));
                    }

                    result = Math.max(cnt, result);
                }
            }
        }
        return result;
    }

    // 회전
    public static int[][] tornado(int distance){
        int[][] result = new int[n][n];

        // 시작점을 잡기 위함
        for (int startRow = 0; startRow < n; startRow+= distance) {
            for (int startCol = 0; startCol < n; startCol+= distance) {
                for (int i = 0; i < distance; i++) { // 회전
                    for(int j = 0; j < distance; j++) {
                        result[startCol + i][startRow +j]=ice[startCol + distance -1 -j][startRow+i];
                    }
                }
            }
        }
        return result;
    }
}

