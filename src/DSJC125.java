import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class DSJC125 {
    public static Random random = new Random();
    public ArrayList<Object_> data;

    public DSJC125() {
        String nomFichier = "DSJC125.5.txt";
        int nbSommet = 125;
        data = new ArrayList<>();
        for (int i = 1; i <= nbSommet; i++) {
            int randomValue = random.nextInt(41) + 10; // Génère un entier aléatoire entre 10 et 50
            data.add(new Object_(i,randomValue,new int[]{}));
        }



        try (InputStream inputStream = DSJC125.class.getClassLoader().getResourceAsStream(nomFichier);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("Le fichier " + nomFichier + " n'a pas pu être chargé.");
            }

            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(" ");

                if (parties.length == 3 && "e".equals(parties[0])) {
                    int chiffre1 = Integer.parseInt(parties[1]);
                    int chiffre2 = Integer.parseInt(parties[2]);

                    data.get(chiffre1-1).indice_incompatible.add(chiffre2);
                    data.get(chiffre2-1).indice_incompatible.add(chiffre1);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

