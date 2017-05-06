/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author 8TITTIT8
 */

public class RunObstacle extends Entity {
    
    private int velX, velY;
    private Random rand;
    
    public RunObstacle(int x, int y) {
        super(x, y);
        rand = new Random();
        this.velX = rand.nextInt(3) - 1;
        this.velY = 0;
    }
    
    @Override
    public void updateRotation() {
        
    }
    
    @Override
    public void updateRunning() {
//        System.out.println(this.velX + " " + this.velY);
//        super.setX(super.getX() + this.velX);
//        super.setY(super.getY() + this.velY);
//        if (((super.getX() - 22) % 60 == 0) && ((super.getY() - 22) % 60 == 0)) {
//            if (super.getX() - 22 == 60) this.velX = rand.nextInt(2);
//            else
//                if (super.getX() - 22 == 480) this.velX = rand.nextInt(2) - 1;
//                else this.velX = rand.nextInt(3) - 1;
//            
//            if (super.getY() - 22 == 60) this.velY = rand.nextInt(2);
//            else
//                if (super.getY() - 22 == 480) this.velY = rand.nextInt(2) - 1;
//                else this.velY = rand.nextInt(3) - 1;
//            
//            int d = rand.nextInt(2);
//            if ((this.velX != 0) && ( this.velY != 0))
//                if (d == 0) this.velX = 0;
//                else this.velY = 0;
//        }
        
        super.setX(super.getX() + this.velX);
        super.setY(super.getY() + this.velY);
        if (((int) (super.getX() - 20 - (Game.sizeCell - Game.sizeObject)/2) % (int) Game.sizeCell == 0) && ((int) (super.getY() - 20 - (Game.sizeCell - Game.sizeObject)/2) % (int) Game.sizeCell == 0)) {
            if ((int) (super.getX() - 20 - (Game.sizeCell - Game.sizeObject)/2) == Game.sizeCell) this.velX = rand.nextInt(2);
            else
                if ((int) (super.getX() - 20 - (Game.sizeCell - Game.sizeObject)/2) == Game.sizeCell*(Game.sizeMap - 2)) this.velX = rand.nextInt(2) - 1;
                else this.velX = rand.nextInt(3) - 1;
            
            if ((int) (super.getY() - 20 - (Game.sizeCell - Game.sizeObject)/2) == Game.sizeCell) this.velY = rand.nextInt(2);
            else
                if ((int) (super.getY() - 20 - (Game.sizeCell - Game.sizeObject)/2) == Game.sizeCell*(Game.sizeMap - 2)) this.velY = rand.nextInt(2) - 1;
                else this.velY = rand.nextInt(3) - 1;
            
            int d = rand.nextInt(2);
            if ((this.velX != 0) && ( this.velY != 0))
                if (d == 0) this.velX = 0;
                else this.velY = 0;
        }

    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(Scalr.resize(Assets.runObstacle, Scalr.Method.BALANCED, (int) Game.sizeObject, (int) Game.sizeObject), super.getX(), super.getY(), null);
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }
    
    
}
