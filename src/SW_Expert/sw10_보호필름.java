package SW_Expert;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
    각 행마다 주입하지 않는, A를 주입하는, B를 주입하는 경우를 모두 해보는 완전탐색
    → 보호 필름 상태를 입력
    → 테스트를 진행
    → DFS로 약품 투입의 모든 경우에 테스트를 진행

    참고 : https://velog.io/@hyeon930/SWEA-2112-%EB%B3%B4%ED%98%B8-%ED%95%84%EB%A6%84-Java
 */
public class sw10_보호필름 {
    static int[][] map, temp;
    static int D, W, K, T, ans;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        T = Integer.parseInt(br.readLine());

        for(int t = 1 ; t <= T ; ++t) {
            st = new StringTokenizer(br.readLine());

            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            map = new int[D][W];
            temp = new int[D][W];
            ans = Integer.MAX_VALUE;

            for(int r = 0 ; r < D ; ++r) {
                st = new StringTokenizer(br.readLine());
                for(int c = 0 ; c < W ; ++c) {
                    int type = Integer.parseInt(st.nextToken());
                    map[r][c] = temp[r][c] = type;
                }
            }

            if(isPass()) {
                ans = 0;
            } else {
                injection(0, 0);
            }

            System.out.println("#" + t + " " + ans);
        }
    }

    private static void injection(int cnt, int layer) {
        if(cnt >= ans) return;

        if(layer == D) {
            if(isPass()) {
                ans = ans > cnt ? cnt : ans;
            }

            return;
        }

        // 주입하지 않음
        injection(cnt, layer + 1);

        // A 주입
        for(int c = 0 ; c < W ; ++c) temp[layer][c] = 0;
        injection(cnt + 1, layer + 1);

        // B 주입
        for(int c = 0 ; c < W ; ++c) temp[layer][c] = 1;
        injection(cnt + 1, layer + 1);

        // 되돌리기
        for(int c = 0 ; c < W ; ++c) temp[layer][c] = map[layer][c];
    }

    private static boolean isPass() {
        for(int c = 0 ; c < W ; ++c) {
            int cnt = 1;
            int type = temp[0][c];
            boolean flag = false;

            for(int r = 1 ; r < D ; ++r) {
                if(type == temp[r][c]) {
                    cnt++;
                } else {
                    type = temp[r][c];
                    cnt = 1;
                }

                if(cnt == K) {
                    flag = true;
                    break;
                }
            }

            if(!flag) return false;
        }

        return true;
    }
}

