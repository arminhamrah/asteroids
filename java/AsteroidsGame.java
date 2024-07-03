import acm.program.*;
import acm.graphics.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import java.applet.AudioClip;
import acm.util.MediaTools;

public class AsteroidsGame extends GraphicsProgram
{
    private ArrayList<Asteroid> asteroids;
    private boolean playing;
    private GLabel notificationLabel, scoreLabel, shipsLabel;
    private Ship ship;
    private ArrayList<Bullet> bullets; 
    private int level;
    private int ships;
    private int score;
    private AudioClip thrustClip, fireClip, bigBangClip, mediumBangClip, smallBangClip;

    public void init()
    {
        thrustClip = MediaTools.loadAudioClip("sounds/thrust.wav");   
        fireClip = MediaTools.loadAudioClip("sounds/fire.wav");   
        bigBangClip = MediaTools.loadAudioClip("sounds/bangLarge.wav");   
        mediumBangClip = MediaTools.loadAudioClip("sounds/bangMedium.wav");   
        smallBangClip = MediaTools.loadAudioClip("sounds/bangSmall.wav");   

        level = 0;
        ships = 3;
        score = 0;

        setBackground(Color.BLACK);

        notificationLabel = new GLabel("(up) = thrust, (left) = rotate left, (right) = rotate right, (space) = fire. Click mouse to continue");
        notificationLabel.setColor(Color.WHITE);
        notificationLabel.setFont("Georgia-Bold-12");
        notificationLabel.setLocation((getWidth()-notificationLabel.getWidth())/2, getHeight()/2-40);
        add(notificationLabel);

        scoreLabel = new GLabel("Score:"+score);
        scoreLabel.setColor(Color.WHITE);
        scoreLabel.setFont("Courier-Plain-10");
        scoreLabel.setLocation(16, 16);
        add(scoreLabel);

        shipsLabel = new GLabel("Ships: " + ships);
        shipsLabel.setColor(Color.WHITE);
        shipsLabel.setFont("Georgia-Plain-10");
        shipsLabel.setLocation(50, 50);
        add(shipsLabel);

        asteroids = new ArrayList<Asteroid>();
        makeAsteroids();
        bullets = new ArrayList<Bullet>();
        makeShip();
    }

    private void makeAsteroids()
    {
        for (int i=0; i<3+level; i++)
        {
            Asteroid asteroid = new Asteroid(this.getWidth(), this.getHeight());
            asteroid.rotate(Math.random()*360);
            asteroid.setLocation(Math.random()*getWidth(), Math.random()*getHeight());
            asteroids.add(asteroid); //add to arraylist
            add(asteroid); //add to feltboard
            asteroid.rotate(Math.random()*360);
            asteroid.increaseVelocity(1);
        }      
    }

    public void run()
    {
        while (true)
        {
            pause(10);
            for (Asteroid a : asteroids)
            {
                a.updatePosition();
            }
            if (playing)
            {
                ship.updatePosition();
                for (int i=0; i<bullets.size(); i++)
                {
                    bullets.get(i).updatePosition();
                    if (!bullets.get(i).stillMoving())
                    {
                        remove(bullets.get(i));
                        bullets.remove(i);
                        i--;
                    }
                }
                shipCollided();
                shipsLabel.setLabel("Ships: " + ships);
                bulletsCollided();
                scoreLabel.setLabel("Score: " + score);
                if(asteroids.size()==0)
                {
                    level++;
                    makeAsteroids();
                }
              
            }
        }
    }

    private Asteroid checkForCollisions(GVectorPolygon obj)
    {
        for (Asteroid a:asteroids)
            if (a.getBounds().intersects(obj.getBounds()))
            {
                return a;
            }
        return null;       
    }

    private void shipCollided()
    {
        Asteroid x = checkForCollisions(ship);
        if (x!=null)
        {
            remove(ship);
            makeShip();
            playing = false;
            ships--;
            if (ships > 0) 
                notificationLabel.setLabel("You died. Click to play again.");
            else
                notificationLabel.setLabel("Game over.");
            add(notificationLabel);
        }
    }

    private void bulletsCollided()
    {
        for(int i=0; i<bullets.size();i++)
        {
            Asteroid x = checkForCollisions(bullets.get(i));
            if(x!=null)
            {
                remove(bullets.get(i));
                bullets.remove(i);
                i--;
                hitAsteroid(x);
                score += 10;
            }
        }
    }

    private void hitAsteroid(Asteroid x)
    {
        remove(x);
        asteroids.remove(x);
        double angle = Math.random()*120;
        if(x instanceof SmallAsteroid) //instance of?
        {
            smallBangClip.play();      
        }
        else if(x instanceof MediumAsteroid)
        {
            for(int i=0;i<3;i++)
            {
                mediumBangClip.play();
                SmallAsteroid smallAsteroid = new SmallAsteroid(this.getWidth(), this.getHeight());
                smallAsteroid.rotate(i*120+angle);
                smallAsteroid.setLocation(x.getX(),x.getY());
                smallAsteroid.increaseVelocity(1);
                add(smallAsteroid);
                asteroids.add(smallAsteroid);
            }
        }
        else
        {
            for(int i=0;i<3;i++)
            {
                bigBangClip.play();
                MediumAsteroid mediumAsteroid = new MediumAsteroid(this.getWidth(), this.getHeight());
                mediumAsteroid.rotate(i*120+angle);
                mediumAsteroid.setLocation(x.getX(),x.getY());
                mediumAsteroid.increaseVelocity(1);
                add(mediumAsteroid);
                asteroids.add(mediumAsteroid);
            }
        }
    }

    public void keyPressed(KeyEvent e)
    {
        if (playing)
        {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                ship.rotate(-10);
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            {
                ship.rotate(10);
            }
            else if(e.getKeyCode() == KeyEvent.VK_UP)
            {
                ship.increaseVelocity(0.1);
                thrustClip.play();
            }
            else if(e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                Bullet bullet = ship.makeBullet();
                add(bullet);
                bullets.add(bullet);
                fireClip.play();
            }
        }
    }

    public void mouseClicked(MouseEvent event)
    {
        if (ships > 0)
        {
            playing = true;
            remove(notificationLabel);
        }
    }

    public void makeShip()
    {
        ship = new Ship(getWidth(), getHeight());
        ship.setLocation(getWidth()/2, getHeight()/2);
        add(ship);
    }
}