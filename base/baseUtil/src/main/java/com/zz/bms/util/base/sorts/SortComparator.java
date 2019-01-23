package com.zz.bms.util.base.sorts;

import java.util.Comparator;

public class SortComparator<T> implements Comparator<T> {





    @Override
    public int compare(T obj1,T obj2){
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
