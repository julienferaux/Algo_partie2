import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {

        /*
        l.add(new Object_(1,5,new int[]{2}));
        l.add(new Object_(2,5,new int[]{1,3,5,4}));
        l.add(new Object_(3,4,new int[]{2}));
        l.add(new Object_(4,1,new int[]{5,2,6}));
        l.add(new Object_(5,2,new int[]{2,4}));
        l.add(new Object_(6,1,new int[]{4}));
        */

        DSJC125 fichier = new DSJC125();
        List<Object_> l = fichier.data;


        List<Object_> res = Dsatur(l);

        System.out.println(DsaturWithFFDpacking(res,150));
          //      System.out.println("-------------------");
        System.out.println(DsaturWithBFDpacking(res,7));


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
        liste_trier.remove(0);

        while (liste_trier.size() != 0){
            Object_ sommetAcolorier = null;
            int saturation_maxima = -1;
            int degre_S = -1;
            ArrayList<Integer> couleurVoisin_de_S =  new ArrayList<>();

            for (int i = 0; i < liste_trier.size(); i++) {
                Object_ tmp = liste_trier.get(i);
                int degre_tmp = 0;
                ArrayList<Integer> couleurVoisin_de_tmp = new ArrayList<>();
                for (int indice: tmp.indice_incompatible) {
                    Object_ voisin = res.get(indice);
                    if (voisin != null){
                        if (!(couleurVoisin_de_tmp.contains(voisin.color))){
                            couleurVoisin_de_tmp.add(voisin.color);
                        }
                        degre_tmp+=1;
                    }
                }
                if (saturation_maxima < couleurVoisin_de_tmp.size() ||
                        (saturation_maxima == couleurVoisin_de_tmp.size() && degre_S < degre_tmp)) {// inférieur à la size de listecouleur
                    saturation_maxima = couleurVoisin_de_tmp.size();
                    couleurVoisin_de_S = couleurVoisin_de_tmp;
                    degre_S = degre_tmp;
                    sommetAcolorier = tmp;
                }
            }
            sommetAcolorier.color = 1;
            for (int i = 1; i <=couleurVoisin_de_S.size()+1 ; i++) {
                if(!couleurVoisin_de_S.contains(i)){
                    sommetAcolorier.color = i;
                    break;
                }
            }
            res.put(sommetAcolorier.indice,sommetAcolorier);
            liste_trier.remove(sommetAcolorier);
        }
        return new ArrayList<>(res.values());
    }

    public static String DsaturWithFFDpacking(List<Object_> liste, int tailleBoite){
        HashMap<Integer,ArrayList<Boite>> map_listBoit = new HashMap<>(); // une liste de boite par couleur (couleur est un entier)
        for (Object_ o: liste) {
            int couleur = o.color;
            if(!map_listBoit.containsKey(couleur)){
                ArrayList<Boite> tmp = new ArrayList<>();
                tmp.add(new Boite());
                map_listBoit.put(couleur,tmp);
            }
            ArrayList<Boite> listBoit = map_listBoit.get(couleur);
            for (int i = 0; i< listBoit.size();i++) {
                Boite b = listBoit.get(i);
                if(tailleBoite - b.remplissage >= o.taille){
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
        int nb_boite = 0;
        for (ArrayList<Boite>listBoit : map_listBoit.values() ) {
            for (Boite b : listBoit) {
                res += b.toString()+"\n";
                nb_boite++;
            }
        }

        return res +"\n   "+nb_boite+ " boites";
    }

    public static String DsaturWithBFDpacking(List<Object_> liste, int tailleBoite) {
        HashMap<Integer,ArrayList<Boite>> map_listBoit = new HashMap<>(); // une liste de boite par couleur (couleur est un entier)
        for (Object_ o : liste) {
            int couleur = o.color;
            if(!map_listBoit.containsKey(couleur)){
                ArrayList<Boite> tmp = new ArrayList<>();
                tmp.add(new Boite());
                map_listBoit.put(couleur,tmp);
            }
            ArrayList<Boite> listBoit = map_listBoit.get(couleur);

            Boite bestFitBoite = null;
            int minRemainingSpace = Integer.MIN_VALUE;

            // Parcourir les boîtes disponibles pour trouver la meilleure boîte
            for (Boite b : listBoit) {
                if (tailleBoite - b.remplissage >= o.taille) {
                    int remainingSpace = tailleBoite - b.remplissage - o.taille;
                    if (remainingSpace > minRemainingSpace) {
                        minRemainingSpace = remainingSpace;
                        bestFitBoite = b;
                    }
                }
            }

            // Si une boite appropriée est trouvee, ajouter lobjet
            if (bestFitBoite != null) {
                bestFitBoite.add(o);
            } else { // Sinon crer une nouvelle boite
                Boite boite = new Boite();
                boite.add(o);
                listBoit.add(boite);
            }
        }

        // Creer la chaine de resultat
        int nb_boite = 0;
        StringBuilder res = new StringBuilder();
        for (ArrayList<Boite>listBoit : map_listBoit.values() ) {
            for (Boite b : listBoit) {
                res.append(b.toString()).append("\n");
                nb_boite++;
            }
        }
        res.append("\n   "+nb_boite+ " boites");
        return res.toString();
    }


}
