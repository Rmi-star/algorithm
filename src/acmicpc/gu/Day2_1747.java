package acmicpc.gu;

import java.util.Scanner;

public class Day2_1747 {
    public static final int MAX = 2000000;
    public static boolean[] prime;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        // 소수 세팅
        Prime();

        while(true) {
            // 소수
            if(prime[n] &&  isPalindrome(n)) {
                System.out.println(n);
                break;
            }

            n++;
        }
    }

    // 에라토스테네스의 체
    public static void Prime() {
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

//    public static boolean isPalindrome(int num) {
//        int res = 0;
//        int temp = num;
//        while(temp!=0) {
//            res = res * 10 + temp % 10;
//            temp /= 10;
//        }
//        return res == num;
//    }

    // 람다식 사용
    public static boolean isPalindrome(int num) {
        StringBuilder sb = new StringBuilder(String.valueOf(num));
        if(sb.toString().equals(sb.reverse().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
