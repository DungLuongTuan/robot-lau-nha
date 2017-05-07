/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.display;

import Main.Assets;
import Main.Box;
import Main.Dirty;
import Main.Dust;
import Main.Floor;
import Main.Game;
import Main.Robot;
import Main.RunObstacle;
import Main.Water;
import Main.paintJPanel;
import controllers.MainController;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author 8TITTIT8
 */

public class Display {
    public static JFrame frame, resultFrame;
    public static JTextField sizeMapField;
    private paintJPanel upRightPanel, rightPanel, downRightPanel;
    private JComboBox<String> pickComboBox;
    private JButton runButton, stopButton, resetButton, robotButton, fixedObstacleButton, runObstacleButton, waterButton, dustButton, changeMapButton;
    private String title;
    private int frameWidth, frameHeight, canvasWidth, canvasHeight;
    public static Canvas canvas;
    private Game game;
    private boolean firstRun = true;
    
    public Display(String title) {
        this.frameWidth = 990;
        this.frameHeight = 660;
        this.canvasWidth = 600;
        this.canvasHeight = 600;
        this.title = title;
        this.frame = new JFrame(this.title);
        this.resultFrame = new JFrame("Result");
        this.sizeMapField = new JTextField();
        this.rightPanel = new paintJPanel();
        this.upRightPanel = new paintJPanel();
        this.downRightPanel = new paintJPanel();
        this.game = new Game("Hello!");
        Game.robot = new Robot(-1000, -1000);
        Game.floor = new Floor();
        Game.node = new ArrayList<>();
        Game.father = new ArrayList<>();
        Game.nodeX = new ArrayList<>();
        Game.nodeY = new ArrayList<>();
        Game.action = new ArrayList<>();
        Game.root = new ArrayList<>();
        Game.cellX = new ArrayList<>();
        Game.cellY = new ArrayList<>();
//        MainController.readKnowledge();
//        MainController.readDirtyKnowledge();
        
        createDisplay();
    }
    
    public void createDisplay() {
        // init Assets
        Assets.init();
        
        // create frame
        frame.setSize(this.frameWidth, this.frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        
        // create right menu
        rightPanel.setSize(300, 600);
        rightPanel.setVisible(true);
        rightPanel.setLocation(660, 20);
//        rightPanel.setBackground(Color.GREEN);
        rightPanel.setImage(Assets.menuBackground);
        rightPanel.paintComponents(rightPanel.getGraphics());
        rightPanel.setLayout(null);
        
        upRightPanel.setSize(280, 280);
        upRightPanel.setImage(Assets.upBackground);
        upRightPanel.paintComponents(upRightPanel.getGraphics());
        upRightPanel.setLocation(10, 10);
        upRightPanel.setLayout(null);
        upRightPanel.setVisible(true);
        
        downRightPanel.setSize(280, 280);
        downRightPanel.setImage(Assets.downBackground);
        downRightPanel.paintComponents(downRightPanel.getGraphics());
        downRightPanel.setLocation(10, 310);
        downRightPanel.setLayout(null);
        downRightPanel.setVisible(true);
        
        createRunButton();
        createStopButton();
        createResetButton();
        createRobotButton();
        createRunObstacleButton();
        createFixedObstacleButton();
        createWaterButton();
        createDustButton();
        createChangeMapButton();
        
        // resize map area
        sizeMapField.setSize(100, 20);
        sizeMapField.setLocation(10, 180);
        downRightPanel.add(sizeMapField);
        
        // pick algorithms
        pickComboBox = new JComboBox<>();
        pickComboBox.setSize(100, 20);
        pickComboBox.setLocation(10, 220);
        pickComboBox.addItem("STC");
        pickComboBox.addItem("Full Spiral STC");
        downRightPanel.add(pickComboBox);
        
        rightPanel.add(downRightPanel);
        rightPanel.add(upRightPanel);
        frame.add(rightPanel);
        
        // create canvas
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(this.canvasWidth + 20, this.canvasHeight + 20));
        canvas.setBounds(0, 0, this.canvasWidth + 20, this.canvasHeight + 20);
        canvas.setFocusable(false);
        
        frame.add(canvas);
        frame.setVisible(true);
        //
        resultFrame.setSize(300, 100);
        resultFrame.setResizable(false);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setLayout(null);
        resultFrame.setVisible(false);
        
        // set to game
        this.game.setCanvas(canvas);
        this.game.start();
    }
    
    public Canvas getCanvas() {
        return this.canvas;
    }
    
