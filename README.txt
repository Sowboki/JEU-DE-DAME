
# Jeu de Dames

## Description
Ce jeu de dames est une implémentation classique du jeu de stratégie pour deux joueurs. Le jeu se joue sur un plateau de dames carré avec des cases alternativement blanches et noires. Chaque joueur commence avec 12 pions placés sur les cases noires des trois premières rangées de leur côté du plateau. Le but du jeu est de capturer tous les pions de l'adversaire ou de les bloquer de sorte qu'ils ne puissent plus bouger.

## Règles du Jeu
1. **Déplacement des Pions** : Les pions se déplacent en diagonale d'une case vers une case adjacente non occupée.
2. **Capture** : Un pion peut capturer un pion adverse en sautant par-dessus ce pion vers une case vide directement derrière. Les captures sont obligatoires.
3. **Promotion en Dame** : Lorsqu'un pion atteint la dernière rangée sur le côté de l'adversaire, il est promu en dame. Les dames peuvent se déplacer en diagonale de plusieurs cases et peuvent capturer un pion adverse en sautant sur n'importe quelle distance, à condition que la case de destination soit vide.
4. **Fin du Jeu** : La partie se termine lorsqu'un joueur capture tous les pions de l'adversaire ou les bloque de sorte qu'ils ne puissent plus se déplacer. Le joueur qui réussit cela gagne la partie.

## Comment Jouer
- Lancez le jeu en exécutant `Interface.java` (compilation avec 'javac .*java')
- Entrez votre nom et choisissez le mode de jeu (Joueur vs Joueur ou Joueur vs Ordinateur).
- Choisissez le niveau de difficulté si vous jouez contre l'ordinateur.
- Cliquez sur une case pour sélectionner un pion, puis cliquez sur une case de destination pour le déplacer.
- Suivez les règles de déplacement et de capture.

#NB:Problèmes Techniques
J'ai rencontré un problème concernant l'interaction avec le plateau de jeu. Malgré tous mes efforts, les pions refusent de se déplacer. De plus, les couleurs censées indiquer la position actuelle des pions et leur chemin de déplacement possible ne changent pas, malgré mes tentatives de modification dans le code. Ce problème pourrait être dû à des erreurs dans la gestion des événements ou dans la mise à jour de l'affichage du plateau. Toute aide ou suggestion pour résoudre ces problèmes serait grandement appréciée
