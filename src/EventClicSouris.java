import javax.swing.*;      // Pour JPanel
import java.awt.event.*;   // Pour ActionListener et ActionEvent
import java.awt.Color;

/**
 * Gère les clics souris sur les cases du jeu.
 * @author Theo Gobé et William-James Tafok
 * @version 1.1
 */
public class EventClicSouris implements MouseListener{
    private JFrame fenetre;
    private Grille grilleJeu;
    
    /**
     * Crée un gestionnaire de clics souris.
     * @param fenetre La fenêtre du jeu
     * @param grilleJeu La grille du jeu
     */
    public EventClicSouris(JFrame fenetre,Grille grilleJeu){
        this.fenetre = fenetre;
        this.grilleJeu = grilleJeu;
    }
    /**
     * Traite les clics souris sur les cases.
     * @param e L'événement souris
     */
    @Override
    public void mousePressed(MouseEvent e) {
        /* ici on a fait un cast, car vu que l'on a creer la class special
        CaseDemineur cette dernière possède des attributs en + d'un JButton normal
        le cast permet d'acceder a ces attributs unique a la classe CaseDemineur
         */

        //permet de bloquer la partie si elle  est finie, on ne fait RIEN
        if (this.grilleJeu.isPartieTerminee()) {
            return; 
        }

        CaseDemineur CaseClique = (CaseDemineur) e.getSource();

        // ici on met l'action pour creuser
        if (e.getButton() == MouseEvent.BUTTON1) {
            // On vérifie si la case est jouable (pas révélée et pas de drapeau)
            if (!CaseClique.isEstRevelee() && CaseClique.getEtatMarquage() == 0) {
                
                if (CaseClique.isEstBombe()) {
                    CaseClique.setEstRevelee(true);
                    this.grilleJeu.revelerToutesLesBombes(CaseClique);
                    this.grilleJeu.terminerPartie();
                    
                    Object[] options = {"Accueil", "Quitter"};
                    int choix = JOptionPane.showOptionDialog(
                        fenetre,
                        "BOMBOCLAAAAT!!!\nDéfaite\nvous avez touché une bombe",
                        "Fin de la partie",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]
                    );
                    
                    if (choix == 0) {
                        // Accueil
                        fenetre.dispose();
                        new MenuAcceuil();
                    } else {
                        // Quitter
                        System.exit(0);
                    }
                } else {
                    // Propagation : révèle les cases autour si pas de bombe à proximité
                    this.grilleJeu.propagation(CaseClique.getLine(), CaseClique.getColonne());
                    
                    // Afficher la propagation immédiatement
                    fenetre.repaint();
                    
                    // Vérifier si le joueur a gagné (vérifier après un délai pour s'assurer que tout est affichage)
                    try {
                        Thread.sleep(100); // Petit délai pour l'affichage
                    } catch (InterruptedException ex) { // e est déjà utilisé pour le MouseEvent, on utilise ex pour l'exception
                        ex.printStackTrace();
                    }
                    
                    if (this.grilleJeu.verifierVictoire()) {
                        this.grilleJeu.afficherEcranVictoire();
                    }
                }
            }
        }

        // CLIC DROIT (Marquer)
        else if (e.getButton() == MouseEvent.BUTTON3) {
            if (!CaseClique.isEstRevelee()) {
                // Cycle : 0 (vide) -> 1 (drapeau) -> 2 (point d'interrogation)
                int ancienEtat= CaseClique.getEtatMarquage();
                int suivant = (ancienEtat + 1) % 3;
                CaseClique.setEtatMarquage(suivant);
            //fonctionnement du compteur
            //si on vient de mettre un drapeau
                if(suivant == 1){
                    this.grilleJeu.mettreAJourCompteurDrapeaux(1);
                }else if(ancienEtat==1){
                    this.grilleJeu.mettreAJourCompteurDrapeaux(-1);
                }
            }
        }
    }

    // Les 4 autres méthodes du contrat, même vides sinon ça compile pas
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}