/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import controllers.MainController;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author 8TITTIT8
 */
public class Assets {
    public static BufferedImage background, robot, runObstacle, box, brick;
    
    public static void init() {
        File directory = new File("");
        String backgroundName = directory.getAbsolutePath() + "\\src\\Image\\floor.png";
        String robotName = directory.getAbsolutePath() + "\\src\\Image\\mini-gemn.png";
        String boxName = directory.getAbsolutePath() + "\\src\\Image\\box.png";
        String brickName = directory.getAbsolutePath() + "\\src\\Image\\brick.png";
        String runObstacleName = directory.getAbsolutePath() + "\\src\\Image\\exaid-brother.png";
        
        background = MainController.loadImage(backgroundName);
        robot = MainController.loadImage(robotName);
        box = MainController.loadImage(boxName);
        brick = MainController.loadImage(brickName);
        runObstacle = MainController.loadImage(runObstacleName);
    }
}
