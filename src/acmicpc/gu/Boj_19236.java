package acmicpc.gu;

import java.util.*;
import java.io.*;
/*
 청소년 상어

 1. 상어가 (0, 0) 부터 시작해 그 칸의 물고기를 잡아먹고 방향을 가진다.
 2. 이후 물고기가 이동하고, 다시 상어가 이동하고, 다시 물고기를 잡아먹고 이 과정이 계속해서 반복된다.
 3. 상어가 이동할 때, 상어는 상어의 방향 내에서 이동할 수 있는 칸 중 하나로 이동한다.
 4. 이 과정이 계속 반복되며 상어가 먹은 물고기 번호의 합의 최댓값을 출력해야 한다.
    → 즉, 상어가 어느 칸을 선택해 이동하느냐에 따라 여러 가지 경우의 수가 발생한다.
    → 상어가 움직인 여러 경우의 수를 다 따져보아 가장 최대로 물고기를 먹은 경우를 구해 그 총합을 구해야 한다.
    → 그러기 위해 모든 경우를 탐색해보는 완전 탐색 기법 중 하나인 DFS 깊이 우선 탐색을 이용해 구현한다.

 참고 : https://minhamina.tistory.com/67
 */
public class Boj_19236 {

    static class Fish {
        int num;
        int x;
        int y;
        int dir;
        int alive; //0 죽음, 1 살아있음

