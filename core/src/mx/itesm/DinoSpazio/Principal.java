package mx.itesm.DinoSpazio;

import com.badlogic.gdx.Game;

public class Principal extends Game
{
	public static final float ANCHO_MUNDO = 1280;
	public static final float ALTO_MUNDO = 720;

	
	@Override
	public void create () {
		setScreen(new PantallaMenu(this));
	}

}
