import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        List<Object_> l = new ArrayList<>();
        /**/
        l.add(new Object_(1,6,new int[]{1,4}));
        l.add(new Object_(2,5,new int[]{}));
        l.add(new Object_(3,4,new int[]{1,8,8}));
        l.add(new Object_(4,3,new int[]{2}));
        l.add(new Object_(5,2,new int[]{}));
        l.add(new Object_(6,1,new int[]{22,2}));



        Object_ a = new Object_(1,6, new int[]{2});
        Object_ b = new Object_(2,6,new int[0]);


        Dsatur(l);




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
    public static String BestFitDecreasingPacking(List<Object_> liste, int tailleBoite) {
        ArrayList<Boite> listBoit = new ArrayList<>();
        listBoit.add(new Boite());

        for (Object_ o : liste) {
            Boite bestFitBoite = null;
            int minRemainingSpace = Integer.MIN_VALUE;

            // Parcourir les boîtes disponibles pour trouver la meilleure boîte
            for (Boite b : listBoit) {
                if (tailleBoite - b.remplissage >= o.taille && !b.isConflict(o)) {
                    int remainingSpace = tailleBoite - b.remplissage - o.taille;
                    if (remainingSpace > minRemainingSpace) {
                        minRemainingSpace = remainingSpace;
                        bestFitBoite = b;
                    }
                }
            }

            // Si une boîte appropriée est trouvée, ajouter l'objet
            if (bestFitBoite != null) {
                bestFitBoite.add(o);
            } else { // Sinon, créer une nouvelle boîte
                Boite boite = new Boite();
                boite.add(o);
                listBoit.add(boite);
            }
        }

        // Créer la chaîne de résultat
        StringBuilder res = new StringBuilder();
        for (Boite b : listBoit) {
            res.append(b.toString()).append("\n");
        }

        return res.toString();
    }

    public static List<Object_> Dsatur(List<Object_> liste){ // les sommets sont représentés par la classe Object_
        Map<Integer,Object_> res = new HashMap<>();             //initialisation
        List<Object_> liste_trier = new ArrayList<>(liste);
        for (int i = 0; i < liste_trier.size() - 1; i++) {      // trie par ordre décroissant des degrets
            for (int j = 0; j < liste_trier.size() - i - 1; j++) {
                if (liste_trier.get(j).indice_incompatible.size() < liste_trier.get(j + 1).indice_incompatible.size()) {
                    // Échangez les objets dans la liste si la taille de la liste_incompatible est plus petite
                    Object_ temp = liste_trier.get(j);
                    liste_trier.set(j, liste_trier.get(j + 1));
                    liste_trier.set(j + 1, temp);
                }
            }
        }
        Object_ etap2 = liste_trier.get(0);     // etape 2
        etap2.color = 1;
        res.put(etap2.indice, etap2);

        while (liste_trier.size() != 0){

        }

        liste_trier.remove(0);


        for (Object_ o: liste_trier ) {
            System.out.println(o.indice+" ");
        }


        return null;
    }

}
