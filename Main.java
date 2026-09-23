import javax.swing.SwingUtilities;
import gui.FenetreEtudiant;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FenetreEtudiant fenetre = new FenetreEtudiant();
                fenetre.setVisible(true);
            }
        });
    }
}