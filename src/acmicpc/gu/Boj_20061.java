package acmicpc.gu;

import java.io.*;
import java.util.*;

/*
 모노미노도미노2
 visited_blue[][]: 파란색 칸 (채워짐 - true, 안 채워짐 - false)
 visited_green[][]: 초록색 칸 (채워짐 - true, 안 채워짐 - false)

 fillBlue(): t에 따라 놓을 수 있는 가장 오른쪽 열을 찾고, 파란색 칸을 채운다.
 fillGreen(): t에 따라 놓을 수 있는 가장 아래쪽 행을 찾고, 초록색 칸을 채운다.
 checkBlue(): 2~5열 중 모두 채워진 열 list에 추가, 0~1열 사이에 블록이 하나라도 있으면 list에 추가
 checkGreen(): 2~5행 중 모두 채워진 행 list에 추가, 0~1행 사이에 블록이 하나라도 있으면 list에 추가
 moveBlue(): 오른쪽으로 한 열씩 옮기기
 moveGreen(): 아래쪽으로 한 행씩 옮기기
 count(): visited_blue, visited_green 중 true인 경우 cnt++

 */
public class Boj_20061 {
    static int N, t, x, y;
    static boolean[][] visited_blue = new boolean[6][6];
    static boolean[][] visited_green = new boolean[6][6];
    static int score = 0;
    static int cnt = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 블록 놓는 횟수

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            t = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());

            fillBlue(t, x, y);
            fillGreen(t, x, y);

            checkBlue();
            checkGreen();
        }

        count();

        bw.write(score + "\n");
        bw.write(cnt + "\n");
        bw.close();
        br.close();
    }

    // t에 따라 놓을 수 있는 가장 오른쪽 열을 찾고, 파란색 칸을 채운다.
    public static void fillBlue(int t, int x, int y) {
        if (t == 1) {
            int max = 0;
            for (int i = 0; i <= 5; i++) {
                if (!visited_blue[x][i]) {
                    max = i;
                } else {
                    break;
                }
            }
            visited_blue[x][max] = true;
        } else if (t == 2) {
            int max = 1;
            for (int i = 1; i <= 5; i++) {
                if (!visited_blue[x][i] && !visited_blue[x][i - 1]) {
                    max = i;
                } else {
                    break;
                }
            }
            visited_blue[x][max] = true;
            visited_blue[x][max - 1] = true;
        } else if (t == 3) {
            int max = 0;
            for (int i = 0; i <= 5; i++) {
                if (!visited_blue[x][i] && !visited_blue[x + 1][i]) {
                    max = i;
                } else {
                    break;
                }
            }
            visited_blue[x][max] = true;
            visited_blue[x + 1][max] = true;
        }
    }

    public static void fillGreen(int t, int x, int y) {
        if (t == 1) {
            int max = 0;
            for (int i = 0; i <= 5; i++) {
                if (!visited_green[i][y]) {
                    max = i;
                } else {
                    break;
                }
            }
            visited_green[max][y] = true;
        } else if (t == 2) {
            int max = 0;
            for (int i = 0; i <= 5; i++) {
                if (!visited_green[i][y] && !visited_green[i][y + 1]) {
                    max = i;
                } else {
                    break;
                }
            }
            visited_green[max][y] = true;
            visited_green[max][y + 1] = true;
        } else if (t == 3) {
            int max = 1;
            for (int i = 1; i <= 5; i++) {
                if (!visited_green[i][y] && !visited_green[i - 1][y]) {
                    max = i;
                } else {
                    break;
                }
            }
            visited_green[max][y] = true;
            visited_green[max - 1][y] = true;
        }
    }

    public static void checkBlue() {
        ArrayList<Integer> list = new ArrayList<>();

        // 2~5줄 확인
        for (int j = 5; j >= 2; j--) {
            int column = 0;
            for (int i = 0; i < 4; i++) {
                if (visited_blue[i][j]) {
                    column++;
                }
            }
            if (column == 4) { // 한줄이 채워진 경우
                list.add(j);
            }
        }
        if (list.size() > 0) {
            moveBlue(list);
        }

        list = new ArrayList<>();

        // 0~1줄 확인
        for (int j = 1; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (visited_blue[i][j]) {
                    list.add(j);
                    break;
                }
            }
        }
        if (list.size() > 0) {
            moveBlue(list);
        }
    }

    public static void checkGreen() {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 5; i >= 2; i--) {
            int row = 0;
            for (int j = 0; j < 4; j++) {
                if (visited_green[i][j]) {
                    row++;
                }
            }
            if (row == 4) {
                list.add(i);
            }
        }
        if (list.size() > 0) {
            moveGreen(list);
        }

        list = new ArrayList<>();

        for (int i = 1; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (visited_green[i][j]) {
                    list.add(i);
                    break;
                }
            }
        }
        if (list.size() > 0) {
            moveGreen(list);
        }
    }

    public static void moveBlue(ArrayList<Integer> list) {
        Collections.reverse(list);

        for (Integer li : list) {
            if (li > 1) { // 2~5인 경우
                for (int j = li; j > 0; j--) {
                    for (int i = 0; i < 4; i++) {
                        visited_blue[i][j] = visited_blue[i][j - 1];
                    }
                }
                for (int i = 0; i < 4; i++) {
                    visited_blue[i][0] = false;
                }
                score++;
            } else { // 0~1인 경우
                for (int j = 5; j > 0; j--) {
                    for (int i = 0; i < 4; i++) {
                        visited_blue[i][j] = visited_blue[i][j - 1];
                    }
                }
                for (int i = 0; i < 4; i++) {
                    visited_blue[i][0] = false;
                }
            }
        }
    }

    public static void moveGreen(ArrayList<Integer> list) {
        Collections.reverse(list);

        for (Integer li : list) {
            if (li > 1) { // 2~5인 경우
                for (int i = li; i > 0; i--) {
                    for (int j = 0; j < 4; j++) {
                        visited_green[i][j] = visited_green[i - 1][j];
                    }
                }
                for (int j = 0; j < 4; j++) {
                    visited_green[0][j] = false;
                }
                score++;
            } else {
                for (int i = 5; i > 0; i--) {
                    for (int j = 0; j < 4; j++) {
                        visited_green[i][j] = visited_green[i - 1][j];
                    }
                }
                for (int j = 0; j < 4; j++) {
                    visited_green[0][j] = false;
                }
            }
        }
    }

    public static void count() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (visited_blue[i][j]) {
                    cnt++;
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (visited_green[i][j]) {
                    cnt++;
                }
            }
        }
    }
}