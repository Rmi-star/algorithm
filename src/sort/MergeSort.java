package sort;

import java.util.ArrayList;

public class MergeSort {
    public ArrayList<Integer> mergeSplitFunc(ArrayList<Integer> dataList) {
        // 배열이 한개이면 리턴
        if(dataList.size() <= 1) {
            return dataList;
        }

        // 그렇지 않으면 분리
        int medium = dataList.size() / 2;
        ArrayList<Integer> leftArr = new ArrayList<Integer>();
        ArrayList<Integer> rightArr = new ArrayList<Integer>();

        leftArr = this.mergeSplitFunc(new ArrayList<Integer>(dataList.subList(0, medium))); // 0 부터 medium-1 인덱스 번호까지 해당 배열 아이템을 서브배열로 추출
        rightArr = this.mergeSplitFunc(new ArrayList<Integer>(dataList.subList(medium, dataList.size()))); // medium 부터 dataList-1 인덱스 번호까지 해당 배열 아이템을 서브배열로 추출

        return mergeFunc(leftArr, rightArr);
    }

    public ArrayList<Integer> mergeFunc(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
        ArrayList<Integer> mergedList = new ArrayList<Integer>();
        int leftPoint = 0;
        int rightPoint = 0;

        // CASE1 : left/right 둘다 있을 때 -> 하나가 먼저 끝까지 가면 반복문 끝
        while(leftList.size() > leftPoint && rightList.size() > rightPoint) {
            if(leftList.get(leftPoint) > rightList.get(rightPoint)) {
                mergedList.add(rightList.get(rightPoint));
                rightPoint += 1;
            } else {
                mergedList.add(leftList.get(leftPoint));
                leftPoint += 1;
            }
        }

        // CASE2 : right 데이터가 없을 때
        while (leftList.size() > leftPoint) {
            mergedList.add(leftList.get(leftPoint));
            leftPoint += 1;
        }
        // CASE3 : left 데이터가 없을 때
        while (rightList.size() > rightPoint) {
            mergedList.add(rightList.get(rightPoint));
            rightPoint += 1;
        }

        return mergedList;
    }
}
