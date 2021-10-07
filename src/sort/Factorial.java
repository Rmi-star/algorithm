package sort;

import java.util.ArrayList;

public class Factorial {

    // 기본적인 재귀함수 구조
//    public int factorialFun(int n) {
//        if(n > 1) {
//            return n * this.factorialFun(n-1);
//        } else {
//            return 1;
//        }
//    }

    // 숫자가 들어있는 배열이 주어졌을 때, 배열의 합 리턴(재귀사용)
//    public int factorialFun(ArrayList<Integer> dataList) {
//        if(dataList.size() <= 0) {
//            return 0;
//        } else {
//            return dataList.get(0) + this.factorialFun(new ArrayList<Integer>(dataList.subList(1, dataList.size())));
//        }
//    }

    //  정수 n이 입력으로 주어졌을 때, n을 1,2,3의 합으로 나타내는 방법의 수수
//    public int factorialFun(int data) {
//        if(data == 1) {
//            return 1;
//        } else if(data == 2) {
//            return 2;
//        } else if(data == 3) {
//            return 4;
//        }
//        return this.factorialFun(data-1) + this.factorialFun(data-2) + this.factorialFun(data-3);
//    }

    // 피보나치 수열
    public int factorialFun(int data) {
        if(data <= 1) {
            return data;
        }
        return this.factorialFun(data-1) + this.factorialFun(data-2);

    }

}
