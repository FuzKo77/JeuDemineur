import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;

/**
 * Gère la grille du jeu de Démineur.
 * @author Theo Gobé et William-James Tafok
 * @version 1.1
 */
public class Grille {
    private CaseDemineur[][] Cases;
    private int lignes;
    private int colonnes;
    private JLabel labelDrapeaux;
    private int nbBombes;
    private int nbDrapeauxPoses;
    //ça va nous permettre de bloque la grille quand on perd
    private boolean PartieTerminee =false;
    private JFrame fenetre;

    /**
     * Crée la grille de Démineur.
     * @param lignes Nombre de lignes
     * @param colonnes Nombre de colonnes
     * @param nbBombes Nombre de bombes
     */
    public Grille(int lignes, int colonnes,int nbBombes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.nbBombes=nbBombes;
        this.nbDrapeauxPoses=0;
        this.labelDrapeaux = new JLabel("Mines restantes :"+ nbBombes);
        this.Cases= new CaseDemineur[lignes][colonnes];
        this.fenetre = new JFrame("Démineur - Partie en cours");

        fenetre.setSize(800, 700);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OptionJeuEvent controleur2 = new OptionJeuEvent(fenetre, this);

        // Les panneaux
        JPanel conteneurPrincipal = new JPanel();
        JPanel panneauInfo = new JPanel();
        JPanel panneauJeu = new JPanel();
        JPanel centrageGrille = new JPanel(); // Pour que la grille ne soit pas écrasée
        JPanel panneauOptions = new JPanel();

        // Les boutons d'action
        JButton btnSauver = new JButton("Sauver");
        JButton btnQuitter = new JButton("Quitter");
        btnSauver.addActionListener(controleur2);
        btnQuitter.addActionListener(controleur2);

        // Le conteneur principal 
        BorderLayout layoutPrincipal = new BorderLayout(10, 10);
        conteneurPrincipal.setLayout(layoutPrincipal);

        // Le panneau du haut utilise FlowLayout (aligné au centre)
        FlowLayout layoutInfo = new FlowLayout(FlowLayout.CENTER, 50, 10);
        panneauInfo.setLayout(layoutInfo);

        // La grille de jeu utilise GridLayout avec les dimensions choisies
        GridLayout layoutGrille = new GridLayout(lignes, colonnes);
        panneauJeu.setLayout(layoutGrille);

        // Le panneau de centrage utilise GridBagLayout (magique pour centrer sans étirer)
        GridBagLayout layoutCentrage = new GridBagLayout();
        centrageGrille.setLayout(layoutCentrage);

        // Le panneau du bas utilise FlowLayout (aligné à droite)
        FlowLayout layoutOptions = new FlowLayout(FlowLayout.RIGHT);
        panneauOptions.setLayout(layoutOptions);

        // On remplit le panneau d'infos
        panneauInfo.add(labelDrapeaux);

        JOptionPane.showMessageDialog(fenetre,
        "Quelques indications pour votre confort pendant votre partie:\n\nIl est préférable de jouer en grand écran.\nSinon si vous choisissez de grandes dimensions pour votre grille\n toutes les cases risquent de ne pas s'afficher, ce qui vous bloquerait dans votre progression.\n\n Pendant votre partie\n\n Vous pourrez utiliser, à l'aide de votre clic gauche:\n\n-Un drapeau: Vous êtes sûr qu'il y'a une bombe à cette endroit.\n-Un point d'interrogation: Vous permet de Vous aidez dans votre raisonnement.\n\n En cas de défaite vous pourrez voir à l'aide d'un code couleur\n\nBombe éclatée ->Rouge\nBombe non-trouvée ->Bleu\nBombe trouvée ->Vert\n\n Bonne Chance!",
        "Information",
        JOptionPane.INFORMATION_MESSAGE);


        //on creer le controleur
        EventClicSouris controleur = new EventClicSouris(fenetre, this);
        //  On remplit la grille de jeu avec la nouvel version de boutons de CaseDemineur
        for (int i = 0; i < lignes; i++) {
            for(int j = 0;j<colonnes;j++){
                CaseDemineur caseBouton = new CaseDemineur();
                /*on donne au case leur cordonnées ça va etre utile pour la propahation 
                 et le cas de défaite pour reveler toute les bombes*/
                caseBouton.setCoordonnes(i,j);
                caseBouton.addMouseListener(controleur);
                this.Cases[i][j] = caseBouton;
                Dimension tailleFixe = new Dimension(20, 20); // Ou 30x30 si tu as beaucoup de cases
                caseBouton.setPreferredSize(tailleFixe);
                caseBouton.setMinimumSize(tailleFixe);
                caseBouton.setMaximumSize(tailleFixe);
                panneauJeu.add(caseBouton);
            }
        }
        this.placeBombes(nbBombes);
        this.calculerVoisins();

        //On place la grille dans son centreur
        centrageGrille.add(panneauJeu);

        //On remplit le panneau d'options
        panneauOptions.add(btnSauver);
        panneauOptions.add(btnQuitter);

        conteneurPrincipal.add(panneauInfo, BorderLayout.NORTH);
        conteneurPrincipal.add(centrageGrille, BorderLayout.CENTER);
        conteneurPrincipal.add(panneauOptions, BorderLayout.SOUTH);

        fenetre.add(conteneurPrincipal);
    /**
     * Vérifie si la partie est terminée.
     * @return true si terminée
     */
        fenetre.setVisible(true);
    }
    /*va permettre au case de connaitre leur cordonné ont en aura besoin pour la propagation
    (c'est la methode ou quand tu clique sur une case ou il n'y a pas de bombes au tour ça révèle les autres*/

