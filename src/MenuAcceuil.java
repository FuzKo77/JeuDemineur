import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;  
import java.awt.event.*;

/**
 * Affiche le menu d'accueil du jeu.
 * @author Theo Gobé et William-James Tafok
 * @version 1.1
 */
public class MenuAcceuil{
    /*element que je vais utiliser pour faire des actions */
    private JFrame fenetre;
    private JButton btnNouvelleP;
    private JButton btnReprendre;
    private JButton btnQuitter;

    /**
     * Crée le menu d'accueil.
     */
    public MenuAcceuil(){
        fenetre = new JFrame();
        fenetre.setSize(800,700);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*ajout du controleur d'action*/
        MenuEvent controleur = new MenuEvent(fenetre);
        /*ajout du pannel qui va stocker les boutons du menu */
        JPanel Menupanneau = new JPanel();
        JPanel Boutonpanneau = new JPanel();
        /*on ajoute les bountons et le texte */
        JLabel nomjeu = new JLabel("Le Demineur");
        JLabel vide1 = new JLabel("");
        JLabel vide2 = new JLabel("");
        JLabel vide3 = new JLabel("");
        JLabel vide4 = new JLabel("");
        JLabel vide5 = new JLabel("");
        JLabel vide6 = new JLabel("");
        JButton btnNouvelleP = new JButton("Nouvelle Partie");
        JButton btnReprendre = new JButton("Reprendre");
        JButton btnQuitter = new JButton("Quitter");

        /*on ajoute les actionListener sur les boutons*/
        btnNouvelleP.addActionListener(controleur);
        btnQuitter.addActionListener(controleur);
        btnReprendre.addActionListener(controleur);
        /*mise en place de la grille */
        GridLayout GrilleAccceuil= new GridLayout(3,3);
        GridLayout GrilleMenu = new GridLayout(3,1);
        /*on ajoute les boutons dans le panneau des boutons */
        Boutonpanneau.setLayout(GrilleMenu);
        Boutonpanneau.add(btnNouvelleP);
        Boutonpanneau.add(btnReprendre);
        Boutonpanneau.add(btnQuitter);
        /*on ajoute le tout dans le panneau principal*/
        Menupanneau.setLayout(GrilleAccceuil);
        nomjeu.setHorizontalAlignment(JLabel.CENTER);
        Menupanneau.add(vide1);
        Menupanneau.add(nomjeu);
        Menupanneau.add(vide2);
        Menupanneau.add(vide3);
        Menupanneau.add(Boutonpanneau);
        Menupanneau.add(vide4);
        Menupanneau.add(vide5);
        Menupanneau.add(vide6);

        fenetre.add(Menupanneau);
        fenetre.setVisible(true);

    }
}