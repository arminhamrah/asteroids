import acm.program.*;
import acm.graphics.*;
import java.awt.Color;
import java.awt.event.*;

public class Test extends GraphicsProgram
{

    public void run()
    {
        GOval dot = new GOval (200, 300, 5, 5);
        dot.setFilled(true);
        dot.setColor(Color.red);
        add(dot);
        GVectorPolygon poly = new GVectorPolygon(getWidth(), getHeight());
        poly.addVertex(50, -60);
        poly.addVertex(60, -50);
        poly.addVertex(10, 10);
        poly.addVertex(15, 15);
        poly.addVertex(-25, 15);
        poly.addVertex(-25, -15);
        poly.addVertex(-20, -10);
        poly.rotate(45);
        poly.scale(2.5);
        add(poly);
        pause(20);
        poly.move(50, 100);
        poly.increaseVelocity(1);
        //how to make new asteroid
        Asteroid steve = new Asteroid(this.getWidth(), this.getHeight());
        steve.setLocation(350, 175);
        steve.rotate(144);
        steve.increaseVelocity(2);
        add(steve);
        while (true)
        {
            pause(10);
            poly.updatePosition();
        }
    }
}