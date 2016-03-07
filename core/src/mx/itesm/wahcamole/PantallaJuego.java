package mx.itesm.wahcamole;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by rmroman on 04/02/16.
 */
public class PantallaJuego implements Screen
{
    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    // Fondo
    private Texture texturaFondo;
    private Sprite spriteFondo;

    // GIF RATITA
    private Texture texturaRatita;
    private Sprite spriteRatita;

    // Dibujar
    private SpriteBatch batch;



    // NIVEL 1
    private int marcador;
    private Texto texto;

    //Boton de regreso a menu

    private Texture texturaBtnHome;
    private Sprite spriteBtnHome;

    //Sonidos
    private Sound efectoGolpe; //Efecto
    private Music musicaFondo;  //Musica de fondo


    public PantallaJuego(Principal principal) {
        this.principal = principal;
        marcador = 0;
        texto = new Texto();
    }


    @Override
    public void show() {
        // Se ejecuta cuando se muestra la pantalla
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO/2, Principal.ALTO_MUNDO/2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO,camara);

        batch = new SpriteBatch();

        cargarTexturasSprites();

        efectoGolpe= Gdx.audio.newSound(Gdx.files.internal("Punch Sound Effect.mp3"));
        musicaFondo= Gdx.audio.newMusic(Gdx.files.internal("musicaFondo.mp3"));
        musicaFondo.setLooping(true);   ///Infinito
        musicaFondo.play();
    }



    private void cargarTexturasSprites() {
        // Fondo
        texturaFondo = new Texture(Gdx.files.internal("fondoNivel1.3.jpg"));
        spriteFondo = new Sprite(texturaFondo);

        // Fondo
        texturaRatita = new Texture(Gdx.files.internal("ratita gif.gif"));
        spriteRatita = new Sprite(texturaRatita);

        //Boton de regreso
        texturaBtnHome = new Texture(Gdx.files.internal("btnHome.jpg"));
        spriteBtnHome= new Sprite(texturaBtnHome);
        spriteBtnHome.setPosition(Principal.ANCHO_MUNDO / 14 - spriteBtnHome.getWidth() / 2,
                Principal.ALTO_MUNDO / 12 );

    }

    @Override
    public void render(float delta) {
        // Leer
        leerEntrada();


        // Borrar la pantalla
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Proyectamos la cámara sobre batch
        batch.setProjectionMatrix(camara.combined);
        // Dibujamos

        batch.begin();

        spriteFondo.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    private void leerEntrada() {
        if (Gdx.input.justTouched()==true) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas); // Transforma las coord
            float touchX = coordenadas.x;
            float touchY = coordenadas.y;

            if (touchX >= spriteBtnHome.getX() &&
                    touchX <= spriteBtnHome.getX() + spriteBtnHome.getWidth()
                    && touchY >= spriteBtnHome.getY()
                    && touchY <= spriteBtnHome.getY() + spriteBtnHome.getHeight()) {
                // Lanzar la pantalla de menu
                principal.setScreen(new PantallaMenu(principal));
                musicaFondo.stop();

            }
        }
    }

    @Override
    public void dispose() {
        // Cuando la PantallaMenu sale de memoria.
        // LIBERAR los recursos
        //para apple liberar memoria es indispensable!!!
        texturaFondo.dispose(); // regresamos la memoria
        texturaBtnHome.dispose();


    }
}
