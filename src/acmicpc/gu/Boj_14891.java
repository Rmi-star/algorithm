package acmicpc.gu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj_14891 {
    static int[][] wheelArr = new int[4][8];
    static int K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 톱니바퀴 4개 입력
        for (int i = 0; i < 4; i++) {
            String s = br.readLine();
            int r = 0, l = 0;
            for (int j = 0; j < 8; j++) {
                wheelArr[i][j] = s.charAt(j) - '0';
            }
        }

        K = Integer.parseInt(br.readLine().trim());

        StringTokenizer st;
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            solution(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));
        }

        // 마무리 계산
        int total = 0;
        if (wheelArr[0][0] == 1) {
            total += 1;
        }
        if (wheelArr[1][0] == 1) {
            total += 2;
        }
        if (wheelArr[2][0] == 1) {
            total += 4;
        }
        if (wheelArr[3][0] == 1) {
            total += 8;
        }
        System.out.println(total);
    }

    // 9시 방향은 2, 3시 방향은 6
    static void solution(int idx, int dir) {
        left(idx - 1, -dir);
        right(idx + 1, -dir);
        rotate(idx, dir);
    }

    // 왼쪽에 있던 톱니바퀴 회전 여부 결정
    static void left(int idx, int dir) {
        if (idx < 0)
            return;

        if (wheelArr[idx][2] != wheelArr[idx + 1][6]) {
            left(idx - 1, -dir);
            rotate(idx, dir);
        }
    }

    // 오른쪽에 있던 톱니바퀴 회전 여부 결정
    static void right(int idx, int dir) {
        if (idx > 3)
            return;

        if (wheelArr[idx][6] != wheelArr[idx - 1][2]) {
            right(idx + 1, -dir);
            rotate(idx, dir);
        }
    }

    // dir = 1 시계방향, dir = -1 반시계방향
    static void rotate(int idx, int dir) {
        if (dir == 1) {
            int temp = wheelArr[idx][7];

            for (int i = 7; i > 0; i--) {
                wheelArr[idx][i] = wheelArr[idx][i - 1];
            }

            wheelArr[idx][0] = temp;

        } else {
            int temp = wheelArr[idx][0];

            for (int i = 0; i < 7; i++) {
                wheelArr[idx][i] = wheelArr[idx][i + 1];
            }

            wheelArr[idx][7] = temp;
        }
    }
}
