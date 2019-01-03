package com.ChazTech.JFX;

import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JFXClass extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("SwampThang.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Swamp Thang");
		primaryStage.setScene(scene);
		primaryStage.show();
		
//		int animationInt = 1;
//		Timeline timeline = null;
//		ArrayList<String> animationList = new ArrayList<String>();
//		MainController mc = new MainController(animationInt, timeline, animationList);
//		mc.startAnimation();
	}
}