import acm.graphics.*;

public class GVectorPolygon extends GPolygon
{
    private double vx, vy;
    private double theta;
    private double maxX, maxY; // dimensions of the "window" to enable "wrapping around"
    private static final double MAXVELOCITY = 3;

    public GVectorPolygon(int windowWidth, int windowHeight)
    {
       // to be written in version 0.1
       super();
       maxX = windowWidth;
       maxY = windowHeight;
    }

    public void increaseVelocity(double numPixels)
    {
       double thetaInRadians = theta * Math.PI / 180;
       vx += numPixels * Math.cos(thetaInRadians);
       vy -= numPixels * Math.sin(thetaInRadians);
       double v = Math.sqrt(vx*vx + vy*vy);
       if (v > MAXVELOCITY)
       {
           vx*=MAXVELOCITY/v;
           vy*=MAXVELOCITY/v;
        }
    }

    public void updatePosition()
    {
        move(vx, vy);
        
        if (getX()<0)
        {
            setLocation(maxX, getY());
        }
        if (getX()>maxX)
        {
            setLocation(0, getY());
        }
        if (getY()<0)
        {
            setLocation(getX(), maxY);
        }
        if (getY()>maxY)
        {
            setLocation(getX(), 0);
        }
    }

    public void rotate(double angle)
    {
        theta+=angle;
        super.rotate(angle);
    }

    public double getTheta()
    {
        return theta;
    }

    public double getVelocityX()
    {
        return vx;
    }

    public double getVelocityY()
    {
        return vy;
    }

    public double getWindowWidth()
    {
        return maxX;
    }
    
    public double getWindowHeight()
    {
        return maxY;
    }
}