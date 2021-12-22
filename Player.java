import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Duke here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Player extends Actor
{
    private int horizontalSpeed = 2, verticalSpeed = 10,
                horizontalVelocity = 0, verticalVelocity = 0,
                maxHorizontalVelocity = 7, maxVerticalVelocity = 10;

    public int  spawnX = 0, spawnY = 0;
                
    private double horizontalVelocityIncrement = 1, 
                   verticalVelocityIncrement = 1, 
                   jumpDuration = 0.25, newFall = 0,
                   fallIncrement = 1;
    
    private String lastHorizontal, lastVertical,
                   previousHorizontal, previousVertical;
    
    private boolean jumping = false, loaded = false, facing = false, //False is left, and true is right;
    alive = true, midAir = false, offMap = false;
    
    private long time = 0,
                systemTime = 0,
                timeOfDeath = 0,
                timeOfJumping = 0,
                jumpHeight = 120,
                startHeightFromJump = 0,
                framesBeforeFall = 10, lastFallFrame = 0,
                respawnTime = 3000;
    
    public String drawState = "idle";
    
    private GreenfootImage sprite = new GreenfootImage("player/boxie_idle.png");
    
    private MyWorld thisWorld = ((MyWorld) getWorld());
    
    /**
     * Act - do whatever the Duke wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Player(int startX, int startY, int width, int height){
        sprite.scale(96, 96);
        setImage(sprite);
        if (startX > 0 && startY > 0){
            spawnX = startX;
            spawnY = startY;
        }
    }
    
    public void act() 
    {
        if (getWorld() == null) return;
        if (!loaded){
            loaded = true;
            thisWorld = ((MyWorld) getWorld());
        }
        if (!alive){
            if (drawState != "dead") changeDrawState("dead");
            
            thisWorld.displayText("respawning in "+(((timeOfDeath + respawnTime) - systemTime) / 1000) , 200, 30);
            thisWorld.displayText("Lost a life, you now have " + thisWorld.lives + " lives left." , thisWorld.getWidth()-500, 30);
            if (systemTime > timeOfDeath + respawnTime){
                alive = true;
                timeOfDeath = 0;
                thisWorld.displayText(" ", 200, 30);
            }
            
            if (offMap) resetPos();
        }
        else checkKeys();
        
        updateSharedValues();
    }
    
    public void updateSharedValues(){
        MyWorld selectedWorld = (MyWorld) getWorld();
        time = selectedWorld.time;
        systemTime = selectedWorld.systemTime;
    }
    
    public void checkKeys()
    {
        checkDirectionChange();
        if (Greenfoot.isKeyDown("right"))
            move("right");
            
        else if (Greenfoot.isKeyDown("left"))
            move("left");
        
        else if (drawState != "idle" && midAir == false)
            changeDrawState("idle");
            
        checkCollision();
    }
    
    public Actor collisionAt(int x, int y){
        return getOneObjectAtOffset(x, y, CollisionBlock.class);
    }
    
    public Actor collisionAtType(int x, int y, int colType){
        Actor check = getOneObjectAtOffset(x, y, CollisionBlock.class), object = null;
        if (check != null){
            for (Actor obj : getObjectsAtOffset(x, y, CollisionBlock.class)){
                if ( ((CollisionBlock) obj).collisionType == colType){
                    object = ((CollisionBlock) obj);
                    break;
                }
            }
        }
        return object;
    }
    
    public void checkCollision(){
        if (collisionAt(0, getImage().getHeight() / 2) != null){
            CollisionBlock block = ((CollisionBlock) collisionAt(0, getImage().getHeight() / 2));
            if (block.breakable == true){
                block.canFall = true;
            }
        }
        
        if (collisionAtType(0, getImage().getHeight() / 4, 1000) != null){
            if (thisWorld.coins == thisWorld.maxCoins){
                thisWorld.level++; thisWorld.loadLevel(thisWorld.level);
            }
            else thisWorld.displayText("not enough coins to pass to the next level!", thisWorld.getWidth()-500, 30);
        }
        
        if (
            getY()-getImage().getHeight() > thisWorld.getHeight() 
            || collisionAtType(0, getImage().getHeight() / 4, 3) != null 
            && collisionAtType(0, getImage().getHeight() / 4, 1) == null
           ){
            respawn();
        }
        
        if (collisionAtType(0, getImage().getHeight() / 4, 999) != null){
            thisWorld.coins += 1;
            thisWorld.removeObject( ((CollisionBlock) collisionAtType(0, getImage().getHeight() / 4, 999)) );
        }
        
        if ((collisionAtType(0, getImage().getHeight() / 2, 1) != null || collisionAtType(0, getImage().getHeight() / 2, 2) != null) && jumping == false) {
            int distanceY = (int)(getImage().getHeight() / 2)+5;
            Actor grounded = getOneObjectAtOffset(0, getImage().getHeight() / 2, CollisionBlock.class);
            
            if (grounded == null ){
                return;
            }
            else {
                if ((((CollisionBlock) grounded).collisionType == 1 || ((CollisionBlock) grounded).collisionType == 2) && midAir){
                    midAir = false;
                    moveOnCollision(grounded);
                }
                if (Greenfoot.isKeyDown("up")){
                    jumping = true;
                    midAir = true;
                    timeOfJumping = System.currentTimeMillis();
                    startHeightFromJump = getY()-10;
                    timeOfJumping = systemTime;
                }
            }
        }
        else if (jumping && timeOfJumping < systemTime){
            double percentageOfJump = (systemTime - timeOfJumping) / (jumpDuration * 1000);
            long jumpY = startHeightFromJump - (long) (jumpHeight * ( percentageOfJump));
            
            if (collisionAtType(0, 7, 1) == null && startHeightFromJump + jumpHeight > jumpY && jumpY < startHeightFromJump + jumpHeight){
                setLocation (getX(),  (int) jumpY );
                if (drawState != "jump")
                    changeDrawState("jump");
            }
            if ( systemTime > timeOfJumping + ( jumpDuration * 1000 ) ){
                jumping = false;
                startHeightFromJump = 0;
                timeOfJumping = 0;
                newFall = getY();
            }
        }
        else {
            if (systemTime - framesBeforeFall > lastFallFrame){
                lastFallFrame = systemTime;
                move("down");
            }
        }
    }
    
    public void checkDirectionChange (){
        if (lastHorizontal != previousHorizontal)
            resetVelocity("horizontal");
            
        else if (lastVertical != previousVertical)
            resetVelocity("vertical");
    }
    
    public void moveOnCollision(Actor collision){
        int actorHeight = collision.getImage().getHeight();
        if (drawState != "idle")
            changeDrawState("idle");
        setLocation(getX(), collision.getY()-(actorHeight+getImage().getHeight()) / 2);
    }
    
    public void move(String direction){
        switch (direction){
            case "up":
                if (drawState != "jump")
                    changeDrawState("jump");
                
                setLocation (getX(), getY() - verticalSpeed - verticalVelocity);
                increaseVelocity("vertical");
                previousVertical = lastVertical;
                lastVertical = "up";
                break;
                
            case "down":
                if (drawState != "jump")
                    changeDrawState("jump");
                setLocation (getX(), getY() + (verticalSpeed + verticalVelocity));
                increaseVelocity("vertical");
                previousVertical = lastVertical;
                lastVertical = "down";
                break;
                
            case "left":
                if (collisionAtType(-horizontalSpeed-horizontalVelocity, 16, 1) != null)
                    return;
                    
                if (getX() - (getImage().getWidth() / 4) < 0)
                    return;
                    
                if (drawState != "walk")
                    changeDrawState("walk");
                    
                setLocation (getX() - horizontalSpeed - horizontalVelocity, getY());
                increaseVelocity("horizontal");
                previousHorizontal = lastHorizontal;
                lastHorizontal = "left";
                if (facing == false){
                    facing = true;
                    getImage().mirrorHorizontally();
                }
                break;
                
            case "right":
                if (collisionAtType(horizontalSpeed+horizontalVelocity, 16, 1) != null)
                    return;
                    
                if (getX() + (getImage().getWidth() / 4) > thisWorld.getWidth())
                    return;
                    
                if (drawState != "walk")
                    changeDrawState("walk");
                    
                setLocation (getX() + horizontalSpeed + horizontalVelocity, getY());
                increaseVelocity("horizontal");
                previousHorizontal = lastHorizontal;
                lastHorizontal = "right";
                if (facing == true){
                    facing = false;
                    getImage().mirrorHorizontally(); 
                }
                break;
        }
    }
    public void increaseVelocity(String direction){
        switch (direction){
            case "vertical":
                if (verticalVelocity + verticalVelocityIncrement < maxVerticalVelocity)
                    verticalVelocity += verticalVelocityIncrement;
                break;
                
            case "horizontal":
                if (horizontalVelocity + horizontalVelocityIncrement < maxHorizontalVelocity)
                    horizontalVelocity += horizontalVelocityIncrement;
                break;
        }
    }
    
    public void changeDrawState(String state){
        drawState = state;
        switch(drawState){
            case "idle": sprite = new GreenfootImage("player/boxie_idle.png"); break;
            case "walk": sprite = new GreenfootImage("player/boxie_walk.png"); break;
            case "jump": sprite = new GreenfootImage("player/boxie_jump.png"); break;
            case "dead": sprite = new GreenfootImage("player/boxie_dead.png"); break;
        }
        draw();
    }
    
    public void draw(){
        sprite.scale(96, 96);
        setImage(sprite);
        if (facing == true)
            getImage().mirrorHorizontally();
    }
    
    /* Resets the current velocity preventing too much slide. */
    public void resetVelocity(String direction){
        switch (direction){
            case "vertical": verticalVelocity = 0; break;
            case "horizontal": horizontalVelocity = 0; break;
        }
    }
    
    public void resetPos(){
        setLocation(spawnX, spawnY);
    }
    
    public void respawn(){
        if (thisWorld.lives > 0){
            resetPos();
            thisWorld.lives -= 1;
            timeOfDeath = systemTime;
        }
        else {
            thisWorld.lost = true;
        }
        alive = false;
    }
    

}
