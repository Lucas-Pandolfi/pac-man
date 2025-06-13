package com.blackcatstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.blackcatstudios.main.Game;
import com.blackcatstudios.world.Camera;
import com.blackcatstudios.world.Node;
import com.blackcatstudios.world.Vector2i;
import com.blackcatstudios.world.World;

public class Entity {	
	protected double x;
	protected double y;
	protected int speed;
	protected int width;
	protected int height;
	protected BufferedImage sprite;
	protected List<Node> paths;
	
	public int depth;
	
	public Entity(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void cameraClamp() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
	
	public void tick() {
		
	}
	
	public static Comparator<Entity> entitySorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity e0, Entity e1) {
			if(e1.depth < e0.depth)
				return +1;
			
			if(e1.depth > e0.depth)
				return -1;
			
			return 0;
		}
	};
	
	public double calculateDistace(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)* (y1 - y2));
	}
	
	public void followPath(List<Node> paths) {
		if(paths != null) 
		{
			if(paths.size() > 0) 
			{
				Vector2i target = paths.get(paths.size() - 1).tile;
				
				if(x < target.x * 16)
					x++;
				else if(x > target.x * 16)
					x--;
				
				if(y < target.y * 16)
					y++;
				else if(y > target.y * 16)
					y--;
				
				if(x == target.x * 16 && y == target.y * 16)
					paths.remove(paths.size() - 1);
			}
		}
	}
	
	public static boolean isColidding(Entity entity1, Entity entity2) {
		Rectangle entityMask1 = new Rectangle(entity1.getX(), entity1.getY(), entity1.getWidth(), entity1.getHeight());
		Rectangle entityMask2 = new Rectangle(entity2.getX(), entity2.getY(), entity2.getWidth(), entity2.getHeight());
			
		return entityMask1.intersects(entityMask2);
	}
	
	public void render(Graphics graphics) {
		graphics.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		//Usado para visualizar a colisão das entidade
		//graphics.setColor(Color.red);
		//graphics.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, mWidth, mHeight);
	}
}
