package sort;

import java.util.ArrayList;
import java.util.Collections;

public class InsertionSort {
    public ArrayList<Integer> sort(ArrayList<Integer> dataList) {
        // 시간 복잡도 -> o(n²)
        for(int index=0; index< dataList.size()-1; index++) {
            for(int index2=index+1; index2>0; index2--) {
                if(dataList.get(index2)<dataList.get(index2-1)) {
                    Collections.swap(dataList, index2, index2-1);
                } else {
                    // index2-1 보다 크면 break;
                    break;
                }
            }
        }
        return dataList;
    }
}
