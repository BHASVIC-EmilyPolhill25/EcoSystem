import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.JOptionPane;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    protected boolean simulationStarted = false;
    
    Slider grassSlider;
    Slider rabbitSlider;
    Slider foxSlider;
    //New Display for the button that starts the simulation
    Display startButton = new Display("Start");
    //values that the sliders sit at automatically
    protected static int autoRPop = 20; 
    protected static int autoFPop = 10; 
    protected static int autoGPop = 50;
    //Actual current populations
    protected static int rabbitPop = 0; 
    protected static int foxPop = 0; 
    protected static int grassPop = 0;
    //Populations that the organisms start at
    protected static int rabbitStartPop = 70; 
    protected static int foxStartPop = 27; 
    protected static int grassStartPop = 360;
    //New Displays for each organisms population
    Display rPopDisplay = new Display("Rabbits: ");
    Display fPopDisplay = new Display("Fox: ");
    Display gPopDisplay = new Display("Grass: ");
    
    //Displays for the 2 reset buttons
    //resetButton only resets the simulation, with the same starting populations, just placed randomly so different to the previous
    Display resetButton = new Display("Reset");
    //resetNumbersButton allows the user to change the starting populations
    Display resetNumbersButton = new Display("Reset Numbers");
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 650, 1);
        
        showStartScreen();
    }
    public void act()
    {
        //Prevents populations from being displayed while user on startScreen
        if(simulationStarted)
            updatePopulations();
        
        if(Greenfoot.mouseClicked(startButton))
        {
            startSimulation();
            simulationStarted = true;
        }
        
        if(Greenfoot.mouseClicked(resetButton))
        {
            reset();
        }
        if(Greenfoot.mouseClicked(resetNumbersButton))
        {
            resetNumbers();
        }
    }
    public void showStartScreen()
    {
        removeObjects(getObjects(Actor.class));
        
        int halfWidth = getWidth()/2;
        int halfHeight = getHeight()/2;
        
        grassSlider = new Slider("Grass Population",1, 10000, autoGPop);
        rabbitSlider = new Slider("Rabbit Population", 1, 500, autoRPop);
        foxSlider = new Slider("Fox Population", 1, 500, autoFPop);
        
        addObject(grassSlider, halfWidth, halfHeight - 100);
        addObject(rabbitSlider, halfWidth, halfHeight);
        addObject(foxSlider, halfWidth, halfHeight + 100);
        
        addObject(startButton , halfWidth, halfHeight - 150);
    }
    public void startSimulation()
    {
        //Gets chosen values from the sliders before removing them(and the start button) then adding the organisms and population/button displays
        grassStartPop = grassSlider.getValue();
        rabbitStartPop = rabbitSlider.getValue();
        foxStartPop = foxSlider.getValue();
        
        removeObjects(getObjects(Actor.class));
        
        addSimulationObjects();
    }
    //boolean allows the organisms added in the beginning have equal numbers of each sex
    private boolean female = true;
    //adds organisms to the world
    public void populateWorld()
    {
        for(int i=0; i<grassStartPop; i++)
        {
            int plantX = Greenfoot.getRandomNumber(getWidth());
            int plantY = Greenfoot.getRandomNumber(getHeight());
            Plant plant = new Plant();
            plant.age = Greenfoot.getRandomNumber(plant.maxAge);
            plant.checkStageOfLife();
            addObject(plant, plantX, plantY);
        }
        for(int i=0; i<rabbitStartPop; i++)
        {
            int rabbitX = Greenfoot.getRandomNumber(getWidth());
            int rabbitY = Greenfoot.getRandomNumber(getHeight());
            Rabbit rabbit = new Rabbit(female);
            addObject(rabbit, rabbitX, rabbitY);
            female = !female;
        }
        for(int i=0; i<foxStartPop; i++)
        {
            int foxX = Greenfoot.getRandomNumber(getWidth());
            int foxY = Greenfoot.getRandomNumber(getHeight());
            Fox fox = new Fox(female);
            addObject(fox, foxX, foxY);
            female = !female;
        }
    }
    public void addSimulationObjects()
    {
        //save start pops
        autoGPop = grassStartPop;
        autoRPop = rabbitStartPop;
        autoFPop = foxStartPop;
        //add organism
        populateWorld();
        //add population displays
        addObject(rPopDisplay, 100, 50);
        addObject(fPopDisplay, 100, 100);
        addObject(gPopDisplay, 100, 150);
        updatePopulations();
        //add buttons
        addObject(resetButton, 100, 200);
        
        addObject(resetNumbersButton, 100, 250);
        showText("", 200, 200);
        //make sure display objects can be seen
        setPaintOrder(Display.class, Organism.class);
    }
    //Keeps the populations current to the simulation
    public void updatePopulations()
    {
        rabbitPop = getObjects(Rabbit.class).size();
        showText("" + rabbitPop, 200, 50);
        foxPop = getObjects(Fox.class).size();
        showText("" + foxPop, 200, 100);
        grassPop = getObjects(Plant.class).size();
        showText("" + grassPop, 200, 150);
    }
    //takes user back to start screen
    public void resetNumbers()
    {
        simulationStarted = false;
        removeObjects(getObjects(Actor.class));
        
        showText("", 200, 50);
        showText("", 200, 100);
        showText("", 200, 150);
        
        showStartScreen();
    }
    //resets populations back to the starting populations
    public void reset()
    {
        removeObjects(getObjects(Organism.class));
        addSimulationObjects();
    }
}
