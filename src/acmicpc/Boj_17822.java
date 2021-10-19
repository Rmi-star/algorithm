package acmicpc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 원판 돌리기
 - i번째 회전할때 사용하는 변수는 xi, di, ki
 - 번호가 xi 의 배수인 원판을 di 방향으로 ki칸 회전시킨다.
 - di 가 0인 경우는 시계 방향, 1인 경우는 반시계 방향이다.
 - 원판에 수가 남아 있으면, 인접하면서 수가 같은 것을 모두 찾는다.
 - 그러한 수가 있는 경우에는 원판에서 인접하면서 같은 수를 모두 지운다.
 - 없는 경우에는 원판에 적힌 수의 평균을 구하고, 평균보다 큰 수에서 1을 빼고, 작은 수에는 1을 더한다.

 */
public class Boj_17822 {

    static int n, m, t;
    static ArrayList<Integer> al[];
    static boolean change; // 인접한 수가 있는 지 없는지 확인을 위한 변수
    static int dx[] = {-1,1,0,0};
    static int dy[] = {0,0,-1,1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stz.nextToken()); // 원판의 수
        m = Integer.parseInt(stz.nextToken()); // 한 원판에 적힌 수의 개수
        t = Integer.parseInt(stz.nextToken()); // 동작 횟수
        al = new ArrayList[n+1];
        for(int i = 1; i <= n; i++) {
            al[i] = new ArrayList<Integer>();
            stz = new StringTokenizer(br.readLine());
            for(int j = 0; j < m; j++)
                al[i].add(Integer.parseInt(stz.nextToken())); // 원판에 적힌 수 입력
        }

        for(int i = 0; i < t; i++){
            stz = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(stz.nextToken()); // x의 배수인 원판을
            int d = Integer.parseInt(stz.nextToken()); // d 방향으로 (0:시계 / 1:반시계)
            int k = Integer.parseInt(stz.nextToken()); // k 칸 회전
            change = false;
            rotate(x, d, k);

            // 모든 원판 검사
            for(int a = 1; a <= n; a++){
                for(int b = 0; b < m; b++) {
                    if(al[a].get(b) != -1) {
                        check(a, b, al[a].get(b));
                    }
                }
            }

            if(!change)
                cal();
        }
        System.out.println(sum());
    }

    // x 배수의 원판을 d 방향으로 k 번 회전
    public static void rotate(int x, int d, int k) {
        for(int i = 1; i <= n; i++) {
            if(i % x == 0) { // x 의 배수이면
                if(d == 0) { // 시계방향
                    for(int j = 0; j < k; j++)
                        // 마지막번호가 1번으로
                        // list[0] → list[size-1]
                        al[i].add(0, al[i].remove(al[i].size()-1));
                }
                else{ // 반시계 방향
                    for(int j = 0; j < k; j++){
                        // 1번이 마지막으로
                        // list[size-1] → list[0]
                        int number = al[i].remove(0);
                        al[i].add(al[i].size(), number);
                    }
                }
            }
        }
    }

    // 인접한 수 ( 원판 위, 아래, 같은 원판에서 양 옆 ) 모두 제거 → -1
    public static void check(int x, int y, int value) {
        for(int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(ny == m)
                ny = 0;
            if(ny == -1)
                ny = m-1;

            if(check(nx) && al[nx].get(ny) == value && al[nx].get(ny) != -1) {
                al[nx].set(ny, -1);
                change = true;
                check(nx, ny, value);
            }
        }
    }
    // 정답 출력 메서드
    public static int sum() {
        int sum = 0;
        for(int a = 1; a <= n; a++){
            for(int b = 0; b < m; b++)
                if(al[a].get(b) != -1)
                    sum += al[a].get(b);
        }
        return sum;
    }

    // 인접한 수의 제거가 없을 경우 실행
    public static void cal() {
        int sum = 0;
        int count = 0;

        // 모든 원판의 평균 구하기
        for(int a = 1; a <= n; a++){
            for(int b = 0; b < m; b++)
                if(al[a].get(b) != -1) {
                    sum += al[a].get(b);
                    count++;
                }
        }

        // 남은 수가 없을 경우
        if(count == 0)
            return;

        double avg = (double) sum / count;

        // 평균보다 큰 수 -1 / 작은수 +1
        for(int a = 1; a <= n; a++){
            for(int b = 0; b < m; b++) {
                if(al[a].get(b) != -1) {
                    if(al[a].get(b) > avg)
                        al[a].set(b, al[a].get(b) - 1);
                    else
                    if(al[a].get(b) < avg)
                        al[a].set(b, al[a].get(b) + 1);
                }
            }
        }
    }

    // 원판의 층이 정상 값인지 확인 → 1층 ~ n층
    public static boolean check(int x) {
        return x >= 1 && x <= n;
    }
}
