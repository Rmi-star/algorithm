package acmicpc.gu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj_12100 {
    static int answer = 0;
    static void recursion(int k, int[][] board){
        if (k == 5) {
            // board에서 가장 큰 블록 값 찾기
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    answer = Math.max(answer, board[i][j]);
                }
            }
            return;
        }

        for(int i=1; i<=4; i++) {
            recursion(k + 1, moveBlock(deepCopy(board), i));
        }
    }

    static int[][] deepCopy(int[][] ary) {
        int[][] ret = new int[ary.length][ary.length];
        int i = 0;
        for (int[] _ary : ary) {
            System.arraycopy(_ary, 0, ret[i++], 0, ret.length);
        }
        return ret;
    }

    static int[][] moveBlock(int[][] board, int dir) {
        board = fillBlank(board, dir);
        switch (dir) {
            case 1: // 위아래로 같은 것들 더해주기
                for (int i = 0; i < board.length - 1; i++)
                    for (int j = 0; j < board.length; j++) {
                        int move = i + 1;
                        if (board[i][j] == board[move][j]) {
                            board[i][j] += board[move][j];
                            board[move][j] = 0;
                        }
                    }
                break;
            case 2: // 아래위로 같은 것들 더해주기
                for (int i = board.length - 1; i >= 1; i--)
                    for (int j = 0; j < board.length; j++) {
                        int move = i - 1;
                        if (board[i][j] == board[move][j]) {
                            board[i][j] += board[move][j];
                            board[move][j] = 0;
                        }
                    }
                break;
            case 3: // 왼->오로 같은 것들 더해주기
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board.length - 1; j++) {
                        int move = j + 1;
                        if (board[i][move] == board[i][j]) {
                            board[i][j] += board[i][move];
                            board[i][move] = 0;
                        }
                    }
                break;
            case 4:
                for (int i = 0; i < board.length; i++)
                    for (int j = board.length - 1; j > 0; j--) {
                        // 오른쪽으로 이동이므로 왼쪽 방향에서 0이 아닌 블럭 찾아서 이동시키기
                        int move = j - 1;
                        if (board[i][move] == board[i][j]) {
                            board[i][j] += board[i][move];
                            board[i][move] = 0;
                        }
                    }
                break;
        }
        return fillBlank(board, dir);
    }

    static int[][] fillBlank(int[][] board, int dir) {
        switch (dir) {
            case 1: // 위로 이동
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board.length; j++) {
                        if (board[i][j] == 0) {
                            // 위로 이동이므로 아래방향에서 0이 아닌 블럭 찾아서 이동시키기
                            int move = i + 1;
                            while (move < board.length) {
                                if (board[move][j] != 0) {
                                    board[i][j] = board[move][j];
                                    board[move][j] = 0;
                                    break;
                                }
                                move++;
                            }
                        }
                    }
                break;
            case 2: // 아래로 이동
                for (int i = board.length - 1; i >= 0; i--)
                    for (int j = 0; j < board.length; j++) {
                        // 아래로 이동이므로 위 방향에서 0이 아닌 블럭 찾아서 이동시키기
                        if (board[i][j] == 0) {
                            int move = i - 1;
                            while (move >= 0) {
                                if (board[move][j] != 0) {
                                    board[i][j] = board[move][j];
                                    board[move][j] = 0;
                                    break;
                                }
                                move--;
                            }
                        }
                    }
                break;
            case 3: // 왼쪽으로 이동
                for (int i = 0; i < board.length; i++)
                    for (int j = 0; j < board.length; j++) {
                        // 왼쪽으로 이동이므로 오른쪽 방향에서 0이 아닌 블럭 찾아서 이동시키기
                        if (board[i][j] == 0) {
                            int move = j + 1;
                            while (move < board.length) {
                                if (board[i][move] != 0) {
                                    board[i][j] = board[i][move];
                                    board[i][move] = 0;
                                    break;
                                }
                                move++;
                            }
                        }
                    }
                break;
            case 4:
                for (int i = 0; i < board.length; i++)
                    for (int j = board.length - 1; j > 0; j--) {
                        // 오른쪽으로 이동이므로 왼쪽 방향에서 0이 아닌 블럭 찾아서 이동시키기
                        if (board[i][j] == 0) {
                            int move = j - 1;
                            while (move >= 0) {
                                if (board[i][move] != 0) {
                                    board[i][j] = board[i][move];
                                    board[i][move] = 0;
                                    break;
                                }
                                move--;
                            }
                        }
                    }
                break;
        }
        return board;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] board = new int[N][N];

        // 보드 만들기
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        recursion(0, board);
        System.out.println(answer);
    }
}
