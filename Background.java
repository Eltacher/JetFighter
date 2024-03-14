import java.awt.Image;

public class Background {

    int x, y, width, height;
    Image image;
    int speed;

    public Background(int x, int y, int w, int h, Image image) 
    {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.image = image;
        speed = 5;
    }

    public void move() 
    {
        setY(getY() + speed);
    }

    public void setSpeed(int s) 
    {
        speed = s;
    }

    // Additional methods for  getting image and handling coordinates
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
