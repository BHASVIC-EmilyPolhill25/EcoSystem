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

    public Plant()
    {
        getImage().scale(getImage().getWidth() /2, getImage().getHeight()/2);
        this.age = 0;
        this.maxAge = 1800;
        this.energy = 2;
    }
    public void act()
    {
        // Add your action code here.
        super.act();
        energy++;
        newPlant();
    }
    public void newPlant()
    {
        if (stageOfLife < 1)
            return;
        if (Greenfoot.getRandomNumber(300) != 0)
            return;
        double angle = Math.toRadians(Greenfoot.getRandomNumber(360));
        
        int distance = Greenfoot.getRandomNumber(10);
        if (Greenfoot.getRandomNumber(100) < 80)
        {
            distance = Greenfoot.getRandomNumber(60);
        }
        else
        {
            distance = 150 + Greenfoot.getRandomNumber(151);
        }
        int xOffset = (int)(distance * Math.cos(angle));
        int yOffset = (int)(distance * Math.cos(angle));
        
        if (getObjectsAtOffset(xOffset, yOffset, Plant.class).isEmpty())
        {
            getWorld().addObject(new Plant(), getX() + xOffset, getY() + yOffset);
        }
    }
    public void reduceEnergy()
    {
        this.energy--;
    }
} 
