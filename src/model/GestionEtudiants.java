package model;

import java.util.ArrayList;
import java.util.List;

public class GestionEtudiants {
    private List<Etudiant> listeEtudiants;

    public GestionEtudiants() {
        this.listeEtudiants = new ArrayList<>();
    }

    public void ajouterEtudiant(Etudiant e) {
        listeEtudiants.add(e);
    }

    public boolean supprimerEtudiant(String matricule) {
        return listeEtudiants.removeIf(e -> e.getMatricule().equalsIgnoreCase(matricule));
    }

    public List<Etudiant> getListeEtudiants() {
        return listeEtudiants;
    }

    public List<Etudiant> rechercher(String matricule, String nom, String parcours, String statut) {
        List<Etudiant> resultats = new ArrayList<>();
        for (Etudiant e : listeEtudiants) {
            boolean matchMatricule = matricule.isEmpty() || e.getMatricule().toLowerCase().contains(matricule.toLowerCase());
            boolean matchNom = nom.isEmpty() || e.getNom().toLowerCase().contains(nom.toLowerCase()) || e.getPrenom().toLowerCase().contains(nom.toLowerCase());
            boolean matchParcours = parcours.isEmpty() || parcours.equals("-- Tous --") || e.getParcours().equalsIgnoreCase(parcours);
            boolean matchStatut = statut.isEmpty() || statut.equals("-- Tous --") || e.getStatut().equalsIgnoreCase(statut);

            if (matchMatricule && matchNom && matchParcours && matchStatut) {
                resultats.add(e);
            }
        }
        return resultats;
    }
}