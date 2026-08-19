import javax.swing.*;      // Pour JPanel
import java.awt.event.*;   // Pour ActionListener et ActionEvent
import java.awt.Color;

/**
 * Gère les événements du menu d'accueil.
 * @author Theo Gobé et William-James Tafok
 * @version 1.1
 */
public class MenuEvent implements ActionListener{
    private JFrame fenetre;
    
    /**
     * Crée un gestionnaire d'événements du menu.
     * @param fenetre La fenêtre du menu
    /**
     * Traite les clics sur les boutons du menu.
     * @param evenement L'événement déclenché
     */
    public MenuEvent(JFrame fenetre){
        this.fenetre=fenetre;
    }

    @Override public void actionPerformed(ActionEvent evenement){
        String texteBouton= evenement.getActionCommand();
        /*Mise en place des conditions */
        if(texteBouton.equals("Nouvelle Partie")){
            fenetre.setVisible(false);
            new DimensionGrille();
        }
        
        if(texteBouton.equals("Reprendre")){
            fenetre.setVisible(false);
            Grille.chargerPartie();
        }

        if(texteBouton.equals("Quitter")){
            int reponse = JOptionPane.showConfirmDialog(fenetre, "Voulez-vous vraiment quitter le jeu ?", "Confirmez la sortie", JOptionPane.YES_NO_OPTION);
            if (reponse == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}