/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author 8TITTIT8
 */
public class paintJPanel extends JPanel {
    private BufferedImage image;
    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(this.image, 0, 0, this);
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
