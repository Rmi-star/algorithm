package acmicpc.gu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/*
 새로운 게임2
 */
public class Boj_17837 {
    public static void main(String[] args) throws IOException {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st =new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 체스판의 크기
        int K = Integer.parseInt(st.nextToken()); // 말의 개수
        int [][]arr =new int[N+2][N+2];
        // 벗어나는 경우 파란색영역으로 설정
        for(int i =0; i<N+2; i++) {
            arr[i][0] = 2;
            arr[0][i] = 2;
            arr[N+1][i] = 2;
            arr[i][N+1] = 2;
        }
        // 체스판 정보
        Deque<Integer>[][]dq = new ArrayDeque[N+2][N+2];
        for(int i =1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j =1; j<=N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                dq[i][j]  =new ArrayDeque<Integer>();
            }
        }
        int[][]play = new int[K][3];
        // 말 정보 입력
        for(int i =0; i< K ;i++) {
            st =new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            play[i][0] = x;
            play[i][1] = y;
            play[i][2] = Integer.parseInt(st.nextToken());//→, ←, ↑, ↓의
            dq[x][y].addLast(i);
        }
        int [] di = new int[] {0,0,0,-1,1};
        int [] dj = new int[] {0,1,-1,0,0};
        int time = 1;
        loop:
        while(time<1001) {
            for(int i =0; i< K ;i++) {
                int x = play[i][0];
                int y = play[i][1];

                // 만역 가야할 값이 2라면(파란색 영역), 방향을 바꿔준다.
                if(arr[x+di[play[i][2]]][y+dj[play[i][2]]]==2) {
                    play[i][2] = play[i][2]==1?2:play[i][2]==2?1:play[i][2]==3?4:3;
                }
                int d = play[i][2]; // 방향
                int xx = x+di[d]; // x 좌표
                int yy = y+dj[d]; // y 좌표
                if(arr[xx][yy]==2) {
                    // 방향 바꾸고서도 2라면 그냥 건너뜀
                    continue;
                }
                // 값 이동
                switch(arr[xx][yy]) {
                    case 0: // 흰색이면
                        int size = dq[x][y].size();
                        boolean flag = false;
                        for(int s = 0; s< size; s++) {
                            // 밑에서 부터 쌓아준다.
                            int a = dq[x][y].pollFirst();
                            if(a==i)flag = true;
                            if(flag) {
                                dq[xx][yy].addLast(a);
                                play[a][0] = xx;
                                play[a][1]= yy;
                            }else {
                                dq[x][y].addLast(a);
                            }
                        }
                        break;
                    case 1: // 빨간색이면
                        // deque 를 stack 처럼 사용
                        while(!dq[x][y].isEmpty()) {
                            int a = dq[x][y].pollLast();
                            dq[xx][yy].addLast(a);
                            play[a][0] = xx;
                            play[a][1] =yy;
                            if(a==i)break;
                        }
                }
                if(dq[xx][yy].size()>3)break loop;
            }
            time++;
        }
        System.out.println(time==1001?-1:time);

    }
}
