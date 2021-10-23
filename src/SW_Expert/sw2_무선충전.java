package SW_Expert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
    1. 먼저 사용자들의 이동 정보를 각 배열에 저장하고 BC의 정보는 AP클래스를 만들어서 ArrayList에 저장하였다.
    2. 시간에 따라 이동하면서 각 시간마다 A와 B의 충전량 합의 최댓값을 구해준다.
    3. 최댓값은 사용자가 각각 사용할 수 있는 충전량을 배열에 저장해놓고 모든 경우를 구해줬다.
    4. 이때 i == j 인 상황이 됐을 때 한쪽은 0 (해당 BC를 사용할 수 없는 경우)일 수도 있으므로
       무조건 반으로 나눠주면 안 되고 둘 다 해당 BC를 이용하고 있는지 확인해주어야 한다. (같은 p값을 가지는지 확인)
    5.시간마다의 최댓값을 모두 더해준 후에 출력

    → 2시간 ( 참고 + 스스로 ) 다음번에는 스스로하기를 ㅠ
 */
public class sw2_무선충전 {

    static class BC {
        int x;
        int y;
        int cover;
        int perf;

        public BC(int x, int y, int cover, int perf) {
            this.x = x;
            this.y = y;
            this.cover = cover;
            this.perf = perf;
        }
    }

    static class Pos {
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int M, A;
    static int[] da;
    static int[] db;
    static ArrayList<BC> bcList;
    static int dx[] = {0, -1, 0, 1, 0};
    static int dy[] = {0, 0, 1, 0, -1}; // x ↑ → ↓ ←

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine()); // 테스트 케이스 수

        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken()); // 총 이동 시간
            A = Integer.parseInt(st.nextToken()); // BC의 개수

            da = new int[M];
            db = new int[M];

            // 사용자 A 이동정보 입력
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<M; i++) {
                da[i] = Integer.parseInt(st.nextToken());
            }

            // 사용자 B 이동정보 입력
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<M; i++) {
                db[i] = Integer.parseInt(st.nextToken());
            }

            // AP 정보 입력
            bcList = new ArrayList<>();
            for(int a=0; a<A; a++) {
                st = new StringTokenizer(br.readLine());

                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int p = Integer.parseInt(st.nextToken());

                bcList.add(new BC(y-1,x-1,c,p));
            }

            int ans = move();
            System.out.println("#" + t + " " + ans);
        }
    }

    static int move() {

        Pos a = new Pos(0, 0);
        Pos b = new Pos(9, 9);

        // 0초일 때의 합
        int sum = getMax(a, b);

        // 시간대별로 이동하면서 그때마다의 최댓값 합해준다.
        for(int t=0; t<M; t++) {
            a = new Pos(a.x+dx[da[t]], a.y+dy[da[t]]);
            b = new Pos(b.x+dx[db[t]], b.y+dy[db[t]]);

            sum += getMax(a, b);
        }
        return sum;
    }

    static int getMax(Pos a, Pos b) {
        // 2차원 배열에 사용자 A(0) 와 사용자 B(1)의 BC 별로 충전가능한 값을 저장
        int[][] charge = new int[2][A];

        // 사용자 A의 충전 가능한 BC의 값
        for(int j=0; j<A; j++) {
            charge[0][j] = check(a,j);
        }

        // 사용자 B의 충전 가능한 BC의 값
        for(int j=0; j<A; j++) {
            charge[1][j] = check(b,j);
        }

        // 사용자 A와 사용자 B의 충전량의 합 중 최댓값 구해준다.
        int max = 0;
        for(int i=0; i<A; i++) {
            for(int j=0; j<A; j++) {
                int sum = charge[0][i] + charge[1][j];

                // 같은 BC를 이용하는 경우 값을 반으로 나눠줘야 한다.
                // 주의할 점은 한 쪽은 아예 값이 0일수도 있으므로(해당 BC를 이용할 수 없는 위치)
                // 정확히 둘다 같이 이용하고 있는 경우에만 나누어준다.
                if(i==j && charge[0][i] == charge[1][j])
                    sum /= 2;

                max = Math.max(sum, max);
            }
        }
        return max;
    }

    static int check(Pos p, int apnum) {
        int x = Math.abs(p.x - bcList.get(apnum).x);
        int y = Math.abs(p.y - bcList.get(apnum).y);
        int dist = x+y;

        // 해당 BC에 포함되는 경우 p값을 리턴
        if(dist <= bcList.get(apnum).cover)
            return bcList.get(apnum).perf;
        else
            return 0;
    }
}
