package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj_13458 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long cnt = 0; // 감독관 수
        int[] a = new int[N]; // 응시자 수

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int B = Integer.parseInt(st.nextToken()); // 총감독이 맡을 수 있는 사람수
        int C = Integer.parseInt(st.nextToken()); // 부감독이 맡을 수 있는 사람수

        for(int i=0; i<N; i++) {
            a[i]-=B;
            cnt++;

            // 총 감독이 맡을 수 있는 경우는 부감독 필요 없으므로 넘어감
            if(a[i]<=0) {
                continue;
            } else { // 부감독이 추가로 필요한 경우
                cnt += a[i]/C;
                // 나머지가 0보다 크면 부감독 한명 더 추가
                if(a[i]%C > 0) {
                    cnt++;
                }
            }
        }

        System.out.println(cnt);
    }
}
