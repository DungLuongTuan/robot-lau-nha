/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Entity;
import Main.Game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
    
    public static void readKnowledge() {
        FileInputStream fi;
        try {
            File directory = new File("");
            String path = directory.getAbsolutePath() + "\\src\\Knowledge\\knowledge2.txt";
            fi = new FileInputStream(path);
            Scanner inp = new Scanner(fi,"UTF-8");
            
            // read array
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
    
    public static void makeFirstNode() {
        int c1, c2, c3, c4, c5, c6, l, r;
        
        /////////////////////////////////////////// down checking //////////////////////////////////////////////////////////////
        r = 2;
        l = 3;
        c1 = c2 = c3 = c4 = c5 = c6 = 0;
        for(Entity e: Game.floor.getEntities()) {
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c1 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c2 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0))) c3 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0))) c4 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0) + 60)) c5 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0) + 60)) c6 = 1;
        }
        
        if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
            Game.node.add(Game.node.size());
            Game.father.add(0);
            Game.nodeX.add(Game.nodeX.get(0));
            Game.nodeY.add(Game.nodeY.get(0) + 120);
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
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0))) c1 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c2 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0))) c3 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c4 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 120) && (e.getY() - 2 == Game.nodeY.get(0))) c5 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 120) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c6 = 1;
        }
        
        if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
            Game.node.add(Game.node.size());
            Game.father.add(0);
            Game.nodeX.add(Game.nodeX.get(0) - 120);
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
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c1 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0))) c2 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c3 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0))) c4 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) + 60) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c5 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) + 60) && (e.getY() - 2 == Game.nodeY.get(0))) c6 = 1;
        }
        
        if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
            Game.node.add(Game.node.size());
            Game.father.add(0);
            Game.nodeX.add(Game.nodeX.get(0) + 120);
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
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0))) c1 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0))) c2 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c3 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0) - 60)) c4 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0) - 60) && (e.getY() - 2 == Game.nodeY.get(0) - 120)) c5 = 1;
            if ((e.getX() - 2 == Game.nodeX.get(0)) && (e.getY() - 2 == Game.nodeY.get(0) - 120)) c6 = 1;
        }
        
        if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
            Game.node.add(Game.node.size());
            Game.father.add(0);
            Game.nodeX.add(Game.nodeX.get(0));
            Game.nodeY.add(Game.nodeY.get(0) - 120);
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
        
        /////////////////////////////////////////// check passed node /////////////////////////////////////////////////////////
        System.out.println("///////");
        for(Integer node: Game.node) {
//            System.out.println(Game.nodeX.get(Game.currentNode) + " " + Game.nodeY.get(Game.currentNode) + " " + Game.nodeX.get(node) + " " + Game.nodeY.get(node));
            if (((int) Game.nodeY.get(Game.currentNode) == 560) || (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(node)) && ( (int) Game.nodeY.get(Game.currentNode) + 120 == (int) Game.nodeY.get(node)))) ok0 = false;
            
            if (((int) Game.nodeY.get(Game.currentNode) == 80) || (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(node)) && ((int) Game.nodeY.get(Game.currentNode) - 120 == (int) Game.nodeY.get(node)))) ok2 = false;
            
            if (((int) Game.nodeX.get(Game.currentNode) == 560) || (((int) Game.nodeX.get(Game.currentNode) + 120 == (int) Game.nodeX.get(node)) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(node)))) ok3 = false;
            
            if (((int) Game.nodeX.get(Game.currentNode) == 80) || (((int) Game.nodeX.get(Game.currentNode) - 120 == (int) Game.nodeX.get(node)) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(node)))) ok1 = false;
            
        }
        System.out.println(ok0 + " " + ok1 + " " + ok2 + " " + ok3);
        /////////////////////////////////////////// downward trend ////////////////////////////////////////////////////////////
        if (Game.robot.getAngle() == 0) {
            // robot on the right edge
            if ((Game.robot.getX() - 2 + 60 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 + 60 == Game.nodeY.get(Game.currentNode))) {
                if (ok1) {   
                    l = 4;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c6 = 1;
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c6 = 1;
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 120);
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
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 + 60 == Game.nodeY.get(Game.currentNode))) {
                // robot on the right edge
                if (ok2) {   
                    l = 4;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c6 = 1;
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c6 = 1;
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 120);
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
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) {
                // robot on the right edge
                if (ok3) {   
                    l = 4;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c6 = 1;
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c6 = 1;
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) - 120);
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
            if ((Game.robot.getX() - 2 + 60 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) {
                if (ok0) {   
                    l = 4;
                    r = 1;
                    c1 = c2 = c3 = c4 = c5 = c6 = 0;
                    for(Entity e: Game.floor.getEntities()) {
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c6 = 1;
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c6 = 1;
                    }
                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode) + 120);
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
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c5 = 1;
                        if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c6 = 1;
                    }

                    if (Game.movement.get(Game.status[l][r][c1][c2][c3][c4][c5][c6]).length != 0) {
                        Game.node.add(Game.node.size());
                        Game.father.add(Game.currentNode);
                        Game.nodeX.add(Game.nodeX.get(Game.currentNode));
                        Game.nodeY.add(Game.nodeY.get(Game.currentNode) - 120);
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
        if ((int) Game.father.get(Game.currentNode) == (int) Game.currentNode) return; // in starting node
        
        // down checking
        if (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(Game.father.get(Game.currentNode))) && ((int) Game.nodeY.get(Game.currentNode) + 120 == (int) Game.nodeY.get(Game.father.get(Game.currentNode)))) {
            // robot on the right edge
            if (Game.robot.getAngle() == 0) r = 2;
            if (Game.robot.getAngle() == 90) r = 3;
            if (Game.robot.getAngle() == 180) r = 0;
            if (Game.robot.getAngle() == 270) r = 1;
            
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) l = 1;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) l = 2;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) l = 3;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) l = 4;
            
            c1 = c2 = c3 = c4 = c5 = c6 = 0;
            for(Entity e: Game.floor.getEntities()) {
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c5 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) + 60)) c6 = 1;
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
        if (((int) Game.nodeX.get(Game.currentNode) - 120 == (int) Game.nodeX.get(Game.father.get(Game.currentNode))) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(Game.father.get(Game.currentNode)))) {
            if (Game.robot.getAngle() == 0) r = 1;
            if (Game.robot.getAngle() == 90) r = 2;
            if (Game.robot.getAngle() == 180) r = 3;
            if (Game.robot.getAngle() == 270) r = 0;
            
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) l = 1;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) l = 2;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) l = 3;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) l = 4;
            
            c1 = c2 = c3 = c4 = c5 = c6 = 0;
            for(Entity e: Game.floor.getEntities()) {
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c2 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c3 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c5 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 120) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c6 = 1;
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
        if (((int) Game.nodeX.get(Game.currentNode) == (int) Game.nodeX.get(Game.father.get(Game.currentNode))) && ((int) Game.nodeY.get(Game.currentNode) - 120 == (int) Game.nodeY.get(Game.father.get(Game.currentNode)))) {
            if (Game.robot.getAngle() == 0) r = 0;
            if (Game.robot.getAngle() == 90) r = 1;
            if (Game.robot.getAngle() == 180) r = 2;
            if (Game.robot.getAngle() == 270) r = 3;
            
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) l = 1;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) l = 2;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) l = 3;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) l = 4;
            
            c1 = c2 = c3 = c4 = c5 = c6 = 0;
            for(Entity e: Game.floor.getEntities()) {
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c1 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c4 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c5 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 120)) c6 = 1;
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
        if (((int) Game.nodeX.get(Game.currentNode) + 120 == (int) Game.nodeX.get(Game.father.get(Game.currentNode))) && ((int) Game.nodeY.get(Game.currentNode) == (int) Game.nodeY.get(Game.father.get(Game.currentNode)))) {
            if (Game.robot.getAngle() == 0) r = 3;
            if (Game.robot.getAngle() == 90) r = 0;
            if (Game.robot.getAngle() == 180) r = 1;
            if (Game.robot.getAngle() == 270) r = 2;
            
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) l = 1;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) l = 2;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) l = 3;
            if ((Game.robot.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (Game.robot.getY() - 2 == Game.nodeY.get(Game.currentNode))) l = 4;

            c1 = c2 = c3 = c4 = c5 = c6 = 0;
            for(Entity e: Game.floor.getEntities()) {
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c1 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) - 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c2 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c3 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode)) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c4 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode) - 60)) c5 = 1;
                if ((e.getX() - 2 == Game.nodeX.get(Game.currentNode) + 60) && (e.getY() - 2 == Game.nodeY.get(Game.currentNode))) c6 = 1;
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
    }
}
