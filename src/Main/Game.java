/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import controllers.MainController;
import static controllers.MainController.collision;
import game.display.Display;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 8TITTIT8
 */

public class Game implements Runnable {
    
    public static ArrayList<Integer> node;
    public static ArrayList<Integer> father;
    public static ArrayList<Integer> nodeX;
    public static ArrayList<Integer> nodeY;
    public static ArrayList<Integer> action;
    public static ArrayList<Integer> root;
    public static ArrayList<Integer> cellX;
    public static ArrayList<Integer> cellY;
    public static ArrayList<Integer> cleanAction = new ArrayList<>();
    public static ArrayList<Integer> dirtyType = new ArrayList<>();
    public static int[][][][][][][][] status = new int[7][4][2][2][2][2][2][2];
    public static ArrayList<int[]> movement = new ArrayList<>();
    public static Floor floor;
    public static Robot robot;
    public static int currentNode;
    public static int currentAction = -1000000000;
    public static boolean isRunning = false;
    public static int algorithm = 1;
    
    // resizeable map
    public static int sizeMap = 10;
    public static int sizeCell = 60;
    public static int sizeDirty = 50;
    public static int sizeObject = 56;
    
    
    private int width;
    private int height;
    private String title;
    private Canvas canvas;
    private volatile Thread thread;
    private boolean running;
    private Graphics g;
    private BufferStrategy bs;
    private int sparkle;
    
    public Game(String title) {
        this.width = 600;
        this.height = 600;
        this.title = title;
        this.sparkle = 1;
    }
    
    public void init() {
        Assets.init();
    }
    
    public void update() {
        ArrayList<Integer> actionClone = new ArrayList<>(this.action);
        Random rand = new Random();
        boolean collision = false;
        boolean wallCollison = false;
        
        if (isRunning == true) {
            // xử lí va chạm vật cản di động và vật cản cô định
            for(Entity e : this.floor.getEntities())
                if(e instanceof RunObstacle) {
                    wallCollison = false;
                    for(Entity e1 : this.floor.getEntities())
                        if (!(e1 instanceof RunObstacle) && !(e1 instanceof Dirty))
                            if (MainController.collision(e.getX() + ((RunObstacle) e).getVelX(), e.getY() + ((RunObstacle) e).getVelY(), e1.getX(), e1.getY())) {
                                if (((RunObstacle) e).getVelX() == 0) {
                                    ((RunObstacle) e).setVelX(rand.nextInt(3) - 1);
                                    if (((RunObstacle) e).getVelX() == 0) ((RunObstacle) e).setVelY(((RunObstacle) e).getVelY() * -1);
                                    else ((RunObstacle) e).setVelY(0);
                                }

                                if (((RunObstacle) e).getVelY() == 0) {
                                    ((RunObstacle) e).setVelY(rand.nextInt(3) - 1);
                                    if (((RunObstacle) e).getVelY() == 0) ((RunObstacle) e).setVelX(((RunObstacle) e).getVelX() * -1);
                                    else ((RunObstacle) e).setVelX(0);
                                }
//                                System.out.println("11111");
//                                System.out.println(e.getX() + " " + e.getY() + " " + ((RunObstacle) e).getVelX() + " " + ((RunObstacle) e).getVelY());
//                                System.out.println(e1.getX() + " " + e1.getY());
                                wallCollison = true;
                            }
                    
                    if (this.robot != null)
                        if (MainController.collision(this.robot.getX() + this.robot.getVelX(), this.robot.getY() + this.robot.getVelY(), e.getX() + ((RunObstacle) e).getVelX(), e.getY() + ((RunObstacle) e).getVelY())) {
                            e.updateRunning();
//                            System.out.println("2222");
                            collision = true;
                            continue;
                        }
                    
                    if (!wallCollison) e.updateRunning();
                }
            
            if (collision) return;
            
            if (this.robot != null && this.currentAction != -1000000000) {
                if (this.robot.getIsDoing()) this.robot.updateRotation();
                if (this.robot.getIsRunning()) this.robot.updateRunning();
                if (this.robot.getIsSpinning() != 0) this.robot.updateSpinning();
                if (!this.robot.getIsDoing() && !this.robot.getIsRunning()) {
                    
                    for (Entity e : this.floor.getEntities()) {
                        if (e instanceof Water)
                            if (((int) (e.getX() - (Game.sizeCell - Game.sizeDirty)/2) == (int) (this.robot.getX() - (Game.sizeCell - Game.sizeObject)/2)) && ((int) (e.getY() - (Game.sizeCell - Game.sizeDirty)/2) == (int) (this.robot.getY() - (Game.sizeCell - Game.sizeObject)/2))) {
                                this.robot.setIsSpinning(Game.cleanAction.get(Game.dirtyType.indexOf((Integer) 1)));
                                this.floor.getEntities().remove(e);
                                break;
                            }
                        if (e instanceof Dust)
                            if (((int) (e.getX() - (Game.sizeCell - Game.sizeDirty)/2) == (int) (this.robot.getX() - (Game.sizeCell - Game.sizeObject)/2)) && ((int) (e.getY() - (Game.sizeCell - Game.sizeDirty)/2) == (int) (this.robot.getY() - (Game.sizeCell - Game.sizeObject)/2))) {
                                this.robot.setIsSpinning(Game.cleanAction.get(Game.dirtyType.indexOf((Integer) 2)));
                                this.floor.getEntities().remove(e);
                                break;
                            }
                    }
                    
                    if (this.robot.getIsSpinning() == 0) {
                        if (this.currentAction == actionClone.size() - 1) MainController.nextStep();
                        else {
                            this.currentAction++;
                            this.robot.setIsDoing(true);
                        }
                    }
                }
            }
        }
    }
    
