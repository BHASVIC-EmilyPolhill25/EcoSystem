import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Organism here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Organism extends Actor
{
    /**
     * Act - do whatever the Organism wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int energy;
    protected int age = 0;
    protected int maxAge;
    protected int population = 1; 
    protected int stageOfLife = 0;
    protected int maxEnergy;
    public Organism()
    {
        
    }
    public void act()
    {
        // Add your action code here.
        age++;
        checkStageOfLife();
        checkLife();
    }
    public void checkLife()
    {
        if (energy <= 0 || age > maxAge || stageOfLife == 3)
        {
            population -= 1;
            if(getWorld() != null)
            {
                getWorld().removeObject(this);
            }
        }
    }
    public void checkStageOfLife()
    {
        if (age < maxAge/3)
        {
            stageOfLife = 0;
        }
        else if (age < 2*(maxAge/3))
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
}
