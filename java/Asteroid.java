import acm.graphics.*;
import java.awt.Color;

public class Asteroid extends GVectorPolygon
{
    private double rotation;

    public Asteroid(int windowWidth, int windowHeight)
    { 
       super(windowWidth, windowHeight);
       this.addVertex(0, 0);
       this.addVertex(0, 50);
       this.addVertex(50, 50);
       this.addVertex(50, 0);
       this.recenter();
       rotation = 1.5*Math.random();
       this.setColor(Color.red);
    }

    // complete this in version 0.2
    @Override
    public void updatePosition()
    {
        this.rotate(rotation);
        super.updatePosition();
    }
}