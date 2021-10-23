package SW_Expert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class sw7_벌꿀채취 {
    private static int n, m, c, answer;
    private static int cost1, cost2;
    private static int[][] map;
    private static boolean[] visit;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st; int T = Integer.parseInt(br.readLine());


        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            map = new int[n][n];
            visit = new boolean[m];

            answer = 0;

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            cost1 = 0;
            cost2 = 0;

            solution();

            System.out.println("#" + t + " " + answer);
        }
    }

    private static void solution() {
        boolean[][] check = new boolean[n][n];
        boolean flag;

        // 노란색 벌통 위치 찾기
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - (m - 1); j++) {

                // 노란색 벌통 방문 처리
                for (int k = j; k < j + m; k++) {
                    check[i][k] = true;
                }

                // 주황색 벌통 위치 찾기
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n - (m - 1); b++) {
                        flag = true;

                        for (int c = 0; c < m; c++) { // 노란색 벌통과 겹치는지 체크
                            if (check[a][b + c]) {
                                flag = false;
                                break;
                            }
                        }

                        if (!flag) continue; // 겹치면 통과

                        check(i, j, a, b, 0); // 노란색, 주황색 벌통을 전부 골랐으므로 max 값 계산
                        answer = Math.max(answer, cost1 + cost2);
                        cost1 = 0;
                        cost2 = 0;
                    }
                }

                // 노란색 벌통 백트래킹
                for (int k = j; k < j + m; k++) {
                    check[i][k] = false;
                }
            }
        }
    }

    private static void check(int i, int j, int a, int b, int depth) {
        if (depth == m) {
            int count1 = 0, count2 = 0, c1 = 0, c2 = 0;

            for (int k = 0; k < m; k++) {
                if (visit[k]) {
                    count1 += map[i][j + k];
                    c1 += (int) Math.pow(map[i][j + k], 2);
                    count2 += map[a][b + k];
                    c2 += (int) Math.pow(map[a][b + k], 2);
                }
            }

            if (count1 <= c) cost1 = Math.max(cost1, c1); // 수익 최대값 계산
            if (count2 <= c) cost2 = Math.max(cost2, c2);

            return;
        }

        visit[depth] = true;
        check(i, j, a, b, depth + 1);
        visit[depth] = false;
        check(i, j, a, b, depth + 1);
    }
}
