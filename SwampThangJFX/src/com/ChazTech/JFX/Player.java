package com.ChazTech.JFX;

public class Player extends SwampTile {
	int playerHealth = 20;
	boolean hasChestKey = false;
	
	Player(int gridSize) {
		super(gridSize, false);
	}
	public int getPlayerHealth() {
		return playerHealth;
	}

	public void setPlayerHealth(int playerHealth) {
		this.playerHealth = playerHealth;
	}
	public boolean getChestKey() {
		return hasChestKey;
	}
	public void setChestKey(boolean hasKey) {
		this.hasChestKey = hasKey;
	}
}