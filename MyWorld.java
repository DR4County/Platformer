import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.List;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    public long time = 0, systemTime = 0;
    public int level = 1, 
               coins = 0, 
               lives = 3,
               maxCoins = 0,
               introLength = 300;
    public boolean lost = false;
    public Actor basePlayer = new Player(0, 0, 32, 32);
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {   
        super(1000, 800, 1, false);
        setBackground(new GreenfootImage("backgrounds\\grasslands.png"));
        prepare();
        setPaintOrder(gui.class);
    }

    public void act(){
        time++;
        systemTime = System.currentTimeMillis();
        displayText("Coins: "+coins+"/"+maxCoins, 50, 30);
        if (time > introLength)
            intro();
    }
    
    public void intro(){
        List objects = getObjects(gui.class);
        for (Object obj : objects){
            Actor objA = ((Actor) obj);
            removeObject(objA);
        }
    }
    
    public int[] pushInt(int[] array, int item){
        int[] tempArray = Arrays.copyOf(array, array.length+1);
        tempArray[tempArray.length-1] = item;
        return tempArray;
    }
    
    public <ARR> ARR[] popArray(ARR[] array){
        return Arrays.copyOf(array, array.length-1);
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare(){
        loadLevel(level);
    }
    
    public void loadLevel(int levelID){
        List objects = getObjects(null);
        
        for (Object obj : objects){
            Actor objA = ((Actor) obj);
            if (objA instanceof Player){
                basePlayer = objA;
                continue;
            }
            removeObject(objA);
        }
        
        switch( levelID ){
            case 1: 
                makeWorldFromString(
                "~0;rrrr~1;r%c~0;"
                + ">^++%b^-p%++bbbb^----bbbb"
                + ">++++++++%%++bbb^---bbb^---bbb^---bbb^---bbb"
                + ">++~2;ebe~0;++++++++++%%%%%++bbb^---bbb^---bbb^---bbb^---bbb^---bbb^---bbb^^^--cb~0;+%%%"
                + ">+++++++++++++++++%%%%++bbb^---bbb^---bbb^---bbb^---bbb^---bbb^---bbb"
                + ">++~2;ebe~0;+++++++++++++++++++++%%%%%%++bbb^---bbb^---bbb^-----+bbbbb^-----bb~1000;b~0;bb^-----b~2;&bbb&~0;b"
                + ">^" + ">^^++cl1-y;ebe~0;"
                );
            break;
            case 2:
                makeWorldFromString("c~0;vrrrbb+p+r%"
                + ">+b^+++++bbbb~1;bbbb----~0;b++bbbb-~1;bbbbbbbbb~0;--------~0;-b~2;&bbbbbbbb&~0;-bbb"
                + "^>+++++++++~1;bbbbbbb----------------~0;+b+++bbbbb+++++bb~1000;b~0;++++++++bb"
                + "^>+b++++bb++~2;&bb&~0;++++bbb++++++++b"
                + "^>+b++~2;&b&~0;+bb++cb+~0;+++bbb+++++++++b"
                + "^>+b++++bb++++++bbbb+++++++++b"
                + "^>+bb+++bb++~2;&bb&-~0;++bbbbbb++++++++b"
                + "^>+b++++bbb+++bbbbbbbbbbbb+++b"
                + "^>+++~2;&b&~0;+++bbb+++++++~1;bbbbbbb~0;----------bbbbb++++bb++b"
                + "^>+cb~0;+++++bbbb++++bbb++~2;&bb&~0;+++++b"
                + "^>+++++++++++++++++++++++++++b"
                + "^>+++++++++++++++++++++++++++b"
                + "^>+++++++++++++++++++++++++++b"
                + "^>+++++++++++++++++++++++++++b"
                + "^>+++++++++++++++++++++++++++b"
                + "^>+++++++++++++++++++++++++++b"
                + "^>+++++++++++++++++++++++++++b"
                );
            break;
            case 3:
                makeWorldFromString(
                ">~2;l1x;ebbbbbbbe"
                + ">^~3;++~2;b~3;+++b+++++b+++++b+++++b+++++b"
                + ">^~3;+bp+++b++b++b++b++b++b++b+++++b"
                + ">^~3;+bb-+~10;+++b++~3;b++~10;b++~3;b++~10;b++~3;b++~10;b++~3;b++~3;b"
                + ">^~3;+++++++++b+++++b+++++b+++++b+~2;b~3;b"
                + ">^~3;+++++++++b+++++b+++++b+++++b++~1000;b"
                + ">^~3;+++++++++b+++++b+++++b+++++b++"
                );
            break;
            case 4: 
                makeWorldFromString("~1;r&"
                +"~0;+r%>+++++~3;&b&"
                + "^>~0;!vlxr;~11;ebe!~0;+++p++++++++++++++++++++++++v"
                + "^^>+~0;&bbbbbbbbbbbbbbbbbbbbbbbbbbb&"
                + "^^^>+++&bbbbbbbbbbbbbbbbbbbbbbbbbbb&"+"%%>!l8-xr;~11;ebe!^^"
                + "^^^>+~0;&bbbbbbbbbbbbbbbbbbbbbbbbbbb&"+"%%>!l8xr;~11;ebe!^^"
                + "^^^>+++~0;&bbbbbbbbbbbbbbbbbbbbbbbbbbb&"+"%%>!l8-xr;~11;ebe!^^"
                + "^^^>+~0;&bbbbbbbbbbbbbbbbbbbbbbbbbbb&"+"%%>!l8xr;~11;ebe!^^"
                + "^^^>+++~0;&bbbbbbbbbbbbbbbbbbbbbbbbbbb&"+"%%>!l8-xr;~11;ebe!^^"
                + "^^^>+~0;&bbbbbbbbbbbbbbbbbbbbbbbbbbb&"+"%%>!l8xr;~11;ebe!^^"
                + "^>+~1000;b~0;"
                );
            break;
            case 5: 
                makeWorldFromString("~8y;~2;r"
                + ">~18y;~11;l1-yr;ere"
                + ">~11y;~0;bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
                + "~12y;>+~1000;b"
                + "~9y;~1x;~0;++p"
                );
            break;
            case 6: 
                makeWorldFromString("~8y;~2;r"
                + ">~18y;~11;e l5-yr;+b++b++b++b++b++b++b++b++b++b++b  e"
                + ">~11y;~0;bbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
                + "^^^>~0;++bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
                + ">^^^;~0;bbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
                + "^^^>~0;++bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
                + ">^^^;~0;bbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
                + "~0x;^;~1000;b"
                + "~9y;~0x;~0;p"
                );
            break;
            default:
                makeWorldFromString("c~0;rrr"
                + ">v+p+b~3;bbbbbbbbbbbbbbb~0;bb"
                + "^>++++++++++++~8;b++++++~3;b~0;bb"
                + "^>+++++++++++++++++++~3;b~0;bb"
                + "^>++++b~3;b+++++++++++++~3;b~0;bb"
                + "^>++++b~3;b+++++++++++++~3;b~0;bb"
                + "^>++++b~3;b+++++++++++++~3;b~0;bb"
                + "^>++++b~3;b+++++~4;b~5;b~6;b~7;b++++~3;b~0;bb"
                + "^>++++b~3;bbbbbbbbbbbbbbb~0;bb"
                + "^>++++bbbbbbbbbbbbbbbb~0;bb"
                );
            break;
        }
    }
    
    public int getTile(int block_ID, String part){
        int savedTile = 0, tileMovement = 1, collisionType = 0, result = 0;
        
        switch (block_ID){
            /* Dirt */ case 0: savedTile = 0; tileMovement = 1; collisionType = 1; break;
            /* Water */ case 1: savedTile = 1; tileMovement = 1; collisionType = 3; break;
            /* Cloud */ case 2: savedTile = 2; tileMovement = 1; collisionType = 2; break;
            /* Wood */ case 3: savedTile = 3; tileMovement = 1; collisionType = 1; break;
            /* Sign 1 */ case 4: savedTile = 4; tileMovement = 1; collisionType = 0; break;
            /* Sign 2 */ case 5: savedTile = 5; tileMovement = 1; collisionType = 0; break;
            /* Sign 3 */ case 6: savedTile = 6; tileMovement = 1; collisionType = 0; break;
            /* Sign 4 */ case 7: savedTile = 7; tileMovement = 1; collisionType = 0; break;
            /* Sign 4 */ case 8: savedTile = 8; tileMovement = 1; collisionType = 1; break;
            /* Crystal Monster */ case 9: savedTile = 9; tileMovement = 1; collisionType = 3; break;
            /* Saw */ case 10: savedTile = 10; tileMovement = 1; collisionType = 3; break;
            /* Fireball */ case 11: savedTile = 11; tileMovement = 1; collisionType = 3; break;
            /* Portal */ case 1000: savedTile = 1000; tileMovement = 1; collisionType = 1000; break;
        }
        switch (part){
            case "ID": result = savedTile; break;
            case "movement": result = tileMovement; break;
            case "collision": result = collisionType; break;
        }
        return result;
    }
    
    public void makeWorldFromString(String world){
        boolean elevator = false, litteralNumber = false, setBlock = false,
                breakable = false, elevatorBump = false, addCollision = false;
                
        int savedNumber = 0,
            savedTile = 0, tileMovement = 1, collisionType = 0,
            x = 0, y = 0, w = 32, h = 32, vx = 0, vy = 0,
            tileID = 0,
            savedNumbers[] = {},
            collisionLayers[] = {},
            index = 0;
        
        for (int i = 0; i < world.toCharArray().length; i++) {
            char symbol = world.toCharArray()[i];
            index++;
            
            if (x > getWidth() / 32+1){
                x = 0;
                y += 1;
            }
            
            if (setBlock == true){
                if (symbol == ';'){
                    setBlock = false;
                    savedTile = getTile(savedNumber, "ID");
                    tileMovement = getTile(savedNumber, "movement");
                    collisionType = getTile(savedNumber, "collision");
                }
                else if (world.toCharArray()[index-1] == 'x'){
                    x = savedNumber;
                    setBlock = false;
                }
                else if (world.toCharArray()[index-1] == 'y'){
                    y = savedNumber;
                    setBlock = false;
                }
                else if (canBeNumber(world.toCharArray()[index-1])){
                    int nnumm = Integer.valueOf(Character.toString(world.toCharArray()[index-1]));
                    
                    if (savedNumber == -123454321)
                        savedNumber = nnumm;
                    
                    else savedNumber = Integer.valueOf(String.valueOf(savedNumber) + Character.toString(world.toCharArray()[index-1]));
                    
                    savedNumbers = pushInt(savedNumbers, nnumm);
                }
            }
            
            if (litteralNumber == true){
                if (symbol == ';')
                    litteralNumber = false;
                    
                else if (world.toCharArray()[index-1] == 'r'){
                    savedNumber = -123454321;
                    savedNumbers = new int[]{};
                }
                else if (world.toCharArray()[index-1] == 'x')
                    vx = savedNumber;
                
                else if (world.toCharArray()[index-1] == 'y')
                    vy = savedNumber;
                
                else if (world.toCharArray()[index-1] == '-')
                    savedNumber = -savedNumber;
                
                else if (world.toCharArray()[index-1] == 'c'){
                    collisionLayers = pushInt(collisionLayers, savedNumber);
                    savedNumber = -123454321;
                }
                else if (canBeNumber(world.toCharArray()[index-1])){
                    int nnumm = Integer.valueOf(Character.toString(world.toCharArray()[index-1]));
                    
                    if (savedNumber == -123454321)
                        savedNumber = nnumm;
                    else savedNumber = Integer.valueOf(String.valueOf(savedNumber) + Character.toString(world.toCharArray()[index-1]));
                    
                    savedNumbers = pushInt(savedNumbers, nnumm);
                }
            }
            
            if (litteralNumber == false && setBlock == false){
                switch (symbol){
                    case '^': y += 1; break;
                    case '%': y-=1; break;
                    case '+': x += 1; break;
                    case '-': x -= 1; break;
                    case '>': x = 0; break;
                    case 'b':
                        var obj = new CollisionBlock(32, 32, savedTile, collisionType);
                        
                        if (elevator){
                            obj.elevator = true;
                            obj.elevatorX = vx;
                            obj.elevatorY = vy;
                            if (elevatorBump)
                                obj.elevatorBump = true;
                        }
                        
                        if (breakable)
                            obj.breakable = true;
                        
                        if (collisionLayers.length > 0){
                            obj.collisionLayers = collisionLayers;
                        }
                        
                        addObject(obj, x*w+16, (getHeight()-16)-32*y);
                        x += tileMovement;
                    break;
                    case 'd':
                        for (Actor object : getObjectsAt(x, y, CollisionBlock.class)){
                            removeObject(((CollisionBlock) object));
                        }
                    break;
                    case 'p':
                        ((Player) basePlayer).spawnX = x*w+16;
                        ((Player) basePlayer).spawnY = (getHeight()-16)-32*(y+1);
                        ((Player) basePlayer).resetPos();
                        addObject(basePlayer,  x*w+16, (getHeight()-16)-32*(y+1));
                        x++;
                    break;
                    
                    /* Elevator */
                    case 'e': elevator = !elevator; break;
                    
                    /* Elevator Bump*/
                    case 'o': elevatorBump = !elevatorBump; break;
                    
                    /* Allows objects to be breakable */
                    case '&': breakable = !breakable; break;
                    
                    /* Take Symbols as litteral */
                    case 'l': litteralNumber = !litteralNumber; savedNumber = -123454321; break;
                    
                    /* Change Tile ID and values */
                    case '~': setBlock = !setBlock; savedNumber = -123454321; break;
                    
                    /* Coin */
                    case 'c': savedTile = 999; tileMovement = 1; collisionType = 999; break;
                    
                    /* Reset */
                    case '!': 
                        elevator = false; litteralNumber = false; breakable = false; elevatorBump = false;
                        addCollision = false; savedNumbers = new int[]{}; collisionLayers = new int[]{};
                        vx = 0; vy = 0;
                    break;
                    
                    /* Rows */
                    case 'r': 
                        for (int xt = 0; xt < (getWidth() / 32)-x; xt++) {
                            obj = new CollisionBlock(32, 32, savedTile, collisionType);
                            if (elevator){
                                obj.elevator = true;
                                obj.elevatorX = vx;
                                obj.elevatorY = vy;
                                if (elevatorBump)
                                    obj.elevatorBump = true;
                            }
                            
                            if (breakable)
                                obj.breakable = true;
                                
                            if (collisionLayers.length > 0)
                                obj.collisionLayers = collisionLayers;
                            
                            addObject(obj, (x*32)+xt*32+16, (getHeight()-16)-32 * y);
                        }
                        y++;
                    break;
                    
                    /* Columns */
                    case 'v':
                        for (int yt = 0; yt < getWidth() / 32; yt++) {
                            obj = new CollisionBlock(32, 32, savedTile, collisionType);
                            if (elevator){
                                obj.elevator = true;
                                obj.elevatorX = vx;
                                obj.elevatorY = vy;
                                if (elevatorBump)
                                    obj.elevatorBump = true;
                            }
                            
                            if (breakable)
                                obj.breakable = true;
                            
                            if (collisionLayers.length > 0)
                                obj.collisionLayers = collisionLayers;
                            
                            addObject(obj, x*32+16, (getHeight()-16)-32 * yt);
                        };
                        x++;
                    break;
                }
            }
        }
    }
    
    private void makeWorld(int worldID){
        switch (worldID){
            case 1:
                int LLI = 0;
                
                for (int y = 0; y < (getHeight() / 3) / 32+1; y++){
                    for (int x = 0; x < getWidth() / 32+1; x++)
                        addObject(new CollisionBlock(32, 32, 0, 1), x*32+16, (getHeight()-16)-32 * y);
                    
                    LLI = y;
                }
                
                for (int x = 0; x < 5; x++)
                    addObject(new CollisionBlock(32, 32, 0, 1), (x+3)*32+16, (getHeight()-16)-32 * (LLI+1));
                
                
                for (int x = 0; x < 5; x++)
                    addObject(new CollisionBlock(32, 32, 0, 1), (x+3)*32+16, (getHeight()-16)-32 * (LLI+2));
                
                
                for (int y = 0; y < 3+1; y++)
                    for (int x = 0; x < 5 ; x++)
                        addObject(new CollisionBlock(32, 32, 0, 1), (x+11)*32+16, (getHeight()-16) - 32 * (y+LLI+1));
                
                for (int y = 0; y < 4+1; y++)
                    for (int x = 0; x < 5 ; x++)
                        addObject(new CollisionBlock(32, 32, 0, 1), (x+17)*32+16, (getHeight()-16) - 32 * (y+LLI+1));
            
            break;
        }
    }
    
    public boolean canBeNumber( char inQuestion ){
        boolean isNumber = false;
        switch (inQuestion){
            case '0':
             case '1':
              case '2':
               case '3':
                case '4':
                 case '5':
                  case '6':
                   case '7':
                    case '8':
                     case '9':
                      isNumber = true;
            break;
        }
        return isNumber;
    }
    
    public void displayText(String text, int x, int y){
        showText(text , x, y);
    }
}
