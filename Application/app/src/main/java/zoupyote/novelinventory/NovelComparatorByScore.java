package zoupyote.novelinventory;

public class NovelComparatorByScore implements java.util.Comparator {

    public int compare(Object o1, Object o2){
        if(o1 instanceof Novel && o2 instanceof Novel){
            Novel n1 = (Novel)o1;
            Novel n2 = (Novel)o2;
            int i1 = getNumberScore(n1), i2 = getNumberScore(n2);
            if(i1 < i2){
                return 1;
            }else if(i1 > i2){
                return -1;
            } else return 0;
        }else return 0;
    }

    public int getNumberScore(Novel n) {
        int result;
        switch(n.getScoreString()){
            case "Mauvais" : result = 0; break;
            case "Bof" : result = 1; break;
            case "Pas mal" : result = 2; break;
            case "Bien" : result=3; break;
            case "Très bien" : result=4; break;
            case "Coup de cœur" : result= 5; break;
            default : result=-1;
        }
        return result;
    }
}
