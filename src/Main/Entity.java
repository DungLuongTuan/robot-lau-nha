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
public abstract class Entity {
    private int x;
    private int y;
    
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void updateRotation();
    public abstract void updateRunning();
    public abstract void render(Graphics g);
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
