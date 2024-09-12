package package1; // Déclaration du package

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;

// Classe Case représentant une case du plateau de jeu
public class Case {
    private Pion pion;            // Pion présent sur la case
    private boolean estBlanc;     // Indique si la couleur de la case est blanche
    private PionButton caseButton; // Bouton personnalisé pour la case

    // Constructeur de la classe Case
    public Case() {
        this.pion = null;         // Initialement, aucune pièce n'est présente sur la case
        this.estBlanc = false;    // Par défaut, la case n'est pas blanche
        this.caseButton = new PionButton(); // Crée un nouveau bouton pour la case
        updateBackgroundColor();  // Met à jour la couleur de fond de la case
    }

    // Définit le pion présent sur la case et met à jour son affichage
    public void setPion(Pion pion) {
        this.pion = pion;
        updateCase();
    }

    // Retourne le bouton de la case
    public PionButton getCaseButton() {
        return this.caseButton;
    }

    // Retourne le pion présent sur la case
    public Pion getPion() {
        return this.pion;
    }

    // Vérifie si la case contient un pion
    public boolean hasPion() {
        return this.pion != null;
    }

    // Définit la couleur de la case et met à jour son affichage
    public void setBlanc(boolean estBlanc) {
        this.estBlanc = estBlanc;
        updateBackgroundColor();
    }

    // Vérifie si la couleur de la case est blanche
    public boolean isBlanc() {
        return this.estBlanc;
    }

    // Met à jour l'affichage du pion sur la case
    public void updateCase() {
        if (pion != null) {
            // Utilise la couleur rouge pour les pions blancs
            if (pion.isBlanc()) {
                caseButton.setPionColor(Color.RED);
            } else {
                // Utilise la couleur noire pour les pions noirs
                caseButton.setPionColor(Color.BLACK);
            }
        } else {
            caseButton.setPionColor(null); // Aucun pion sur la case
        }
    }

    // Met à jour la couleur de fond de la case
    private void updateBackgroundColor() {
        Color brown = new Color(165, 42, 42); // Définit une couleur marron

        if (estBlanc) {
            caseButton.setBackground(Color.WHITE); // Fond blanc si la case est blanche
        } else {
            caseButton.setBackground(brown); // Fond marron sinon
        }
    }

    // Met à jour l'arrière-plan de la case
    public void updateBackground() {
        updateBackgroundColor();
    }
}