        Fish(int num, int x, int y, int dir, int alive) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.alive = alive;
        }
    }

    public static int[][] map; //전체 맵
    public static Fish[] fish; //물고기 정보 저장
    public static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1}; //상, 상좌, 좌, 좌하, 하, 하우, 우, 상우
    public static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
    public static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[4][4];
        fish = new Fish[17];
        for(int i = 0; i < 4; i++) {
            st  = new StringTokenizer(br.readLine(), " ");
            for(int j = 0; j < 4; j++) {
                int num = Integer.parseInt(st.nextToken()); // 물고기 번호
                int dir = Integer.parseInt(st.nextToken())-1; // 물고기 방향  - 방향이 0부터 시작하기 때문에 -1 (0~7)
                fish[num] = new Fish(num, i, j, dir, 1); // 물고기 배열에 저장 - 번호, x좌표, y좌표, 방향, 생사 여부(초반엔 다 살아있음 1)
                map[i][j] = num; //map에 물고기 번호 저장
            }
        }

        int sx = 0, sy = 0; //상어의 위치
        int sd = fish[map[0][0]].dir; //초기 상어의 방향
        int eat = map[0][0]; // 먹은 물고기 번호 합 저장 변수 - (0, 0) 물고기 먹음
        fish[map[0][0]].alive = 0; //(0, 0) 물고기 죽음
        map[0][0] = -1; //상어가 있는 위치 -1

        // 최대 물고기 번호합 구하기 위해 DFS 수행
        dfs(sx, sy, sd, eat);

        System.out.println(ans);
    }

    public static void dfs(int sx, int sy, int sd, int eat) {
        ans = Math.max(ans, eat); //이전에 먹었던 물고기 번호 합 max 비교해 ans에 저장

        /*

        DFS는 재귀를 통해 진행되기 때문에, 한 경우를 체크하고 그다음 경우를 체크하기 위해서 실행했던 내용을 취소함으로써 되돌려 놓는 과정이 필요하다.
        즉, 맵과 물고기의 상태를 기억해 놓고, 다시 이전의 상태로 되돌려 놓는 과정이 필요하다.

        ex) 상어가 1번 물고기를 먹었다.
        1번 물고기는 죽은 상태로 다른 물고기들은 이동하고, 상어 역시 이동한다. (상어가 물고기 먹고 -> 물고기 이동 -> 상어 이동 반복)
        1번 물고기를 먹은 경우의 수를 재귀를 통해 모두 훑어본 뒤, 2번 물고기를 먹은 경우의 수를 진행한다.
        이때 이미 1번 물고기 경우의 수를 알아보는 과정에서 물고기들이 다 이동해 초반의 맵 상태를 잃었고, 1번 물고기 역시 죽은 상태이다.
        그래서 상어가 물고기를 먹기 전의 맵의 상태, 물고기의 상태를 기억해 놓고 경우마다 기억했던 상태를 다시 되돌려 주는 과정이 필요하다.
        (같은 말 = 경우마다 어떤 물고기가 먹혔는지, 그때의 맵은 어떠했는지 기억해 놓고, 다른 물고기를 먹는 경우에는 이전 경우에서 기억한 물고기와 맵의 상태를 되돌려 주어야 한다는 것)
        -> DFS 내에서 맵과 물고기의 상태를 복사해 놓는다.

         */

        //map 배열 복사
        int[][] tempMap = new int[map.length][map.length];
        for(int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, tempMap[i], 0, map.length);
        }

        //fish 배열 복사
        Fish[] tempFish = new Fish[fish.length];
        for(int i = 1; i <= 16; i++)
            tempFish[i] = new Fish(fish[i].num, fish[i].x, fish[i].y, fish[i].dir, fish[i].alive);

        // 물고기 이동
        moveFish();

        // 상어 이동
        for(int i = 1; i < 4; i++) { //4*4 행렬로 1칸, 2칸, 3칸까지 최대로 이동 가능
            int nx = sx + dx[sd] * i;
            int ny = sy + dy[sd] * i;

            //경계를 벗어나지 않고, 물고기가 없는 빈칸이 아닐 경우
            if(nx >= 0 && nx < 4 && ny >= 0 && ny < 4 && map[nx][ny] != 0) {
                int eatFish = map[nx][ny];
                int nd = fish[eatFish].dir;
                map[sx][sy] = 0;
                map[nx][ny] = -1;
                fish[eatFish].alive = 0;

                dfs(nx, ny, nd, eat+eatFish);

                fish[eatFish].alive = 1; // 물고기 상태, 상어의 위치 원래대로 되돌리기
                map[sx][sy] = -1;
                map[nx][ny] = eatFish;
            }
        }

        // 맵 상태, 물고기 정보 되돌리기
        for(int j = 0; j < map.length; j++)
            System.arraycopy(tempMap[j], 0, map[j], 0, map.length);

        for(int i=1; i<=16; i++)
            fish[i] = new Fish(tempFish[i].num, tempFish[i].x, tempFish[i].y, tempFish[i].dir, tempFish[i].alive);
    }

    //물고기 이동
    public static void moveFish() {
        for(int i = 1; i < 17; i++) { //i는 현재 물고기의 번호
            if(fish[i].alive == 0) { //죽은 물고기라면 넘김
                continue;
            }

            int cnt = 0;
            int dir = fish[i].dir;//현재 i번째 물고기의 방향
            int nx = 0, ny = 0; //물고기가 이동할 칸의 x, y값

            while(cnt < 8) { //이동할 수 있는 위치를 찾을때까지 45도 방향 바꾸며 반복
                dir %= 8; //방향 +1로 범위 넘어가는 걸 처리하기 위한 나머지 연산
                fish[i].dir = dir; //방향 바꿨다면 바뀐 것 적용

                nx = fish[i].x + dx[dir]; //방향에 맞게 좌표 이동
                ny = fish[i].y + dy[dir];

                //이동할 위치에 상어가 없고, 경계를 넘지 않는다면 이동 가능
                if(nx >= 0 && nx < 4 && ny >= 0 && ny < 4 && map[nx][ny] != -1) {
                    if(map[nx][ny] == 0) { //이동할 위치가 빈칸일 경우
                        map[fish[i].x][fish[i].y] = 0; //기존 위치 빈칸으로
                        fish[i].x = nx;
                        fish[i].y = ny;
                        map[nx][ny] = i;
                    } else { //이동할 위치에 다른 물고기가 있을 경우
                        // 바꿀 물고기 위치 변경
                        int changeFish = fish[map[nx][ny]].num;
                        fish[changeFish].x = fish[i].x;
                        fish[changeFish].y = fish[i].y;
                        map[fish[changeFish].x][fish[changeFish].y] = changeFish;

                        //현재 물고기 위치 변경
                        fish[i].x = nx;
                        fish[i].y = ny;
                        map[nx][ny] = i;
                    }
                    break;
                } else {
                    dir++;
                    cnt++;
                }
            }
        }
    }

}