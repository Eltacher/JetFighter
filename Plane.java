import java.awt.Image;

public class Plane {
	
	int x, y, width, height;
	Image image;
	boolean alive;
	int health, speed, screenWidth, rocketNumber, score;

	public Plane(int x, int y, int w, int h, Image image) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.image = image;
		alive = true;
		health = 100;
		speed = 10;
		rocketNumber = 100;
		score = 0;
	}
	
	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public void setScreenWidth(int w)
	{
		screenWidth = w;
	}

	public void decreaseHealth(int x)
	{
		health = health - x;
		if(health<=0) {
			alive = false;
		}
	}
	
	public void increaseHealth(int x)
	{
		health = health + x;
	}

	public void increaseX(){
		x++;
	}

	public void decreaseX(){
		x--;
	}

	public void increaseY(){
		y++;
	}

	public void decreaseY(){
		y--;
	}

	public void moveLeft()
	{
		for(int i=0; i<=speed; i++) 
		{
			if(x > 0) 
			{
				decreaseX();				
			}
		}
	}

	public void moveRight()
	{
		for(int i=0; i<=speed; i++) 
		{
			if(x < screenWidth - width) 
			{
				increaseX();
			}
		}
	}

	public void decreaseRocket()
	{
		rocketNumber--;
	}
	

	public int getRocketNumber()
	{
		return rocketNumber;
	}

	public void setRocketNumber(int rocketNumber)
	{
		this.rocketNumber = rocketNumber;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public void increaseScore(int userScore)
	{
		score += userScore;
	}

	public Image getImage() 
	{
		return image;
	}

	public int getX() 
	{
		return x;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
}
