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
        super();
    }
    public void act()
    {
        // Add your action code here.
        super.act();
        energy++;
        newPlant();
    }
    protected void setVariables()
    {
        this.age = 0;
        this.maxAge = 1800;
        this.energy = 2;
    }
    public void newPlant()
    {
        //must be older that 1/3 of its lifespan
        if (stageOfLife < 1)
            return;
        //Ensures plants are only created sporadically
        if (Greenfoot.getRandomNumber(300) != 0)
            return;
        //Allows a random direction from the parent plant
        double angle = Math.toRadians(Greenfoot.getRandomNumber(360));
        //Random distance, occassionally further out, and mostly near the parent
        int distance;
        if (Greenfoot.getRandomNumber(100) < 80)
        {
            distance = Greenfoot.getRandomNumber(50);
        }
        else
        {
            distance = 150 + Greenfoot.getRandomNumber(151);
        }
        //Offset from the parent plant for x and y
        int xOffset = (int)(distance * Math.cos(angle));
        int yOffset = (int)(distance * Math.sin(angle));
        //checks coordinate is in world
        if(!xInWorld(getX() + xOffset) || !yInWorld(getY() + yOffset))
            return;
        //checks that a plant is not near the proposed coordinate
        if (isFree(getX() + xOffset, getY() + yOffset,20))
        {
            getWorld().addObject(new Plant(), getX() + xOffset, getY() + yOffset);
        }
    }
    protected boolean isFree(int x, int y, int radius)
    {
        for (Plant plant: getWorld().getObjects(Plant.class))
        {
            double distance = findDistance(plant, x, y);
            if (distance<radius)
            {
                return false;
            }
        }
        return true;
    }
    //increases the size of the plant
    protected void grow()
    {
        getImage().scale(originalWidth /3, originalHeight /3);
    }
    //decreases the size of the plant
    protected void shrink()
    {
        getImage().scale(originalWidth /4, originalHeight /4);
    }
    
} 
