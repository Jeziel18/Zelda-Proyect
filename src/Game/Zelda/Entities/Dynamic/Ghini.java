package Game.Zelda.Entities.Dynamic;

import Game.GameStates.Zelda.ZeldaGameState;
import Game.Zelda.Entities.Statics.DungeonDoor;
import Game.Zelda.Entities.Statics.SectionDoor;
import Game.Zelda.Entities.Statics.SolidStaticEntities;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static Game.GameStates.Zelda.ZeldaGameState.worldScale;
import static Game.Zelda.Entities.Dynamic.Direction.DOWN;
import static Game.Zelda.Entities.Dynamic.Direction.UP;

/**
 * Created by AlexVR on 3/15/2020
 */
public class Ghini extends BaseMovingEntity {


    private final int animSpeed = 60; 
    int newMapX=0,newMapY=0,xExtraCounter=0,yExtraCounter=0,deathCooldown =0;
    public boolean movingMap = false;
    Direction movingTo;
    boolean R = false;
    boolean shift = false;
    int hit = 20, randMove = 0, inAction = 0, direct = 0; 
    boolean linkAttack = false;
    boolean damage = false;
    Random move = new Random();


    public Ghini(int x, int y, BufferedImage[] sprite, Handler handler) {
        super(x, y, sprite, handler);
        speed = 1;
        BufferedImage[] animList = new BufferedImage[2];
        animList[0] = sprite[0];
        animList[1] = sprite[1];
        
        animation = new Animation(animSpeed,animList);
    }

    @Override
    public void tick() {
    	
    	if (handler.getZeldaGameState().ghiniDeath) {
    		y = -10;
    		speed = 0;
             }
    		  
          //For Movement
    	if (randMove <= 90) {
    	randMove = move.nextInt(121);
    	direct = move.nextInt(4);
    	inAction = randMove + 60;
    	}
    	BufferedImage[] animList = new BufferedImage[2];
    	if (inAction > 0) {
    		speed = 1;
    		inAction--;
    	switch(direct) {
    		case 0:                
                    animList[0] = sprites[3];
                    animList[1] = sprites[3];
                    animation = new Animation(animSpeed, animList);
                    direction = UP;
                    sprite = sprites[3];
                animation.tick();
                move(direction);
                break;
    		case 1:
                    animList[0] = sprites[2];
                    animList[1] = sprites[2];
                    animation = new Animation(animSpeed, animList);
                    direction = DOWN;
                    sprite = sprites[2];
                animation.tick();
                move(direction);
                break;
    		case 2:
    				animList[0] = sprites[2];
    				animList[1] = sprites[2];
                    animation = new Animation(animSpeed, animList);
                    direction = Direction.LEFT;
                    sprite = sprites[2];
                animation.tick();
                move(direction);
                break;
    		case 3:
    				animList[0] = sprites[2];
    				animList[1] = sprites[2];
                    animation = new Animation(animSpeed, animList);
                    direction = Direction.RIGHT;
                    sprite = (sprites[2]);
                animation.tick();
                move(direction);
            }
    	}else {
                moving = false; 
                randMove-=2;
                }
                
             } 


    @Override
    public void render(Graphics g) {
        if (moving) {
            g.drawImage(animation.getCurrentFrame(),x , y, width , height  , null);

        } else {
            	g.drawImage(sprite, x , y, width , height , null);	
            
        }
       
    }
        
    

    @Override
    public void move(Direction direction) {
        moving = true;
        //check for collisions}
            for (SolidStaticEntities objects : handler.getZeldaGameState().objects.get(handler.getZeldaGameState().mapX).get(handler.getZeldaGameState().mapY)) {
                if ((objects instanceof SectionDoor) && objects.bounds.intersects(bounds) && direction == ((SectionDoor) objects).direction) {
                    if (!(objects instanceof DungeonDoor)) {
                       
                    }
                    else {
                       
                    }
                }
                else if (!(objects instanceof SectionDoor) && objects.bounds.intersects(interactBounds)) {
                    speed = -10;
                }
            }
        
        switch (direction) {
            case RIGHT:
                x += speed;
                break;
            case LEFT:
                x -= speed;

                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;

                break;
        }
        bounds.x = x;
        bounds.y = y;
        changeIntersectingBounds();
        
    }
}
