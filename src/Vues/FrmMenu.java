package Vues;

import Entities.Tache;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lstTheme.getSelectedValue() == null){
                    JOptionPane.showMessageDialog(null,"Sélectionner un thème", "Choix du thème", JOptionPane.ERROR_MESSAGE);
                } else if (lstProjets.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(null,"Séléctionner un projet", "Choix du projet", JOptionPane.ERROR_MESSAGE);
                } else if (txtTache.getText().compareTo("") == 0) {
                    JOptionPane.showMessageDialog(null,"Saisir une tâche", "Choix de la tâche", JOptionPane.ERROR_MESSAGE);
                }else {
                    if (monPlanning.containsKey(lstTheme.getSelectedValue())){ // S'il existe dans la clé alors

                    }else { // S'il n'existe pas
                        Tache maTache = new Tache(txtTache.getText(), cboAssigne.getSelectedItem().toString(), false); // on ajoute les valeurs saisie dans l'objet maTache

                        taches.add(maTache); // on ajoute l'objet au tableau
                        mesProjets.put(lstProjets.getSelectedValue().toString(), taches); // on ajoute le tableau avec la clé qu'on get la valeur en la transformant en string dans mesProjets
                        monPlanning.put(lstTheme.getSelectedValue().toString(), mesProjets); // on ajoute le HashMap avec la clé qu'on get la valeur en la transformant en string dans monPlanning
                        root.removeAllChildren();
                    }
                    JOptionPane.showMessageDialog(null,"Ajout reussi");
                }

            }
        });
    }
}

