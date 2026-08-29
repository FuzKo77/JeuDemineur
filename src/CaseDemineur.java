import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Représente une case du jeu de Démineur.
 * @author Theo Gobé et William-James Tafok
 * @version 1.1
 */
public class CaseDemineur extends JButton {
    // on creer tout les attribut qu'on aura besoin lors de la partie
    private boolean estBombe = false;
    private boolean estRevelee = false;
    private int etatMarquage = 0;
    private int minesVoisines = 0;
    private int ligne;
    private int colonne;
    private ImageIcon imgDrapeau = chargerImage("Drapeau_img3.png");
    private ImageIcon imgBombe = chargerImage("Bombe_img3.png");

    private ImageIcon chargerImage(String nomFichier) {
        URL ressource = CaseDemineur.class.getResource("/" + nomFichier);
        if (ressource != null) {
            return new ImageIcon(ressource);
        }
        return new ImageIcon("../Image_Sae/" + nomFichier);
    }

    /**
     * Crée une case du Démineur.
     */
    public CaseDemineur() {
        /*ici on a donc tout les attribut d'un bouton clasique mais grace a nos ajouts
        on possède également les autres attributs*/
        super(); 
        this.setMargin(new java.awt.Insets(0, 0, 0, 0)); // Supprime les marges internes
        this.setIconTextGap(0);                         // Supprime l'espace entre texte et icône
        this.setBorderPainted(true);                    //conserve les bordure
        this.setFont(new Font("Arial", Font.BOLD, 17)); // On prépare la police
    }

    /*la on va mettre les methodes qui vont nous servir a changer l'etat des bouton
    drapeau->?->et le cas ou c'est vide 
    donc pour vide valeur = 0 /le drapeau valeur = 1/interogation = 2*/


    /**
     * Rafraîchit l'affichage de la case.
     */
    public void rafraichirAffichage() {
        if (this.estRevelee) {
            this.setEnabled(false); // On grise le bouton
            if (this.estBombe) {
                this.setDisabledIcon(imgBombe);//ça laisse l'image en couleur sinon quand on clique elle est en noir et blanc gris
                this.setIcon(imgBombe);
            } else {
                if (this.minesVoisines > 0) {
                    this.setText("" + this.minesVoisines); // On affiche "1", "2", etc.
                } else {
                    this.setText(""); // On laisse la case vide
                }
                this.setBackground(Color.LIGHT_GRAY);
            }
        } else {
            // Si la case est encore cachée, on gère les drapeaux
            if (this.etatMarquage == 1) {
                this.setIcon(imgDrapeau);
            } else if (this.etatMarquage == 2) {
                this.setIcon(null);
                this.setText("?");
                this.setForeground(Color.BLUE);
            } else {
                this.setIcon(null);
                this.setText(""); // Case vide
            }
        }
    }

    /**
     * Définit les coordonnées de la case.
     * @param ligne La ligne
     * @param colonne La colonne
     */
    public void setCoordonnes(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
    }
    
    /**
     * Retourne la ligne de la case.
     * @return La ligne
     */
    public int getLine(){
        return ligne;
    }
    
    /**
     * Retourne la colonne de la case.
     * @return La colonne
     */
    public int getColonne(){
        return colonne;
    }

    /**
     * Vérifie si la case est révélée.
     * @return true si révélée
     */
    public boolean isEstRevelee() {
        return this.estRevelee;
    }

    /**
     * Définit si la case est révélée.
     * @param b true pour révéler
     */
    public void setEstRevelee(boolean b) {
        this.estRevelee = b;
        this.rafraichirAffichage();
    }

    /**
     * Vérifie si la case contient une bombe.
     * @return true s'il y a une bombe
     */
    public boolean isEstBombe() {
        return this.estBombe;
    }

    /**
     * Définit si la case contient une bombe.
     * @param b true s'il y a une bombe
     */
    public void setEstBombe(boolean b) {
        this.estBombe = b;
    }
    
    /**
     * Retourne l'état du marquage de la case.
     * @return L'état (0=vide, 1=drapeau, 2=point d'interrogation)
     */
    public int getEtatMarquage() {
        return this.etatMarquage;
    }

    /**
     * Définit l'état du marquage de la case.
     * @param e L'état (0=vide, 1=drapeau, 2=point d'interrogation)
     */
    public void setEtatMarquage(int e) {
        this.etatMarquage = e;
        this.rafraichirAffichage();
    }

    /**
     * Définit le nombre de mines voisines.
     * @param n Le nombre de mines voisines
     */
    public void setMinesVoisines(int n) {
        this.minesVoisines = n;
    }

    /**
     * Retourne le nombre de mines voisines.
     * @return Le nombre de mines voisines
     */
    public int getMinesVoisines() {
        return this.minesVoisines;
    }
}