/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 *
 * @author 8TITTIT8
 */
public class Robot extends Entity{
    private int velX = 1;
    private int velY = 1;
    private int angle = 0;
    private boolean isRunning;
    private boolean isDoing;
    private int isSpinning;
    
    public Robot(int x, int y) {
        super(x, y);
    }

    @Override
    public void updateRotation() {
        int goalAngle = this.angle;
        ArrayList<Integer> rootClone = new ArrayList<>(Game.root);
        
        if (rootClone.get(Game.currentAction) == 0) {
            if (Game.action.get(Game.currentAction) == 0) goalAngle = 180;
            if (Game.action.get(Game.currentAction) == 1) goalAngle = 270;
            if (Game.action.get(Game.currentAction) == 2) goalAngle = 0;
            if (Game.action.get(Game.currentAction) == 3) goalAngle = 90;
        }
        if (rootClone.get(Game.currentAction) == 1) {
            if (Game.action.get(Game.currentAction) == 0) goalAngle = 270;
            if (Game.action.get(Game.currentAction) == 1) goalAngle = 0;
            if (Game.action.get(Game.currentAction) == 2) goalAngle = 90;
            if (Game.action.get(Game.currentAction) == 3) goalAngle = 180;
        }
        if (rootClone.get(Game.currentAction) == 2) {
            if (Game.action.get(Game.currentAction) == 0) goalAngle = 0;
            if (Game.action.get(Game.currentAction) == 1) goalAngle = 90;
            if (Game.action.get(Game.currentAction) == 2) goalAngle = 180;
            if (Game.action.get(Game.currentAction) == 3) goalAngle = 270;
        }
        if (rootClone.get(Game.currentAction) == 3) {
            if (Game.action.get(Game.currentAction) == 0) goalAngle = 90;
            if (Game.action.get(Game.currentAction) == 1) goalAngle = 180;
            if (Game.action.get(Game.currentAction) == 2) goalAngle = 270;
            if (Game.action.get(Game.currentAction) == 3) goalAngle = 0;
        }
        //System.out.println(this.angle + " " + goalAngle + " " + Game.currentAction + " " + rootClone.get(Game.currentAction) + " " + Game.action.get(Game.currentAction));
        if (this.angle != goalAngle) {
            if (((360 + this.angle - goalAngle) % 360) > ((360 + goalAngle - this.angle) % 360)) this.angle = (this.angle + 1) % 360;
            else this.angle = (this.angle - 1 + 360) % 360;
        }
        else {
            this.isRunning = true;
            this.isDoing = false;
        }

    }
    
    public void updateSpinning() {
        this.angle = (this.angle + 1) % 360;
        int goalAngle = this.angle;
        ArrayList<Integer> rootClone = new ArrayList<>(Game.root);
        
        if (rootClone.get(Game.currentAction) == 0) {
            if (Game.action.get(Game.currentAction) == 0) goalAngle = 180;
            if (Game.action.get(Game.currentAction) == 1) goalAngle = 270;
            if (Game.action.get(Game.currentAction) == 2) goalAngle = 0;
            if (Game.action.get(Game.currentAction) == 3) goalAngle = 90;
        }
        if (rootClone.get(Game.currentAction) == 1) {
            if (Game.action.get(Game.currentAction) == 0) goalAngle = 270;
            if (Game.action.get(Game.currentAction) == 1) goalAngle = 0;
            if (Game.action.get(Game.currentAction) == 2) goalAngle = 90;
            if (Game.action.get(Game.currentAction) == 3) goalAngle = 180;
        }
        if (rootClone.get(Game.currentAction) == 2) {
            if (Game.action.get(Game.currentAction) == 0) goalAngle = 0;
            if (Game.action.get(Game.currentAction) == 1) goalAngle = 90;
            if (Game.action.get(Game.currentAction) == 2) goalAngle = 180;
            if (Game.action.get(Game.currentAction) == 3) goalAngle = 270;
        }
        if (rootClone.get(Game.currentAction) == 3) {
            if (Game.action.get(Game.currentAction) == 0) goalAngle = 90;
            if (Game.action.get(Game.currentAction) == 1) goalAngle = 180;
            if (Game.action.get(Game.currentAction) == 2) goalAngle = 270;
            if (Game.action.get(Game.currentAction) == 3) goalAngle = 0;
        }
        if (goalAngle == this.angle) {
            if (this.isSpinning > 0) this.isSpinning--;
        }
    }
    
    public void updateRunning() {
        ArrayList<Integer> cellXClone = new ArrayList<>(Game.cellX);
        ArrayList<Integer> cellYClone = new ArrayList<>(Game.cellY);
        boolean ok = true;
        
        if (this.angle == 0) {
            super.setY(super.getY() + 1);
            if ((super.getY() - 22) % 60 == 0) {
                Game.cellX.add(super.getX() - 2);
                Game.cellY.add(super.getY() - 2);
                this.isRunning = false;
            }
        }
        if (this.angle == 90) {
            super.setX(super.getX() - 1);
            if ((super.getX() - 22) % 60 == 0) {
                Game.cellX.add(super.getX() - 2);
                Game.cellY.add(super.getY() - 2);
                this.isRunning = false;
            }
        }
        if (this.angle == 180) {
            super.setY(super.getY() - 1);
            if ((super.getY() - 22) % 60 == 0) {
                Game.cellX.add(super.getX() - 2);
                Game.cellY.add(super.getY() - 2);
                this.isRunning = false;
            }
        }
        if (this.angle == 270) {
            super.setX(super.getX() + 1);
            if ((super.getX() - 22) % 60 == 0) {
                Game.cellX.add(super.getX() - 2);
                Game.cellY.add(super.getY() - 2);
                this.isRunning = false;
            }
        }
        
    }
    
    @Override
    public void render (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.YELLOW);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.6));
        
        //
        AffineTransform backup = g2.getTransform();
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(this.angle), super.getX() + 28, super.getY() + 28);
        g2.transform(trans);
        g2.fillArc(super.getX() + 28 - 80, super.getY() + 28 - 80, 160, 160, 150, 240);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1));
        g2.drawImage(Assets.robot, super.getX(), super.getY(), null);
        g2.setTransform(backup);
        
    } 

    public int getVelX() {
        if (this.angle == 90) return -1;
        if (this.angle == 270) return 1;
        return 0;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        if (this.angle == 0) return 1;
        if (this.angle == 180) return -1;
        return 0;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    
    public boolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean getIsDoing() {
        return isDoing;
    }

    public void setIsDoing(boolean isDoing) {
        this.isDoing = isDoing;
    }

    public int getIsSpinning() {
        return isSpinning;
    }

    public void setIsSpinning(int isSpinning) {
        this.isSpinning = isSpinning;
    }

}
