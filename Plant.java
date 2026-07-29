import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Plant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Plant extends Organism
{
    /**
     * Act - do whatever the Plant wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int age;
    private int health;
    public Plant()
    {
        
    }
    public void act()
    {
        // Add your action code here.
        age++;
        newPlant();
    }
    public void newPlant()
    {
        MyWorld myWorld = (MyWorld)getWorld();
        int width = myWorld.getWidth();
        if ((age % 600) == 0)
        {
            int x = Greenfoot.getRandomNumber(width);
            getWorld().addObject(new Plant(), x, getY());
        } 
    }
}
