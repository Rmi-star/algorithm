package sort;

import java.util.ArrayList;

public class BinarySearch {
    public boolean searchFunc(ArrayList<Integer> dataList, Integer searchItem) {
        if(dataList.size() == 1 && searchItem == dataList.get(0)) {
            return true;
        }
        if(dataList.size() == 1 && searchItem != dataList.get(0)) {
            return false;
        }
        if(dataList.size() == 0) {
            return false;
        }

        // 중간값 지정
        int medium = dataList.size() / 2;

        // 찾으면 true
        if(searchItem == dataList.get(medium)) {
            return true;
        } else {
            if (searchItem < dataList.get(medium)) {
                // 중간값보다 작으면 중간값 기준 앞에서 비교
                return this.searchFunc(new ArrayList<Integer>(dataList.subList(0, medium)), searchItem);
            } else {
                // 중간값보다 크면 중간값 기준 뒤에서 비교
                return this.searchFunc(new ArrayList<Integer>(dataList.subList(medium, dataList.size())), searchItem);
            }
        }
    }
}
