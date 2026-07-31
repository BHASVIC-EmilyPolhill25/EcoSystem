import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Animal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Animal extends Organism
{
    /**
     * Act - do whatever the Animal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected char sex;
    protected int speed;
    protected int wanderX;
    protected int wanderY;
    protected int wanderTime = 0;
    protected int searchArea = 200;
    protected int breedEnergyMin;
    protected int breedCost;

    public Animal()
    {
        getImage().scale(getImage().getWidth() /13, getImage().getHeight()/13);
        int num = Greenfoot.getRandomNumber(2);
        if (num == 1)
            sex = 'f';
        else
            sex = 'm';
    }
    public void act()
    {
        super.act();
        energy--;
        moveAnimal();
        eat();
        //reproduce();
    }
    protected void wander()
    {
        int destinationX = getX()+wanderX;
        int destinationY = getY()+wanderY;
        if(wanderTime<=0 || (findDistance(destinationX, destinationY) < speed))
        {
            while (findDistance(destinationX, destinationY)< 50 || ( !(xInWorld(destinationX) && yInWorld(destinationY))))
            {
                wanderX = Greenfoot.getRandomNumber(501)-250;
                wanderY = Greenfoot.getRandomNumber(501)-250;
                destinationX = getX()+wanderX;
                destinationY = getY()+wanderY;
            }
                
            wanderTime = 75;
        }
        wanderTime--;
        turnTowards(destinationX, destinationY);
        move(speed);
    }
    protected double findDistance(Actor actor)
    {
        double distance = Math.hypot(
                    actor.getX() - getX(),
                    actor.getY() - getY()
                );
        return distance;
    }
    public char getSex()
    {
        return sex;
    }
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
    protected double findDistance(int x, int y)
    {
        double distance = Math.hypot(
                    x - getX(),
                    y - getY()
                );
        return distance;
    }
    // T is a placeholder for any Actor class, stores e.g. Fox
    // currentClass stores e.g. Fox.class
    protected <T extends Actor> T findNearest(Class<T> currentClass)
    {
        List<T> objects = getObjectsInRange(searchArea, currentClass);
        if (!objects.isEmpty())
        {
            double shortestDistance = Double.MAX_VALUE;
            T nearestQ = null;
            for (T object : objects)
            {
                double distance = Math.hypot(
                    object.getX() - getX(),
                    object.getY() - getY()
                );
            
                if (distance < shortestDistance)
                {
                    shortestDistance = distance;
                    nearestQ= object;
                }
            }
            return nearestQ;
        }
        else
        {
            return null;
       }
    }
    protected void moveTowardObject(Actor nearest)
    {
        turnTowards(nearest.getX(), nearest.getY());
        move(speed);
    }
    protected abstract void moveAnimal();
    protected <T extends Animal> T reproduce(Class<T> currentClass)
    {
        if (energy> breedEnergyMin && stageOfLife >= 1)
        {
            List<T> objects = getObjectsInRange(searchArea, currentClass);
            List<T> availableObjects = newArrayList<T>();
            for (T object : objects)
            {
                if ((object.sex != this.sex) && (object.energy > breedEnergyMin) && object.stageOfLife >= 1)
                {
                    availableObjects.add(object);
                }
            }
            if (!availableObjects.isEmpty())
            {
                double shortestDistance = Double.MAX_VALUE;
                T nearestQ = null;
                for (T object : availableObjects)
                {
                    double distance = Math.hypot(
                        object.getX() - getX(),
                        object.getY() - getY()
                    );
                
                    if (distance < shortestDistance)
                    {
                        shortestDistance = distance;
                        nearestQ= object;
                    }
                }
                return nearestQ;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    protected <T extends Animal> void breed(T partner, Class<T> cls)
    {
        if (partner == null)
            return;
    
        if (intersects(partner))
        {
            try
            {
                T baby = cls.getDeclaredConstructor().newInstance();
    
                getWorld().addObject(
                    baby,
                    (getX() + partner.getX()) / 2,
                    (getY() + partner.getY()) / 2
                );
                
                energy -= breedCost;
                partner.energy -= partner.breedCost;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    protected abstract void eat();
}