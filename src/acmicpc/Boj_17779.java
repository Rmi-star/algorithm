package acmicpc;
import java.util.*;
import java.io.*;
/*
 게리맨더링 2
 1. 4 중 for 문으로 (x,y,d1,d2) 경우의 수를 전부 구한다.
 2. (x,y) 좌표에서 시작하여 d1, d2를 기준으로 경계선만 체크한다.
 3. 경계선을 기준으로 1,2,3,4 구역의 인구수를 구한다.
 4. 5 구역은 (전체 인구수 - 나머지 구역의 인구수) 로 계산
 5. 각 구역 인구수를 오름차순으로 정렬한 뒤에 최댓값에서 최소값을 빼고 min 배열과 비교해서 최소값 구한다.
 */
public class Boj_17779 {
    static int N;
    static int[][] arr;
    static int totalPeople = 0;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // input
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        // 지역 크기
        arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                totalPeople += arr[i][j];
            }
        }

        // solution
        // 1. 기준에 맞는 x, y, d1, d2 선정
        // 기준점
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                // 경계의 길이
                for (int d1 = 1; d1 < N; d1++) {
                    for (int d2 = 1; d2 < N; d2++) {

                        // 범위 필터링 1 ≤ x < x+d1+d2 ≤ N
                        if (x + d1 + d2 >= N) continue;
                        // 범위 필터링 1 ≤ y-d1 < y < y+d2 ≤ N
                        if (y - d1 < 0 || y + d2 >= N) continue;
                        // 시도해볼 수 있는 상태면 5번 선거구를 만들어보자.
                        solution(x, y, d1, d2);
                    }
                }
            }
        }

        System.out.println(min);
    }

    static void solution(int x, int y, int d1, int d2) {
        boolean[][] border = new boolean[N][N];

        // 경계선 세팅
        for (int i = 0; i <= d1; i++) {
            border[x + i][y - i] = true;
            border[x + d2 + i][y + d2 - i] = true;
        }

        for (int i = 0; i <= d2; i++) {
            border[x + i][y + i] = true;
            border[x + d1 + i][y - d1 + i] = true;
        }

        int[] peopleSum = new int[5];

        // 1 구역 인구수
        for (int i = 0; i < x + d1; i++) {
            for (int j = 0; j <= y; j++) {
                if (border[i][j]) break;
                peopleSum[0] += arr[i][j];
            }
        }

        // 2 구역 인구수
        for (int i = 0; i <= x + d2; i++) {
            for (int j = N - 1; j > y; j--) {
                if (border[i][j]) break;
                peopleSum[1] += arr[i][j];
            }
        }

        // 3 구역 인구수
        for (int i = x + d1; i < N; i++) {
            for (int j = 0; j < y - d1 + d2; j++) {
                if (border[i][j]) break;
                peopleSum[2] += arr[i][j];
            }
        }

        // 4 구역 인구수
        for (int i = x + d2 + 1; i < N; i++) {
            for (int j = N - 1; j >= y - d1 + d2; j--) {
                if (border[i][j]) break;
                peopleSum[3] += arr[i][j];
            }
        }

        // 5 구역 인구수
        peopleSum[4] = totalPeople;

        for (int i = 0; i < 4; i++) {
            peopleSum[4] -= peopleSum[i];
        }

        // 정렬
        Arrays.sort(peopleSum);

        // 최대 - 최소
        min = Math.min(min, peopleSum[4] - peopleSum[0]);
    }
}
