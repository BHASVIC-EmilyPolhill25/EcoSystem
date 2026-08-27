import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Slider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Slider extends UIObjects
{
    /**
     * Act - do whatever the Slider wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int maxValue;
    protected int minValue;
    protected int value;
    
    protected int width = 390;
    protected int height = 70;
    protected int handleSize = 20;
    
    protected int handleX = width/2;
    protected int minX = 10;
    protected int maxX = 290;
    protected String label;
    
    protected int labelY = 15;
    protected int sliderY = 45;
    public Slider(String aLabel, int aMin, int aMax, int aStartingValue)
    {
        this.label = aLabel;
        this.maxValue = aMax;
        this.minValue = aMin;
        this.value = aStartingValue;
        this.handleX = valueToX(value);
        updateImage();
    }
    public void act()
    {
        checkMouse();
    }
    public void updateImage()
    {
        //Creates new image
        GreenfootImage image = new GreenfootImage(width, height);
        
        //adds label
        image.drawString(label, 10, labelY);
        //draws bar
        image.fillRect(minX, sliderY - 3, maxX-minX, 6);
        //draws handle
        image.fillOval(handleX - handleSize/2, sliderY - handleSize/2, handleSize, handleSize);
        //choice display (String.valueOf converts a data type to a string so it can be displayed)
        image.drawString(String.valueOf((int)value), width - 50, sliderY + 5);
        
        setImage(image);
    }
    //checks if slider handle is being dragged
    public void checkMouse()
    {
        if (Greenfoot.mouseDragged(this))
        {
            //relative to the image
            int relativeMouseX = Greenfoot.getMouseInfo().getX() - (getX() - width / 2);
            //check in range of slider before setting value
            if (relativeMouseX > maxX)
            {
                handleX = maxX;
            }
            else if(relativeMouseX < minX)
            {
                handleX = minX;
            }
            else
            {
                handleX = relativeMouseX;
            }
            value = xToValue(handleX);
            updateImage();
        }
    }
    //Math.round() rounds to the nearest whole number)
    //converts number of organisms to x value on image
    public int valueToX(int thisValue)
    {
        double proportion = (double)(thisValue - minValue)/(maxValue - minValue);
        int newX = minX + Math.round((float)(proportion * (maxX - minX)));
        return newX;
    }
    //converts x value on image to number of organisms
    public int xToValue(int thisX)
    {
        double proportion = (double)(thisX - minX)/(maxX - minX);
        int newValue = minValue + Math.round((float)(proportion * (maxValue - minValue)));
        return newValue;
    }
    
    public int getValue()
    {
        return value;
    }
}