    public void render() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
//        System.out.println(robot.getX() + " " + robot.getY() + " " + robot.getAngle() + " " + Game.sizeCell + " " + Game.sizeObject + " " + (int) (Game.sizeCell - Game.sizeObject)/2);
        // make clone value
        ArrayList<Entity> cloneEntities = new ArrayList<>(this.floor.getEntities());
        float cloneSizeCell = Game.sizeCell;
        float cloneSizeDirty = Game.sizeDirty;
        float cloneSizeObject = Game.sizeObject;
        float cloneSizeMap = Game.sizeMap;
        
        // make graphics
        g = bs.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;
        //c clear screen
        g.clearRect(0, 0, 1000, 1000);
        
        // draw background        
        g.setColor(new Color(168, 241, 247));
        for(int i = 0; i < cloneSizeMap; ++i)
            for(int j = 0; j < cloneSizeMap; ++j) {
                g.fillRect((int) (20 + i*cloneSizeCell + (cloneSizeCell - cloneSizeObject)/2), (int) (20 + j*cloneSizeCell + (cloneSizeCell - cloneSizeObject)/2), (int) cloneSizeObject, (int) cloneSizeObject);
            }
        g.setColor(null);

        // draw wall
        for(Entity e : cloneEntities)
            if (e instanceof Brick) e.render(g);
        
        // draw pass cell
//        this.sparkle = (this.sparkle + 1) % 120;
//        for(int i = 0; i < Game.cellX.size(); ++i) {
//            if (this.sparkle < 40) {
//                g2.drawImage(Scalr.resize(Assets.sparkle, Scalr.Method.BALANCED, (int) Game.sizeObject, (int) Game.sizeObject), (int) (Game.cellX.get(i) + (Game.sizeCell - Game.sizeObject)/2), (int) (Game.cellY.get(i) + (Game.sizeCell - Game.sizeObject)/2), null);
//                continue;
//            }
//            if (this.sparkle < 80) {
//                g2.drawImage(Scalr.resize(Assets.sparkle1, Scalr.Method.BALANCED, (int) Game.sizeObject, (int) Game.sizeObject), (int) (Game.cellX.get(i) + (Game.sizeCell - Game.sizeObject)/2), (int) (Game.cellY.get(i) + (Game.sizeCell - Game.sizeObject)/2), null);
//                continue;
//            }
//            g2.drawImage(Scalr.resize(Assets.sparkle2, Scalr.Method.BALANCED, (int) Game.sizeObject, (int) Game.sizeObject), (int) (Game.cellX.get(i) + (Game.sizeCell - Game.sizeObject)/2), (int) (Game.cellY.get(i) + (Game.sizeCell - Game.sizeObject)/2), null);
//        }

        boolean isRepeat;
        for(int i = 0; i < Game.cellX.size(); ++i) {
            isRepeat = false;
            for(int j = i - 1; j >= 0; --j)
                if (((int) Game.cellX.get(i) == (int) Game.cellX.get(j)) && ((int) Game.cellY.get(i) == (int) Game.cellY.get(j))) {
                    isRepeat = true;
                    break;
                }
            if (isRepeat) g.setColor(new Color(0, 140, 140));
            else g.setColor(new Color(100, 200, 200));
            
            g.fillRect((int) (Game.cellX.get(i) + (Game.sizeCell - Game.sizeObject)/2), (int) (Game.cellY.get(i) + (Game.sizeCell - Game.sizeObject)/2), Game.sizeObject, Game.sizeObject);
            
            g.setColor(null);
        }
        //draw spanning tree
        g.setColor(Color.RED);
        g2.setStroke(new BasicStroke(6));
        for(int i = 1; i < Game.node.size(); ++i) {
            g2.drawLine(Game.nodeX.get(i), Game.nodeY.get(i), Game.nodeX.get(Game.father.get(i)), Game.nodeY.get(Game.father.get(i)));
        }
        g.setColor(null);
        
        // draw entities
        for(Entity e : cloneEntities)
            if (e instanceof Dirty) e.render(g);
        for(Entity e : cloneEntities)
            if (e instanceof Box) e.render(g);
        for(Entity e : cloneEntities)
            if (e instanceof RunObstacle) e.render(g);
        // draw robot
        if (this.robot != null) this.robot.render(g);

        // end
        bs.show();
        g.dispose();
    }
    
    @Override
    public void run() {
        init();
        int fps = 180;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        
        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;
            
            if (delta >= 1) {
                update();
                render();
                delta--;
            }
        }
        
        stop();
    }
    
    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized  void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    
}
