package net.fischboeck.caesar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import net.fischboeck.caesar.io.MouseHandler;
import net.fischboeck.caesar.model.SceneNode;
import net.fischboeck.caesar.renderer.WorldRenderer;
import net.fischboeck.caesar.systems.MapCursorSystem;
import net.fischboeck.caesar.systems.StreetSystem;

import static net.fischboeck.caesar.util.Constants.CAMERA_TRANSLATION_SPEED;

public class OpenCaesar extends ApplicationAdapter {

	private IsometricTiledMapRenderer renderer;

	private MapCursorSystem mapCursorSystem;
	private StreetSystem streetSystem;

	private InputMultiplexer inputMultiplexer;
	private OrthographicCamera camera;

	private MouseHandler mapCursorHandler;

	private WorldRenderer worldRenderer;
	private ShapeRenderer shapeRenderer;

	private SceneNode sceneGraph;

	private AssetManager assetManager;

	private SpriteBatch spriteBatch;
	
	@Override
	public void create () {
		TiledMap map = new TmxMapLoader().load(Gdx.files.internal("assets/test-map.tmx").path());
		renderer = new IsometricTiledMapRenderer(map);

		assetManager = new AssetManager();
		assetManager.load("assets/test-tileset.png", Texture.class);
		assetManager.load("assets/badlogic.jpg", Texture.class);
		assetManager.load("assets/region-test.png", Texture.class);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = .4f;

		mapCursorHandler = new MouseHandler(camera);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.getProcessors().add(mapCursorHandler);
		Gdx.input.setInputProcessor(inputMultiplexer);

		this.shapeRenderer = new ShapeRenderer();
		this.spriteBatch = new SpriteBatch();
		sceneGraph = new SceneNode("ROOT");
		this.worldRenderer = new WorldRenderer(renderer, spriteBatch, shapeRenderer, sceneGraph);
		this.mapCursorSystem = new MapCursorSystem(sceneGraph);
		this.streetSystem = new StreetSystem(sceneGraph, assetManager);

		// probably not the best way to do it
		while (!assetManager.update()) {
			System.out.println(assetManager.getProgress());
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

		// update camera
		if ((Gdx.input.isKeyPressed(Input.Keys.D))) {
			camera.translate(CAMERA_TRANSLATION_SPEED, 0);
		}
		if ((Gdx.input.isKeyPressed(Input.Keys.A))) {
			camera.translate(-CAMERA_TRANSLATION_SPEED, 0);
		}
		if ((Gdx.input.isKeyPressed(Input.Keys.W)))
		{
			camera.translate(0, CAMERA_TRANSLATION_SPEED);
		}
		if ((Gdx.input.isKeyPressed(Input.Keys.S))) {
			camera.translate(0, -CAMERA_TRANSLATION_SPEED);
		}

		camera.update();
		worldRenderer.render(camera, 0);
	}
	
	@Override
	public void dispose () {

	}
}
