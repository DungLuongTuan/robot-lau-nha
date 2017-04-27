/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics;

/**
 *
 * @author 8TITTIT8
 */
public class RunObstacle extends Entity {

    public RunObstacle(int x, int y) {
        super(x, y);
    }
    
    public void updateRotation() {
        
    }
    
    public void updateRunning() {
        
    }
    
    public void render(Graphics g) {
        g.drawImage(Assets.runObstacle, super.getX(), super.getY(), null);
    }
}
