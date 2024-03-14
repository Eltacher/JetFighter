import java.awt.Image; 

public class Enemy {
    boolean alive;
    int health;
    int screenWidth;
    int moveState;
    int moveCount;
    int ammo;
    int x, y, width, height;
    Image image;

    public Enemy(int x, int y, int w, int h, Image image, int screenWidth, int health, int ammo) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.image = image;
        alive = true;
        this.health = health;
        this.screenWidth = screenWidth;
        this.ammo = ammo;
    }

    public void decreaseHealth(int x) 
    {
        health = health - x;
        if (health <= 0) 
        {
            alive = false;
        }
    }

  
    public void moveLeft()    // Move enemy plane left
    {
        if (getX() > 5) {
            setX(getX() - 2);
           
        } 
        else 
        {
            moveState = 1;
        }
    }

    
    public void moveRight()  // Move enemy plane right
    { 
        if (screenWidth - getWidth() > getX() + 5)
        {
            setX(getX() + 2);
            
        } 
    	else 
    	{
            moveState = 0;
        }
    }

    
    public void moveLR()  // Move enemy plane
    {
        if (moveState == 1) 
        {
            moveRight();
        } 
        else 
        {
            moveLeft();
        }
    }

    
    public void moveToDown()  // Move enemy plane down
    {
        setY(getY() + 1);
    }

    public boolean isEnemyAlive() 
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

    public int getMoveState() 
    {
        return moveState;
    }

    public void setMoveState(int moveState) 
    {
        this.moveState = moveState;
    }

    public int getMoveCount() 
    {
        return moveCount;
    }

    public void setMoveCount(int moveCount) 
    {
        this.moveCount = moveCount;
    }

    public int getAmmo() 
    {
        return ammo;
    }

    public void setAmmo(int ammo) 
    {
        this.ammo = ammo;
    }

    public void decreaseAmmo() 
    {
        ammo--;
    }

    // Additional methods for getting image and handling coordinates
    public int getX() 
    {
        return x;
    }

    public void setX(int x) 
    {
        this.x = x;
    }

    public int getY() 
    {
        return y;
    }

    public void setY(int y) 
    {
        this.y = y;
    }

    public int getWidth() 
    {
        return width;
    }

    public int getHeight() 
    {
        return height;
    }

    public Image getImage()
    {
        return image;
    }
}
