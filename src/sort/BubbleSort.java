package sort;

import java.util.ArrayList;
import java.util.Collections;

public class BubbleSort {
    public ArrayList<Integer> sort(ArrayList<Integer> dataList) {
        // 시간 복잡도 -> o(n²)
        for(int index=0; index<dataList.size()-1; index++) {
            // 자리 교환 여부 체크
            boolean swap = false;

            // 뒤에 부분은 정렬할수록 큰 수들이 위치하여 정렬할 필요가 없기 때문에 -index를 해줌.
            for(int index2=0; index2<dataList.size()-1-index; index2++) {
                if(dataList.get(index2)> dataList.get(index2+1)) {
                    Collections.swap(dataList, index2,index2+1);
                    swap = true;
                }
            }

            if(swap==false) {
                // 자리가 교환되지 않았으면 정렬이 다 된것으로 리턴해준다.
               break;
            }
        }

        return dataList;
    }
}
