package sort;

import java.util.ArrayList;
import java.util.Collections;

public class SelectionSort {
    public ArrayList<Integer> sort(ArrayList<Integer> dataList) {
        // 시간 복잡도 -> o(n²)
        int lowest; // 가장 작은 인덱스 기억
        for(int stand=0; stand< dataList.size()-1; stand++) {
            lowest = stand; // 순회 첫 번째 값이 가장 작다고 가정하고 시작
            for(int index=stand+1; index<dataList.size()-1; index++) {
                if(dataList.get(lowest) > dataList.get(index)) {
                    lowest = index;
                }
                Collections.swap(dataList, lowest, stand);
            }
        }
        return dataList;
    }
}
