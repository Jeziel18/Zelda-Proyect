package Game.Zelda.Entities.Dynamic;

import Game.GameStates.Zelda.ZeldaGameState;
import Game.Zelda.Entities.Statics.DungeonDoor;
import Game.Zelda.Entities.Statics.MovingTile;
import Game.Zelda.Entities.Statics.SectionDoor;
import Game.Zelda.Entities.Statics.SolidStaticEntities;
import Game.Zelda.Entities.Statics.Sword;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static Game.GameStates.Zelda.ZeldaGameState.worldScale;
import static Game.Zelda.Entities.Dynamic.Direction.DOWN;
import static Game.Zelda.Entities.Dynamic.Direction.UP;

/**
 * Created by AlexVR on 3/15/2020
 */
public class Link extends BaseMovingEntity {


    private final int animSpeed = 120; 
    int newMapX=0,newMapY=0,xExtraCounter=0,yExtraCounter=0,deathCooldown =0;
    public boolean movingMap = false, linkHurt = false;
    Direction movingTo;
    boolean R = false;
    boolean shift = false;
    int hit = 20; 
    boolean linkAttack = false;
    boolean linkHit = false, animacion = false, attacking = false;
    boolean bienveR = false, bienveL = false, bienveU = false, bienveD = false;
    int powerUp = 2;
    boolean keyLock = false, moving = false;


    public Link(int x, int y, BufferedImage[] sprite, Handler handler) {
        super(x, y, sprite, handler);
        speed = 4;
        BufferedImage[] animList = new BufferedImage[2];
        animList[0] = sprite[4];
        animList[1] = sprite[5];
        
        animation = new Animation(animSpeed,animList);
    }

