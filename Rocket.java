import java.awt.Image;
import java.util.Random;

public class Rocket {
	
	int x, y, width, height;
	Image image;
	int damage, direction, screenHeight;
	Random rand = new Random();
	int bulletSpeed = rand.nextInt(5) + 10;

	public Rocket(int x, int y, int screenHeight, int damage, int direction, int w, int h, Image image)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.image = image;
		this.screenHeight = screenHeight;
		this.damage = damage;
		this.direction = direction;
	}
	
	
	public void move() // Move
	{
		if(direction == 1)
		{
			setY(getY() + bulletSpeed);
		}
		else
		{
			setY(getY() - bulletSpeed);
		}
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDirection(){
		return direction;
	}

	public void setDirection(int direction){
		this.direction = direction;
	}
	
	// Getters and Setters for x, y, width, height, and image
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
