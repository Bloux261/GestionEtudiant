package gui;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Etudiant;
import model.GestionEtudiants;

public class FenetreEtudiant extends JFrame {

    private GestionEtudiants gestionnaire;

    private JTextField txtSearchMatricule, txtSearchNom;
    private JComboBox<String> cbSearchParcours, cbSearchStatut;

    private JTextField txtMatricule, txtNom, txtPrenom, txtEmail, txtTel;
    private JSpinner spinnerDateNaiss;
    private JComboBox<String> cbParcours, cbAnnee, cbStatut;

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotalCount, lblAffichageCount;

    private final Color COLOR_PRIMARY = new Color(13, 110, 253);
    private final Color COLOR_SUCCESS = new Color(40, 167, 69);
    private final Color COLOR_WARNING = new Color(255, 193, 7);
    private final Color COLOR_DANGER = new Color(220, 53, 69);
    private final Color COLOR_BG = new Color(248, 249, 250);
    private final Color COLOR_BORDER = new Color(222, 226, 230);

    public FenetreEtudiant() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        gestionnaire = new GestionEtudiants();

        setTitle("Gestion des Étudiants");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout(12, 12));
        contentPane.setBackground(COLOR_BG);
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(contentPane);

        contentPane.add(creerPanneauRecherche(), BorderLayout.NORTH);
        contentPane.add(creerPanneauFormulaire(), BorderLayout.WEST);
        contentPane.add(creerPanneauTableau(), BorderLayout.CENTER);

        chargerDonneesTest();
        actualiserTableau(gestionnaire.getListeEtudiants());
    }

    private JButton creerBouton(String texte, Color bg, Color fg) {
        JButton btn = new JButton(texte);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
            new LineBorder(bg, 1),
            new EmptyBorder(6, 12, 6, 12)
        ));
        return btn;
    }

    private void styliserComposant(JComponent comp) {
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comp.setBackground(Color.WHITE);
        comp.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDER, 1),
            new EmptyBorder(4, 6, 4, 6)
        ));
    }

    private JPanel creerPanneauRecherche() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDER, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));

        JLabel lblTitle = new JLabel("Recherche d'étudiant");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(COLOR_PRIMARY);

        txtSearchMatricule = new JTextField(8);
        styliserComposant(txtSearchMatricule);

        txtSearchNom = new JTextField(10);
        styliserComposant(txtSearchNom);

        String[] parcoursOptions = {"-- Tous --", "Informatique", "Réseaux", "Génie Logiciel", "Systèmes Embarqués"};
        cbSearchParcours = new JComboBox<>(parcoursOptions);
        styliserComposant(cbSearchParcours);

        String[] statutOptions = {"-- Tous --", "Actif", "Inactif"};
        cbSearchStatut = new JComboBox<>(statutOptions);
        styliserComposant(cbSearchStatut);

        JButton btnRechercher = creerBouton(" Rechercher", COLOR_PRIMARY, Color.WHITE);
        JButton btnReinitialiser = creerBouton("Réinitialiser", Color.WHITE, Color.BLACK);
        btnReinitialiser.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDER, 1), new EmptyBorder(6, 12, 6, 12)));

        panel.add(lblTitle);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(new JLabel("Matricule :"));
        panel.add(txtSearchMatricule);
        panel.add(new JLabel("Nom :"));
        panel.add(txtSearchNom);
        panel.add(new JLabel("Parcours :"));
        panel.add(cbSearchParcours);
        panel.add(new JLabel("Statut :"));
        panel.add(cbSearchStatut);
        panel.add(btnRechercher);
        panel.add(btnReinitialiser);

        btnRechercher.addActionListener(e -> rechercher());
        btnReinitialiser.addActionListener(e -> {
            txtSearchMatricule.setText("");
            txtSearchNom.setText("");
            cbSearchParcours.setSelectedIndex(0);
            cbSearchStatut.setSelectedIndex(0);
            actualiserTableau(gestionnaire.getListeEtudiants());
        });

        return panel;
    }

    private JPanel creerPanneauFormulaire() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(320, 0));
        panel.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDER, 1),
            new EmptyBorder(14, 14, 14, 14)
        ));

        JLabel lblTitle = new JLabel("Formulaire Étudiant");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(COLOR_PRIMARY);

        JPanel formFields = new JPanel(new GridBagLayout());
        formFields.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 2, 6, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMatricule = new JTextField(12);
        txtNom = new JTextField(12);
        txtPrenom = new JTextField(12);
        txtEmail = new JTextField(12);
        txtTel = new JTextField(12);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerDateNaiss = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerDateNaiss, "dd/MM/yyyy");
        spinnerDateNaiss.setEditor(dateEditor);

        styliserComposant(txtMatricule);
        styliserComposant(txtNom);
        styliserComposant(txtPrenom);
        styliserComposant(spinnerDateNaiss);
        styliserComposant(txtEmail);
        styliserComposant(txtTel);

        String[] parcoursOptions = {"-- Sélectionner --", "Informatique", "Réseaux", "Génie Logiciel", "Systèmes Embarqués"};
        cbParcours = new JComboBox<>(parcoursOptions);
        styliserComposant(cbParcours);

        String[] anneeOptions = {"2024 - 2025", "2023 - 2024", "2022 - 2023"};
        cbAnnee = new JComboBox<>(anneeOptions);
        styliserComposant(cbAnnee);

        String[] statutOptions = {"Actif", "Inactif"};
        cbStatut = new JComboBox<>(statutOptions);
        styliserComposant(cbStatut);

        int row = 0;
        ajouterChampFormulaire(formFields, gbc, "Matricule :", txtMatricule, row++);
        ajouterChampFormulaire(formFields, gbc, "Nom :", txtNom, row++);
        ajouterChampFormulaire(formFields, gbc, "Prénom :", txtPrenom, row++);
        ajouterChampFormulaire(formFields, gbc, "Date de naissance :", spinnerDateNaiss, row++);
        ajouterChampFormulaire(formFields, gbc, "Email :", txtEmail, row++);
        ajouterChampFormulaire(formFields, gbc, "Téléphone :", txtTel, row++);
        ajouterChampFormulaire(formFields, gbc, "Parcours :", cbParcours, row++);
        ajouterChampFormulaire(formFields, gbc, "Année universitaire :", cbAnnee, row++);
        ajouterChampFormulaire(formFields, gbc, "Statut :", cbStatut, row++);

        JPanel pnlBoutons = new JPanel(new GridLayout(2, 3, 6, 6));
        pnlBoutons.setBackground(Color.WHITE);

        JButton btnNouveau = creerBouton("Nouveau", COLOR_SUCCESS, Color.WHITE);
        JButton btnEnregistrer = creerBouton("Enregistrer", COLOR_PRIMARY, Color.WHITE);
        JButton btnModifier = creerBouton("Modifier", COLOR_WARNING, Color.WHITE);
        JButton btnSupprimer = creerBouton("Supprimer", COLOR_DANGER, Color.WHITE);
        JButton btnAnnuler = creerBouton("Annuler", Color.WHITE, Color.BLACK);
        btnAnnuler.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDER, 1), new EmptyBorder(6, 6, 6, 6)));

        pnlBoutons.add(btnNouveau);
        pnlBoutons.add(btnEnregistrer);
        pnlBoutons.add(new JLabel(""));
        pnlBoutons.add(btnModifier);
        pnlBoutons.add(btnSupprimer);
        pnlBoutons.add(btnAnnuler);

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(formFields, BorderLayout.CENTER);
        panel.add(pnlBoutons, BorderLayout.SOUTH);

        btnNouveau.addActionListener(e -> viderChamps());
        btnAnnuler.addActionListener(e -> viderChamps());
        btnEnregistrer.addActionListener(e -> ajouterEtudiant());
        btnModifier.addActionListener(e -> modifierEtudiant());
        btnSupprimer.addActionListener(e -> supprimerEtudiant());

        return panel;
    }

    private void ajouterChampFormulaire(JPanel panel, GridBagConstraints gbc, String label, Component comp, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.4;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        panel.add(comp, gbc);
    }

    private JPanel creerPanneauTableau() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDER, 1),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTitle = new JLabel(" Liste des Étudiants");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(217, 119, 6));

        String[] colonnes = {"Matricule", "Nom", "Prénom", "Date naissance", "Email", "Parcours", "Année Univ.", "Statut"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.getTableHeader().setForeground(new Color(33, 37, 41));
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(220, 235, 252));
        table.setSelectionForeground(Color.BLACK);
        table.setAutoCreateRowSorter(true);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                chargerFormulaireDepuisTableau();
            }
        });

        table.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                if ("Actif".equals(value)) {
                    lbl.setBackground(new Color(212, 237, 218));
                    lbl.setForeground(new Color(25, 135, 84));
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                } else {
                    lbl.setBackground(new Color(248, 215, 218));
                    lbl.setForeground(new Color(220, 53, 69));
                }
                lbl.setOpaque(true);
                return lbl;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new LineBorder(COLOR_BORDER, 1));

        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBackground(Color.WHITE);

        lblTotalCount = new JLabel("Total : 0 étudiants");
        lblTotalCount.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JPanel pnlPagination = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        pnlPagination.setBackground(Color.WHITE);

        pnlPagination.add(creerBouton("<", new Color(245, 245, 245), Color.BLACK));
        pnlPagination.add(creerBouton("1", COLOR_PRIMARY, Color.WHITE));
        pnlPagination.add(creerBouton("2", new Color(245, 245, 245), Color.BLACK));
        pnlPagination.add(creerBouton("3", new Color(245, 245, 245), Color.BLACK));
        pnlPagination.add(creerBouton(">", new Color(245, 245, 245), Color.BLACK));

        lblAffichageCount = new JLabel("Affichage 1 - 10 sur 10  ");
        lblAffichageCount.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JComboBox<String> cbLimit = new JComboBox<>(new String[]{"10", "20", "50"});
        styliserComposant(cbLimit);

        pnlPagination.add(lblAffichageCount);
        pnlPagination.add(cbLimit);

        pnlFooter.add(lblTotalCount, BorderLayout.WEST);
        pnlFooter.add(pnlPagination, BorderLayout.EAST);

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(pnlFooter, BorderLayout.SOUTH);

        return panel;
    }

    private void ajouterEtudiant() {
        String mat = txtMatricule.getText().trim();
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(spinnerDateNaiss.getValue());

        String email = txtEmail.getText().trim();
        String tel = txtTel.getText().trim();
        String parcours = cbParcours.getSelectedItem().toString();
        String annee = cbAnnee.getSelectedItem().toString();
        String statut = cbStatut.getSelectedItem().toString();

        if (mat.isEmpty() || nom.isEmpty() || prenom.isEmpty() || cbParcours.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir au moins le Matricule, le Nom, le Prénom et le Parcours !", "Champs requis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Etudiant e = new Etudiant(mat, nom, prenom, date, email, tel, parcours, annee, statut);
        gestionnaire.ajouterEtudiant(e);
        actualiserTableau(gestionnaire.getListeEtudiants());
        viderChamps();
        JOptionPane.showMessageDialog(this, "Étudiant ajouté avec succès !");
    }

    private void modifierEtudiant() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un étudiant dans le tableau à modifier !", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mat = txtMatricule.getText().trim();
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(spinnerDateNaiss.getValue());

        String email = txtEmail.getText().trim();
        String tel = txtTel.getText().trim();
        String parcours = cbParcours.getSelectedItem().toString();
        String annee = cbAnnee.getSelectedItem().toString();
        String statut = cbStatut.getSelectedItem().toString();

        for (Etudiant e : gestionnaire.getListeEtudiants()) {
            if (e.getMatricule().equalsIgnoreCase(mat)) {
                e.setNom(nom);
                e.setPrenom(prenom);
                e.setDateNaissance(date);
                e.setEmail(email);
                e.setTelephone(tel);
                e.setParcours(parcours);
                e.setAnneeUniversitaire(annee);
                e.setStatut(statut);
                break;
            }
        }

        actualiserTableau(gestionnaire.getListeEtudiants());
        viderChamps();
        JOptionPane.showMessageDialog(this, "Étudiant " + mat + " modifié avec succès !");
    }

    private void supprimerEtudiant() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un étudiant dans le tableau à supprimer.", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricule = tableModel.getValueAt(table.convertRowIndexToModel(row), 0).toString();
        int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer l'étudiant " + matricule + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            gestionnaire.supprimerEtudiant(matricule);
            actualiserTableau(gestionnaire.getListeEtudiants());
            viderChamps();
            JOptionPane.showMessageDialog(this, "Étudiant supprimé.");
        }
    }

    private void chargerFormulaireDepuisTableau() {
        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        txtMatricule.setText(tableModel.getValueAt(modelRow, 0).toString());
        txtNom.setText(tableModel.getValueAt(modelRow, 1).toString());
        txtPrenom.setText(tableModel.getValueAt(modelRow, 2).toString());

        try {
            String strDate = tableModel.getValueAt(modelRow, 3).toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d = sdf.parse(strDate);
            spinnerDateNaiss.setValue(d);
        } catch (Exception ignored) {}

        txtEmail.setText(tableModel.getValueAt(modelRow, 4).toString());
        cbParcours.setSelectedItem(tableModel.getValueAt(modelRow, 5).toString());
        cbAnnee.setSelectedItem(tableModel.getValueAt(modelRow, 6).toString());
        cbStatut.setSelectedItem(tableModel.getValueAt(modelRow, 7).toString());
    }

    private void rechercher() {
        String mat = txtSearchMatricule.getText().trim();
        String nom = txtSearchNom.getText().trim();
        String parcours = cbSearchParcours.getSelectedItem().toString();
        String statut = cbSearchStatut.getSelectedItem().toString();

        List<Etudiant> resultats = gestionnaire.rechercher(mat, nom, parcours, statut);
        actualiserTableau(resultats);
    }

    private void actualiserTableau(List<Etudiant> liste) {
        tableModel.setRowCount(0);
        for (Etudiant e : liste) {
            tableModel.addRow(new Object[]{
                e.getMatricule(),
                e.getNom(),
                e.getPrenom(),
                e.getDateNaissance(),
                e.getEmail(),
                e.getParcours(),
                e.getAnneeUniversitaire(),
                e.getStatut()
            });
        }
        lblTotalCount.setText("Total : " + liste.size() + " étudiants");
        lblAffichageCount.setText("Affichage 1 - " + liste.size() + " sur " + liste.size() + "  ");
    }

    private void viderChamps() {
        txtMatricule.setText("");
        txtNom.setText("");
        txtPrenom.setText("");
        spinnerDateNaiss.setValue(new Date());
        txtEmail.setText("");
        txtTel.setText("");
        cbParcours.setSelectedIndex(0);
        cbStatut.setSelectedIndex(0);
        table.clearSelection();
    }

    private void chargerDonneesTest() {
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021001", "RAKOTOMALALA", "Andry", "12/05/2002", "andry@example.com", "0340000001", "Informatique", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021002", "RABEZANAHARY", "Miora", "03/11/2001", "miora@example.com", "0340000002", "Réseaux", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021003", "RANDRIANARISOA", "Tiana", "21/07/2001", "tiana@example.com", "0340000003", "Informatique", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021004", "VOLA", "Faly", "19/02/2002", "faly@example.com", "0340000004", "Génie Logiciel", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021005", "RAKOTO", "Hery", "30/09/2001", "hery@example.com", "0340000005", "Réseaux", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021006", "ANDRIAMBOLO", "Lalatiana", "17/06/2002", "lalatiana@example.com", "0340000006", "Informatique", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021007", "RATSIMBAZAFY", "Tojo", "08/04/2001", "tojo@example.com", "0340000007", "Génie Logiciel", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021008", "RASOLOFONIAINA", "Nantenaina", "25/12/2002", "nantenaina@example.com", "0340000008", "Systèmes Embarqués", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021009", "RAZAFINDRAKOTO", "Heriniaina", "14/01/2002", "heriniaina@example.com", "0340000009", "Informatique", "2024 - 2025", "Actif"));
        gestionnaire.ajouterEtudiant(new Etudiant("ETU2021010", "MAHAFALY", "Aina", "05/10/2001", "aina@example.com", "0340000010", "Réseaux", "2024 - 2025", "Actif"));
    }
}