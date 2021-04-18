package org.semillita.raycaster.ui.signs;

import org.semillita.raycaster.core.Game.State;
import org.semillita.raycaster.core.Launcher;
import org.semillita.raycaster.ui.ConfirmButton;
import org.semillita.raycaster.ui.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ReturnSign {

private double sizeDenominator;
	
	private Texture returnT;
	
	private ConfirmButton ok;
	
	public ReturnSign(double sizeDenominator) {
		this.sizeDenominator = sizeDenominator;
		
		returnT = new Texture("Return.png");
	
		ok = new ConfirmButton(new Texture("OK.png"), 40);
	}
	
	public void render(SpriteBatch batch, int y, int signWidth, int signHeight) {
		batch.draw(returnT, (int) (Gdx.graphics.getWidth() / 2 - (returnT.getWidth() / sizeDenominator) / 2), (int) (y + signHeight / 3), 
				(int) (returnT.getWidth() / sizeDenominator), (int) (returnT.getHeight() / sizeDenominator));
		
		ok.draw(batch, y, this);
	}
	
	public void mouseMove(State state, int x, int y, UI ui) {
		ok.mouseMove(x, y, ui);
	}
	
	public void mousePress(State state, int x, int y) {
		ok.mousePress(x, y);
	}
	
	public void mouseRelease(State state, int x, int y) {
		ok.mouseRelease(x, y);
	}
	
	public void confirmButtonCallback() {
		Launcher.getGame().returnToMenu();
	}
	
}
