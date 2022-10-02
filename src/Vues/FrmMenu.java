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
        taches = new ArrayList<>();


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
                if (lstTheme.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(null, "Sélectionner un thème", "Choix du thème", JOptionPane.ERROR_MESSAGE);
                } else if (lstProjets.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(null, "Séléctionner un projet", "Choix du projet", JOptionPane.ERROR_MESSAGE);
                } else if (txtTache.getText().compareTo("") == 0) {
                    JOptionPane.showMessageDialog(null, "Saisir une tâche", "Choix de la tâche", JOptionPane.ERROR_MESSAGE);
                } else {

                    Tache maTache = new Tache(txtTache.getText(), cboAssigne.getSelectedItem().toString(), false); // on ajoute les valeurs saisie dans l'objet maTache

                    if (!monPlanning.containsKey(lstTheme.getSelectedValue())) { // S'il n'existe pas
                        taches.add(maTache); // on ajoute l'objet au tableau
                        mesProjets.put(lstProjets.getSelectedValue().toString(), taches); // on ajoute le tableau avec la clé qu'on get la valeur en la transformant en string dans mesProjets
                        monPlanning.put(lstTheme.getSelectedValue().toString(), mesProjets); // on ajoute le HashMap avec la clé qu'on get la valeur en la transformant en string dans monPlanning
                    } else { // S'il existe dans la clé alors
                        taches.add(maTache); // on ajoute l'objet au tableau
                        mesProjets.put(lstProjets.getSelectedValue().toString(), taches); // on ajoute le tableau avec la clé qu'on get la valeur en la transformant en string dans mesProjets
                    }

                    DefaultMutableTreeNode noeudTheme = null;
                    DefaultMutableTreeNode noeudProjets = null;
                    DefaultMutableTreeNode noeudTache = null;

                    root.removeAllChildren();

                    for (String thm : monPlanning.keySet()) {
                        noeudTheme = new DefaultMutableTreeNode(thm);

                        for (String prj : monPlanning.get(thm).keySet()) {
                            noeudProjets = new DefaultMutableTreeNode(prj);

                            for (Tache tch : monPlanning.get(thm).get(prj)) {
                                noeudTache = new DefaultMutableTreeNode(tch.getNomTache());
                                noeudProjets.add(noeudTache);
                                noeudTache = new DefaultMutableTreeNode(tch.getNomDeveloppeur());
                                noeudProjets.add(noeudTache);
                                noeudTache = new DefaultMutableTreeNode(tch.isEstTerminee());
                                noeudProjets.add(noeudTache);
                            }
                            noeudTheme.add(noeudProjets);
                        }
                        root.add(noeudTheme);
                    }
                    model = new DefaultTreeModel(root);
                    trTache.setModel(model);


                }
                JOptionPane.showMessageDialog(null, "Ajout reussi");
            }
        });
    }
}

