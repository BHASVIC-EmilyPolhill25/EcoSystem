import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 650, 1);
        
        for(int i=0; i<40; i++)
        {
            int plantX = Greenfoot.getRandomNumber(getWidth());
            int plantY = Greenfoot.getRandomNumber(getHeight());
            Plant plant = new Plant();
            addObject(plant, plantX, plantY);
            plant.setAge(600);
        }
        for(int i=0; i<6; i++)
        {
            int rabbitX = Greenfoot.getRandomNumber(getWidth());
            int rabbitY = Greenfoot.getRandomNumber(getHeight());
            Rabbit rabbit = new Rabbit();
            addObject(rabbit, rabbitX, rabbitY);
        }
        for(int i=0; i<3; i++)
        {
            int foxX = Greenfoot.getRandomNumber(getWidth());
            int foxY = Greenfoot.getRandomNumber(getHeight());
            Fox fox = new Fox();
            addObject(fox, foxX, foxY);
        }
    }
}
