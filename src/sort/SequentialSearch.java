package sort;

import java.util.ArrayList;

public class SequentialSearch {
    public int searchFunc(ArrayList<Integer> dataList, Integer searchItem) {
        for(int index=0; index< dataList.size(); index++) {
            // 일치하면 인덱스번호 리턴
            if(dataList.get(index) == searchItem) {
                return index;
            }
        }
        // 없으면 -1 리턴
        return -1;
    }
}
