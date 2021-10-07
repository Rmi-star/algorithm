package sort;

import java.util.ArrayList;
import java.util.Arrays;

public class QuickSort  {
    public ArrayList<Integer> sort(ArrayList<Integer> dataList) {
        // 하나일 때 dataList 리턴
        if(dataList.size() <= 1) {
            return dataList;
        }

        int pivot = dataList.get(0);
        ArrayList<Integer> leftArr = new ArrayList<Integer>();
        ArrayList<Integer> rightArr = new ArrayList<Integer>();

        // pivot 기준으로 작으면 leftArr 에 크면 rightArr 로
        for(int index=1; index<dataList.size(); index++) {
            if(dataList.get(index) > pivot) {
                rightArr.add(dataList.get(index));
            } else {
                leftArr.add(dataList.get(index));
            }
        }

        // 재귀
        ArrayList<Integer> mergedArr = new ArrayList<Integer>();
        // pivot 기준으로 나누고 정렬을 데이터가 한개가 될 때까지 반복하고, 병합
        mergedArr.addAll(this.sort(leftArr));
        mergedArr.addAll(Arrays.asList(pivot)); // 배열로 들어가야함
        mergedArr.addAll(this.sort(rightArr));

        return mergedArr;
    }
}
