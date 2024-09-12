package package1;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class PionButton extends JButton {
    private Color pionColor;

    public PionButton() {
        setContentAreaFilled(false); // Empêche le bouton de remplir sa zone de contenu
    }

    public void setPionColor(Color color) {
        this.pionColor = color;
        repaint(); // Redessine le bouton avec la nouvelle couleur
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pionColor != null) {
            g.setColor(pionColor);
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10); // Dessine un cercle
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50); // Taille préférée du bouton
    }
}
