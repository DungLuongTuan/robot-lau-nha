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
    public static BufferedImage background, robot, runObstacle, box, brick, menuBackground, upBackground, downBackground, button, sparkle, sparkle1, sparkle2, water, dust;
    
    public static void init() {
        File directory = new File("");
        String backgroundName = directory.getAbsolutePath() + "\\src\\Image\\floor.png";
        String robotName = directory.getAbsolutePath() + "\\src\\Image\\mini-gemn.png";
        String boxName = directory.getAbsolutePath() + "\\src\\Image\\box.png";
        String brickName = directory.getAbsolutePath() + "\\src\\Image\\brick.png";
        String runObstacleName = directory.getAbsolutePath() + "\\src\\Image\\exaid-brother.png";
        String upBackgroundName = directory.getAbsolutePath() + "\\src\\Image\\rb.png";
        String downBackgroundName = directory.getAbsolutePath() + "\\src\\Image\\lb.png";
        String menuBackgroundName = directory.getAbsolutePath() + "\\src\\Image\\menuBackground.png";
        String buttonName = directory.getAbsolutePath() + "\\src\\Image\\button.png";
        String sparkleName = directory.getAbsolutePath() + "\\src\\Image\\sparkle2.png";
        String sparkle1Name = directory.getAbsolutePath() + "\\src\\Image\\sparkle3.png";
        String sparkle2Name = directory.getAbsolutePath() + "\\src\\Image\\sparkle4.png";
        String waterName = directory.getAbsolutePath() + "\\src\\Image\\water.png";
        String dustName = directory.getAbsolutePath() + "\\src\\Image\\dust.png";
        
        background = MainController.loadImage(backgroundName);
        robot = MainController.loadImage(robotName);
        box = MainController.loadImage(boxName);
        brick = MainController.loadImage(brickName);
        runObstacle = MainController.loadImage(runObstacleName);
        upBackground = MainController.loadImage(upBackgroundName);
        downBackground = MainController.loadImage(downBackgroundName);
        menuBackground = MainController.loadImage(menuBackgroundName);
        button = MainController.loadImage(buttonName);
        sparkle = MainController.loadImage(sparkleName);
        sparkle1 = MainController.loadImage(sparkle1Name);
        sparkle2 = MainController.loadImage(sparkle2Name);
        water = MainController.loadImage(waterName);
        dust = MainController.loadImage(dustName);
    }
}
