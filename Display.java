import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Populations here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Display extends UIObjects
{
    /**
     * Act - do whatever the Populations wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    //This class is used do display values/buttons on the screen
    String name;
    public Display(String aname)
    {
        this.name = aname;
        setImage(new GreenfootImage(name, 30, Color.BLACK, Color.WHITE));
    }
    public void act()
    {
        setImage(new GreenfootImage(name, 30, Color.BLACK, Color.WHITE));
    }
}