    //methode concerant l'etat de la partie
    /**
     * Termine la partie.
     */
    public boolean isPartieTerminee() {
        return PartieTerminee;
    }

    public void terminerPartie() {
        this.PartieTerminee = true;
    /**
     * Révèle toutes les bombes avec code couleur.
     * @param caseExplose La case qui a explosé
     */
    }

    //methode concernant la gestion des bombes
    public void revelerToutesLesBombes(CaseDemineur caseExplose) {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                CaseDemineur Case = this.Cases[i][j];
                //rouge=bombe cliqué/qui a 'sauter'
                //bleu= bombe non trouvé par le joueur
                //vert= bombe marque(donc que le joueur a trouvé)

                if(Case.isEstBombe()){
                    /*on revele la case*/
                    Case.setEstRevelee(true);
                    if(Case==caseExplose){
                        Case.setBackground(Color.RED);
                    }else if(Case.getEtatMarquage()==1){
                        Case.setBackground(Color.GREEN);
                    }else {
    /**
     * Met à jour le compteur de drapeaux.
     * @param changement Nombre de drapeaux à ajouter ou retirer
     */
                        Case.setBackground(Color.BLUE);
                    }
                }
            }
        }
    }
    //methode pour le nbr de bombes restantes
    public void mettreAJourCompteurDrapeaux(int changement) {
        this.nbDrapeauxPoses += changement;
        int restantes = this.nbBombes - this.nbDrapeauxPoses;
        this.labelDrapeaux.setText("Mines restantes : " + restantes);
        
        // Optionnel : changer la couleur si on dépasse le nombre de mines
        if (restantes < 0) {
            labelDrapeaux.setForeground(Color.RED);
        } else {
            labelDrapeaux.setForeground(Color.BLACK);
    /**
     * Place les bombes aléatoirement sur la grille.
     * @param nbTotal Nombre de bombes à placer
     */
        }
    }


    public void placeBombes(int nbTotal) {
        //On crée l'objet Random 
        Random generateur = new Random();

        int posees = 0;
        while (posees < nbTotal) {
            // On demande un nombre entre 0 et (lignes - 1)
            int l = generateur.nextInt(this.lignes); 
            int c = generateur.nextInt(this.colonnes);
 /**
     * Calcule le nombre de mines voisines pour chaque case.
     */
    
            if (!this.Cases[l][c].isEstBombe()) {
                this.Cases[l][c].setEstBombe(true);
                posees++;
            }
        }
    }

   public void calculerVoisins() {
        // On parcourt chaque ligne i de la grille
        for (int i = 0; i < lignes; i++) {
            // On parcourt chaque colonne j de la grille
            for (int j = 0; j < colonnes; j++) {
                
                // On ne calcule le chiffre que pour les cases qui ne cachent pas de bombe
                if (!this.Cases[i][j].isEstBombe()) {
                    int nbMinesTrouvees = 0;

                    // On scanne le voisinage : de la ligne du haut à celle du bas
                    for (int ligneVoisine = i - 1; ligneVoisine <= i + 1; ligneVoisine++) {
                        // De la colonne de gauche à celle de droite
                        for (int colonneVoisine = j - 1; colonneVoisine <= j + 1; colonneVoisine++) {
                            
                            /* on n'exclus ce qui est hors du tableau pour pas se retrouver avec des valeur
                            negative en terme d'indication*/
                            boolean ligneValide = (ligneVoisine >= 0 && ligneVoisine < lignes);
                            boolean colonneValide = (colonneVoisine >= 0 && colonneVoisine < colonnes);

                            if (ligneValide && colonneValide) {
                                // Si la case voisine contient une bombe, on incrémente le compteur
                                if (this.Cases[ligneVoisine][colonneVoisine].isEstBombe()) {
                                    nbMinesTrouvees++;
                                }
                            }
                        }
                    }
                    
                    // On transmet le total à la case actuelle
    /**
     * Sauvegarde la partie dans un fichier.
     */
                    this.Cases[i][j].setMinesVoisines(nbMinesTrouvees);
                }
            }
        }
    }


    public void sauvegarder() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("sauvegarde.txt"));
            
            // Écrire les dimensions
            writer.write("DIMENSIONS:" + lignes + "," + colonnes);
            writer.newLine();
            
            // Écrire le nombre de bombes
            writer.write("NBOMBES:" + nbBombes);
            writer.newLine();
            
            // Écrire le nombre de drapeaux posés
            writer.write("DRAPEAUX_POSES:" + nbDrapeauxPoses);
            writer.newLine();
            
            // Écrire la date de sauvegarde
            writer.write("DATE:" + new java.util.Date());
            writer.newLine();
            
            // Écrire l'état de toutes les cases
            writer.write("CASES:");
            writer.newLine();
            
            for (int i = 0; i < lignes; i++) {
                for (int j = 0; j < colonnes; j++) {
                    CaseDemineur cas = this.Cases[i][j];
                    // Format: ligne,colonne,estBombe,estRevelee,etatMarquage
                    writer.write(i + "," + j + "," + 
                                cas.isEstBombe() + "," + 
                                cas.isEstRevelee() + "," + 
                                cas.getEtatMarquage());
                    writer.newLine();
    /**
     * Charge une partie sauvegardée.
     */
                }
            }
            
            writer.close();
            JOptionPane.showMessageDialog(fenetre, "Partie sauvegardée !", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(fenetre, "Erreur de sauvegarde: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void chargerPartie() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("sauvegarde.txt"));
            String ligne;
            
            // Variables pour stocker les informations de sauvegarde
            int lignesGrille = 0;
            int colonnesGrille = 0;
            int nbBombesCharge = 0;
            int drapeauxPoses = 0;
            
            // Lire les informations générales
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith("DIMENSIONS:")) {
                    String[] parties = ligne.replace("DIMENSIONS:", "").split(",");
                    lignesGrille = Integer.parseInt(parties[0]);
                    colonnesGrille = Integer.parseInt(parties[1]);
                }
                else if (ligne.startsWith("NBOMBES:")) {
                    nbBombesCharge = Integer.parseInt(ligne.replace("NBOMBES:", ""));
                }
                else if (ligne.startsWith("DRAPEAUX_POSES:")) {
                    drapeauxPoses = Integer.parseInt(ligne.replace("DRAPEAUX_POSES:", ""));
                }
                else if (ligne.startsWith("CASES:")) {
                    break; // Fin des infos, début des cases
                }
            }
            
            // Créer une nouvelle grille
            Grille grilleChargee = new Grille(lignesGrille, colonnesGrille, nbBombesCharge);
            
            // Mettre à jour le compteur de drapeaux
            grilleChargee.nbDrapeauxPoses = drapeauxPoses;
            int restantes = nbBombesCharge - drapeauxPoses;
            grilleChargee.labelDrapeaux.setText("Mines restantes : " + restantes);
            
            // Charger l'état de chaque case
            while ((ligne = reader.readLine()) != null) {
                String[] parties = ligne.split(",");
                if (parties.length == 5) {
                    int i = Integer.parseInt(parties[0]);
                    int j = Integer.parseInt(parties[1]);
                    boolean estBombe = Boolean.parseBoolean(parties[2]);
                    boolean estRevelee = Boolean.parseBoolean(parties[3]);
                    int etatMarquage = Integer.parseInt(parties[4]);
                    
                    CaseDemineur cas = grilleChargee.Cases[i][j];
                    cas.setEstBombe(estBombe);
                    cas.setEstRevelee(estRevelee);
                    cas.setEtatMarquage(etatMarquage);
                }
            }
            
            // Recalculer les voisins après avoir chargé l'état des bombes
            grilleChargee.calculerVoisins();
            
            // Rafraîchir l'affichage de toutes les cases
            for (int i = 0; i < grilleChargee.Cases.length; i++) {
                for (int j = 0; j < grilleChargee.Cases[i].length; j++) {
                    grilleChargee.Cases[i][j].rafraichirAffichage();
                }
            }
            
    /**
     * Vérifie si le joueur a gagné.
     * @return true si victoire
     */
            reader.close();
            JOptionPane.showMessageDialog(null, "Partie chargée avec succès !", "Chargement", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Aucune partie sauvegardée trouvée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean verifierVictoire() {
        // Parcourir toutes les cases
        int casesRevelees = 0;
        int casesSansBombe = 0;
        int bombsRevelees = 0;
        int bombsTotales = 0;
        
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                CaseDemineur cas = this.Cases[i][j];
                
                if (cas.isEstBombe()) {
                    bombsTotales++;
                    if (cas.isEstRevelee()) {
                        bombsRevelees++;
                        // Une bombe a été révélée, pas de victoire
                        return false;
                    }
                } else {
                    casesSansBombe++;
                    if (cas.isEstRevelee()) {
                        casesRevelees++;
                    } else {
                        // Une case sans bombe n'a pas été révélée, pas de victoire
    /**
     * Propage la révélation sur les cases vides.
     * @param ligne La ligne de la case
     * @param colonne La colonne de la case
     */
                        return false;
                    }
                }
            }
        }
        
        // Si on arrive ici, toutes les conditions sont remplies
        return true;
    }

    public void propagation(int ligne, int colonne) {
        // Vérifier que la case est dans les limites du tableau
        if (ligne < 0 || ligne >= lignes || colonne < 0 || colonne >= colonnes) {
            return;
        }

        CaseDemineur cas = this.Cases[ligne][colonne];

        // Si la case est déjà révélée, on arrête
        if (cas.isEstRevelee()) {
            return;
        }

        // Si c'est une bombe, on ne la révèle pas
        if (cas.isEstBombe()) {
            return;
        }

        // Révéler la case (même si elle a des mines autour)
        cas.setEstRevelee(true);

        // Si la case a des mines autour, on n'étend pas la propagation
        if (cas.getMinesVoisines() > 0) {
            return;
        }
/**
     * Affiche l'écran de victoire.
     */
    
        // Sinon (0 mines autour), on propage récursivement à tous les voisins
        for (int ligneVoisine = ligne - 1; ligneVoisine <= ligne + 1; ligneVoisine++) {
            for (int colonneVoisine = colonne - 1; colonneVoisine <= colonne + 1; colonneVoisine++) {
                // On ne traite pas la case elle-même
                if (ligneVoisine != ligne || colonneVoisine != colonne) {
                    propagation(ligneVoisine, colonneVoisine);
                }
            }
        }
    }

    public void afficherEcranVictoire() {
        this.PartieTerminee = true;
        
        Object[] options = {"Accueil", "Quitter"};
        int choix = JOptionPane.showOptionDialog(
            null,
            "Félicitations!\nVous avez remporté la partie!",
            "Victoire!",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (choix == 0) {
            // Accueil
            new MenuAcceuil();
        } else {
            // Quitter
            System.exit(0);
        }
    }
}