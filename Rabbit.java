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
    //Rabbits created after the simulation started
    public Rabbit(Animal mother)
    {
        super(mother);
    }
    //Rabbits created with the simulation
    public Rabbit(boolean female)
    {
        super(female);
    }
    protected void setVariables()
    {
        this.maxAge = 3000;
        this.age = 0;
        this.speed = 2;
        this.energy = 1000;
        this.maxEnergy = 2500;
        this.breedEnergyMin = 800;
        this.breedCost = 200;
        this.litterSize = 3;
    }
    public void act()
    {
        super.act();
    }
    protected Animal createBaby(Animal mother)
    {
        return new Rabbit(mother);
    }
    protected Class<? extends Animal> getMateClass()
    {
        return Rabbit.class;
    }
    protected Class<? extends Animal> getPredatorClass()
    {
        return Fox.class;
    }
    protected Class<? extends Organism> getFoodClass()
    {
        return Plant.class;
    }
}
