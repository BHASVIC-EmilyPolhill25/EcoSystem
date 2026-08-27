import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Organism here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Organism extends Actor
{
    /**
     * Act - do whatever the Organism wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int energy;
    protected int age;
    protected int maxAge;
    protected int stageOfLife;
    protected int maxEnergy;
    //booleans here ensure growth/shrinking only occurs once
    protected boolean grown = false;
    protected boolean small = false;
    
    protected int originalWidth;
    protected int originalHeight;
    
    protected boolean canMove = true;
    //Organisms created while the simulation is running
    public Organism()
    {
        //allows image size to be changed according to the original size
        originalWidth = getImage().getWidth();
        originalHeight = getImage().getHeight();
        
        setVariables();
        
        this.age = 0;

        checkStageOfLife();
    }
    //Organisms created with the simulation
    public Organism(boolean female)
    {
        originalWidth = getImage().getWidth();
        originalHeight = getImage().getHeight();
        
        setVariables();
        //random age creates a more varied ecosystem
        this.age = Greenfoot.getRandomNumber(maxAge);

        checkStageOfLife();
    }
    public void act()
    {
        age++;
        checkStageOfLife();
    }
    //Kills organism if needed
    //returns true if organism dies
    public boolean checkLife()
    {
        if (energy <= 0 || age > maxAge || stageOfLife == 3)
        {
            if(getWorld() != null)
            {
                getWorld().removeObject(this);
                return true;
            }
        }
        return false;
    }
    //Checks what third of the organisms lifespan they are in (child, adult, elderly)
    //Also makes organism smaller if a child/bigger if an adult
    public void checkStageOfLife()
    {
        if (age < maxAge/3)
        {
            stageOfLife = 0;
            if (small == false)
            {
                shrink();
                small = true;
            }
        }
        else 
        {
            if (grown == false)
            {
                grow();
                grown = true;
            }
            if (age < 2*(maxAge/3))
            {
                stageOfLife = 1;
            }
            else if (age < maxAge)
            {
                stageOfLife = 2;
            }
            else
            {
                stageOfLife = 3;
            }
        }
    }
    //returns distance between an object and a coordinate
    protected double findDistance(Actor actor, int x, int y)
    {
        double distance = Math.hypot(
                    actor.getX() - x,
                    actor.getY() - y
                );
        return distance;
    }
    //returns distance between an object and the current object
    protected double findDistance(Actor actor)
    {
        double distance = Math.hypot(
                    actor.getX() - getX(),
                    actor.getY() - getY()
                );
        return distance;
    }
    // returns distance between coordinate and current object
    protected double findDistance(int x, int y)
    {
        double distance = Math.hypot(
                    x - getX(),
                    y - getY()
                );
        return distance;
    }
    public int getEnergy()
    {
        return energy;
    }
    public int getStageOfLife()
    {
        return stageOfLife;
    }
    protected void setAge(int newAge)
    {
        age = newAge;
    }
    protected void setStageOfLife(int number)
    {
        stageOfLife = number;
    }
    //returns true if x coordinate is not in the world
    protected boolean xInWorld(int x)
    {
        if (x<0 || x>getWorld().getWidth())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    //returns true if y coordinate not in the world
    protected boolean yInWorld(int y)
    {
        if (y<0 || y>getWorld().getHeight())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    protected abstract void grow();
    protected abstract void shrink();
    protected abstract void setVariables();
}
