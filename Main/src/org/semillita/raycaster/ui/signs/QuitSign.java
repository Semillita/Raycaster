package org.semillita.raycaster.ui.signs;

import org.semillita.raycaster.core.Launcher;
import org.semillita.raycaster.core.Game.State;
import org.semillita.raycaster.ui.ColorButton;
import org.semillita.raycaster.ui.ConfirmButton;
import org.semillita.raycaster.ui.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class QuitSign {

private double sizeDenominator;
	
	private Texture quit;
	
	private ConfirmButton ok;
	
	public QuitSign(double sizeDenominator) {
		this.sizeDenominator = sizeDenominator;
		
		quit = new Texture("Resources/QUIT.png");
	
		ok = new ConfirmButton(new Texture("OK.png"), 40);
	}
	
	public void render(SpriteBatch batch, int y, int signWidth, int signHeight) {
		batch.draw(quit, (int) (Gdx.graphics.getWidth() / 2 - (quit.getWidth() / sizeDenominator) / 2), (int) (y + signHeight / 3), 
				(int) (quit.getWidth() / sizeDenominator), (int) (quit.getHeight() / sizeDenominator));
		
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
		Gdx.app.exit();
	}
	
}
