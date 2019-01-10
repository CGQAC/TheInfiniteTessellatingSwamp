package com.ChazTech.JFX;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class MainController {
	int animationInt = 1;
	int textimationInt = 1;
	Timeline timeline;
	Timeline textTimeline;
	ArrayList<ALObject> animationList = new ArrayList<ALObject>();
	ArrayList<String> animationSlide = new ArrayList<String>();
	ArrayList<String> backupSlide = new ArrayList<String>();
	ArrayList<String> textSlide = new ArrayList<String>();
	Boolean newGame = false;
	String difficulty;
	int gridSize = 50;
	Player player;
	SwampTile swampTile;
	SwampTile keyTile;
	boolean helpVisible = false;
	int firstBlank;
	boolean moveEnabled = false;
	int currentLineLower = 1;
	int currentLineUpper = 6;
	boolean bobIntro = true;
	int bobSpot = 1;

	@FXML
	private Label command;
	@FXML
	private Label displayArea;
	@FXML
	private Label disp1;
	@FXML
	private Label disp2;
	@FXML
	private Label disp3;
	@FXML
	private Label disp4;
	@FXML
	private Label disp5;
	@FXML
	private Label disp6;
	@FXML
	private Label disp7;
	@FXML
	private Label disp8;
	@FXML
	private Label disp9;
	@FXML
	private Label disp10;
	@FXML
	private Label disp11;
	@FXML
	private Label disp12;
	@FXML
	private Label disp13;
	@FXML
	private Label disp14;
	@FXML
	private Label disp15;
	@FXML
	private Label disp16;
	@FXML
	private Label disp17;
	@FXML
	private Label disp18;
	@FXML
	private Label disp19;
	@FXML
	private Label disp20;
	@FXML
	private Label disp21;
	@FXML
	private Label disp22;
	@FXML
	private Label disp23;
	@FXML
	private Label disp24;
	@FXML
	private Button btnNorth;
	@FXML
	private Button btnEast;
	@FXML
	private Button btnSouth;
	@FXML
	private Button btnWest;
	@FXML
	private TextField inputCommand;
	
	//INITIALISE
	public void initialize() {		
		btnNorth.setVisible(false);
		btnEast.setVisible(false);
		btnSouth.setVisible(false);
		btnWest.setVisible(false);
		inputCommand.setVisible(false);
		command.setVisible(false);
		getTextContent();
		startAnimation();
	}
	public void setVisible(boolean vis) {
		btnNorth.setVisible(vis);
		btnEast.setVisible(vis);
		btnSouth.setVisible(vis);
		btnWest.setVisible(vis);
		inputCommand.setVisible(vis);
		command.setVisible(vis);
		if (vis) {
			inputCommand.requestFocus();
		}
	}
	public void getTextContent() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("Assets/TextContent"));
			String line = reader.readLine();
			textSlide.add("");
			while (line != null) {
				textSlide.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//INPUT + GENERAL DISPLAY
	@FXML
	public void onEnter(ActionEvent event){
		System.out.println("::" + inputCommand.getText());
		String command = inputCommand.getText().toLowerCase();
		if (bobIntro) {
			if (command.contains("hi") || command.contains("hey") || command.contains("hello")) {
				if (command.contains("bob")) {
					if (command.equals("hey bob")) {
						startAnimation("Bob_Wave");
					}
					if (command.equals("hi bob")) {
						startAnimation("Bob_Wave");
					}
					if (command.equals("hello bob")) {
						startAnimation("Bob_Wave");
					}
				}
			}
		}
		if (command.contains("more")) {
			currentLineUpper++;
			System.out.println("cLLcLU: " + currentLineLower + " " + currentLineUpper);
			startTextimation(currentLineLower,currentLineUpper,100);
		}
		if (command.contains("append")) {
			appendToDisplay(command);
		}
		if (command.equals("help")) {
			if (helpVisible == false) {
				helpVisible = true;
				System.out.println("Loading Help Screen...");
				try {
					help();
				} catch (Exception e) {
				}
				displayAnimation("HelpScreen",100);
			}
		}
		if (command.equals("hide help")) {
			helpVisible = false;
			hidehelp();
		}
		if (command.contains("start game")) {
			startGame(command);
		}
		if (command.contains("end game")) {
			newGame = false;
			animationInt = 1;
			textimationInt = 1;
			newGame = false;
			difficulty = "";
			gridSize = 50;
			helpVisible = false;
			firstBlank = 0;
			moveEnabled = false;
			currentLineLower = 1;
			currentLineUpper = 6;
			bobIntro = true;
			animationList = new ArrayList<ALObject>();
			animationSlide = new ArrayList<String>();
			backupSlide = new ArrayList<String>();
			displayAnimation("TitleAnimation", 100);
		}
		if (command.contains("go")) {
			if (moveEnabled) {
				if (command.contains("go n")) {
					if (player.getX() < gridSize) {
						player.setX(player.getX() + 1);
					} else {
						player.setX(1);
					}
				}
				if (command.contains("go e")) {
					if (player.getY() < gridSize) {
						player.setY(player.getY() + 1);
					} else {
						player.setY(1);
					}
				}
				if (command.contains("go s")) {
					if (player.getX() > 1) {
						player.setX(player.getX() - 1);
					} else {
						player.setX(gridSize);
					}
				}
				if (command.contains("go w")) {
					if (player.getY() > 1) {
						player.setY(player.getY() - 1);
					} else {
						player.setY(gridSize);
					}
				}
				if (checkForEvent()) {
					
				}
				delete_ShowPos();
			}
		}
		inputCommand.clear();
	}
	public void appendToDisplay(String appendText) {
		firstBlank = firstBlankLine();
		if (appendText.length() <= 83) {
			appendText = padRight(appendText, "n");
			if (firstBlank != 0) {
				addLines(appendText, firstBlank);
			} else {
				moveTextUp();
				addLines(appendText, 23);
			}
		} else {
			int noLinesRequired = (int) Math.ceil((float)appendText.length() / 83);
			if (firstBlank != 0) {
				if (firstBlank + noLinesRequired > 22) {
					for (int i = firstBlank + noLinesRequired; i > 22; i--) {
						moveTextUp();
					}
				}
			} else {
				for (int i = 1; i <= noLinesRequired; i++) {
					moveTextUp();
				}
				firstBlank = 24 - noLinesRequired;
			}
			AtomicInteger splitCounter = new AtomicInteger(0);
			Collection<String> splittedStrings = appendText
			                                    .chars()
			                                    .mapToObj(_char -> String.valueOf((char)_char))
			                                    .collect(Collectors.groupingBy(stringChar -> splitCounter.getAndIncrement() / 83
			                                                                ,Collectors.joining()))
			                                    .values();
			int i = 1;
			int len = splittedStrings.size();
			String cAt83;
			boolean pRL = false;
			for (String a : splittedStrings) {
				if (a.length() > 82) {
					cAt83 = a.substring(82, 83);
				} else {
					cAt83 = " ";
				}
				if (pRL == true) {
					if (cAt83 != " ") {
						a = padRight(a, "lr");
					} else {
						a = padRight(a, "l");
					}
					pRL = false;
				} else {
					if (cAt83 != " ") {
						a = padRight(a, "r");
						pRL = true;
					} else {
						a = padRight(a, "n");
					}
				}
				addLines(a, firstBlank);
				firstBlank++;
			}
			

			
		}
	}
	public void addLines(String textToAppend, int atLineNo) {
		switch (atLineNo) {
			case 2: disp2.setText(textToAppend); break;
			case 3: disp3.setText(textToAppend); break; 
			case 4: disp4.setText(textToAppend); break; 
			case 5: disp5.setText(textToAppend); break; 
			case 6: disp6.setText(textToAppend); break; 
			case 7: disp7.setText(textToAppend); break; 
			case 8: disp8.setText(textToAppend); break; 
			case 9: disp9.setText(textToAppend); break; 
			case 10: disp10.setText(textToAppend); break; 
			case 11: disp11.setText(textToAppend); break; 
			case 12: disp12.setText(textToAppend); break; 
			case 13: disp13.setText(textToAppend); break; 
			case 14: disp14.setText(textToAppend); break; 
			case 15: disp15.setText(textToAppend); break; 
			case 16: disp16.setText(textToAppend); break; 
			case 17: disp17.setText(textToAppend); break; 
			case 18: disp18.setText(textToAppend); break; 
			case 19: disp19.setText(textToAppend); break; 
			case 20: disp20.setText(textToAppend); break; 
			case 21: disp21.setText(textToAppend); break; 
			case 22: disp22.setText(textToAppend); break; 
			case 23: disp23.setText(textToAppend); break; 
		}
	}
	public String padRight(String padThis, String lOrRorN) {
		if (lOrRorN == "l") {
			return "|-" + String.format("%1$-" + 83 + "s", padThis) + " |";
		}
		if (lOrRorN == "r") {
			return "| " + String.format("%1$-" + 83 + "s", padThis) + "-|";
		}
		if (lOrRorN == "lr") {
			return "|-" + String.format("%1$-" + 83 + "s", padThis) + "-|";
		}
		return "| " + String.format("%1$-" + 83 + "s", padThis) + " |";
	}
	public int firstBlankLine() {
		int firstBlank = 0;
		String blankLine = "|                                                                                     |";
		for (int i = 23; i >= 2; i--) {
			switch (i) {
				case 2: 
					if (disp2.getText().equals(blankLine)) {
						firstBlank = 2;
					}
					break;
				case 3: 
					if (disp3.getText().equals(blankLine)) {
						firstBlank = 3;
					}
					break;
				case 4: 
					if (disp4.getText().equals(blankLine)) {
						firstBlank = 4;
					}
					break;
				case 5: 
					if (disp5.getText().equals(blankLine)) {
						firstBlank = 5;
					}
					break;
				case 6: 
					if (disp6.getText().equals(blankLine)) {
						firstBlank = 6;
					}
					break;
				case 7: 
					if (disp7.getText().equals(blankLine)) {
						firstBlank = 7;
					}
					break;
				case 8: 
					if (disp8.getText().equals(blankLine)) {
						firstBlank = 8;
					}
					break;
				case 9: 
					if (disp9.getText().equals(blankLine)) {
						firstBlank = 9;
					}
					break;
				case 10: 
					if (disp10.getText().equals(blankLine)) {
						firstBlank = 10;
					}
					break;
				case 11: 
					if (disp11.getText().equals(blankLine)) {
						firstBlank = 11;
					}
					break;
				case 12: 
					if (disp12.getText().equals(blankLine)) {
						firstBlank = 12;
					}
					break;
				case 13: 
					if (disp13.getText().equals(blankLine)) {
						firstBlank = 13;
					}
					break;
				case 14: 
					if (disp14.getText().equals(blankLine)) {
						firstBlank = 14;
					}
					break;
				case 15: 
					if (disp15.getText().equals(blankLine)) {
						firstBlank =  15;
					}
					break;
				case 16: 
					if (disp16.getText().equals(blankLine)) {
						firstBlank = 16;
					}
					break;
				case 17: 
					if (disp17.getText().equals(blankLine)) {
						firstBlank = 17;
					}
					break;
				case 18: 
					if (disp18.getText().equals(blankLine)) {
						firstBlank = 18;
					}
					break;
				case 19: 
					if (disp19.getText().equals(blankLine)) {
						firstBlank = 19;
					}
					break;
				case 20: 
					if (disp20.getText().equals(blankLine)) {
						firstBlank = 20;
					}
					break;
				case 21: 
					if (disp21.getText().equals(blankLine)) {
						firstBlank = 21;
					}
					break;
				case 22: 
					if (disp22.getText().equals(blankLine)) {
						firstBlank = 22;
					}
					break;
				case 23: 
					if (disp23.getText().equals(blankLine)) {
						firstBlank = 23;
					}
					break;
			}
		}
		return firstBlank;
	}
	public void moveTextUp() {
		disp2.setText(disp3.getText());
		disp3.setText(disp4.getText());
		disp4.setText(disp5.getText());
		disp5.setText(disp6.getText());
		disp6.setText(disp7.getText());
		disp7.setText(disp8.getText());
		disp8.setText(disp9.getText());
		disp9.setText(disp10.getText());
		disp10.setText(disp11.getText());
		disp11.setText(disp12.getText());
		disp12.setText(disp13.getText());
		disp13.setText(disp14.getText());
		disp14.setText(disp15.getText());
		disp15.setText(disp16.getText());
		disp16.setText(disp17.getText());
		disp17.setText(disp18.getText());
		disp18.setText(disp19.getText());
		disp19.setText(disp20.getText());
		disp20.setText(disp21.getText());
		disp21.setText(disp22.getText());
		disp22.setText(disp23.getText());
		disp23.setText("|                                                                                     |");
	}
	
	//START GAME
	public void startGame(String command){
		if (newGame == false) {
			String[] inputs = command.split(" ");
			int i = 1;
			for (String a : inputs) {
				if (i == 3) {
					try {
						gridSize = Integer.parseInt(a);
					} catch (Exception e) {
					}
				}
				if (i == 4) {
					difficulty = a;
				}
				i++;
			}
			if (i < 4) {
				gridSize = 50;
				difficulty = "medium";
			}
			disp1.setText("New game started || Grid Size: " + gridSize + "   Difficulty: " + difficulty);
			player = new Player(gridSize);
			swampTile = new SwampTile(gridSize, true);
			while (player.getX() == swampTile.getX() && player.getY() == swampTile.getY()) {
				swampTile = new SwampTile(gridSize, true);
			}
			keyTile = new SwampTile(gridSize, false);
			while ((keyTile.getX() == swampTile.getX() && keyTile.getY() == swampTile.getY()) && (keyTile.getX() == player.getX() && keyTile.getY() == player.getY())) {
				keyTile = new SwampTile(gridSize, false);
			}
			newGame = true;
//			delete_ShowPos();
			System.out.println("---");
			moveEnabled = false;
			startTextimation(currentLineLower,currentLineUpper,100);
		}
	}
	
	//HELP
	public void help() {
		backupSlide.clear();
		backupSlide.add(disp1.getText());
		backupSlide.add(disp2.getText());
		backupSlide.add(disp3.getText());
		backupSlide.add(disp4.getText());
		backupSlide.add(disp5.getText());
		backupSlide.add(disp6.getText());
		backupSlide.add(disp7.getText());
		backupSlide.add(disp8.getText());
		backupSlide.add(disp9.getText());
		backupSlide.add(disp10.getText());
		backupSlide.add(disp11.getText());
		backupSlide.add(disp12.getText());
		backupSlide.add(disp13.getText());
		backupSlide.add(disp14.getText());
		backupSlide.add(disp15.getText());
		backupSlide.add(disp16.getText());
		backupSlide.add(disp17.getText());
		backupSlide.add(disp18.getText());
		backupSlide.add(disp19.getText());
		backupSlide.add(disp20.getText());
		backupSlide.add(disp21.getText());
		backupSlide.add(disp22.getText());
		backupSlide.add(disp23.getText());
		backupSlide.add(disp24.getText());
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Assets/BackupScreen"));
			writer.write("");
			for (int i = 0; i < 24; i++) {
				if (i != 23) {
					writer.write(backupSlide.get(i) + "\r\n");
				} else {
					writer.write(backupSlide.get(23));
				}
			}
		    writer.close();
		} catch (IOException e) {
			
		}
	}
	public void hidehelp() {
		displayAnimation("BackupScreen", 100);
	}
	
	//DELETE/DISABLE THESE FUNCTIONS BEFORE RELEASE
	public void delete_ShowPos() {
		disp2.setText("player at: " + player.getX() + "," + player.getY());
		disp3.setText("Treasure at: " + swampTile.getX() + "," + swampTile.getY());
		disp4.setText("chest key at: " + keyTile.getX() + "," + keyTile.getY());
	}

	//MOVEMENT + EVENT TEST
	public void goNorth(ActionEvent event) {
		if (moveEnabled) {
			if (player.getX() < gridSize) {
				player.setX(player.getX() + 1);
			} else {
				player.setX(1);
			}
			inputCommand.requestFocus();
			checkForEvent();
			delete_ShowPos();
		}
	}
	public void goEast(ActionEvent event) {
		if (moveEnabled) {
			if (player.getY() < gridSize) {
				player.setY(player.getY() + 1);
			} else {
				player.setY(1);
			}
			inputCommand.requestFocus();
			checkForEvent();
			delete_ShowPos();
		}
	}
	public void goSouth(ActionEvent event) {
		if (moveEnabled) {
			if (player.getX() > 1) {
				player.setX(player.getX() - 1);
			} else {
				player.setX(gridSize);
			}
			inputCommand.requestFocus();
			checkForEvent();
			delete_ShowPos();
		}
	}
	public void goWest(ActionEvent event) {
		if (moveEnabled) {
			if (player.getY() > 1) {
				player.setY(player.getY() - 1);
			} else {
				player.setY(gridSize);
			}
			inputCommand.requestFocus();
			checkForEvent();
			delete_ShowPos();
		}
	}
	public boolean checkForEvent() {
		Boolean event = false;
		int eventPer = 0;
		switch (difficulty) {
			case "easy": eventPer = 10; break;
			case "medium": eventPer = 25; break;
			case "hard": eventPer = 50; break;
		}
		if ((Math.random() * 100) + 1 <= eventPer) {
			disp4.setText("event");
			event = true;
		} else {
			disp4.setText("no event");
		}
		return event;
	}
	
	//ANIMATIONS
	public void startTextimation(int lineStart, int lineEnd, int frameTime) {
		setVisible(false);
		textimationInt = lineStart;
        textTimeline = new Timeline(
                new KeyFrame(
                    Duration.millis(frameTime),
                    new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent actionEvent) {   
                            Platform.runLater(()->{
                            	if (textSlide.get(textimationInt).contains("<anim>")) {
                            		String[] animFile = textSlide.get(textimationInt).split(">");
                            		System.out.println(animFile[1]);
                            		textTimeline.stop();
                            		displayAnimation(animFile[1], 100);
                            	} else {
                            		appendToDisplay(textSlide.get(textimationInt));
                            		textimationInt++;
                            		currentLineLower++;
                            	}
                            	if (currentLineLower == currentLineUpper) {
                            		setVisible(true);
                            	}
                            });
                        }
                    }
                )
            );
    	textTimeline.setCycleCount(lineEnd); 
    	textTimeline.play();
	}
	public void startAnimation() { 
		displayAnimation("TitleAnimation", 100);
	}
	public void startAnimation(ActionEvent event) {
		btnNorth.setVisible(false);
		btnEast.setVisible(false);
		btnSouth.setVisible(false);
		btnWest.setVisible(false);
		displayAnimation("TitleAnimation",100);
	}
	public void startAnimation(String filename) {
		displayAnimation(filename,100);
	}
    public void changeAnimation(int animationInt, int animationLength) {
    	System.out.println("Slide:" + animationInt + "/" + animationLength);
    	for (int i = 1; i <= 24; i++) {
        	String slideText = (String) animationList.get(animationInt-1).getALObject().get(i-1);
    		switch(i) {
    		case 1:
    			disp1.setText(slideText);
    			break;
    		case 2:
    			disp2.setText(slideText);
    			break;
    		case 3:
    			disp3.setText(slideText);
    			break;
    		case 4:
    			disp4.setText(slideText);
    			break;
    		case 5:
    			disp5.setText(slideText);
    			break;
    		case 6:
    			disp6.setText(slideText);
    			break;
    		case 7:
    			disp7.setText(slideText);
    			break;
    		case 8:
    			disp8.setText(slideText);
    			break;
    		case 9:
    			disp9.setText(slideText);
    			break;
    		case 10:
    			disp10.setText(slideText);
    			break;
    		case 11:
    			disp11.setText(slideText);
    			break;
    		case 12:
    			disp12.setText(slideText);
    			break;
    		case 13:
    			disp13.setText(slideText);
    			break;
    		case 14:
    			disp14.setText(slideText);
    			break;
    		case 15:
    			disp15.setText(slideText);
    			break;
    		case 16:
    			disp16.setText(slideText);
    			break;
    		case 17:
    			disp17.setText(slideText);
    			break;
    		case 18:
    			disp18.setText(slideText);
    			break;
    		case 19:
    			disp19.setText(slideText);
    			break;
    		case 20:
    			disp20.setText(slideText);
    			break;
    		case 21:
    			disp21.setText(slideText);
    			break;
    		case 22:
    			disp22.setText(slideText);
    			break;
    		case 23:
    			disp23.setText(slideText);
    			break;
    		case 24:
    			disp24.setText(slideText);
    			break;
    		}
    	}
    	
    	if (animationInt == animationLength) {
    		animationInt = 1;
            timeline.stop();
            setVisible(true);
    		if (bobIntro) {
    			if (bobSpot == 3) {
    				appendToDisplay("testing 3");
        			bobIntro = false;
    			}
    			if (bobSpot == 2) {
    				appendToDisplay("testing 2");
    				bobSpot++;
    			}
    			if (bobSpot == 1) {
    				appendToDisplay("testing");
    				bobSpot++;
    			}
    		}
    	}
    }
	public void displayAnimation(String fileName, int frameTime) {
		setVisible(false);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("Assets/" + fileName));
			String line = reader.readLine();
			int i = 1;
			int j = 0;
			while (line != null) {
				if (i == 25) {
					animationList.add(new ALObject(animationSlide));
					animationSlide = new ArrayList<String>();
					i = 1;
				} else {
					animationSlide.add(line);
					i++;
					line = reader.readLine();
				}
			}
			animationList.add(new ALObject(animationSlide));
			animationSlide = new ArrayList<String>();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	int animationLength = animationList.size();
        timeline = new Timeline(
            new KeyFrame(
                Duration.millis(frameTime),
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {   
                        Platform.runLater(()->{
                        	changeAnimation(animationInt, animationLength);
                        	animationInt++;
                        });
                    }
                }
            )
        );
        timeline.setCycleCount(animationLength); 
        timeline.play();
	}
}
