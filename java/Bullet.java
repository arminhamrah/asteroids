import java.awt.Color;

public class Bullet extends GVectorPolygon
{
    private int numTurns;
    private static final int MAXTURNS=150;
    
    public Bullet(int windowWidth, int windowHeight)
    {
        super(windowWidth, windowHeight);
        this.addVertex(-10, -10);
        this.addVertex(-15, 0);
        this.addVertex(-10, 10);
        this.addVertex(0, 15);
        this.addVertex(10, 10);
        this.addVertex(15, 0);
        this.addVertex(10, -10);
        this.addVertex(0, -15);
        this.setColor(Color.GREEN);
        numTurns = 0;
    }
    
    @Override
    public void updatePosition()
    {
        numTurns++;
        super.updatePosition();
    }
    
    public boolean stillMoving()
    {
        return numTurns < MAXTURNS;
    }
    
    // complete this in version 0.5

}