    public void createRunButton() {
        runButton = new JButton("RUN");
        runButton.setBounds(80, 33, 120, 50);
        upRightPanel.add(runButton);
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Game.robot != null && firstRun == true) {
                    System.out.println(pickComboBox.getSelectedItem());
                    if (pickComboBox.getSelectedItem().equals("STC")) {
                        Game.algorithm = 1;
                        MainController.readKnowledge(1);
                    }
                    else {
                        Game.algorithm = 2;
                        MainController.readKnowledge(2);
                    }
                    MainController.readDirtyKnowledge();
                    MainController.makeFirstNode();
                    firstRun = false;
                }
                Game.isRunning = true;
            }
        });
    }
    
    public void createStopButton() {
        stopButton = new JButton("STOP");
        stopButton.setBounds(80, 115, 120, 50);
        upRightPanel.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.isRunning = false;
            }
        });
    }
    
    public void createResetButton() {
        resetButton = new JButton("RESET");
        resetButton.setBounds(80, 197, 120, 50);
        upRightPanel.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    
                //reset map
                Game.sizeMap = 10;
                Game.sizeCell = 60;
                Game.sizeDirty = 50;
                Game.sizeObject = 56;

                // new map
                try {
                    int size = Integer.parseInt((sizeMapField.getText()));
                    Game.sizeMap = size;
                    Game.sizeCell = (Game.sizeCell*10/Game.sizeMap);
                    Game.sizeDirty = (Game.sizeDirty*10/Game.sizeMap);
                    Game.sizeObject = (Game.sizeObject*10/Game.sizeMap);
                }
                catch (NumberFormatException ex) {
                    
                }
                
                Game.floor = new Floor();
                Game.node = new ArrayList<>();
                Game.father = new ArrayList<>();
                Game.nodeX = new ArrayList<>();
                Game.nodeY = new ArrayList<>();
                Game.action = new ArrayList<>();
                Game.root = new ArrayList<>();
                Game.cellX = new ArrayList<>();
                Game.cellY = new ArrayList<>();
                Game.robot = new Robot(-100, -100);
                Game.robot.setIsDoing(false);
                Game.robot.setIsRunning(false);
                Game.robot.setIsSpinning(0);
                Game.currentAction = -1000000000;
                Game.isRunning = false;
                
                resultFrame = new JFrame("Result");
                resultFrame.setSize(300, 100);
                resultFrame.setResizable(false);
                resultFrame.setLocationRelativeTo(null);
                resultFrame.setLayout(null);
                resultFrame.setVisible(false);
                
                firstRun = true;
            }
        });
    }
    
    public void createRobotButton() {
        robotButton = new JButton();
        robotButton.setBounds(10, 10, 56, 56);
        robotButton.setIcon(new ImageIcon(Assets.robot));
        downRightPanel.add(robotButton);
        robotButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println(Game.sizeCell + " " + Game.sizeDirty + " " + Game.sizeObject + " " + Game.sizeMap);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x0 = -680;
                int y0 = -340;
                if ((e.getPoint().x >= x0 + 20 + Game.sizeCell && e.getPoint().x <= x0 + 20 + Game.sizeCell*(Game.sizeMap - 1)) && (e.getPoint().y >= y0 + 20 + Game.sizeCell  && e.getPoint().y <= y0 + 20 + Game.sizeCell*(Game.sizeMap - 1))) {
                    int x = -x0 + e.getPoint().x;
                    int y = -y0 + e.getPoint().y;
                    x = (int) ((x - 20) / Game.sizeCell);
                    y = (int) ((y - 20) / Game.sizeCell);
                    Game.robot.setX((int) (x*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2));
                    Game.robot.setY((int) (y*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2));
                    Game.robot.setIsDoing(false);
                    Game.robot.setIsRunning(false);
                    Game.node.add(Game.node.size());
                    Game.father.add(0);
                    Game.nodeX.add((int) (x*Game.sizeCell + 20));
                    Game.nodeY.add((int) (y*Game.sizeCell + 20));
                    Game.cellX.add((int) (x*Game.sizeCell + 20));
                    Game.cellY.add((int) (y*Game.sizeCell + 20));
                    Game.currentNode = Game.node.size() - 1;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    public void createRunObstacleButton() {
        runObstacleButton = new JButton();
        runObstacleButton.setBounds(214, 10, 56, 56);
        runObstacleButton.setIcon(new ImageIcon(Assets.runObstacle));
        downRightPanel.add(runObstacleButton);
        runObstacleButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                int x0 = -884;
                int y0 = -340;
                if ((e.getPoint().x >= x0 + 20 + Game.sizeCell && e.getPoint().x <= x0 + 20 + Game.sizeCell*(Game.sizeMap - 1)) && (e.getPoint().y >= y0 + 20 + Game.sizeCell  && e.getPoint().y <= y0 + 20 + Game.sizeCell*(Game.sizeMap - 1))) {
                    RunObstacle runObstacle = new RunObstacle(0, 0);
                    int x = -x0 + e.getPoint().x;
                    int y = -y0 + e.getPoint().y;
                    x = (int) ((x - 20) / Game.sizeCell);
                    y = (int) ((y - 20) / Game.sizeCell);
                    runObstacle.setX((int) (x*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2));
                    runObstacle.setY((int) (y*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2));
                    Game.floor.addEntity(runObstacle);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    public void createFixedObstacleButton() {
        fixedObstacleButton = new JButton();
        fixedObstacleButton.setBounds(112, 10, 56, 56);
        fixedObstacleButton.setIcon(new ImageIcon(Assets.box));
        downRightPanel.add(fixedObstacleButton);
        fixedObstacleButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                int x0 = -782;
                int y0 = -340;
                if ((e.getPoint().x >= x0 + 20 + Game.sizeCell && e.getPoint().x <= x0 + 20 + Game.sizeCell*(Game.sizeMap - 1)) && (e.getPoint().y >= y0 + 20 + Game.sizeCell  && e.getPoint().y <= y0 + 20 + Game.sizeCell*(Game.sizeMap - 1))) {
                    Box box = new Box(0, 0);
                    int x = -x0 + e.getPoint().x;
                    int y = -y0 + e.getPoint().y;
                    x = (int) ((x - 20) / Game.sizeCell);
                    y = (int) ((y - 20) / Game.sizeCell);
                    box.setX((int) (x*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2));
                    box.setY((int) (y*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeObject)/2));
                    Game.floor.addEntity(box);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    public void createWaterButton() {
        waterButton = new JButton();
        waterButton.setBounds(112, 100, 56, 56);
        waterButton.setIcon(new ImageIcon(Assets.water));
        downRightPanel.add(waterButton);
        waterButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x0 = -782;
                int y0 = -430;
                if ((e.getPoint().x >= x0 + 20 + Game.sizeCell && e.getPoint().x <= x0 + 20 + Game.sizeCell*(Game.sizeMap - 1)) && (e.getPoint().y >= y0 + 20 + Game.sizeCell  && e.getPoint().y <= y0 + 20 + Game.sizeCell*(Game.sizeMap - 1))) {
                    Water water = new Water(0, 0);
                    int x = -x0 + e.getPoint().x;
                    int y = -y0 + e.getPoint().y;
                    x = (int) ((x - 20) / Game.sizeCell);
                    y = (int) ((y - 20) / Game.sizeCell);
                    water.setX((int) (x*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeDirty)/2));
                    water.setY((int) (y*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeDirty)/2));
                    Game.floor.addEntity(water);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
    }
    
    public void createDustButton() {
        dustButton = new JButton();
        dustButton.setBounds(10, 100, 56, 56);
        dustButton.setIcon(new ImageIcon(Assets.dust));
        downRightPanel.add(dustButton);
        dustButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x0 = -680;
                int y0 = -430;
                if ((e.getPoint().x >= x0 + 20 + Game.sizeCell && e.getPoint().x <= x0 + 20 + Game.sizeCell*(Game.sizeMap - 1)) && (e.getPoint().y >= y0 + 20 + Game.sizeCell  && e.getPoint().y <= y0 + 20 + Game.sizeCell*(Game.sizeMap - 1))) {
                    Dust dust = new Dust(0, 0);
                    int x = -x0 + e.getPoint().x;
                    int y = -y0 + e.getPoint().y;
                    x = (int) ((x - 20) / Game.sizeCell);
                    y = (int) ((y - 20) / Game.sizeCell);
                    dust.setX((int) (x*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeDirty)/2));
                    dust.setY((int) (y*Game.sizeCell + 20 + (Game.sizeCell - Game.sizeDirty)/2));
                    Game.floor.addEntity(dust);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
    }
    
    public void createChangeMapButton() {
        JButton changeMapButton = new JButton("Change map!!!");
        changeMapButton.setBounds(140, 180, 130, 20);
        downRightPanel.add(changeMapButton);
        changeMapButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!Game.isRunning) {
                    try {
                        int size = Integer.parseInt((sizeMapField.getText()));
                        System.out.println(size);
                        //reset map
                        Game.sizeMap = 10;
                        Game.sizeCell = 60;
                        Game.sizeDirty = 50;
                        Game.sizeObject = 56;

                        // new map
                        Game.sizeMap = size;
                        Game.sizeCell = (Game.sizeCell*10/Game.sizeMap);
                        Game.sizeDirty = (Game.sizeDirty*10/Game.sizeMap);
                        Game.sizeObject = (Game.sizeObject*10/Game.sizeMap);

                        // do as reset button
                        Game.floor = new Floor();
                        Game.node = new ArrayList<>();
                        Game.father = new ArrayList<>();
                        Game.nodeX = new ArrayList<>();
                        Game.nodeY = new ArrayList<>();
                        Game.action = new ArrayList<>();
                        Game.root = new ArrayList<>();
                        Game.cellX = new ArrayList<>();
                        Game.cellY = new ArrayList<>();
                        Game.robot = new Robot(-100, -100);
                        Game.robot.setIsDoing(false);
                        Game.robot.setIsRunning(false);
                        Game.robot.setIsSpinning(0);
                        Game.currentAction = -1000000000;
                        Game.isRunning = false;

                        resultFrame = new JFrame("Result");
                        resultFrame.setSize(300, 100);
                        resultFrame.setResizable(false);
                        resultFrame.setLocationRelativeTo(null);
                        resultFrame.setLayout(null);
                        resultFrame.setVisible(false);

                        firstRun = true;
                    }
                    catch (NumberFormatException ex) {
                        
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
    }
}

