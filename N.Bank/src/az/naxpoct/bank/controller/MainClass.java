/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.naxpoct.bank.controller;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Rashad Suleymanov
 */
public class MainClass extends Application {
    //yess adlindi!
    private double xOffset = 0;
    private double yOffset = 0;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_design.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
              root.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });

                    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            stage.setX(event.getScreenX() - xOffset);
                            stage.setY(event.getScreenY() - yOffset);
                        }
                    });
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        MainController controller = loader.getController();
        controller.setStage(stage);
        stage.show();

      new FadeIn(root).play();   
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
