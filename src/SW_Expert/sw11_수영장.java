package SW_Expert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
   12개월로 완전탐색
   4가지 경우가 가능
    ① 이용하지 않는 월에는 구매하지 않아도 된다. 하지만 구매해도 된다.
    ② 1일 이용권으로 이번 달 사용 횟수를 모두 구매한다.
    ③ 1개월 이용권을 구매한다.
    ④ 3개월 이용권을 구매한다. (10월 까지만 구매 가능)
 */
public class sw11_수영장 {
    static int[] cost;
    static int[] plan;
    static int T, ans;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        T = Integer.parseInt(br.readLine());

        for(int t = 1 ; t <= T ; ++t) {
            cost = new int[4];
            plan = new int[13];

            st = new StringTokenizer(br.readLine());
            for(int i = 0 ; i < 4 ; ++i)
                cost[i] = Integer.parseInt(st.nextToken()); // 비용 입력

            st = new StringTokenizer(br.readLine());
            for(int i = 1 ; i <= 12 ; ++i)
                plan[i] = Integer.parseInt(st.nextToken()); // 12월 계획 입력

            ans = cost[3];
            dfs(1, 0);

            System.out.println("#" + t + " " + ans);
        }
    }

    private static void dfs(int month, int total) {
        if(month == 13) { // 12월 다 확인
            ans = ans > total ? total : ans; // 최소 찾기
            return;
        }

        // 이용안할 때 안사기
        if(plan[month] == 0) dfs(month + 1, total);

        // 1일 사용권으로 채우기 이용횟수 1회 이상
        if(plan[month] > 0) dfs(month + 1, total + plan[month] * cost[0]);

        // 1달 사용권으로 채우기
        dfs(month + 1, total + cost[1]);

        // 3달 사용권으로 채우기  10월까지만 구매가능
        if(month <= 10) dfs(month + 3, total + cost[2]);
    }
}
