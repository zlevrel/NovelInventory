package zoupyote.novelinventory.NovelCompator;

import zoupyote.novelinventory.Novel;

public class NovelComparatorByName implements java.util.Comparator {

    public int compare(Object o1, Object o2){
        if(o1 instanceof Novel && o2 instanceof Novel){
            Novel n1 = (Novel)o1;
            Novel n2 = (Novel)o2;
            if(n1.getName().compareTo(n2.getName())<0){
                return -1;
            }else return 1;
        }else return 0;
    }
}
