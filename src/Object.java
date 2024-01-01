import java.util.ArrayList;

public class Object {
    public int indice;
    public int taille;
    public ArrayList<Integer> indice_incompatible;

    public Object(int indice, int taille, ArrayList<Integer> indice_incompatible) {
        this.indice = indice;
        this.taille = taille;
        this.indice_incompatible = indice_incompatible;
    }
}
