import javax.swing.*;      // Pour JPanel
import java.awt.event.*;   // Pour ActionListener et ActionEvent
import java.awt.Color;
import java.awt.*;

/**
 * Gère les événements de sélection des dimensions.
 * @author Theo Gobé et William-James Tafok
 * @version 1.1
 */
public class DimensionEvent implements ActionListener{
    private JFrame fenetre;
    private JTextField CLignes;
    private JTextField CColonnes;
    private JTextField CBombes;
    
    /**
     * Crée un gestionnaire d'événements de dimensions.
     * @param fenetre La fenêtre
     * @param lignes Champ des lignes
     * @param colonnes Champ des colonnes
     * @param bombes Champ des bombes
    /**
     * Traite les clics de validation des dimensions.
     * @param evenement L'événement déclenché
     */
    public DimensionEvent(JFrame fenetre, JTextField lignes, JTextField colonnes, JTextField bombes){
        this.fenetre = fenetre;
        this.CLignes = lignes;
        this.CColonnes = colonnes;
        this.CBombes = bombes;
    }

    @Override public void actionPerformed(ActionEvent evenement){

        String texte= evenement.getActionCommand();
        if(texte.equals("VALIDER")){
            /*On met en place le try-catch*/
            try{

                int a = Integer.parseInt(CLignes.getText());
                int b = Integer.parseInt(CColonnes.getText());
                int c = Integer.parseInt(CBombes.getText());
                /*après avoir attribuer les valeur on verifie si elle corespondent au critère
                donc entre 4 et 30 */
                if (a < 4 || a > 30 || b < 4 || b > 30 || c==0 || c>= a*b) {
                    throw new IllegalArgumentException(); 
                }
                //une fois qu'on a passé toutes les conditions on confirme les valeur pour la grille
                fenetre.setVisible(false);
                new Grille(a, b, c);

            }
            /*si l'utilisateur entre une/des lettres a la place du nombre,rien ou autre */
            catch(NumberFormatException e){
                afficherErreur("ERREUR", "Entrez un nombre entier entre 4 et 30\npour les lignes et les colonnes.\nPour jouer il faut au minimum une bombe,\n et au maximum le nombre de case du demineur -1");
                //on vide les champs de texte
                CLignes.setText("");
                CColonnes.setText("");
                CBombes.setText("");
            }
            //Si l'utilisateur a entrer un nombre réel ou un nombre trop petit,trop grand
            catch(IllegalArgumentException e){
                afficherErreur("ERREUR", "Entrez un nombre entier entre 4 et 30\npour les lignes et les colonnes .\nPour jouer il faut au minimum une bombe,\n et au maximum le nombre de case du demineur -1");
                //on vide les champs de texte
                CLignes.setText("");
                CColonnes.setText("");
                CBombes.setText("");
            }
        }
    }
    
    private void afficherErreur(String titre, String message) {
        JFrame frameErreur = new JFrame(titre);
        frameErreur.setSize(400, 250);
        frameErreur.setLocationRelativeTo(fenetre);
        frameErreur.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setForeground(Color.RED);
        
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> frameErreur.dispose());
        
        JPanel panelBouton = new JPanel();
        panelBouton.add(btnOk);
        
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(panelBouton, BorderLayout.SOUTH);
        
        frameErreur.add(panel);
        frameErreur.setVisible(true);
    }
}   