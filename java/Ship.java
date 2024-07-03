import java.awt.Color;

public class Ship extends GVectorPolygon
{

    public Ship(int windowWidth, int windowHeight)
    {
        super(windowWidth, windowHeight);        
        this.addVertex(-10, -20);
        this.addVertex(40, 0);
        this.addVertex(-10, 20);
        this.addVertex(0, 0);
        this.recenter();
        this.setColor(Color.white);
        this.recenter();
    }

    public Bullet makeBullet()
    {
        Bullet bullet = new Bullet((int)(getWindowWidth()), (int)(getWindowHeight()));
        bullet.setLocation(this.getCenterX(), this.getCenterY());
        bullet.rotate(getTheta());
        bullet.increaseVelocity(100);
        return bullet;
    }
}