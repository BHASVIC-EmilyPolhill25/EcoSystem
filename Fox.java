import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fox extends Animal
{
    /**
     * Act - do whatever the Fox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Fox()
    {
        getImage().scale(getImage().getWidth() /2, getImage().getHeight()/2);
        this.age = 0;
        this.maxAge = 4200;
        this.speed = 1;
        this.energy = 1800;
        this.maxEnergy = 5000;
        this.breedEnergyMin = 1500;
        this.breedCost = 500;
    }
    public void act()
    {
        // Add your action code here.
        super.act();
    }
    protected void moveAnimal()
    {
        Fox nearestFox = reproduce(Fox.class);
        if (nearestFox!=null)
        {
            if (intersects(nearestFox))
                {
                    breed(nearestFox, Fox.class);
                }
                else
                {
                    moveTowardObject(nearestFox);
                }
        }
        else
        {
            Rabbit nearest = findNearest(Rabbit.class);
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
    protected void eat()
    {
        if (energy < maxEnergy)
        {
            Rabbit currentRabbit = (Rabbit)getOneIntersectingObject(Rabbit.class);
            if (currentRabbit != null)
            {
                energy += (currentRabbit.getEnergy() / 2);
                getWorld().removeObject(currentRabbit);
            }
        }
    }
}
