import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;  
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 * Affiche la fenêtre pour choisir les dimensions de la grille.
 * @author Theo Gobé et William-James Tafok
 * @version 1.1
 */
public class DimensionGrille{
    private JFrame fenetre;
    
    /**
     * Crée la fenêtre de sélection des dimensions.
     */
    public DimensionGrille(){
        
        fenetre = new JFrame();
        fenetre.setSize(800,700);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*ajout d'une bulle de dialogue pour indiquer a l'utilisateur les contraintes a respecter*/
        JOptionPane.showMessageDialog(fenetre,
        "Indication Dimension Grille:\nEntrez un nombre entier entre 4 et 30\npour les lignes et les colonnes.\nPour jouer il faut au minimum une bombe et au maximum le nombres de cases du demineur -1",
        "Information",
        JOptionPane.INFORMATION_MESSAGE);

        /*declaration des bouton de dimension*/
        JPanel PanneauDimension= new JPanel();
        JPanel PanneauValidation= new JPanel();
        JPanel panneauBouton= new JPanel();
        JLabel texte = new JLabel("Choix dimensions");
        JLabel TLignes = new JLabel("LIGNES :");
        JLabel TColonnes = new JLabel("COLONNES :");
        JLabel vide1 = new JLabel("");
        JLabel vide2 = new JLabel("");
        JLabel vide3 = new JLabel("");
        JLabel vide4 = new JLabel("");
        JLabel TBombes = new JLabel("Nombre de bombe :");
        JLabel vide5 = new JLabel("");
        JTextField CLignes = new JTextField();
        JTextField CColonnes = new JTextField();
        JTextField CBombes = new JTextField();

        JButton btnValider = new JButton("VALIDER");
        btnValider.setPreferredSize(new Dimension(100, 40));
        
        /*On creer le controleur(Listener) */
        DimensionEvent controleur = new DimensionEvent(fenetre,CLignes,CColonnes,CBombes);

        /*Ajout du controleur d'evenement */
        btnValider.addActionListener(controleur);

        /*mise en place des element*/
        GridLayout Choix= new GridLayout(4,3);
        FlowLayout layoutBouton= new FlowLayout(FlowLayout.CENTER);
        GridLayout PanneauPage= new GridLayout(2,1);
        PanneauValidation.setLayout(PanneauPage);
        panneauBouton.setLayout(layoutBouton);
        PanneauDimension.setLayout(Choix);

        /*on ajoute les element a leur panneaux respectif*/
        PanneauDimension.add(vide1);
        PanneauDimension.add(texte);
        PanneauDimension.add(vide2);
        PanneauDimension.add(TLignes);
        PanneauDimension.add(CLignes);
        PanneauDimension.add(vide3);
        PanneauDimension.add(TColonnes);
        PanneauDimension.add(CColonnes);
        PanneauDimension.add(vide4);
        PanneauDimension.add(TBombes);
        PanneauDimension.add(CBombes);
        PanneauDimension.add(vide5);

        /*pour le panneau qui va contenir le bouton histoire que la mise en forme soit correcte */
        panneauBouton.add(btnValider);

        /*ajout du panneau avec les dimension dans le panneau principal */
        PanneauValidation.add(PanneauDimension);
        PanneauValidation.add(panneauBouton);

        /*ajout du panneaau principal a la fenetre  */
        fenetre.add(PanneauValidation);
        fenetre.setVisible(true);
    }
}