import package1.Plateau;    // Importe la classe Plateau du package1
import package1.Pion;       // Importe la classe Pion du package1
import package1.PionButton; // Importe la classe PionButton du package1
import package1.Case;       // Importe la classe Case du package1

import javax.swing.*;       // Importe toutes les classes de javax.swing pour les interfaces graphiques
import java.awt.*;          // Importe toutes les classes de java.awt pour les composants et les événements graphiques

// Définition de la classe Interface qui étend JFrame pour créer une fenêtre
public class Interface extends JFrame {
    private Plateau plateau;                    // Représente le plateau de jeu
    private String nomJoueur;                   // Stocke le nom du joueur
    private boolean modeJeuContreOrdinateur;    // Indique si le jeu est contre l'ordinateur
    private int niveauDifficulte;               // Niveau de difficulté : 1 pour facile, 2 pour moyen, 3 pour difficile

    // Constructeur de l'Interface
    public Interface(int taillePlateau) {
        this.plateau = new Plateau(taillePlateau);  // Initialise le plateau avec la taille donnée

        setTitle("Jeu de Dames");                    // Définit le titre de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Opération par défaut à la fermeture de la fenêtre

        afficherEcranBienvenue(); // Affiche l'écran de bienvenue pour la configuration initiale

        JPanel plateauPanel = createPlateauPanel();  // Crée le panneau du plateau de jeu
        add(plateauPanel);                           // Ajoute le panneau du plateau à la fenêtre

        initialiserGraphique();  // Initialise les aspects graphiques du jeu
        pack();                  // Adapte la fenêtre à la taille des composants
        setLocationRelativeTo(null);  // Centre la fenêtre
        setVisible(true);        // Rend la fenêtre visible
    }

    // Méthode pour afficher l'écran de bienvenue et configurer le jeu
    private void afficherEcranBienvenue() {
        nomJoueur = JOptionPane.showInputDialog(this, "Entrez votre nom :"); // Demande le nom du joueur

        // Options pour choisir le mode de jeu
        Object[] options = {"Joueur vs Joueur", "Joueur vs Ordinateur"};
        int modeJeu = JOptionPane.showOptionDialog(this, 
                "Choisissez le mode de jeu :", 
                "Sélection du Mode de Jeu", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, options, options[0]);

        modeJeuContreOrdinateur = (modeJeu == JOptionPane.NO_OPTION); // Détermine si le mode contre l'ordinateur est sélectionné

        // Si le jeu est contre l'ordinateur, demande de choisir la difficulté
        if (modeJeuContreOrdinateur) {
            Object[] difficultes = {"Facile", "Moyen", "Difficile"};
            niveauDifficulte = JOptionPane.showOptionDialog(this, 
                    "Choisissez la difficulté :", 
                    "Sélection de la Difficulté", 
                    JOptionPane.YES_NO_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, difficultes, difficultes[0]) + 1;
        }
    }

    // Méthode pour initialiser les aspects graphiques du plateau
    private void initialiserGraphique() {
        for (int y = 0; y < plateau.getTaille(); y++) {
            for (int x = 0; x < plateau.getTaille(); x++) {
                plateau.getCase(x, y).updateBackground(); // Met à jour l'arrière-plan de chaque case
            }
        }
    }

    // Crée et retourne le panneau contenant le plateau de jeu
    private JPanel createPlateauPanel() {
        JPanel plateauPanel = new JPanel(new GridLayout(plateau.getTaille(), plateau.getTaille())); // Utilise GridLayout
        for (int y = 0; y < plateau.getTaille(); y++) {
            for (int x = 0; x < plateau.getTaille(); x++) {
                Case caseActuelle = plateau.getCase(x, y);
                plateauPanel.add(caseActuelle.getCaseButton()); // Ajoute le bouton de chaque case au panneau
            }
        }
        return plateauPanel;
    }

    // Point d'entrée du programme
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interface(10)); // Lance l'interface dans le fil d'exécution de l'interface graphique
    }
}
