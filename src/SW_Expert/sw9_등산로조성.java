package SW_Expert;
import java.io.*;
import java.util.*;

/*
    1. 참고사항
    - 깎았을 때 와 깎지 않았을 때로 나누어 구별한다.

    2. 구현
    - 봉우리의 최대값을 list에 집어넣는다.
    - 최대값들을 dfs를 돌린다.
    - 4방향으로 이동하면서 자신보다 낮은 봉우리면 dfs를 시작한다.
    - 자신보다 높은 봉우리면 K만큼 깍아보고 자신보다 작아지는지 확인한다.
    - 다음 봉우리에 자신의 -1값으로 바꾼 후 dfs를 시작한다.
    - 가장 긴 cnt를 출력한다.

    참고: https://toastfactory.tistory.com/202
 */
public class sw9_등산로조성 {
    public static int[] dx = {-1,1,0,0};
    public static int[] dy = {0,0,-1,1};
    public static int[][] map;
    public static boolean[][] visit;
    public static int N,K,max;

    public static void dfs(int row , int col , int cnt, boolean use) {
        max = Math.max(max, cnt);
        visit[row][col] = true;

        for (int i = 0; i < 4; i++) {
            int nx = row + dx[i];
            int ny = col + dy[i];

            if(0 <= nx && nx < N && 0 <= ny && ny < N && !visit[nx][ny]) {
                if(map[nx][ny] < map[row][col]) {
                    dfs(nx,ny,cnt+1,use);
                } else { //아직 깍지 않았다.
                    if(!use) { //깎아도 지금봉우리보다 작을 때
                        if(map[nx][ny] -K < map[row][col]) {
                            int temp = map[nx][ny];
                            map[nx][ny] = map[row][col]-1;
                            dfs(nx,ny,cnt+1,true);
                            map[nx][ny] = temp;
                        }
                    }
                }
            }
        }
        visit[row][col] = false;
    }

    public static void main(String[] args) throws Exception {
        //System.setIn(new FileInputStream("test.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken()); //지도크기 3~8
            K = Integer.parseInt(st.nextToken()); //공사가능높이 1~5

            map = new int[N][N];
            visit = new boolean[N][N];
            int high = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken()); //지형의높이 1~20
                    high = Math.max(high, map[i][j]);
                }
            }

            List<int[]> top = new ArrayList<int[]>();

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(map[i][j] == high)
                        top.add(new int[] {i,j});
                }
            }

            max = -1;

            for (int i = 0; i < top.size(); i++) {
                int [] ij = top.get(i);
                dfs(ij[0], ij[1] , 1, false);
            }

            sb.append("#" + tc + " " + max + "\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
