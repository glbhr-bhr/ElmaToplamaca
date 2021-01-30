package com.yeni.elmatoplamaca;
// kodlar java da yazılmış Android oyunu olarak ödev teslimi yapılmış. !!!!

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class ElmaToplamaca extends ApplicationAdapter implements InputProcessor {
	private OrthographicCamera kamera;
	private SpriteBatch batch;
	private Texture sepetresim,sagbutton,solbutton;
	private Texture resimarkaplan;
	private Rectangle rctSepet,rctsolbutton,rctsagbutton;
	private Texture APPLE;
	private Array<Rectangle> elmalar;
	private long DüşmeZamani;
	private Sound seselma;
	private boolean touchActive=false;
	private Music music;







	@Override
	public void create() {
		kamera =new  OrthographicCamera();
		kamera.setToOrtho(false,800,680);
		sepetresim=new Texture(Gdx.files.internal("SEPETRESİM2.png"));
		APPLE=new Texture(Gdx.files.internal("APPLE.png"));
		seselma=Gdx.audio.newSound(Gdx.files.internal("seselma.mp3"));
		resimarkaplan=new Texture("resimarkaplan.png");
		elmalar =new Array<Rectangle>();

		music = Gdx.audio.newMusic(Gdx.files.internal("bahcemusic.mp3"));
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();

		solbutton =new Texture(Gdx.files.internal("solbutton.png"));
		//telefon ekranında pixsel bozukluğu için;
		solbutton.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
		rctsolbutton=new Rectangle(0,20,140,140);

		sagbutton= new Texture(Gdx.files.internal("sagbutton.png"));
		sagbutton.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
		rctsagbutton=new Rectangle(800-140,20,140,140);

		batch=new SpriteBatch();
		Gdx.input.setInputProcessor(this);

		rctSepet=new Rectangle();
		rctSepet .width =130;
		rctSepet.height=130;
		rctSepet.x=800/2- rctSepet.width/2;
		rctSepet.y=30;
		elmaUret();

	}
private void elmaUret(){
		Rectangle rctElma=new Rectangle();
		rctElma .width=60;
		rctElma.height=60;
		rctElma.x= MathUtils .random(0,800-67);
		rctElma.y=480;
		elmalar.add(rctElma);
		DüşmeZamani= TimeUtils.nanoTime();


}






	@Override
	public void render() {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		kamera.update();

		batch.setProjectionMatrix(kamera.combined);
		batch.begin();
		batch.draw(resimarkaplan ,0,0);
		batch.draw(sepetresim,rctSepet.x,rctSepet.y);
		for (Rectangle rctElma:elmalar){
			batch.draw(APPLE,rctElma.x,rctElma.y);

		}

		batch.draw(solbutton,rctsolbutton.x,rctsolbutton.y);
		batch.draw(sagbutton,rctsagbutton.x,rctsagbutton.y);

		batch.end();

		if (touchActive){
			Vector3 DokunmaPozisyon=new Vector3();
			DokunmaPozisyon.set(Gdx.input.getX(),Gdx.input.getY(),0);
			kamera.unproject(DokunmaPozisyon);

			if (rctsolbutton.contains(DokunmaPozisyon.x,DokunmaPozisyon.y)){
				rctSepet.x-= 200f*Gdx.graphics.getDeltaTime();

			}
			if (rctsagbutton.contains(DokunmaPozisyon.x,DokunmaPozisyon.y)){
				rctSepet.x+= 200f*Gdx.graphics.getDeltaTime();

			}


		}

if (TimeUtils.nanoTime()-DüşmeZamani>1000000000){
	elmaUret();
}

		Iterator <Rectangle> elma=elmalar.iterator();
while (elma.hasNext()){
	Rectangle rctElma= elma .next();
	rctElma.y  -= 200 * Gdx.graphics.getDeltaTime();
	if (rctElma.y+60<0){
		elma.remove();
	}





	if (rctElma.overlaps(rctSepet)){
		seselma.play();
		elma.remove();

	}






}

	}


	@Override
	public void dispose() {
		APPLE.dispose();
		sepetresim.dispose();
		seselma.dispose();
		batch.dispose();
		solbutton.dispose();
		sagbutton.dispose();

	}




	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchActive=true;
		return false;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchActive=false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
