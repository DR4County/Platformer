import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class into here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class gui extends Actor
{
    private boolean loaded = false;
    private MyWorld thisWorld = ((MyWorld) getWorld());
    public int x = 0,
               y = 0;
    /**
     * Act - do whatever the into wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (loaded == false){
            thisWorld = ((MyWorld) getWorld());
            loaded = true;
            setLocation(x, y);
        }
    }
}
