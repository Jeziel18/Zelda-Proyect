package Game.Zelda.Entities.Statics;

import Game.Galaga.Entities.BaseEntity;
import Game.GameStates.Zelda.ZeldaGameState;
import Game.Zelda.Entities.Dynamic.Direction;
import Main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by AlexVR on 3/15/2020
 */
public class MovingTile extends SolidStaticEntities {
    public String name;
    public MovingTile(int x, int y, String name, BufferedImage sprite, Handler handler) {
    	super(x, y, sprite,handler);
        bounds = new Rectangle((x * (ZeldaGameState.stageWidth/16)) + ZeldaGameState.xOffset,(y * (ZeldaGameState.stageHeight/11)) + ZeldaGameState.yOffset,width,height);
        this.name = name;
    }

    @Override
    public void tick() {
        super.tick();

    }
     public String getDirection() {
    	 return name;
     }

}
