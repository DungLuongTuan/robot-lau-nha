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
public class Dust extends Dirty {
    public Dust(int x, int y) {
        super(x, y);
    }
    @Override
    public void updateRotation() {
        
    }

    @Override
    public void updateRunning() {
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Scalr.resize(Assets.dust, Scalr.Method.BALANCED, (int) Game.sizeDirty, (int) Game.sizeDirty), super.getX(), super.getY(), null);
    }
}
