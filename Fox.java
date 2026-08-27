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
    //Foxes that were created after the simulation started
    public Fox(Animal mother)
    {
        super(mother);
    }
    //Foxes that are created with the simulation
    public Fox(boolean female)
    {
        super(female);
        //setVariables();
    }
    public void act()
    {
        super.act();
    }
    protected void setVariables()
    {
        this.age = 0;
        this.maxAge = 4200;
        this.speed = 1;
        this.energy = 1000;
        this.maxEnergy = 5000;
        this.breedEnergyMin = 500;
        this.breedCost = 500;
        this.litterSize = 1;
        this.staticEatTime = 60;
    }
    protected Animal createBaby(Animal mother)
    {
        return new Fox(mother);
    }
    protected Class<? extends Animal> getMateClass()
    {
        return Fox.class;
    }
    protected Class<? extends Animal> getPredatorClass()
    {
        return null;
    }
    protected Class<? extends Organism> getFoodClass()
    {
        return Rabbit.class;
    }
}
