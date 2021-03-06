/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Box;
import Main.Dirty;
import Main.Entity;
import Main.FixedObstacle;
import Main.Game;
import Main.RunObstacle;
import game.display.Display;
import static game.display.Display.resultFrame;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 8TITTIT8
 */

public class MainController {
    
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException ex) {
            System.exit(1);
        }
        return(null);
    }
    
    public static void readKnowledge(int algorithm) {
        FileInputStream fi;
        try {
            File directory = new File("");
            String path = directory.getAbsolutePath() + "\\src\\Knowledge\\knowledge" + algorithm + ".txt";
            fi = new FileInputStream(path);
            Scanner inp = new Scanner(fi,"UTF-8");
            
            // read array
            Game.status = new int[7][4][2][2][2][2][2][2];
            Game.movement = new ArrayList<>();
            
            int j = 0;
            while (inp.hasNextLine()) {
                String temp = inp.nextLine(); 
                String [] item = temp.split(" ");
                int [] e = new int[item.length];
                for(int i = 0; i < item.length; ++i)
                    e[i] = Integer.parseInt(item[i]);
                
                int [] move = new int[e.length - 8];
                for(int i = 8; i < e.length; ++i) {
                    move[i-8] = e[i];
                }
                Game.status[e[0]][e[1]][e[2]][e[3]][e[4]][e[5]][e[6]][e[7]] = j;
                Game.movement.add(move);
                j++;
            }
            inp.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void readDirtyKnowledge() {
        FileInputStream fi;
        try {
            File directory = new File("");
            String path = directory.getAbsolutePath() + "\\src\\Knowledge\\knowledge3.txt";
            fi = new FileInputStream(path);
            Scanner inp = new Scanner(fi,"UTF-8");
            
            // read array
            while (inp.hasNextLine()) {
                String temp = inp.nextLine(); 
                String [] item = temp.split(" ");
                int [] e = new int[item.length];
                for(int i = 0; i < item.length; ++i)
                    e[i] = Integer.parseInt(item[i]);
                
                Game.dirtyType.add(e[0]);
                Game.cleanAction.add(e[1]);
            }
            inp.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean collision(int x1, int y1, int x2, int y2) {
        if ((x1 == x2) && (y1 < y2) && (y1 + Game.sizeCell > y2)) return true;
        if ((x1 == x2) && (y2 < y1) && (y2 + Game.sizeCell > y1)) return true;
        if ((y1 == y2) && (x1 < x2) && (x1 + Game.sizeCell > x2)) return true;
        if ((y1 == y2) && (x2 < x1) && (x2 + Game.sizeCell > x1)) return true;
        if ((x1 < x2) && (y1 < y2) && (x1 + Game.sizeCell > x2) && (y1 + Game.sizeCell > y2)) return true;
        if ((x1 < x2) && (y2 < y1) && (x1 + Game.sizeCell > x2) && (y2 + Game.sizeCell > y1)) return true;
        if ((x2 < x1) && (y1 < y2) && (x2 + Game.sizeCell > x1) && (y1 + Game.sizeCell > y2)) return true;
        if ((x2 < x1) && (y2 < y1) && (x2 + Game.sizeCell > x1) && (y2 + Game.sizeCell > y1)) return true;
        if ((x1 == x2) && (y1 == y2)) return true;
        return false;
    }
    
    public static void makeFirstNode() {
        int c1, c2, c3, c4, c5, c6, l, r;
        int brickBlank = (int) (Game.sizeCell - Game.sizeObject)/2;
        int dirtyBlank = (int) (Game.sizeCell - Game.sizeDirty)/2;
        int sizeCell = (int) Game.sizeCell;
        
        /////////////////////////////////////////// down checking //////////////////////////////////////////////////////////////
        r = 2;
        l = 3;
        c1 = c2 = c3 = c4 = c5 = c6 = 0;
        for(Entity e: Game.floor.getEntities()) {
            if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c1 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c2 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0))) c3 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0))) c4 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0) + sizeCell)) c5 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0) + sizeCell)) c6 = 1;
            }
        }
        
        if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
            Game.node.add(Game.node.size());
            Game.father.add(0);
            Game.nodeX.add(Game.nodeX.get(0));
            Game.nodeY.add(Game.nodeY.get(0) +  2*sizeCell);
            Game.currentNode = Game.node.size() - 1;
            for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                Game.action.add(action);
                Game.root.add((Integer) 0);
            }
            Game.currentAction = -1;
            return;
        }
        
        /////////////////////////////////////////// left checking ////////////////////////////////////////////////////////////
        r = 1;
        l = 1;
        c1 = c2 = c3 = c4 = c5 = c6 = 0;
        for(Entity e: Game.floor.getEntities()) {
            if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0))) c1 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c2 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0))) c3 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c4 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0))) c5 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c6 = 1;
            }
        }
        
        if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
            Game.node.add(Game.node.size());
            Game.father.add(0);
            Game.nodeX.add(Game.nodeX.get(0) - 2*sizeCell);
            Game.nodeY.add(Game.nodeY.get(0));
            Game.currentNode = Game.node.size() - 1;
            for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                Game.action.add(action);
                Game.root.add((Integer) 1);
            }
            Game.currentAction = -1;
            return;
        }
        
        /////////////////////////////////////////// right checking //////////////////////////////////////////////////////////
        r = 3;
        l = 4;
        c1 = c2 = c3 = c4 = c5 = c6 = 0;
        for(Entity e: Game.floor.getEntities()) {
            if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c1 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0))) c2 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c3 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0))) c4 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c5 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0))) c6 = 1;
            }
        }
        
        if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
            Game.node.add(Game.node.size());
            Game.father.add(0);
            Game.nodeX.add(Game.nodeX.get(0) + 2*sizeCell);
            Game.nodeY.add(Game.nodeY.get(0));
            Game.currentNode = Game.node.size() - 1;
            for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                Game.action.add(action);
                Game.root.add((Integer) 3);
            }
            Game.currentAction = -1;
            return;
        }
        
        /////////////////////////////////////////// up checking ////////////////////////////////////////////////////////////
        r = 0;
        l = 2;
        c1 = c2 = c3 = c4 = c5 = c6 = 0;
        for(Entity e: Game.floor.getEntities()) {
            if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0))) c1 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0))) c2 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c3 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0) - sizeCell)) c4 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(0) - 2*sizeCell)) c5 = 1;
                if ((e.getX() - brickBlank == Game.nodeX.get(0)) && (e.getY() - brickBlank == Game.nodeY.get(0) - 2*sizeCell)) c6 = 1;
            }
        }
        
        if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
            Game.node.add(Game.node.size());
            Game.father.add(0);
            Game.nodeX.add(Game.nodeX.get(0));
            Game.nodeY.add(Game.nodeY.get(0) - 2*sizeCell);
            Game.currentNode = Game.node.size() - 1;
            for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                Game.action.add(action);
                Game.root.add((Integer) 2);
            }
            Game.currentAction = -1;
            return;
        }
        
    }
    
    public static void nextStep() {
        int l = 0, r = 0, c1, c2, c3, c4, c5, c6;
        boolean ok0, ok1, ok2, ok3;
        ok0 = ok1 = ok2 = ok3 = true;
        boolean doubleEdge0 = false, doubleEdge1 = false, doubleEdge2 = false, doubleEdge3 = false;
        boolean upLeft, upRight, downLeft, downRight;
        int cnt;
        int brickBlank = (int) (Game.sizeCell - Game.sizeObject)/2;
        int dirtyBlank = (int) (Game.sizeCell - Game.sizeDirty)/2;
        int sizeCell = (int) Game.sizeCell;
        int sizeDirty = (int) Game.sizeDirty;
        int sizeObject = (int) Game.sizeObject;
        int sizeMap = (int) Game.sizeMap;
        
        
        /////////////////////////////////////////// check double edge /////////////////////////////////////////////////////////
        if (Game.algorithm == 2) {
            upLeft = upRight = downLeft = downRight = true;
            // check doubleEdge down
            if ((int) Game.nodeY.get(Game.currentNode) < 20 + sizeCell*(sizeMap - 2)) {
                for(Entity e: Game.floor.getEntities()) {
                    if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) + sizeCell)) upLeft = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) + sizeCell)) upRight = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) + 2*sizeCell)) downLeft = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) + 2*sizeCell)) downRight = false;
                    }
                }
                if ((upLeft && downRight && !upRight & !downLeft) || (!upLeft && !downRight && upRight && downLeft)) doubleEdge0 = true;
                cnt = 0;
                for(Integer node: Game.node) {
                    if (((int) Game.nodeX.get(node) == (int) Game.nodeX.get(Game.currentNode)) && ((int) Game.nodeY.get(node) == (int) Game.nodeY.get(Game.currentNode) + 2*sizeCell)) {
                        cnt++;
                        if (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(Game.father.get(node))) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(Game.father.get(node)))) doubleEdge0 = false;
                        if (((int) Game.nodeX.get(Game.father.get(Game.currentNode)) == Game.nodeX.get(Game.currentNode)) && ((int) Game.nodeY.get(Game.father.get(Game.currentNode)) == (int) Game.nodeY.get(Game.currentNode) + 2*sizeCell)) doubleEdge0 = false; 
                    }
                }
                if (cnt >= 2) doubleEdge0 = false;
            }
            // check doubleEdge left
            upLeft = upRight = downLeft = downRight = true;
            if ((int) Game.nodeX.get(Game.currentNode) > 20 + 2*sizeCell) {
                for(Entity e: Game.floor.getEntities()) {
                    if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) - 3*sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) - sizeCell)) upLeft = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) - sizeCell)) upRight = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) - 3*sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode))) downLeft = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode))) downRight = false;
                    }
                }
                if ((upLeft && downRight && !upRight & !downLeft) || (!upLeft && !downRight && upRight && downLeft)) doubleEdge1 = true;
                cnt = 0;
                for(Integer node: Game.node) {
                    if (((int) Game.nodeX.get(node) == (int) Game.nodeX.get(Game.currentNode) - 2*sizeCell) && ((int) Game.nodeY.get(node) == (int) Game.nodeY.get(Game.currentNode))) {
                        cnt++;
                        if (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(Game.father.get(node))) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(Game.father.get(node)))) doubleEdge1 = false;
                        if (((int) Game.nodeX.get(Game.father.get(Game.currentNode)) == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && ((int) Game.nodeY.get(Game.father.get(Game.currentNode)) == (int) Game.nodeY.get(Game.currentNode))) doubleEdge1 = false; 
                    }
                }
                if (cnt >= 2) doubleEdge1 = false;
            }
            // check doubleEdge up
            upLeft = upRight = downLeft = downRight = true;
            if ((int) Game.nodeY.get(Game.currentNode) > 20 + 2*sizeCell) {
                for(Entity e: Game.floor.getEntities()) {
                    if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) - 3*sizeCell)) upLeft = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) - 3*sizeCell)) upRight = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) - 2*sizeCell)) downLeft = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) - 2*sizeCell)) downRight = false;
                    }
                }
                if ((upLeft && downRight && !upRight & !downLeft) || (!upLeft && !downRight && upRight && downLeft)) doubleEdge2 = true;
                cnt = 0;
                for(Integer node: Game.node) {
                    if (((int) Game.nodeX.get(node) == (int) Game.nodeX.get(Game.currentNode)) && ((int) Game.nodeY.get(node) == (int) Game.nodeY.get(Game.currentNode) - 2*sizeCell)) {
                        cnt++;
                        if (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(Game.father.get(node))) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(Game.father.get(node)))) doubleEdge2 = false;
                        if (((int) Game.nodeX.get(Game.father.get(Game.currentNode)) == Game.nodeX.get(Game.currentNode)) && ((int) Game.nodeY.get(Game.father.get(Game.currentNode)) == (int) Game.nodeY.get(Game.currentNode) - 2*sizeCell)) doubleEdge2 = false; 
                    }
                }
                if (cnt >= 2) doubleEdge2 = false;
            }
            // check doubleEdge right
            upLeft = upRight = downLeft = downRight = true;
            if ((int) Game.nodeX.get(Game.currentNode) < 20 + sizeCell*(sizeMap - 2)) {
                for(Entity e: Game.floor.getEntities()) {
                    if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) - sizeCell)) upLeft = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) + 2*sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode) - sizeCell)) upRight = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode))) downLeft = false;
                        if ((e.getX() - brickBlank == (int) Game.nodeX.get(Game.currentNode) + 2*sizeCell) && (e.getY() - brickBlank == (int) Game.nodeY.get(Game.currentNode))) downRight = false;
                    }
                }
                if ((upLeft && downRight && !upRight & !downLeft) || (!upLeft && !downRight && upRight && downLeft)) doubleEdge3 = true;
                cnt = 0;
                for(Integer node: Game.node) {
                    if (((int) Game.nodeX.get(node) == (int) Game.nodeX.get(Game.currentNode) + 2*sizeCell) && ((int) Game.nodeY.get(node) == (int) Game.nodeY.get(Game.currentNode))) {
                        cnt++;
                        if (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(Game.father.get(node))) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(Game.father.get(node)))) doubleEdge3 = false;
                        if (((int) Game.nodeX.get(Game.father.get(Game.currentNode)) == Game.nodeX.get(Game.currentNode) + 2*sizeCell) && ((int) Game.nodeY.get(Game.father.get(Game.currentNode)) == (int) Game.nodeY.get(Game.currentNode))) doubleEdge3 = false; 
                    }
                }
                if (cnt >= 2) doubleEdge3 = false;
            }
        }
        /////////////////////////////////////////// check passed node /////////////////////////////////////////////////////////
        
        for(Integer node: Game.node) {

            if (((int) Game.nodeY.get(Game.currentNode) == 20 + sizeCell*(sizeMap - 1)) || (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(node)) && ( (int) Game.nodeY.get(Game.currentNode) + 2*sizeCell == (int) Game.nodeY.get(node)) && !doubleEdge0)) ok0 = false;

            if (((int) Game.nodeY.get(Game.currentNode) == 20 + sizeCell) || (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(node)) && ((int) Game.nodeY.get(Game.currentNode) - 2*sizeCell == (int) Game.nodeY.get(node)) && !doubleEdge2)) ok2 = false;
            
            if (((int) Game.nodeX.get(Game.currentNode) == 20 + sizeCell*(sizeMap - 1)) || (((int) Game.nodeX.get(Game.currentNode) + 2*sizeCell == (int) Game.nodeX.get(node)) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(node)) && !doubleEdge3)) ok3 = false;
            
            if (((int) Game.nodeX.get(Game.currentNode) == 20 + sizeCell) || (((int) Game.nodeX.get(Game.currentNode) - 2*sizeCell == (int) Game.nodeX.get(node)) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(node)) && !doubleEdge1)) ok1 = false;
            
        }

        /////////////////////////////////////////// downward trend ////////////////////////////////////////////////////////////
        if (Game.robot.getAngle() == 0) {
            // robot on the right edge
            if ((Game.robot.getX() - brickBlank + sizeCell == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank + sizeCell == Game.nodeY.get(Game.currentNode))) {
                if (ok1) {   
                    l = 4;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c6 = 1;
                        }
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 1);
                        }
                        return;
                    }
                }
                
                if (ok0) {
                    l = 2;
                    r = 2;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 0);
                        }
                        return;
                    }
                }
                
                if (ok3) {
                    l = 1;
                    r = 3;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 3);
                        }
                        return;
                    }
                }
            }
            else {
                // robot on the left edge
                if (ok1) {   
                    l = 2;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c6 = 1;
                        }
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 1);
                        }
                        return;
                    }
                }
                
                if (ok0) {
                    l = 1;
                    r = 2;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 0);
                        }
                        return;
                    }
                }
                
                if (ok3) {
                    l = 3;
                    r = 3;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 3);
                        }
                        return;
                    }
                }
            }
        }
        
        /////////////////////////////////////////// left checking //////////////////////////////////////////////////////////////
        if (Game.robot.getAngle() == 90) {
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank + sizeCell == Game.nodeY.get(Game.currentNode))) {
                // robot on the right edge
                if (ok2) {   
                    l = 4;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c6 = 1;
                        }
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 2);
                        }
                        return;
                    }
                }
                
                if (ok1) {
                    l = 2;
                    r = 2;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 1);
                        }
                        return;
                    }
                }
                
                if (ok0) {
                    l = 1;
                    r = 3;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 0);
                        }
                        return;
                    }
                }
            }
            else {
                // robot on the left edge
                if (ok2) {   
                    l = 2;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c6 = 1;
                        }
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 2);
                        }
                        return;
                    }
                }
                
                if (ok1) {
                    l = 1;
                    r = 2;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 1);
                        }
                        return;
                    }
                }
                
                if (ok0) {
                    l = 3;
                    r = 3;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 0);
                        }
                        return;
                    }
                }
            }
        }
        
        /////////////////////////////////////////// up checking //////////////////////////////////////////////////////////////
        if (Game.robot.getAngle() == 180) {
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) {
                // robot on the right edge
                if (ok3) {   
                    l = 4;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c6 = 1;
                        }
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 3);
                        }
                        return;
                    }
                }
                
                if (ok2) {
                    l = 2;
                    r = 2;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 2);
                        }
                        return;
                    }
                }
                
                if (ok1) {
                    l = 1;
                    r = 3;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 1);
                        }
                        return;
                    }
                }
            }
            else {
                // robot on the left edge
                if (ok3) {   
                    l = 2;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                            if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c6 = 1;
                        }
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 3);
                        }
                        return;
                    }
                }
                
                if (ok2) {
                    l = 1;
                    r = 2;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 2);
                        }
                        return;
                    }
                }
                
                if (ok1) {
                    l = 3;
                    r = 3;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 1);
                        }
                        return;
                    }
                }
            }
        }
        
        /////////////////////////////////////////// right checking //////////////////////////////////////////////////////////////
        if (Game.robot.getAngle() == 270) {
            if ((Game.robot.getX() - brickBlank + sizeCell == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) {
                if (ok0) {   
                    l = 4;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c6 = 1;
                        }
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 0);
                        }
                        return;
                    }
                }
                
                if (ok3) {
                    l = 2;
                    r = 2;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 3);
                        }
                        return;
                    }
                }
                
                if (ok2) {
                    l = 1;
                    r = 3;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 2);
                        }
                        return;
                    }
                }
            }
            else {
                if (ok0) {   
                    l = 2;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c6 = 1;
                        }
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 0);
                        }
                        return;
                    }
                }
                
                if (ok3) {
                    l = 1;
                    r = 2;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 2*sizeCell);
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode));
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 3);
                        }
                        return;
                    }
                }
                
                if (ok2) {
                    l = 3;
                    r = 3;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c5 = 1;
                            if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c6 = 1;
                        }
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 2*sizeCell);
                        Game.currentNode = Game.node.size() - 1;
                        for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                            Game.action.add(action);
                            Game.root.add((Integer) 2);
                        }
                        return;
                    }
                }
            }
        }
        
        /////////////////////////////////////////// back checking //////////////////////////////////////////////////////////////
        
