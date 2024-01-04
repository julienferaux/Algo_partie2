import java.util.ArrayList;
import java.util.List;

public class Boite {
    public List<Object_> list = new ArrayList<>();
    public List<Integer> list_indice = new ArrayList<>();
    public int remplissage = 0;
    public Boite() {
    }

    @Override
    public String toString() {
        String res = "Boite{"+remplissage+", [";
        String virgul = "";
        for (Object_ o:list) {

            res+= virgul+o.indice +"-"+o.taille;
            virgul = ",";
        }
        res += "]}";
        return res;
    }

    public boolean isConflict(Object_ object){
        int indice = object.indice;
        boolean res = false;
        for (Object_ o: list) {
          if(o.isConflict(indice)){
              res = true;
              break;
          }
        }
        if(res == false){
            for (int i: object.indice_incompatible) {
                if (list_indice.contains(i)){
                    res = true;
                    break;
                }
            }
        }
        return res;
    }
    public void add(Object_ object){
        list.add(object);
        list_indice.add(object.indice);
        remplissage+= object.taille;
    }
}
