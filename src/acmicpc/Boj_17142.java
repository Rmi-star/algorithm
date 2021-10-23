package acmicpc;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 연구소3
 - 백트래킹으로 바이러스를 퍼뜨릴 수있는 공간에 M개의 바이러스 활성화
 - M 개의 바이러스를 활성화 시켰다면 BFS 로 바이러스 퍼뜨림
 - 모든 빈칸에 바이러스를 퍼뜨렸다면 그때까지 걸린 시간 Math.min()으로 mis 변수 저장
 - 주의: 비활성화 바이러스 → 활성화 바이러스 만나면 할성화 되어퍼뜨려야 함
 -      이때는 빈칸에 퍼뜨렸다는 것을 의미하는 cnt 변수 증가 X

 */
public class Boj_17142 {
    static int N, M;
    static int[][] map;
    static ArrayList<Point> virusPoint;
    static int count;
    static int min = Integer.MAX_VALUE;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        count = N*N; // 바이러스를 퍼뜨려야하는 공간 개수 저장

        map = new int[N][N];
        virusPoint = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();

                // 벽은 제외
                if(map[i][j] == 1) count--;

                // 바이러스도 제외
                if(map[i][j] == 2) {
                    count--;
                    virusPoint.add(new Point(i, j));
                }
            }
        }

        if(count == 0) { // 빈 칸이 없으면 퍼뜨릴 곳도 없음
            System.out.println(0);
        } else {
            dfs(0, 0);
            System.out.println(min != Integer.MAX_VALUE ? min : -1);
        }
    }

    // 백트래킹으로 바이러스가 위치할 수 있는 곳에 M개 바이러스를 놓아봄
    // (위치할 수 있는 곳 N 개에서 M개를 뽑는 조합이라 생각하면 됨)
    public static void dfs(int depth, int start) {
        if(depth == M) { // M개의 바이러스를 활성화 시킨 경우
            bfs();
            return;
        } else {
            // 2차원 배열 map을 돌면서 dfs하면 시간초과 -> 바이러스가 있는 곳만 dfs로 돌아봄
            for(int i = start; i < virusPoint.size(); i++) {
                Point p = virusPoint.get(i);

                map[p.x][p.y] = 3; // 바이러스 활성화
                dfs(depth+1, i+1);
                map[p.x][p.y] = 2; // dfs후에는 다시 원상태로 돌려놓음
            }
        }
    }

    // bfs로 바이러스를 퍼뜨려봄
    public static void bfs() {
        Queue<Point> queue = new LinkedList<>();
        int[][] visited = new int[N][N];

        // copy map
        int[][] copyMap = new int[N][N]; // 기존 맵을 건들면 안되니까 복사해서 사용
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                copyMap[i][j] = map[i][j];

                if(copyMap[i][j] == 3) { // 활성화된 바이러스가 있는 위치라면 큐에 추가
                    queue.add(new Point(i, j));
                    visited[i][j] = 1;
                }
            }
        }

        int cnt = 0; // 바이러스가 퍼진 칸의 개수를 저장

        while(!queue.isEmpty()) {
            int cx = queue.peek().x;
            int cy = queue.poll().y;

            for(int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                // 범위 벗어나거나 벽이라면 퍼뜨릴 수 X
                if(nx < 0 || ny < 0 || nx >= N || ny >= N || copyMap[nx][ny] == 1)
                    continue;

                if(visited[nx][ny] == 0) {
                    visited[nx][ny] = visited[cx][cy] + 1;
                    queue.add(new Point(nx, ny));

                    if(copyMap[nx][ny] == 0) //비활성바이러스를 활성화 시킨 경우 포함X
                        cnt++;

                    // 바이러스를 모든 빈 칸에 퍼뜨린 경우
                    // 그때까지 걸린 시간이 min보다 작다면 갱신
                    // (시작할 때 방문 의미로 1을 주고 시작했으니 -1해서 계산)
                    if(cnt == count) {
                        min = Math.min(min, visited[nx][ny]-1);
                    }
                }
            }
        }
    }
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}