    @Override
    public void tick() {
    	for (Zora enemies: handler.getZeldaGameState().zoraList) {
        	if (enemies instanceof Zora && !handler.getZeldaGameState().inCave) {
             	if(enemies.bounds.intersects(bounds) || bounds.contains(enemies.bounds)) {
        		linkHurt = true;
        		
             } }
        }
    	
    	if(powerUp >= 0 && powerUp <= 1) {
    		handler.getMusicHandler().playEffect("zeldaitem.wav"); // Play Effect when pick up sword
    	}
    	
    	// Take 1 health from Link
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_Z) && handler.getZeldaGameState().health > 0) {
    		handler.getZeldaGameState().health--;
    	}
    	
    
    	if (linkHurt && !handler.getZeldaGameState().attackLink) {
    		 if (deathCooldown<=0){ 
                 deathCooldown=61;
                 linkHurt=false;
             }else{
                 deathCooldown--;
             }
    		 if (deathCooldown == 60) {
    			 handler.getZeldaGameState().health--;
    			 handler.getMusicHandler().playEffect("laser.wav");
    		 }
    	}
        if (movingMap){
            switch (movingTo) {
                case RIGHT:
                    handler.getZeldaGameState().cameraOffsetX+=4;
                    newMapX++;
                    if (xExtraCounter>0){
                        x-=3;
                        xExtraCounter-=(1/2);
                        animation.tick();

                    }else{ 
                        x--;
                    }
                    break;
                case LEFT:
                    handler.getZeldaGameState().cameraOffsetX-=4;
                    newMapX--;
                    if (xExtraCounter>0){
                        x+=3;
                        xExtraCounter-=(1/2);
                        animation.tick();

                    }else{
                        x++;
                    }
                    break;
                case UP:
                    handler.getZeldaGameState().cameraOffsetY-=4;
                    newMapY++;
                    if (yExtraCounter>0){
                        y+=3;
                        yExtraCounter-=(1/2);
                        animation.tick();

                    }else{
                        y++;
                    }
                    break;
                case DOWN:
                    handler.getZeldaGameState().cameraOffsetY+=4;
                    newMapY--;
                    if (yExtraCounter>0){
                        y-=3;
                        yExtraCounter-=(1/2);
                        animation.tick();
                    }else{
                        y--;
                    }
                    break;
            }
            bounds = new Rectangle(x,y,width,height);
            changeIntersectingBounds();
            if (newMapX == 0 && newMapY == 0){
                movingMap = false;
                movingTo = null;
                newMapX = 0;
                newMapY = 0;
            }
        }else {
        	
        	 if(animacion) {
             	animation.tick();
             	if(animation.end) {
             		bienveR = true;
             		bienveL = true;
             		bienveU = true;
             		bienveD = true;
             		animacion = false;
             		attacking = false;
             		handler.getZeldaGameState().attackLink = false;
             		speed = 4;
             	}
             }
             if(handler.getZeldaGameState().gotSword && handler.getZeldaGameState().swordTimer >= 0 && handler.getZeldaGameState().swordTimer <= 119) {
                 sprite = Images.oldMan[4];  // Link pick up sword
                 powerUp--; // timer for music to sound 1 time
                 keyLock = true;  //Locking keys
             }else{
            	 keyLock = false;
             }
             
             if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER) && handler.getZeldaGameState().gotSword) {
            	speed = 0; // While attacking Link can't move
            	handler.getZeldaGameState().attackLink = true;
            	
             	int animationSpeed = 120; //Link Attack animation Speed
             	  	
                      if (direction.equals(UP)) {
                          BufferedImage[] animList = new BufferedImage[3];
                          animList[0] = Images.zeldaLinkAttacks[9];
                          animList[1] = Images.zeldaLinkAttacks[10];
                          animList[2] = Images.zeldaLinkAttacks[11];
                          animation = new Animation(animationSpeed, animList);
                          direction = UP;
                          sprite = sprites[4];
                          
                      }
                     
                      else if (direction.equals(DOWN)) {
                          BufferedImage[] animList = new BufferedImage[3];
                          animList[0] = Images.zeldaLinkAttacks[0];
                          animList[1] = Images.zeldaLinkAttacks[1];
                          animList[2] = Images.zeldaLinkAttacks[2];
                          animation = new Animation(animationSpeed, animList);
                          direction = DOWN;
                          sprite = sprites[0];
                          
                      }
                      
                      else if (direction == Direction.LEFT) {
                          BufferedImage[] animList = new BufferedImage[3];
                          animList[0] = Images.zeldaLinkAttacks[6];
                          animList[1] = Images.zeldaLinkAttacks[7];
                          animList[2] = Images.zeldaLinkAttacks[8];
                          animation = new Animation(animationSpeed, animList);
                          direction = Direction.LEFT;
                          sprite = Images.flipHorizontal(sprites[3]);
                          
                      }
                      
                      else if (direction == Direction.RIGHT) {
                          BufferedImage[] animList = new BufferedImage[3];
                          animList[0] = Images.zeldaLinkAttacks[3];
                          animList[1] = Images.zeldaLinkAttacks[4];
                          animList[2] = Images.zeldaLinkAttacks[5];
                          animation = new Animation(animationSpeed, animList);
                          direction = Direction.RIGHT;
                          sprite = (sprites[3]);
                          
                      }
                      animacion = true; //Changing all booleans
                      attacking = true; // Making Link attack 
             	 
             }
            
        	if(!keyLock) {
            if (handler.getKeyManager().up) {
                if (direction != UP || bienveU) {
                	bienveU = false;
                    BufferedImage[] animList = new BufferedImage[2];
                    animList[0] = sprites[4];
                    animList[1] = sprites[5];
                    animation = new Animation(animSpeed, animList);
                    direction = UP;
                    sprite = sprites[4];
                }
                animation.tick();
                move(direction);

            } else if (handler.getKeyManager().down) {
                if (direction != DOWN || bienveD) {
                	bienveD = false;
                    BufferedImage[] animList = new BufferedImage[2];
                    animList[0] = sprites[0];
                    animList[1] = sprites[1];
                    animation = new Animation(animSpeed, animList);
                    direction = DOWN;
                    sprite = sprites[0];
                }
                animation.tick();
                move(direction);
            } else if (handler.getKeyManager().left) {
                if (direction != Direction.LEFT || bienveL) {
                	bienveL = false;
                    BufferedImage[] animList = new BufferedImage[2];
                    animList[0] = Images.flipHorizontal(sprites[2]);
                    animList[1] = Images.flipHorizontal(sprites[3]);
                    animation = new Animation(animSpeed, animList);
                    direction = Direction.LEFT;
                    sprite = Images.flipHorizontal(sprites[3]);
                }
                animation.tick();
                move(direction);
            } else if (handler.getKeyManager().right) {
                if (direction != Direction.RIGHT || bienveR) {
                	bienveR = false;
                    BufferedImage[] animList = new BufferedImage[2];
                    animList[0] = (sprites[2]);
                    animList[1] = (sprites[3]);
                    animation = new Animation(animSpeed, animList);
                    direction = Direction.RIGHT;
                    sprite = (sprites[3]);
                }
                animation.tick();
                move(direction);
            }
            } else {
                moving = false;
                
            }
        }
        if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_H)) && handler.getZeldaGameState().health < 4) {
			handler.getZeldaGameState().health++;
        }
    }

    @Override
    public void render(Graphics g) {
        if (moving) {
            g.drawImage(animation.getCurrentFrame(),x , y, width , height  , null);

        } else {
            if (movingMap){
            	
                g.drawImage(animation.getCurrentFrame(),x , y, width, height  , null);
            
            }
            if(attacking) {
                g.drawImage(animation.getCurrentFrame(),x , y, width, height  , null);

            }
            else {
            	g.drawImage(sprite, x , y, width , height , null);	
            }
            
        }
        
        
       
        
    }
        
    

    @Override
    public void move(Direction direction) {
        moving = true;
        changeIntersectingBounds();
        //check for collisions
        if (ZeldaGameState.inCave){
            for (SolidStaticEntities objects : handler.getZeldaGameState().caveObjects) {
                if ((objects instanceof DungeonDoor) && objects.bounds.intersects(bounds) && direction == ((DungeonDoor) objects).direction) {
                    if (((DungeonDoor) objects).name.equals("caveStartLeave")) {
                        ZeldaGameState.inCave = false;
                        x = ((DungeonDoor) objects).nLX;
                        y = ((DungeonDoor) objects).nLY;
                        direction = DOWN;
                    }
                } else if (!(objects instanceof DungeonDoor) && objects.bounds.intersects(interactBounds)) {
                    //dont move
                    return;
                }
            }
        }
        else {
            for (SolidStaticEntities objects : handler.getZeldaGameState().objects.get(handler.getZeldaGameState().mapX).get(handler.getZeldaGameState().mapY)) {
                if ((objects instanceof SectionDoor) && objects.bounds.intersects(bounds) && direction == ((SectionDoor) objects).direction) {
                    if (!(objects instanceof DungeonDoor)) {
                        movingMap = true;
                        movingTo = ((SectionDoor) objects).direction;
                        switch (((SectionDoor) objects).direction) {
                            case RIGHT:
                                newMapX = -(((handler.getZeldaGameState().mapWidth) + 1) * worldScale)/4;
                                newMapY = 0;
                                handler.getZeldaGameState().mapX++;
                                xExtraCounter = 8 * worldScale + (2 * worldScale);
                                break;
                            case LEFT:
                                newMapX = (((handler.getZeldaGameState().mapWidth) + 1) * worldScale)/4;
                                newMapY = 0;
                                handler.getZeldaGameState().mapX--;
                                xExtraCounter = 8 * worldScale + (2 * worldScale);
                                break;
                            case UP:
                                newMapX = 0;
                                newMapY = -(((handler.getZeldaGameState().mapHeight) + 1) * worldScale)/4;
                                handler.getZeldaGameState().mapY--;
                                yExtraCounter = 8 * worldScale + (2 * worldScale);
                                break;
                            case DOWN:
                                newMapX = 0;
                                newMapY = (((handler.getZeldaGameState().mapHeight) + 1) * worldScale)/4;
                                handler.getZeldaGameState().mapY++;
                                yExtraCounter = 8 * worldScale + (2 * worldScale);
                                break;
                        }
                        return;
                    }  
                    else {
                        if (((DungeonDoor) objects).name.equals("caveStartEnter")) {
                            ZeldaGameState.inCave = true;
                            x = ((DungeonDoor) objects).nLX;
                            y = ((DungeonDoor) objects).nLY;
                            direction = UP;
                        }
                    }
                } else if (objects instanceof MovingTile) {
                	
                	if (objects.bounds.intersects(interactBounds)){
                		speed = 5;
                		keyLock = true;
                		moving = true;
                		if(moving) {
                	if (((MovingTile) objects).name.equals("Up")){
                		direction = Direction.UP;
                		y -= speed;
                	} else if (((MovingTile) objects).name.equals("Down")) {
                		direction = Direction.DOWN;
                		y += speed;
                	} else if (((MovingTile) objects).name.equals("Left")) {
                		x -= speed;
                	} else if (((MovingTile) objects).name.equals("Right")) {
               			x += speed;
           	} 
                	} else {
                		speed = 4;
                		keyLock = false;
                		moving = false;
                	}
                	}
                }
                else if (!(objects instanceof SectionDoor) && !(objects instanceof MovingTile) && objects.bounds.intersects(interactBounds)) {
                    //dont move
                    return;
                }
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
