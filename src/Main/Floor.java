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
        for(int i = 0; i < 10; ++i){
            brick = new Brick(20 + 2, i*60 + 20 + 2);
            this.Entities.add(brick);
            brick = new Brick(560 + 2, i*60 + 20 + 2);
            this.Entities.add(brick);
        }
        for(int i = 1; i < 9; ++i) {
            brick = new Brick(i*60 + 20 + 2, 20 + 2);
            this.Entities.add(brick);
            brick = new Brick(i*60 + 20 + 2, 560 + 2);
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
