package model;

public class Etudiant {
    private String matricule;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String email;
    private String telephone;
    private String parcours;
    private String anneeUniversitaire;
    private String statut;

    public Etudiant(String matricule, String nom, String prenom, String dateNaissance, 
                    String email, String telephone, String parcours, 
                    String anneeUniversitaire, String statut) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.parcours = parcours;
        this.anneeUniversitaire = anneeUniversitaire;
        this.statut = statut;
    }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getParcours() { return parcours; }
    public void setParcours(String parcours) { this.parcours = parcours; }

    public String getAnneeUniversitaire() { return anneeUniversitaire; }
    public void setAnneeUniversitaire(String anneeUniversitaire) { this.anneeUniversitaire = anneeUniversitaire; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return nom + " " + prenom + " (" + matricule + ")";
    }
}