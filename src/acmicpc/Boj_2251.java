package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 물통의 현재 상태와 물을 붓는 행위를 관리하는 구조체
class State {
    int[] X;
    State(int[] _X) {
        X = new int[3];
        for(int i=0; i<3; i++) X[i] = _X[i];
    }

    State move(int from, int to, int[] Limit) {
        //from 물통에서 to 물통으로 물을 옮긴다.
        int[] nX = new int[]{X[0], X[1], X[2]};

        if(X[from] + X[to] >= Limit[to]) {
            // to 가 가득찬 경우
            nX[from] -= Limit[to] - X[to];
            nX[to] = Limit[to];
        } else  {
            // from 이 먼저 다 떨어진 경우
            nX[to] += nX[from];
            nX[from] = 0;
        }

        return new State(nX);
    }
}

public class Boj_2251 {
    static int[] Limit;
    static boolean[] possible;
    static boolean[][][] visit;
    static StringBuilder sb = new StringBuilder();

    // 물통 탐색 시작
    static void bfs(int x1, int x2, int x3) {
        Queue<State> Q = new LinkedList<>();
        visit[x1][x2][x3] = true;
        Q.add(new State(new int[] {x1, x2, x3}));

        // BFS 과정 시작
        while (!Q.isEmpty()) {
            State st = Q.poll();
            if(st.X[0] == 0) possible[st.X[2]] = true;

            for(int from=0; from<3; from++) {
                for(int to=0; to<3; to++) {
                    // 같은 물통끼리는 물을 부울수 없기 떄문에
                    if(from == to) continue;
                    State next = st.move(from, to, Limit);

                    if(!visit[next.X[0]][next.X[1]][next.X[2]]) {
                        visit[next.X[0]][next.X[1]][next.X[2]] = true;
                        Q.add(next);
                    }
                }
            }
        }
    }

    static void pro() {
        bfs(0, 0, Limit[2]);

        // 정답 게산
        for(int i=0; i<=Limit[2]; i++) {
            if(possible[i]) sb.append(i).append(' ');
        }

        System.out.println(sb);
    }

    static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        StringTokenizer st = new StringTokenizer(input);

        Limit = new int[3];
        for(int i=0; i<3; i++) {
            Limit[i] = Integer.parseInt(st.nextToken());
        }
        visit = new boolean[205][205][205];
        possible = new boolean[205];
    }


    public static void main(String[] args) {
        try {
            input();
            pro();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
