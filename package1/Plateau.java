package package1; // Déclaration du package

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;

// Classe Plateau représentant le plateau de jeu
public class Plateau {
    private Case[][] cases; // Tableau 2D de cases
    private int taille;     // Taille du plateau
    private Pion pionSelectionne; // Pion actuellement sélectionné par le joueur
    private boolean joueurActuel; // Indique le joueur actuel (true ou false)

    // Constructeur de Plateau
    public Plateau(int taille) {
        this.taille = taille; // Initialisation de la taille
        this.cases = new Case[taille][taille]; // Initialisation du tableau de cases
        initialiserCases();  // Initialisation des cases du plateau
        initialiserPions();  // Place les pions sur le plateau
        joueurActuel = true; // Définit le joueur actuel comme true par défaut
    }

    // Retourne la taille du plateau
    public int getTaille() {
        return taille;
    }
	
    // Retourne la case aux coordonnées spécifiées
    public Case getCase(int x, int y) {
        return cases[y][x];
    }
	
    // Initialisation des cases du plateau avec des couleurs alternées
    private void initialiserCases() {
        boolean doitEtreBlanc;
        for (int y = 0; y < this.cases.length; y++) {
            doitEtreBlanc = (y % 2 == 0);
            for (int x = 0; x < this.cases[y].length; x++) {
                this.cases[y][x] = new Case();
                this.cases[y][x].setBlanc(doitEtreBlanc);
                doitEtreBlanc = !doitEtreBlanc; // Alterne la couleur de la case
            }
        }
    }

	public void initialiserPions() {
		// Nombre de pions par joueur
		int nombrePions = 20;

		// Initialiser les pions blancs
		int pionsPlaces = 0; // Compteur pour le nombre de pions placés
		for (int y = 0; y < taille; y++) {
			for (int x = 0; x < taille; x++) {
				if ((x + y) % 2 != 0) { // Placer sur les cases noires
					if (pionsPlaces < nombrePions) {
						Pion pionBlanc = new Pion(x, y, true); // true pour blanc
						this.cases[y][x].setPion(pionBlanc);
						this.cases[y][x].updateCase(); // Met à jour l'affichage de la case
						pionsPlaces++;
					}
				}
			}
			if (pionsPlaces >= nombrePions) break; // Arrêter dès que 12 pions sont placés
		}

		// Initialiser les pions noirs
		pionsPlaces = 0;
		for (int y = taille - 1; y >= 0; y--) {
			for (int x = 0; x < taille; x++) {
				if ((x + y) % 2 != 0) { // Placer sur les cases noires
					if (pionsPlaces < nombrePions) {
						Pion pionNoir = new Pion(x, y, false); // false pour noir
						this.cases[y][x].setPion(pionNoir);
						this.cases[y][x].updateCase(); // Met à jour l'affichage de la case
						pionsPlaces++;
					}
				}
			}
			if (pionsPlaces >= nombrePions) break; // Arrêter dès que 12 pions sont placés
		}
	}




	public JPanel createPlateauPanel() {
		JPanel plateauPanel = new JPanel(new GridLayout(taille, taille));
		for (int y = 0; y < taille; y++) {
			for (int x = 0; x < taille; x++) {
				PionButton caseButton = cases[y][x].getCaseButton();
				caseButton.addActionListener(new CaseActionListener(x, y));
				plateauPanel.add(caseButton);
			}
		}
		return plateauPanel;
	}


    public boolean estMouvementLegal(Pion pion, int xDest, int yDest) {
        if (pion.isDame()) {
            int deltaX = Math.abs(pion.getX() - xDest);
            int deltaY = Math.abs(pion.getY() - yDest);

            if (deltaX == deltaY && deltaX > 0) {
                return true;
            }
        } else {
            if (Math.abs(pion.getX() - xDest) == 1 &&
                    ((pion.isBlanc() && yDest == pion.getY() + 1) ||
                            (!pion.isBlanc() && yDest == pion.getY() - 1))) {
                return true;
            }
        }

        if (Math.abs(pion.getX() - xDest) == 2) {
            return effectuerCapture(pion, xDest, yDest);
        }

        return false;
    }

    public boolean estPositionValide(int x, int y) {
        return x >= 0 && x < taille && y >= 0 && y < taille;
    }

    public boolean estVide(int x, int y) {
        if (estPositionValide(x, y)) {
            return cases[y][x].getPion() == null;
        }
        return false; // Position invalide
    }

    public void retirerPion(Pion pion) {
        if (estPositionValide(pion.getX(), pion.getY())) {
            cases[pion.getY()][pion.getX()].setPion(null);
        }
    }

    public void deplacerPion(Pion pion, int xDest, int yDest) {
        if (estPositionValide(xDest, yDest) && estVide(xDest, yDest)) {
            cases[pion.getY()][pion.getX()].setPion(null);
            cases[yDest][xDest].setPion(pion);
            pion.setPosition(xDest, yDest);
        }
    }

    private class CaseActionListener implements ActionListener {
        private final int x;
        private final int y;

        public CaseActionListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Pion pion = null;
            if (pionSelectionne != null) {
                pion = pionSelectionne;
            } else if (hasPion(new int[]{x, y})) {
                pion = getPionDepuisCase(new int[]{x, y});
            }

