import java.util.ArrayList;

public class Object_ {
    public int indice;
    public int taille;
    public ArrayList<Integer> indice_incompatible =new ArrayList<>();
    public int color = -1;

    public Object_( int indice, int taille, int[] tab) {
        this.indice = indice;
        this.taille = taille;
        for (int i:tab) {
            this.indice_incompatible.add(i);
        }
    }
    public boolean isConflict(int indice){
        boolean res = false;
        if(indice_incompatible != null){
            for (Integer i: indice_incompatible) {
                if(i == indice){
                    res = true;
                    break;
                }
            }
        }
        return res;
    }
}
