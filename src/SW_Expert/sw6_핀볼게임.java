package SW_Expert;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 - 각 블록별로 들어온 방향에 따른 나가는 방향을 분기
 - 웜홀을 페어로 관리하여 바로바로 이동시켜주기
 - 벽에 부딪히는 경우(맵을 벗어나는 경우) 좌표를 바꾸지 않고 방향만 바꾸면
   벽 바로 앞에 0이 아닌 다른 것이 있는 경우 무시되어 올바르게 작동하지 않음
   따라서 좌표는 벽 밖으로 나갔다가 다시 들어오게 해야함

* 참고 : https://velog.io/@hyeon930/SWEA-5650-%ED%95%80%EB%B3%BC-%EA%B2%8C%EC%9E%84-Java
 */
public class sw6_핀볼게임 {
    static class Node {
        int r, c, d;

        Node(int r, int c){
            this.r = r;
            this.c = c;
        }

        Node(int r, int c, int d){
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int[][] wormhole;
    static int[][] map;
    static int N, T, ans;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        T = Integer.parseInt(br.readLine());

        for(int t = 1 ; t <= T ; ++t) {
            N = Integer.parseInt(br.readLine());

            map = new int[N][N];
            wormhole = new int[11][4];
            ans = 0;

            for(int r = 0 ; r < 11 ; ++r) {
                for(int c = 0 ; c < 4 ; ++c) {
                    wormhole[r][c] = -1;
                }
            }

            for(int r = 0 ; r < N ; ++r) {
                st = new StringTokenizer(br.readLine());
                for(int c = 0 ; c < N ; ++c) {
                    map[r][c] = Integer.parseInt(st.nextToken());

                    if(map[r][c] > 5) {

                        if(wormhole[map[r][c]][0] == -1) {
                            wormhole[map[r][c]][0] = r;
                            wormhole[map[r][c]][1] = c;
                        } else {
                            wormhole[map[r][c]][2] = r;
                            wormhole[map[r][c]][3] = c;
                        }
                    }
                }
            }

            for(int r = 0 ; r < N ; ++r) {
                for(int c = 0 ; c < N ; ++c) {
                    if(map[r][c] == 0) {
                        for(int d = 0 ; d < 4 ; ++d) {
                            Node ball = new Node(r, c, d);
                            int score = go(ball);
                            ans = score > ans ? score : ans;
                        }
                    }
                }
            }

            System.out.println("#" + t + " " + ans);
        }

    }

    private static int go(Node start) {
        int score = 0;
        int nr = start.r;
        int nc = start.c;
        int d = start.d;

        while(true) {
            nr += dir[d][0];
            nc += dir[d][1];

            // 벽에 부딫힌 경우
            if(nr < 0 || nr >= N || nc < 0 || nc >= N) {
                score++;
                d = (d + 2) % 4;
                continue;
            }

            // 블랙홀 또는 제자리로 돌아온 경우
            if(nr == start.r && nc == start.c) return score;

            int type = map[nr][nc];

            switch(type) {
                case -1:
                    return score;
                case 0:
                    break;
                case 1:
                    score++;
                    if(d == 0 || d == 1) d = (d + 2) % 4;
                    else if(d == 2) d = 1;
                    else if(d == 3) d = 0;
                    break;
                case 2:
                    score++;
                    if(d == 1 || d == 2) d = (d + 2) % 4;
                    else if(d == 3) d = 2;
                    else if(d == 0) d = 1;
                    break;
                case 3:
                    score++;
                    if(d == 2 || d == 3) d = (d + 2) % 4;
                    else if(d == 0) d = 3;
                    else if(d == 1) d = 2;
                    break;
                case 4:
                    score++;
                    if(d == 0 || d == 3) d = (d + 2) % 4;
                    else if(d == 1) d = 0;
                    else if(d == 2) d = 3;
                    break;
                case 5:
                    score++;
                    d = (d + 2) % 4;
                    break;
                default:
                    if(wormhole[type][0] == nr && wormhole[type][1] == nc) {
                        nr = wormhole[type][2];
                        nc = wormhole[type][3];
                    } else {
                        nr = wormhole[type][0];
                        nc = wormhole[type][1];
                    }
                    break;
            }
        }
    }
}
