/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author 8TITTIT8
 */
public class Floor {
    private ArrayList<Entity> Entities = new ArrayList<>();
    
    public Floor() {
        Brick brick;
        for(int i = 0; i < Game.sizeMap; ++i){
            brick = new Brick((int) (20 + (Game.sizeCell - Game.sizeObject)/2),(int) (i*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2));
            this.Entities.add(brick);
            brick = new Brick((int) (20 + Game.sizeCell*(Game.sizeMap - 1) + (Game.sizeCell - Game.sizeObject)/2), (int) (i*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2));
            this.Entities.add(brick);
        }
        for(int i = 1; i < Game.sizeMap - 1; ++i) {
            brick = new Brick((int) (i*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2), (int) (20 + (Game.sizeCell - Game.sizeObject)/2));
            this.Entities.add(brick);
            brick = new Brick((int) (i*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2), (int) (20 + Game.sizeCell*(Game.sizeMap - 1) + (Game.sizeCell - Game.sizeObject)/2));
            this.Entities.add(brick);
        }
    }
    
    public ArrayList<Entity> getEntities() {
        return this.Entities;
    }
    
    public void addEntity (Entity e) {
        this.Entities.add(e);
    }
}
