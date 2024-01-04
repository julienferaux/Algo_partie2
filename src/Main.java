import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        List<Object_> l = new ArrayList<>();
        /**/
        l.add(new Object_(1,6,new int[]{}));
        l.add(new Object_(2,5,new int[]{6}));
        l.add(new Object_(3,4,new int[]{}));
        l.add(new Object_(4,4,new int[]{}));
        l.add(new Object_(5,2,new int[]{3}));
        l.add(new Object_(6,1,new int[]{}));



        Object_ a = new Object_(1,6, new int[]{2});
        Object_ b = new Object_(2,6,new int[0]);


        System.out.println(FirstFitDecreasingPacking(l,6));
    }

    public static int fractionalPacking(List<Object_> liste, int tailleBoite){
        int sommeTailleBoite = 0;
        for ( Object_ o: liste) {
            sommeTailleBoite+=o.taille;
        }
        return (sommeTailleBoite / tailleBoite)+1;
    }

    public static String FirstFitDecreasingPacking(List<Object_> liste, int tailleBoite){ // liste d'object trié décroissant par hi
        ArrayList<Boite> listBoit = new ArrayList<>();
        listBoit.add(new Boite());
        for (Object_ o: liste) {
            for (int i = 0; i< listBoit.size();i++) {
                Boite b = listBoit.get(i);
                if(tailleBoite - b.remplissage >= o.taille && b.isConflict(o) == false){
                    b.add(o);
                    break;
                }else if(i+1 == listBoit.size()){
                    Boite boite = new Boite();
                    boite.add(o);
                    listBoit.add(boite);
                    break;
                }
            }
        }
        String res ="";
        for (Boite b : listBoit) {
            res += b.toString()+"\n";
        }
        return res;
    }



}
