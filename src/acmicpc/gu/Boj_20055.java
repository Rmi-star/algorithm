package acmicpc.gu;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 컨베이어 벨트 위의 로봇
    1. 벨트가 한 칸 회전 → 벨트 회전 시 로봇의 위치도 우측으로 이동하므로 로봇 내구도 감소 X
                      → 우측으로 1칸 이동
    2. 로봇이 한 칸 이동 → 로봇 배열이 비어있어야 하고, 내구도 1 이상이어야 함
    3. 로봇 올림 → 벨트 배열을 확인 후 로봇 올림
    4. IF 내구도가 0인 칸 발생시 k--
 */
public class Boj_20055 {
    static int N, K, left, right;
    static int[] belt;
    static boolean[] robot;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        belt = new int[N * 2]; // 벨트의 내구도
        robot = new boolean[N]; // 로봇 존재 여부

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < belt.length; i++) {
            belt[i] = Integer.parseInt(st.nextToken());
        }

        bw.write(simulation(0) + "\n");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int simulation(int cnt) {
        while (isOK()) {
            int temp = belt[belt.length - 1]; // 1. 벨트 한 칸 회전
            for (int i = belt.length - 1; i > 0; i--) {
                belt[i] = belt[i - 1];
            }
            belt[0] = temp;

            for (int i = robot.length - 1; i > 0; i--) {    // 로봇도 벨트와 같이 회전
                robot[i] = robot[i - 1];
            }
            robot[0] = false;
            robot[N - 1] = false;

            for (int i = N - 1; i > 0; i--) {   // 2. 로봇 이동가능하면 이동
                if (robot[i - 1] && !robot[i] && belt[i] >= 1) {
                    robot[i] = true;
                    robot[i - 1] = false;
                    belt[i]--;
                }
            }

            if (belt[0] > 0) {     // 3. 올라가는 위치에 로봇 올리기
                robot[0] = true;
                belt[0]--;
            }

            cnt++;
        }

        return cnt;
    }

    public static boolean isOK() {
        int cnt = 0;

        for (int i = 0; i < belt.length; i++) {
            if (belt[i] == 0) {
                cnt++;
            }
            if (cnt >= K) {
                return false;
            }
        }
        return true;
    }
}



