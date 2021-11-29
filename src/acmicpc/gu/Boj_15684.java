package acmicpc.gu;

import java.io.*;
import java.util.StringTokenizer;

/*
 사다리 타기
 */
public class Boj_15684 {
    static int n, m, h, ret;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken()); // 세로선
        m = Integer.parseInt(st.nextToken()); // 가로선
        h = Integer.parseInt(st.nextToken()); // 세로선마다 놓을 수 있는 위치의 개수

        map = new int[h+1][n+1];

        for(int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            map[a][b] = 1;
        }

        ret = 4; // 4로 초기화
        dfs(0, 1,1);

        if(ret == 4)
            ret = -1; // 변동이 없으면

        System.out.println(ret);

    }

    static void dfs(int count, int y, int x) {
        if(count >= ret) return;
        if(check()) {
            ret = count;
            return;
        }
        if (count == 3) return;

        for(int i=y; i<=h; i++) {
            for(int j=x; j<n; j++) {
                if(map[i][j]==0 && map[i][j-1]==0 && map[i][j+1]==0) { // 선이 모두 없으면
                    map[i][j] = 1;
                    dfs(count+1, i,j);
                    map[i][j] = 0;
                }
            }
            x = 1; // 첫번째 사다리부터 다시 찾기
        }
    }

    static boolean check() {
        boolean ret = true;

        for(int i=1; i<=n; i++) { // 가로세로 모두 만족하는지 확인
            int pos = i;

            for(int j=0; j<=h; j++) {
                if(map[j][pos] == 1) { // 세로선 내려오다가 오른쪽 가로선 만나면
                    pos++; // 오른쪽 새로선으로 이동
                } else if(map[j][pos-1] == 1) { // 세로선 내려오다가 왼쪽 가로선 만나면
                    pos--; // 왼쪽 새로선으로 이동
                }
            }

            if(pos != i) { // 2에서 시작한게 2로 끝나지 않으면
                return ret = false;
            }
        }

        return ret;
    }
}
