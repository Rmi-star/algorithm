package acmicpc.gu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj_14501 {
    static int N, answer=0;
    static int[] T,P;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

//        T = new int[N];
//        P = new int[N];

        int[] T = new int[N + 2];
        int[] P = new int[N + 2];
        int[] dp = new int[N + 2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            T[i] = Integer.parseInt(st.nextToken());
            P[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = N; i > 0; i--) {
            int day = i + T[i];     // i번째 날의 상담기간

            if (day <= N + 1) {
                dp[i] = Math.max(P[i] + dp[day], dp[i + 1]);
            } else { // 상담일 초과
                dp[i] = dp[i + 1];
            }
        }

        for (int n : dp) {
            System.out.print(n + " | ");
        }
        System.out.println(dp[1]);

//        dfs(0, 0);
//        System.out.println(answer);

    }

//    static void dfs(int index, int value) {
//        if(index>=N) {
//            answer = Math.max(answer, value);
//            return;
//        }
//
//        // 해당 index를 포함
//        if (index + T[index] <= N) {
//            dfs(index + T[index], value + P[index]);
//        } else {
//            dfs(index + T[index], value); // n을 넘어가면 value 합치지 않음
//        }
//        dfs(index+1, value); // 해당 index 미포함
//    }
}
