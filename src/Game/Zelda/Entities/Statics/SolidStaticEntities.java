package Game.Zelda.Entities.Statics;

import Game.Zelda.Entities.BaseEntity;
import Main.Handler;

import java.awt.image.BufferedImage;

/**
 * Created by AlexVR on 3/14/2020
 */
public class SolidStaticEntities extends BaseEntity {

    public SolidStaticEntities(int x, int y, BufferedImage sprite, Handler handler) {
        super(x, y, sprite,handler);
    }
}
