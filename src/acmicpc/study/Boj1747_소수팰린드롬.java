package acmicpc.study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Boj1747_소수팰린드롬 {
    static final int RANGE = 1_500_000;
    public static void main(String[] args) throws IOException {
        BufferedReader bw = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bw.readLine());
        boolean[] isPrime = new boolean[RANGE];
        Arrays.fill(isPrime , true);
        int ans = 0;

        // 소수 만들기 → 에라토스테네스의 체
        isPrime[0] = false;
        isPrime[1] = false;
        for(int i=2; i*i<=RANGE; i++) {
            if(!isPrime[i]) continue;
            for(int j=i*i; j<=RANGE; j+=i) {
                isPrime[j] = false;
            }
        }

        while (N<=RANGE) {
            // 소수면서 팰린드롬이면
            if(isPrime[N] && isPal(N)) {
                ans = N;
                break;
            }
            N++;
        }

        System.out.println(ans);
    }

    public static boolean isPal(int N) {
        // 문자열 뒤집기
        String str = String.valueOf(N);
        StringBuffer sb = new StringBuffer(str);
        String reverse = sb.reverse().toString();

        // 문자열을 뒤집은게 똑같으면 true
        if(str.equals(reverse))
            return true;

        // 똑같지 않으면 false
        return false;
    }
}
