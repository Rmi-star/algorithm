package acmicpc.gu;

import java.util.Scanner;

public class Day1_6588 {
    public static final int MAX = 1000000;
    public static boolean[] prime;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 소수 세팅
        get_prime();

        // 짝수 n에 대한 두 소수 구하기
        while(true) {
            int n = sc.nextInt();
            boolean ck = false;

            if(n==0)
                break;

            for(int i=2; i<=n/2; i++) {
                if(prime[i] && prime[n-i]) {
                    System.out.println(n + " = " + i + " + " + (n-i));
                    ck = true;
                    break;
                }
            }

            if(!ck) {
                System.out.println("Goldbach's conjecture is wrong.");
            }

        }
    }

    // 에라토스테네스의 체
    public static void get_prime() {
        prime = new boolean[MAX+1];

        for(int i=2; i<=MAX; i++) {
            prime[i] = true;
        }

        for(int i=2; i<=MAX; i++) {
            if(!prime[i])
                continue; // 이미 지워진 수라면 pass

            // 이미 지워진 숫자가 아니라면, 그 배수부터 출발하여 가능한 모든 숫자 지우기
            for(int j=i*2; j<=MAX; j+=i) {
                prime[j] = false;
            }
        }
    }
}
