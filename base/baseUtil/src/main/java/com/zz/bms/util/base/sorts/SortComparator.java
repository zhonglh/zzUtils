package com.zz.bms.util.base.sorts;

import java.util.Comparator;

public class SortComparator implements Comparator<Object> {




    @Override
    public int compare(Object obj1,Object obj2){
        if(obj1 == null && obj2 == null) {
            return 0;
        }
        else if(obj1 == null) {
            return 1;
        }
        else if(obj2 == null) {
            return -1;
        }
        else {
            if(obj1 instanceof ISort && obj2 instanceof ISort){
                return ((ISort)obj2).getSortKey().compareTo( ((ISort)obj1).getSortKey());
            }else {
                return obj2.toString().compareTo(obj1.toString());
            }
        }


    }
}
