import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Rabbit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rabbit extends Animal
{
    /**
     * Act - do whatever the Rabbit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Rabbit()
    {
        getImage().scale(getImage().getWidth() /2, getImage().getHeight()/2);
        this.age = 0;
        this.maxAge = 3000;
        this.speed = 2;
        this.energy = 1200;
        this.maxEnergy = 2400;
        this.breedEnergyMin = 800;
        this.breedCost = 200;
    }
    public void act()
    {
        // Add your action code here.
        super.act();
    }
    protected void moveAnimal()
    {
        List<Fox> foxes = getObjectsInRange(200, Fox.class);
        if (foxNearby())
        {
            runAway();
        }
        else 
        {
            Rabbit nearestRabbit = reproduce(Rabbit.class);
            if (nearestRabbit != null)
            {
                if (intersects(nearestRabbit))
                {
                    breed(nearestRabbit, Rabbit.class);
                }
                else
                {
                    moveTowardObject(nearestRabbit);
                }
            }
            else
            {
                Plant nearest = findNearest(Plant.class);
                if (nearest!=null)
                {
                    moveTowardObject(nearest);
                }
                else
                {
                    wander();
                }
            }
        }
    }
    protected void eat()
    {
        if (energy < maxEnergy)
        {
            Plant currentPlant = (Plant)getOneIntersectingObject(Plant.class);
            if (currentPlant != null)
            {
                energy+= (currentPlant.getEnergy()/2);
                getWorld().removeObject(currentPlant);
            }
        }
    }
    public boolean foxNearby()
    {
        List<Fox> foxes = getObjectsInRange(200, Fox.class);
        return !foxes.isEmpty();
    }
    public void runAway()
    {
        
    }
}
