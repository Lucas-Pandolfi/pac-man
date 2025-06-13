package com.blackcatstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.blackcatstudios.main.Game;
import com.blackcatstudios.main.GameState;
import com.blackcatstudios.world.World;

public class Player extends Entity {

	public double speed = 1.2;
	public boolean right, left, up, down;
	public int mx = 0;
	public int my = 0;	
	public int ammo = 0;
	public double life = 100, maxLife = 100;
	public boolean hasGun = false;
	public boolean keyboardShoot = false;
	public boolean mouseShoot = false;
	public boolean  isDamaged = false;
	
	private boolean moved = false;
	private int damageFrames = 0;
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private BufferedImage playerDamage;
	private BufferedImage[] leftPlayer= new BufferedImage[3];
	private BufferedImage[] rightPlayer = new BufferedImage[3]; 
	
	public Player(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
	}

	public void tick() {
		depth = 1;
		
		walk();
		
		damageAnimation();
		
		//Entity.cameraClamp();
	}
	
	/*public void takeDamage() {
		if(Game.random.nextInt(100) < 10) 
		{
			 Game.player.life -= Game.random.nextInt(3);
			 Game.player.isDamaged = true;
		}
	}*/
	
	public void render(Graphics graphics) {
		
	}
	
	private void walk() {
		moved = false;
		if(right && World.collidedWithWallTile((int)(x + speed), (int)y, width, height)) {
	        moved = true;
	        x += speed;
	    }
	    else if(left && World.collidedWithWallTile((int)(x - speed), (int)y, width, height)) {
	        moved = true;
	        x -= speed;
	    }
	    
	    if(up && World.collidedWithWallTile((int)x, (int)(y - speed), width, height)) {
	        moved = true;
	        y -= speed;
	    }
	    else if(down && World.collidedWithWallTile((int)x, (int)(y + speed), width, height)) {
	        moved = true;
	        y += speed;
	    }
		
		if(moved) {
			frames++;
			
			if(frames == maxFrames) {
				frames = 0;
				index++;
				
				if(index >= maxIndex)
					index = 0;
			}
		}
	}
	
	private void getSprites() {
		for(int i = 0; i < rightPlayer.length; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, width, height);			
		}
		
		for(int i = 0; i < leftPlayer.length; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, width, height);			
		}
		
		playerDamage = Game.spritesheet.getSprite(0, 16, width, height);
	}
	
	private void damageAnimation() {
		if(isDamaged) 
		{
			damageFrames++;
			if(damageFrames == 8)
			{
				damageFrames = 0;
				isDamaged = false;
			}
		}
	}
}
