package Game.Zelda.Entities.Statics;

import Game.Galaga.Entities.BaseEntity;
import Game.GameStates.Zelda.ZeldaGameState;
import Game.Zelda.Entities.MMBaseEntity;
import Game.Zelda.Entities.Dynamic.Direction;
import Main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by AlexVR on 3/15/2020
 */
public class MMMovingTile extends MMBaseEntity {
    public String name;
    public MMMovingTile(int x, int y, String name, BufferedImage sprite, Handler handler) {
    	super(x, y, sprite,handler);
        bounds = new Rectangle(x ,y ,width,height);
        this.name = name;
    }

    @Override
    public void tick() {
        super.tick();

    }
     public String getDirection() {
    	 return name;
     }
     
     @Override
     public void render(Graphics g) {
         g.drawImage(sprite,x ,y,width,height,null);
     }

}
