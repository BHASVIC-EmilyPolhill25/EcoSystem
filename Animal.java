import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
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
    protected int noTurnTime = Greenfoot.getRandomNumber(10);
    protected int searchArea = 200;
    protected int babySearchArea = 100;
    protected int breedEnergyMin;
    protected int breedCost;
    protected int breedCooldown = 0;
    protected int litterSize;
    protected boolean nextGen = false;
    protected Animal mother;
    protected int staticEatTime = 20;
    protected int eatTime = staticEatTime;
    //Animals with a mother- were created after simulation started
    public Animal(Animal aMother)
    {
        super();
        int num = Greenfoot.getRandomNumber(2);
        //setVariables();
        this.mother = aMother;
        //sex is random
        if (num == 1)
        {
            setSex(true);
        }
            
        else
        {
            setSex(false);
        }
        //checkStageOfLife();
        nextGen = true;
    }
    //Animals created with the simulation
    public Animal(boolean female)
    {
        super(female);
        //sex is not random, switches every new creation
        setSex(female);
    }
    public void act()
    {
        super.act();
        energy--;
        //Checks whether animal should die
        if (checkLife())
            return;
        //If breedCooldown not run down yet, keep it lowering
        if (breedCooldown>0)
            breedCooldown--;
        //Stops moving if eating
        if (eating())
            return;
        //If being eaten, stop moving
        if (canMove == false)
            return;
        //Stops animals from getting stuck at edges
        if (atEdgeOfWorld())
        {
            wander();
            move(speed);
            return;
        }
        //Stops animals from turning constantly
        if (noTurnTime<=0)
        {
            //Adults/animals with no mother move by themselves
            //Children follow their mothers(when not in danger/not going to food)
            if(stageOfLife > 0 || mother == null || nextGen == false)
            {
                //If nothing of interest then move randomly
                if(!moveAnimal(searchArea))
                    wander();
            }
            else
            {
                //If mother has died then set mother to null
                if (mother.getWorld() == null)
                {
                    mother = null;
                    return;
                }
                //If nothing of interest then move towards mother
                if(!moveAnimal(babySearchArea))
                {
                    turnTowards(mother.getX() + Greenfoot.getRandomNumber(14)-7, mother.getY() + Greenfoot.getRandomNumber(14)-7);
                }
            }
            noTurnTime = 15;
        }
        move(speed);
        noTurnTime--;
    }
    protected void setSex(boolean female)
    {
        //sets sex and adds red squares for f and blue squares for m
        Color colour;
        if (female == true)
        {
            this.sex = 'f';
            colour = Color.RED;
        }
        else
        {
            this.sex = 'm';
            colour = Color.BLUE;
        }
        getImage().setColor(colour);
        
        int squareSize = 3;
        int centreX = (int)(getImage().getWidth()/2);
        int centreY = (int)(getImage().getHeight()/2);
        getImage().fillRect(centreX - squareSize/2,centreY - squareSize/2,squareSize,squareSize);
    }
    //Chooses random coordinates in the world, within a radius of the animal to turn towards
    protected void wander()
    {
        int destinationX = getX()+wanderX;
        int destinationY = getY()+wanderY;
        //if moving too long or going to reach destination next act then
        if(wanderTime<=0 || (findDistance(destinationX, destinationY) < speed))
        {
            //choose new coordinates
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
    }
    public char getSex()
    {
        return sex;
    }
    
    // T is a placeholder for any Actor class, stores e.g. Fox
    // currentClass stores e.g. Fox.class
    //finds the nearest object in a class within a radius
    //returns the nearest object or null if no object of that class is in radius
    protected <T extends Actor> T findNearest(Class<T> currentClass, int aSearchArea)
    {
        List<T> objects = getObjectsInRange(aSearchArea, currentClass);
        if (!objects.isEmpty())
        {
            //Allows you to find shortest distance by using the highest possible value to compare to
            double shortestDistance = Double.MAX_VALUE;
            T nearestQ = null;
            for (T object : objects)
            {
                double distance = findDistance(object);
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
    protected void turnTowardObject(Actor nearest)
    {
        turnTowards(nearest.getX(), nearest.getY());
    }
    //movement
    //returns true if something of intrest has been found(breeding partner, predator, or food)
    protected boolean moveAnimal(int aSearchArea)
    {
            Animal predator = null;
            //Checks if class has a predator
            if (getPredatorClass() != null)
                predator = findNearest(getPredatorClass(), aSearchArea);
            //turns away if a predator is found
            if (predator != null)
            {
                runAway(aSearchArea);
                return true;
            }
            //if not bred recently
            if(breedCooldown <= 0)
            {
                Animal nearestMate = findMate(getMateClass());
                if (nearestMate != null)
                {
                    if (intersects(nearestMate))
                    {
                        breed(nearestMate);
                    }
                    else
                    {
                        turnTowardObject(nearestMate);
                    }
                    return true;
                }
            }
            //if food found in the area
            if(findTurnFood(aSearchArea))
            {
                return true;
            }
            return false;
        }
    //if viable mate, return the (nearest) mate, else return null
    protected <T extends Animal> T findMate(Class<T> currentClass)
    {
        //not bred recently and old enough and have enough energy
        if (energy> breedEnergyMin && stageOfLife >= 1 && breedCooldown <= 0)
        {
            //all objects of the class in range
            List<T> objects = getObjectsInRange(searchArea, currentClass);
            //only stores viable mates
            List<T> availableObjects = new ArrayList<T>();
            for (T object : objects)
            {
                //checks mate meets the conditions
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
                    double distance = findDistance(object);
                
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
    //Makes the child if the partners intersect
    protected <T extends Animal> void breed(T partner)
    {
        //Checks there is a partner and makes sure only females do this method so we don't have double the children
        if (partner == null || this.sex == 'm')
            return;
    
        if (intersects(partner))
        {
            for(int i=0;i<litterSize;i++)
            {
                //creates baby and adds it to the world in between its mother and father
                Animal baby = createBaby(this);
                getWorld().addObject(baby,(getX() + partner.getX()) / 2,(getY() + partner.getY()) / 2);
                }
            //reduces energy and partners energy
            energy -= breedCost;
            partner.energy -= partner.breedCost;
            breedCooldown = 360;
        }
    }
    //once animal has reached food source
    //returns true if animal in process of eating
    protected boolean eating()
    {
        //stops them eating too much
        if (energy < maxEnergy)
        {
            Organism currentFood = (Organism)getOneIntersectingObject(getFoodClass());
            if (currentFood != null)
            {
                //makes them stop to eat
                if (eatTime > 0)
                {
                    eatTime--;
                    //stops food from moving
                    currentFood.canMove = false;
                    return true;
                }
                else
                {
                    //receives energy from food and removes it
                    energy+= (currentFood.getEnergy()/2);
                    getWorld().removeObject(currentFood);
                    //resets eat time
                    eatTime = staticEatTime;
                    return false;
                }
            }
            else
                return false;
        }
        else
            return false;        
    }
    //finds food and turns towards it
    //returns true if food found within a radius
    protected boolean findTurnFood(int aSearchArea)
    {
        if(energy < maxEnergy)
        {
            Organism nearestFood = findNearest(getFoodClass(), aSearchArea);
            if (nearestFood != null)
            {
                turnTowardObject(nearestFood);
                return true;
            }
            return false;
        }
        else
        {
            return false;
        }
    }
    //Checks if animal at the edge of the world
    //returns true if the animal is
    protected boolean atEdgeOfWorld()
    {
        if (getX() > getWorld().getWidth() - 2 || getX() < 2 || getY() > getWorld().getHeight() - 2 || getY() < 2)
        {
            return true;
        }
        else
            return false;
    }
    //Makes animal image larger
    protected void grow()
    {
        getImage().scale(originalWidth /26, originalHeight /26);
    }
    //Makes animal image smaller
    protected void shrink()
    {
        getImage().scale(originalWidth /36, originalHeight /36);
    }
    protected abstract Animal createBaby(Animal mother);
    //Lets animal turn away from the nearest predator
    protected void runAway(int aSearchArea)
    {
        Animal nearestPredator = findNearest(getPredatorClass(), aSearchArea);
        turnTowards(nearestPredator.getX(), nearestPredator.getY());
        turn(180);
    }
    protected abstract Class<? extends Animal> getMateClass();
    protected abstract Class<? extends Animal>getPredatorClass();
    protected abstract Class<? extends Organism> getFoodClass();
}