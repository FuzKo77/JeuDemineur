import javax.swing.*;      // Pour JPanel
import java.awt.event.*;   // Pour ActionListener et ActionEvent
import java.awt.Color;

/**
 * Gère les événements des boutons du jeu (Sauver, Quitter).
 * @author Theo Gobé et William-James Tafok
 * @version 1.1
 */
public class OptionJeuEvent implements ActionListener{
    private JFrame fenetre;
    private Grille grille;
    
    /**
     * Crée un gestionnaire d'options du menu.
     * @param fenetre La fenêtre
     */
    public OptionJeuEvent(JFrame fenetre){
        this.fenetre=fenetre;
        this.grille=null;
    }
    
    
    /**
     * Traite les clics des boutons d'options.
     * @param evenement L'événement déclenché
     */
     /** Crée un gestionnaire d'options du jeu.
     * @param fenetre La fenêtre du jeu
     * @param grille La grille du jeu
     */
    public OptionJeuEvent(JFrame fenetre, Grille grille){
        this.fenetre=fenetre;
        this.grille=grille;
    }

    @Override public void actionPerformed(ActionEvent evenement){
        String texteBouton= evenement.getActionCommand();
        /*Mise en place des conditions */
        
        if(texteBouton.equals("Sauver")){
            if(this.grille!=null){
                this.grille.sauvegarder();
            }
        }

        if(texteBouton.equals("Quitter")){
            int reponse = JOptionPane.showConfirmDialog(fenetre, "Voulez-vous vraiment quitter le jeu ?", "Confirmez la sortie", JOptionPane.YES_NO_OPTION);
            if (reponse == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}