package Game.Zelda.Entities.Statics;

import java.awt.image.BufferedImage;

import Game.PacMan.World.Map;
import Main.Handler;
import Resources.Images;

public class SolidSpawner {

	public static void Spawn(int x, int y, BufferedImage Images, Handler handler, Map map) {
		
		if(handler.getZeldaGameState().caveTimer == 0) {
		SolidStaticEntities oldMan = new SolidStaticEntities(x, y, Resources.Images.oldMan[0] , handler); // Make solid Old Man
		handler.getZeldaGameState().caveObjects.add(oldMan);
		SolidStaticEntities fire = new SolidStaticEntities(x, y, Resources.Images.oldMan[0], handler); // Make solid Fire
		handler.getZeldaGameState().caveObjects.add(fire);
		}
	}

}
