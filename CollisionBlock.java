import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CollisionBlock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CollisionBlock extends Actor
{
    /**
     * Act - do whatever the CollisionBlock wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    /**
     * Block ID's
     * 0 - Dirt
     */
    private GreenfootImage sprite = new GreenfootImage("tiles\\dirt.png");
    private String spriteURL = "tiles\\dirt.png";
    private boolean loaded = false;
    
    public int w, h, ID, x, y, startX, startY;
    
    private MyWorld thisWorld = ((MyWorld) getWorld());
    public boolean elevator = false;
    public int elevatorX = 0, elevatorY = -5,
               collisionType = 0,
               timeBeforeFall = 50, timeToRespawn = 100,
               rotation = 0, collisionLayers[] = {};
               
    public long timeOfBreak = 0, timeOfBroken = 0;
    public boolean breakable = false, canFall = false, broken = false,
                   elevatorBump = false;

    public CollisionBlock (int width, int height, int Sprite_ID, int newType){
        GreenfootImage image = getImage();
        image.scale(width, height); 
        w = width;
        h = height;
        ID = Sprite_ID;
        collisionType = newType;
    }
    
    public void act() {
        if (loaded == false){
            thisWorld = ((MyWorld) getWorld());
            setSprite(w, h);
            loaded = true;
            x = getX();
            y = getY();
            startX = getX();
            startY = getY();
        }
        
        checkElevator();
        checkBreakable();
    }
    
    public String getSprite(int Index, String part){
        String fileName = "dirt.png";
        switch (part){
            case "left":
                switch (Index){
                    case 0: fileName = "dirt-left.png"; break;
                    case 1: fileName = "water-full.png"; break;
                }
            break;
            case "right":
                switch (Index){
                    case 0: fileName = "dirt-right.png"; break;
                    case 1: fileName = "water-full.png"; break;
                }
            break;
            case "single":
                switch (Index){
                    case 0: fileName = "dirt-single.png"; break;
                    case 1: fileName = "water-full.png"; break;
                }
            break;
            case "top":
                switch (Index){
                    case 0: fileName = "grass-middle.png"; break;
                    case 1: fileName = "water.png"; break;
                }
            break;
            case "top-left":
                switch (Index){
                    case 0: fileName = "grass-left.png"; break;
                    case 1: fileName = "water.png"; break;
                }
            break;
            case "top-right":
                switch (Index){
                    case 0: fileName = "grass-right.png"; break;
                    case 1: fileName = "water.png"; break;
                }
            break;
            case "top-single":
                switch (Index){
                    case 0: fileName = "grass-single.png"; break;
                    case 1: fileName = "water.png"; break;
                }
            break;
            default:
                switch (Index){
                    case 0: fileName = "dirt.png"; break;
                     case 1: fileName = "water-full.png"; break;
                      case 2: fileName = "cloud.png"; break;
                       case 3: fileName = "wood.png"; break;
                        case 4: fileName = "sign-1.png"; break;
                         case 5: fileName = "sign-2.png"; break;
                        case 6: fileName = "sign-3.png"; break;
                       case 7: fileName = "sign-4.png"; break;
                      case 8: fileName = "chair.png"; break;
                     case 9: fileName = "crystal-monster.png"; break;
                    case 10: fileName = "saw.png"; break;
                     case 11: fileName = "fireball.png"; break;
                      case 999: fileName = "coin.png"; break;
                       case 1000: fileName = "portal-box.png"; break;
                }
            break;
        }
        return "tiles\\"+fileName;
    }
    
    public int getMapping(int ID){
        int mappingSize = 1;
        switch (ID){
            case 0: 
             case 1:
              mappingSize = 6;
            break;
            
            case 2: case 3: case 4:
             case 5: case 6: case 7:
              case 8: case 9: case 10:
               case 11: case 999: case 1000:
              mappingSize = 1;
            break;
        }
        return mappingSize;
    }
    
    public void setSprite(int width, int height){
        boolean middle = collisionAtType(-((getImage().getWidth() / 2)+1), 0, ID)
                         && collisionAtType(16, 0, ID),
                         
            left = collisionAtType((getImage().getWidth() / 2), 0, ID) 
                   && collisionAtType(16, 0, ID), 
                   
            right = !collisionAtType(getImage().getWidth() / 2, 0, ID), 
            
            single = !collisionAtType((getImage().getWidth() / 2)+1, 0, ID)
                     && !collisionAtType(-(getImage().getWidth() / 2)-1, 0, ID),
                     
            top = !collisionAtType(0, -((getImage().getHeight() / 2)+1), ID)
                  && collisionAtType(-((getImage().getWidth() / 2)+1), 0, ID)
                  && collisionAtType(16, 0, ID),
                  
            onTop = !collisionAtType(0, -((getImage().getHeight() / 2)+1), ID);
      
        if (ID == 999 && thisWorld != null){ thisWorld.maxCoins += 1; }
      
        if (getMapping(ID) == 1)
            spriteURL = getSprite(ID, "");
            
        else if (getMapping(ID) == 6){
            if (middle)
                spriteURL = getSprite(ID, "");
                
            else if (left)
                spriteURL = getSprite(ID, "left");
                
            else if (right)
                spriteURL = getSprite(ID, "right");
                
            if (single)
                spriteURL = getSprite(ID, "single");
                
            if (top)
                spriteURL = getSprite(ID, "top");
                
            else if (left && onTop)
                spriteURL = getSprite(ID, "top-left");
                
            else if (right && onTop)
                spriteURL = getSprite(ID, "top-right");
                
            if (single && onTop)
                spriteURL = getSprite(ID, "top-single");
        }
        
        sprite = new GreenfootImage(spriteURL);
        sprite.scale(width, height);
        setImage(sprite);
    }
    
    public boolean collisionAt(int x, int y){
        return getOneObjectAtOffset(x, y, CollisionBlock.class) != null;
    }
    
    public boolean collisionAtType(int x, int y, int checkingID){
        boolean sameType = false;
        Actor object = getOneObjectAtOffset(x, y, CollisionBlock.class);
        if (object != null)
            for (Actor obj : getObjectsAtOffset(x, y, CollisionBlock.class))
                if ( ((CollisionBlock) obj).ID == checkingID){
                    sameType = true;
                    break;
                }
        
        return object != null && sameType == true;
    }
    
        public void checkElevator(){
        if (elevator == true){
            if (elevatorBump == false){
                if (y - h > thisWorld.getHeight())
                    y = 0;
                else if (y + h < 0)
                    y = thisWorld.getHeight();
                
                if (x - w > thisWorld.getWidth())
                    x = 0;
                else if (x + w < 0)
                    x = thisWorld.getWidth();
            }
            else if (elevatorBump == true){
                if (y - h > thisWorld.getHeight() || y + h < 0)
                    elevatorY = -elevatorY;
                    
                if (x - w > thisWorld.getWidth() || x + w < 0)
                    elevatorX = -elevatorX;

                for (var i=0; i < collisionLayers.length; i++){
                    if (collisionAtType(elevatorX, 0, collisionLayers[i]))
                        elevatorX = -elevatorX;

                    if (collisionAtType(0, elevatorY, collisionLayers[i]))
                        elevatorY = -elevatorY;
                }
            }
            setLocation(x+=elevatorX, y+=elevatorY);
        }
    }
    
    public void checkBreakable(){
        if (breakable == true && canFall == true){
            if (timeOfBreak == 0)
                timeOfBreak = thisWorld.time;
            
            if (rotation > 360)
                rotation = 0;
            else rotation++; getImage().rotate(rotation);
            
            if (!broken && thisWorld.time > timeOfBreak+timeBeforeFall )
                setLocation(getX(), y+=4);
            
            if (broken && thisWorld.time > timeOfBroken+timeToRespawn){
                timeOfBroken = 0; timeOfBreak = 0;
                getImage().rotate(0);
                setImage(new GreenfootImage(spriteURL));
                canFall = false;
                broken = false;
                y = startY;
                setLocation(getX(), y);
            }
            
            if (!broken && y - h > thisWorld.getHeight()){
                broken = true;
                timeOfBroken = thisWorld.time;
                y = startY;
            }
        }
    }
}
