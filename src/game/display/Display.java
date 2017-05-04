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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 8TITTIT8
 */
public class Display {
    public static JFrame frame, resultFrame;
    private paintJPanel upRightPanel, rightPanel, downRightPanel;
    private JButton runButton, stopButton, resetButton, robotButton, fixedObstacleButton, runObstacleButton, waterButton, dustButton;
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
        this.rightPanel = new paintJPanel();
        this.upRightPanel = new paintJPanel();
        this.downRightPanel = new paintJPanel();
        this.game = new Game("Hello!");
        Game.robot = new Robot(-100, -100);
        Game.floor = new Floor();
        Game.node = new ArrayList<>();
        Game.father = new ArrayList<>();
        Game.nodeX = new ArrayList<>();
        Game.nodeY = new ArrayList<>();
        Game.action = new ArrayList<>();
        Game.root = new ArrayList<>();
        Game.cellX = new ArrayList<>();
        Game.cellY = new ArrayList<>();
        MainController.readKnowledge();
        
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
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                if ((e.getPoint().x >= -600 && e.getPoint().x <= -120) && (e.getPoint().y >= -260  && e.getPoint().y <= 220)) {
                    int x = 660 + e.getPoint().x + 20;
                    int y = 320 + e.getPoint().y + 20;
                    x = (x - 20) / 60;
                    y = (y - 20) / 60;
                    Game.robot.setX(x*60 + 20 + 2);
                    Game.robot.setY(y*60 + 20 + 2);
                    Game.robot.setIsDoing(false);
                    Game.robot.setIsRunning(false);
                    Game.node.add(Game.node.size());
                    Game.father.add(0);
                    Game.nodeX.add(x*60 + 20);
                    Game.nodeY.add(y*60 + 20);
                    Game.cellX.add(x*60 + 20);
                    Game.cellY.add(y*60 + 20);
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
                if ((e.getPoint().x >= -804 && e.getPoint().x <= -324) && (e.getPoint().y >= -260  && e.getPoint().y <= 220)) {
                    RunObstacle runObstacle = new RunObstacle(0, 0);
                    int x = 864 + e.getPoint().x + 20;
                    int y = 320 + e.getPoint().y + 20;
                    x = (x - 20) / 60;
                    y = (y - 20) / 60;
                    runObstacle.setX(x*60 + 20 + 2);
                    runObstacle.setY(y*60 + 20 + 2);
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
                if ((e.getPoint().x >= -702 && e.getPoint().x <= -222) && (e.getPoint().y >= -260  && e.getPoint().y <= 220)) {
                    Box box = new Box(0, 0);
                    int x = 762 + e.getPoint().x + 20;
                    int y = 320 + e.getPoint().y + 20;
                    x = (x - 20) / 60;
                    y = (y - 20) / 60;
                    box.setX(x*60 + 20 + 2);
                    box.setY(y*60 + 20 + 2);
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
                if ((e.getPoint().x >= -702 && e.getPoint().x <= -222) && (e.getPoint().y >= -350  && e.getPoint().y <= 130)) {
                    Water water = new Water(0, 0);
                    int x = 762 + e.getPoint().x + 20;
                    int y = 410 + e.getPoint().y + 20;
                    x = (x - 20) / 60;
                    y = (y - 20) / 60;
                    water.setX(x*60 + 20 + 5);
                    water.setY(y*60 + 20 + 5);
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
                if ((e.getPoint().x >= -600 && e.getPoint().x <= -120) && (e.getPoint().y >= -350  && e.getPoint().y <= 130)) {
                    Dust dust = new Dust(0, 0);
                    int x = 660 + e.getPoint().x + 20;
                    int y = 410 + e.getPoint().y + 20;
                    x = (x - 20) / 60;
                    y = (y - 20) / 60;
                    dust.setX(x*60 + 20 + 5);
                    dust.setY(y*60 + 20 + 5);
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
}