//        System.out.println("currentNode: " + Game.nodeX.get(Game.currentNode) + " " + Game.nodeY.get(Game.currentNode));
//        System.out.println("father:      " + Game.nodeX.get(Game.father.get(Game.currentNode)) + " " + Game.nodeY.get(Game.father.get(Game.currentNode)));
//        System.out.println(" ");
        
        // down checking
        if (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(Game.father.get(Game.currentNode))) && ((int) Game.nodeY.get(Game.currentNode) + 2*sizeCell == (int) Game.nodeY.get(Game.father.get(Game.currentNode)))) {
            // robot on the right edge
            if (Game.robot.getAngle() == 0) r = 2;
            if (Game.robot.getAngle() == 90) r = 3;
            if (Game.robot.getAngle() == 180) r = 0;
            if (Game.robot.getAngle() == 270) r = 1;
            
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) l = 1;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) l = 2;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) l = 3;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) l = 4;
            
            c1 = c2 = c3 = c4 = c5 = c6 = 0;
            for(Entity e: Game.floor.getEntities()) {
                if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c5 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) + sizeCell)) c6 = 1;
                }
            }

            if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                Game.currentNode = Game.father.get(Game.currentNode);
                for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                    Game.action.add(action);
                    Game.root.add((Integer) 0);
                }
                return;
                }
        }
        
        // left checking
        if (((int) Game.nodeX.get(Game.currentNode) - 2*sizeCell == (int) Game.nodeX.get(Game.father.get(Game.currentNode))) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(Game.father.get(Game.currentNode)))) {
            if (Game.robot.getAngle() == 0) r = 1;
            if (Game.robot.getAngle() == 90) r = 2;
            if (Game.robot.getAngle() == 180) r = 3;
            if (Game.robot.getAngle() == 270) r = 0;
            
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) l = 1;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) l = 2;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) l = 3;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) l = 4;
            
            c1 = c2 = c3 = c4 = c5 = c6 = 0;
            for(Entity e: Game.floor.getEntities()) {
                if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c2 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c3 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c5 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - 2*sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c6 = 1;
                }
            }

            if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                Game.currentNode = Game.father.get(Game.currentNode);
                for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                    Game.action.add(action);
                    Game.root.add((Integer) 1);
                }
                return;
            }
        }
        
        // up checking
        if (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(Game.father.get(Game.currentNode))) && ((int) Game.nodeY.get(Game.currentNode) - 2*sizeCell == (int) Game.nodeY.get(Game.father.get(Game.currentNode)))) {
            if (Game.robot.getAngle() == 0) r = 0;
            if (Game.robot.getAngle() == 90) r = 1;
            if (Game.robot.getAngle() == 180) r = 2;
            if (Game.robot.getAngle() == 270) r = 3;
            
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) l = 1;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) l = 2;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) l = 3;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) l = 4;
            
            c1 = c2 = c3 = c4 = c5 = c6 = 0;
            for(Entity e: Game.floor.getEntities()) {
                if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c1 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c4 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c5 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - 2*sizeCell)) c6 = 1;
                }
            }

            if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                Game.currentNode = Game.father.get(Game.currentNode);
                for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                    Game.action.add(action);
                    Game.root.add((Integer) 2);
                }
                return;
            }
        }
        
        // right checking
        if (((int) Game.nodeX.get(Game.currentNode) + 2*sizeCell == (int) Game.nodeX.get(Game.father.get(Game.currentNode))) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(Game.father.get(Game.currentNode)))) {
            if (Game.robot.getAngle() == 0) r = 3;
            if (Game.robot.getAngle() == 90) r = 0;
            if (Game.robot.getAngle() == 180) r = 1;
            if (Game.robot.getAngle() == 270) r = 2;
            
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) l = 1;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) l = 2;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) l = 3;
            if ((Game.robot.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) l = 4;

            c1 = c2 = c3 = c4 = c5 = c6 = 0;
            for(Entity e: Game.floor.getEntities()) {
                if (!(e instanceof Dirty) && !(e instanceof RunObstacle)) {
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c1 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) - sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c2 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c3 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode)) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c4 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode) - sizeCell)) c5 = 1;
                    if ((e.getX() - brickBlank == Game.nodeX.get(Game.currentNode) + sizeCell) && (e.getY() - brickBlank == Game.nodeY.get(Game.currentNode))) c6 = 1;
                }
            }

            if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                Game.currentNode = Game.father.get(Game.currentNode);
                for(Integer action: Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6])) {
                    Game.action.add(action);
                    Game.root.add((Integer) 3);
                }
                return;
            }
  
        }
        
        if ((int) Game.father.get(Game.currentNode) == (int) Game.currentNode) {
            
            if ((Game.cellX.get(0) == Game.robot.getX() - brickBlank) && (Game.cellY.get(0) == Game.robot.getY() - brickBlank)) {
                Game.isRunning = false;

                //
                int repeat = 0;
                int cover = 0;
                int sum = (sizeMap - 2)*(sizeMap - 2);
                for(int i = 1; i < sizeMap - 1; ++i)
                    for(int j = 1; j < sizeMap - 1; ++j) {
                        for(int k = 0; k < Game.cellX.size(); ++k)
                            if ((Game.cellX.get(k) == i*sizeCell + 20) && (Game.cellY.get(k) == j*sizeCell + 20)) {
                                cover++;
                                break;
                            }
                    }

                for (Entity e: Game.floor.getEntities())
                    if (e instanceof Box) sum--;

                repeat = Game.cellX.size() - cover - 1;
                //
                JPanel panel = new JPanel();
                panel.setSize(300, 100);
                panel.setLocation(0, 0);
                panel.setLayout(new FlowLayout());

                JLabel label1 = new JLabel();
                label1.setText("ĐỘ BAO PHỦ:     " + cover + " / " + sum + "    ( " + Math.floor(((float) cover / sum)*100) + "% )");
                JLabel label2 = new JLabel();
                label2.setText("ĐỘ LẶP:         " + repeat + " / " + cover + "    ( " + Math.floor(((float) repeat / cover)*100) + "% )");

                panel.add(label1);
                panel.add(label2);

                Display.resultFrame.add(panel);
                Display.resultFrame.setVisible(true);

                return;
            }
            else {
                if (((int) Game.robot.getX() - brickBlank + sizeCell == (int) Game.cellX.get(0)) && ((int) Game.robot.getY() - brickBlank == (int) Game.cellY.get(0))) {
                    Game.root.add((Integer) (Game.robot.getAngle()/90));
                    if ((Game.robot.getAngle()/90) == 0) Game.action.add((Integer) 0);
                    if ((Game.robot.getAngle()/90) == 1) Game.action.add((Integer) 1);
                    if ((Game.robot.getAngle()/90) == 2) Game.action.add((Integer) 3);
                    if ((Game.robot.getAngle()/90) == 3) Game.action.add((Integer) 2);
                }
                if (((int) Game.robot.getX() - brickBlank - sizeCell == (int) Game.cellX.get(0)) && ((int) Game.robot.getY() - brickBlank == (int) Game.cellY.get(0))) {
                    Game.root.add((Integer) (Game.robot.getAngle()/90));
                    if ((Game.robot.getAngle()/90) == 0) Game.action.add((Integer) 3);
                    if ((Game.robot.getAngle()/90) == 1) Game.action.add((Integer) 2);
                    if ((Game.robot.getAngle()/90) == 2) Game.action.add((Integer) 1);
                    if ((Game.robot.getAngle()/90) == 3) Game.action.add((Integer) 0);
                }
                if (((int) Game.robot.getX() - brickBlank == (int) Game.cellX.get(0)) && ((int) Game.robot.getY() - brickBlank + sizeCell== (int) Game.cellY.get(0))) {
                    Game.root.add((Integer) (Game.robot.getAngle()/90));
                    if ((Game.robot.getAngle()/90) == 0) Game.action.add((Integer) 2);
                    if ((Game.robot.getAngle()/90) == 1) Game.action.add((Integer) 1);
                    if ((Game.robot.getAngle()/90) == 2) Game.action.add((Integer) 0);
                    if ((Game.robot.getAngle()/90) == 3) Game.action.add((Integer) 3);
                }
                if (((int) Game.robot.getX() - brickBlank == (int) Game.cellX.get(0)) && ((int) Game.robot.getY() - brickBlank - sizeCell == (int) Game.cellY.get(0))) {
                    Game.root.add((Integer) (Game.robot.getAngle()/90));
                    if ((Game.robot.getAngle()/90) == 0) Game.action.add((Integer) 0);
                    if ((Game.robot.getAngle()/90) == 1) Game.action.add((Integer) 3);
                    if ((Game.robot.getAngle()/90) == 2) Game.action.add((Integer) 2);
                    if ((Game.robot.getAngle()/90) == 3) Game.action.add((Integer) 1);
                }
                
                boolean blank;
                
                if ((Game.robot.getX() - brickBlank + sizeCell == Game.cellX.get(0)) && (Game.robot.getY() - brickBlank + sizeCell == Game.cellY.get(0))) {
                    blank = true;
                    for(Entity e : Game.floor.getEntities())
                        if ((e instanceof Box) && (e.getX() == Game.robot.getX()) && (e.getY() == Game.robot.getY() + sizeCell)) {
                            blank = false;
                            break;
                        }
                    if (blank) {
//                        Game.action.add((Integer) 0);
//                        Game.action.add((Integer) 3);
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        if ((Game.robot.getAngle()/90) == 0) {
                            Game.action.add((Integer) 2);
                            Game.action.add((Integer) 1);
                        }
                        if ((Game.robot.getAngle()/90) == 1) {
                            Game.action.add((Integer) 1);
                            Game.action.add((Integer) 0);
                        }
                        if ((Game.robot.getAngle()/90) == 2) {
                            Game.action.add((Integer) 0);
                            Game.action.add((Integer) 3);
                        }
                        if ((Game.robot.getAngle()/90) == 3) {
                            Game.action.add((Integer) 3);
                            Game.action.add((Integer) 2);
                        }
                    }
                    else {
//                        Game.action.add((Integer) 3);
//                        Game.action.add((Integer) 0);
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        if ((Game.robot.getAngle()/90) == 0) {
                            Game.action.add((Integer) 1);
                            Game.action.add((Integer) 2);
                        }
                        if ((Game.robot.getAngle()/90) == 1) {
                            Game.action.add((Integer) 0);
                            Game.action.add((Integer) 1);
                        }
                        if ((Game.robot.getAngle()/90) == 2) {
                            Game.action.add((Integer) 3);
                            Game.action.add((Integer) 0);
                        }
                        if ((Game.robot.getAngle()/90) == 3) {
                            Game.action.add((Integer) 2);
                            Game.action.add((Integer) 3);
                        }
                    }
                }
                
                if ((Game.robot.getX() - brickBlank - sizeCell == Game.cellX.get(0)) && (Game.robot.getY() - brickBlank - sizeCell == Game.cellY.get(0))) {
                    blank = true;
                    for(Entity e : Game.floor.getEntities())
                        if ((e instanceof Box) && (e.getX() == Game.robot.getX()) && (e.getY() == Game.robot.getY() - sizeCell)) {
                            blank = false;
                            break;
                        }
                    if (blank) {
//                        Game.action.add((Integer) 2);
//                        Game.action.add((Integer) 1);
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        if ((Game.robot.getAngle()/90) == 0) {
                            Game.action.add((Integer) 0);
                            Game.action.add((Integer) 3);
                        }
                        if ((Game.robot.getAngle()/90) == 1) {
                            Game.action.add((Integer) 3);
                            Game.action.add((Integer) 2);
                        }
                        if ((Game.robot.getAngle()/90) == 2) {
                            Game.action.add((Integer) 2);
                            Game.action.add((Integer) 1);
                        }
                        if ((Game.robot.getAngle()/90) == 3) {
                            Game.action.add((Integer) 1);
                            Game.action.add((Integer) 0);
                        }
                    }
                    else {
//                        Game.action.add((Integer) 1);
//                        Game.action.add((Integer) 2);
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        if ((Game.robot.getAngle()/90) == 0) {
                            Game.action.add((Integer) 3);
                            Game.action.add((Integer) 0);
                        }
                        if ((Game.robot.getAngle()/90) == 1) {
                            Game.action.add((Integer) 2);
                            Game.action.add((Integer) 3);
                        }
                        if ((Game.robot.getAngle()/90) == 2) {
                            Game.action.add((Integer) 1);
                            Game.action.add((Integer) 2);
                        }
                        if ((Game.robot.getAngle()/90) == 3) {
                            Game.action.add((Integer) 0);
                            Game.action.add((Integer) 1);
                        }
                    }
                }
                
                if ((Game.robot.getX() - brickBlank - sizeCell == Game.cellX.get(0)) && (Game.robot.getY() - brickBlank + sizeCell == Game.cellY.get(0))) {
                    blank = true;
                    for(Entity e : Game.floor.getEntities())
                        if ((e instanceof Box) && (e.getX() == Game.robot.getX() - sizeCell) && (e.getY() == Game.robot.getY())) {
                            blank = false;
                            break;
                        }
                    if (blank) {
//                        Game.action.add((Integer) 1);
//                        Game.action.add((Integer) 0);
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        if ((Game.robot.getAngle()/90) == 0) {
                            Game.action.add((Integer) 3);
                            Game.action.add((Integer) 2);
                        }
                        if ((Game.robot.getAngle()/90) == 1) {
                            Game.action.add((Integer) 2);
                            Game.action.add((Integer) 1);
                        }
                        if ((Game.robot.getAngle()/90) == 2) {
                            Game.action.add((Integer) 1);
                            Game.action.add((Integer) 0);
                        }
                        if ((Game.robot.getAngle()/90) == 3) {
                            Game.action.add((Integer) 0);
                            Game.action.add((Integer) 3);
                        }
                    }
                    else {
//                        Game.action.add((Integer) 0);
//                        Game.action.add((Integer) 1);
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        if ((Game.robot.getAngle()/90) == 0) {
                            Game.action.add((Integer) 2);
                            Game.action.add((Integer) 3);
                        }
                        if ((Game.robot.getAngle()/90) == 1) {
                            Game.action.add((Integer) 1);
                            Game.action.add((Integer) 2);
                        }
                        if ((Game.robot.getAngle()/90) == 2) {
                            Game.action.add((Integer) 0);
                            Game.action.add((Integer) 1);
                        }
                        if ((Game.robot.getAngle()/90) == 3) {
                            Game.action.add((Integer) 3);
                            Game.action.add((Integer) 0);
                        }
                    }
                }
                
                if ((Game.robot.getX() - brickBlank + sizeCell == Game.cellX.get(0)) && (Game.robot.getY() - brickBlank - sizeCell == Game.cellY.get(0))) {
                    blank = true;
                    for(Entity e : Game.floor.getEntities())
                        if ((e instanceof Box) && (e.getX() == Game.robot.getX() + sizeCell) && (e.getY() == Game.robot.getY())) {
                            blank = false;
                            break;
                        }
                    if (blank) {
//                        Game.action.add((Integer) 3);
//                        Game.action.add((Integer) 2);
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        if ((Game.robot.getAngle()/90) == 0) {
                            Game.action.add((Integer) 1);
                            Game.action.add((Integer) 0);
                        }
                        if ((Game.robot.getAngle()/90) == 1) {
                            Game.action.add((Integer) 0);
                            Game.action.add((Integer) 3);
                        }
                        if ((Game.robot.getAngle()/90) == 2) {
                            Game.action.add((Integer) 3);
                            Game.action.add((Integer) 2);
                        }
                        if ((Game.robot.getAngle()/90) == 3) {
                            Game.action.add((Integer) 2);
                            Game.action.add((Integer) 1);
                        }
                    }
                    else {
//                        Game.action.add((Integer) 2);
//                        Game.action.add((Integer) 3);
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        Game.root.add((Integer) (Game.robot.getAngle()/90));
                        if ((Game.robot.getAngle()/90) == 0) {
                            Game.action.add((Integer) 0);
                            Game.action.add((Integer) 1);
                        }
                        if ((Game.robot.getAngle()/90) == 1) {
                            Game.action.add((Integer) 3);
                            Game.action.add((Integer) 0);
                        }
                        if ((Game.robot.getAngle()/90) == 2) {
                            Game.action.add((Integer) 2);
                            Game.action.add((Integer) 3);
                        }
                        if ((Game.robot.getAngle()/90) == 3) {
                            Game.action.add((Integer) 1);
                            Game.action.add((Integer) 2);
                        }
                    }
                }
            }
        }
    }
}
