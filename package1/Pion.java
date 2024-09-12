package package1; // Déclaration du package

// Classe Pion représentant un pion dans le jeu de dames
public class Pion {
    // Coordonnées du pion sur le plateau
    private int coordX;
    private int coordY;

    // Type du pion ("pion" ou "dame")
    private String typePion;

    // Couleur du pion (true si marron, false si blanc)
    private boolean marron;

    // Constructeur de la classe Pion
    public Pion(int coordX, int coordY, boolean marron) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.typePion = "pion"; // Par défaut, le type est "pion"
        this.marron = marron;
    }

    // Retourne la coordonnée X du pion
    public int getX() {
        return coordX;
    }

    // Retourne la coordonnée Y du pion
    public int getY() {
        return coordY;
    }

    // Définit la position du pion
    public void setPosition(int x, int y) {
        this.coordX = x;
        this.coordY = y;
    }

    // Vérifie si le pion est de couleur marron
    public boolean isMarron() {
        return marron;
    }

    // Vérifie si le pion est de couleur blanche
    public boolean isBlanc() {
        return !marron;
    }

    // Vérifie si le pion est une dame
    public boolean isDame() {
        return "dame".equals(typePion);
    }

    // Promouvoir le pion en dame
    public void promouvoirEnDame() {
        this.typePion = "dame";
    }

    // Vérifie si le pion peut se déplacer à une position donnée
    public boolean peutBouger(int xDest, int yDest, Plateau plateau) {
        // Vérifie si la position est valide et vide
        if (!plateau.estPositionValide(xDest, yDest) || !plateau.estVide(xDest, yDest)) {
            return false;
        }

        // Applique la logique de déplacement spécifique selon le type de pion
        if (!isDame()) {
            return peutBougerCommePionSimple(xDest, yDest);
        } else {
            return peutBougerCommeDame(xDest, yDest, plateau);
        }
    }

    // Logique de déplacement spécifique pour un pion simple
    private boolean peutBougerCommePionSimple(int xDest, int yDest) {
        int deltaX = Math.abs(xDest - coordX);
        int deltaY = Math.abs(yDest - coordY);

        // Autorise un déplacement d'une case en diagonale
        if (deltaX == 1 && deltaY == 1) {
            return (marron && yDest > coordY) || (!marron && yDest < coordY);
        }
        return false;
    }

    // Logique de déplacement spécifique pour une dame
    private boolean peutBougerCommeDame(int xDest, int yDest, Plateau plateau) {
        int deltaX = Math.abs(xDest - coordX);
        int deltaY = Math.abs(yDest - coordY);

        // Autorise un déplacement en diagonale si les cases sur le trajet sont vides
        if (deltaX == deltaY && deltaX > 0) {
            int xDirection = Integer.compare(xDest, coordX);
            int yDirection = Integer.compare(yDest, coordY);
            for (int x = coordX + xDirection, y = coordY + yDirection; x != xDest; x += xDirection, y += yDirection) {
                if (!plateau.estVide(x, y)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Capture d'un pion adverse
    public boolean mange(Pion cible, Plateau plateau) {
        int xCible = cible.getX();
        int yCible = cible.getY();

        // Calcule la position de capture
        int xCapture = (getX() + xCible) / 2;
        int yCapture = (getY() + yCible) / 2;

        // Vérifie la validité de la capture
        if (!plateau.estPositionValide(xCapture, yCapture)) {
            return false;
        }

        // Vérifie si la case de capture contient un pion adverse
        Pion pionCapture = plateau.getPionDepuisCase(new int[]{xCapture, yCapture});
        if (pionCapture != null && pionCapture.isMarron() != isMarron()) {
            plateau.retirerPion(pionCapture);
            plateau.deplacerPion(this, xCible, yCible);
            return true;
        }

        return false;
    }

    // Vérifie si le pion est toujours en jeu
    public boolean isVivant() {
        return coordX >= 0 && coordY >= 0;
    }
}