            if (pion != null) {
                if (pionSelectionne == null) {
                    if (pion.isBlanc() == joueurActuel) {
                        pionSelectionne = pion;
                        highlightCase(x, y);
                    }
                } else if (pionSelectionne.equals(pion)) {
                    pionSelectionne = null;
                    unhighlightCase(x, y);
                    update();
                } else {
                    if (estMouvementLegal(pionSelectionne, x, y)) {
                        cases[pionSelectionne.getY()][pionSelectionne.getX()].setPion(null);
                        cases[y][x].setPion(pionSelectionne);
                        pionSelectionne.setPosition(x, y);
                        verifierPromotionPion(pionSelectionne);

                        if (!effectuerCapture(pionSelectionne, x, y) || !peutIlManger(pionSelectionne)) {
                            pionSelectionne = null;
                            changerTour();
                            update();
                        }
                    }
                }
            } else if (pionSelectionne != null) {
                pionSelectionne = null;
                unhighlightCase(x, y);
                update();
            }
        }
    }








	// Met à jour l'affichage du plateau
	public void update() {
		for (int y = 0; y < taille; y++) {
			for (int x = 0; x < taille; x++) {
				Case caseActuelle = cases[y][x];
				Pion pion = caseActuelle.getPion();

				if (pion != null) {
					// Met à jour l'affichage de la case avec le pion
					caseActuelle.setPion(pion);
				} else {
					// Retire tout pion de l'affichage de la case
					caseActuelle.setPion(null);
				}

				caseActuelle.updateCase(); // Met à jour l'affichage graphique de la case
			}
		}
	}

	// Met en évidence une case spécifique
	public void highlightCase(int x, int y) {
		JButton caseButton = cases[y][x].getCaseButton();
		caseButton.setBackground(Color.yellow);
	}

	// Supprime la mise en évidence d'une case spécifique
	public void unhighlightCase(int x, int y) {
		JButton caseButton = cases[y][x].getCaseButton();

		if (cases[y][x].isBlanc()) {
			caseButton.setBackground(Color.white);
		} else {
			caseButton.setBackground(Color.black);
		}
	}

	// Change le tour du joueur actuel
	public void changerTour() {
		joueurActuel = !joueurActuel;
	}

	// Vérifie si un pion doit être promu en dame
	public void verifierPromotionPion(Pion pion) {
		if (pion.isBlanc() && pion.getY() == taille - 1 ||
				!pion.isBlanc() && pion.getY() == 0) {
			pion.promouvoirEnDame();
		}
	}

	// Retourne le pion à une position donnée
	public Pion getPionDepuisCase(int[] pos) {
		if (pos == null || pos.length != 2) {
			return null;
		}

		int x = pos[0];
		int y = pos[1];

		if (estPositionValide(x, y)) {
			return this.cases[y][x].getPion();
		} else {
			return null;
		}
	}

	// Vérifie si une position donnée contient un pion
	public boolean hasPion(int[] pos) {
		try {
			return this.cases[pos[1]][pos[0]].hasPion();
		} catch (Exception e) {
			return false;
		}
	}    

	// Exécute la capture d'un pion
	public boolean effectuerCapture(Pion pion, int xDest, int yDest) {
		int xCapture = (pion.getX() + xDest) / 2;
		int yCapture = (pion.getY() + yDest) / 2;

		if (hasPion(new int[]{xCapture, yCapture})) {
			Pion pionCapture = getPionDepuisCase(new int[]{xCapture, yCapture});
			if (pionCapture != null && pionCapture.isBlanc() != pion.isBlanc()) {
				cases[yCapture][xCapture].setPion(null);
				cases[pion.getY()][pion.getX()].setPion(null);
				cases[yDest][xDest].setPion(pion);
				pion.setPosition(xDest, yDest);
				return true;
			}
		}
		return false;
	}


	public boolean peutIlManger(Pion pion) {
		boolean returned = false;
		for (int x = pion.getX() - 2; x <= pion.getX() + 2; x++) {
			for (int y = pion.getY() - 2; y <= pion.getY() + 2; y++) {
				if (estPositionValide(x, y) && Math.abs(x - pion.getX()) == 2 && Math.abs(y - pion.getY()) == 2) {
					int[] curPos = {x, y};
					if (hasPion(curPos)) {
						Pion cibleTest = getPionDepuisCase(curPos);
						if (cibleTest != null && cibleTest.isBlanc() != pion.isBlanc()) {
							int xCapture = (pion.getX() + x) / 2;
							int yCapture = (pion.getY() + y) / 2;
							if (estVide(xCapture, yCapture)) {
								returned = true;
							}
						}
					}
				}
			}
		}
		return returned;
	}


	// Vérifie si la partie est terminée
	public boolean estPartieTerminee() {
		boolean blancsRestants = false, noirsRestants = false;
		for (Case[] ligne : cases) {
			for (Case caseActuelle : ligne) {
				Pion pion = caseActuelle.getPion();
				if (pion != null) {
					if (pion.isBlanc()) {
						blancsRestants = true;
					} else {
						noirsRestants = true;
					}
					if (blancsRestants && noirsRestants) {
						return false;
					}
				}
			}
		}
		return true; // La partie est terminée si l'un des joueurs n'a plus de pions
	}

	// Vérifie si le joueur avec les pions blancs a gagné
	public boolean estVainqueurBlanc() {
		for (Case[] ligne : cases) {
			for (Case caseActuelle : ligne) {
				Pion pion = caseActuelle.getPion();
				if (pion != null && !pion.isBlanc()) {
					return false; // Si un pion noir est trouvé, le joueur blanc n'a pas gagné
				}
			}
		}
		return true; // Si aucun pion noir n'est trouvé, le joueur blanc a gagné
	}
}
