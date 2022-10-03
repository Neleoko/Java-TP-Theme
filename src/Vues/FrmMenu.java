package Vues;

import Entities.Tache;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class FrmMenu extends JFrame {
    private JList lstTheme;
    private JList lstProjets;
    private JTree trTache;
    private JTextField txtTache;
    private JComboBox cboAssigne;
    private JButton btnValider;
    private JPanel pnlRoot;
    private JLabel lblMenu;
    private JLabel lblTheme;
    private JLabel lblProjets;
    private JLabel lblTache;
    private HashMap<String, HashMap<String, ArrayList<Tache>>> monPlanning;
    private HashMap<String,ArrayList<Tache>> mesProjets;
    private ArrayList<Tache> taches;
    DefaultMutableTreeNode root;
    DefaultTreeModel model;
    public FrmMenu() {
        this.setTitle("Projet");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        monPlanning = new HashMap<>();
        mesProjets = new HashMap<>();



        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e); // Quand la fenetre souvre

                root = new DefaultMutableTreeNode("Les tâches à faire"); // permet de créer root qui est un noeud de base avec un nom en ""
                model = new DefaultTreeModel(root); // permet de créer model qui peut avoir des noeud enfant
                trTache.setModel(model); // on ajoute le model dans le Jtree
            }
        });

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (lstTheme.getSelectedValue() == null) { // si theme est pas choisis
                    JOptionPane.showMessageDialog(null, "Sélectionner un thème", "Choix du thème", JOptionPane.ERROR_MESSAGE);
                } else if (lstProjets.getSelectedValue() == null) { // si le projets est pas choisis
                    JOptionPane.showMessageDialog(null, "Séléctionner un projet", "Choix du projet", JOptionPane.ERROR_MESSAGE);
                } else if (txtTache.getText().compareTo("") == 0) { // si la tache est pas choisis
                    JOptionPane.showMessageDialog(null, "Saisir une tâche", "Choix de la tâche", JOptionPane.ERROR_MESSAGE);
                } else { // Tout est ok !

                    Tache maTache = new Tache(txtTache.getText(), cboAssigne.getSelectedItem().toString(), false); // on ajoute les valeurs saisie dans l'objet maTache

                    if (!monPlanning.containsKey(lstTheme.getSelectedValue())) { // S'il n'y a pas le theme

                        mesProjets = new HashMap<>(); // on reset le hash map des projets
                        taches = new ArrayList<>(); // on reset la liste des taches
                        taches.add(maTache); // on ajoute l'objet des taches au tableau
                        mesProjets.put(lstProjets.getSelectedValue().toString(), taches); // on ajoute le tableau dans le hash map avec la clé des projets
                        monPlanning.put(lstTheme.getSelectedValue().toString(), mesProjets); // on ajoute le hasmap de mes projets dans le hash map des planning avec le nom du theme en valeur

                    } else { // Si la theme existe pas on va le rajouter

                        mesProjets = new HashMap<>(); // on reset le hashmap des projets entier

                        if (monPlanning.get(lstTheme.getSelectedValue().toString()).containsKey(lstProjets.getSelectedValue().toString())){ // on recupere le nom du theme et on regarde s'il a une clé
                            monPlanning.get(lstTheme.getSelectedValue().toString()).get(lstProjets.getSelectedValue().toString()).add(maTache); // on ajoute ma tache au theme

                        }else { // sinon

                            taches = new ArrayList<>(); // on reset le tableau
                            taches.add(maTache); // on ajoute l'objet au tableau
                            monPlanning.get(lstTheme.getSelectedValue().toString()).put(lstProjets.getSelectedValue().toString(), taches); // on ajoute le projet au theme en clé et l'objet ma tache en valeur

                        }
                    }
                    // on definie en null les noeuds principaux
                    DefaultMutableTreeNode noeudTheme = null;
                    DefaultMutableTreeNode noeudProjets = null;
                    DefaultMutableTreeNode noeudTache = null;

                    root.removeAllChildren(); // on enleve les noeud de bases

                    for (String thm : monPlanning.keySet()) { // pour chaque clé de projets
                        noeudTheme = new DefaultMutableTreeNode(thm); // on met le noeud avec le nom du theme

                        for (String prj : monPlanning.get(thm).keySet()) { // pour chaque clé de themes
                            noeudProjets = new DefaultMutableTreeNode(prj);  // on met le noeud avec le nom du projet

                            for (Tache tch : monPlanning.get(thm).get(prj)) { // pour chaque taches
                                noeudTache = new DefaultMutableTreeNode(tch.getNomTache()); // ajoute un noeud de la tache
                                noeudProjets.add(noeudTache); // ajoute le noeud de la tache au noeud projets
                                noeudTache = new DefaultMutableTreeNode(tch.getNomDeveloppeur());
                                noeudProjets.add(noeudTache);
                                noeudTache = new DefaultMutableTreeNode(tch.isEstTerminee());
                                noeudProjets.add(noeudTache);
                            }
                            noeudTheme.add(noeudProjets); // ajoute le noeud de projets dans le noeud theme
                        }
                        root.add(noeudTheme); // ajoute le noeud theme au noeud principale
                    }
                    model = new DefaultTreeModel(root); // definie le model avec le route
                    trTache.setModel(model); // ajoute le model au Jtree

                    JOptionPane.showMessageDialog(null, "Ajout reussi"); // message de reussite
                }

            }
        });
    }
}

