package com.ugen.block;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by eugen_000 on 9/3/2016.
 */
public class GameScreen extends ScreenAdapter{

    InputHandler handler;

    private PolyominoesGame game;

    private GameWorld world;
    private WorldRenderer renderer;

    public GameScreen (PolyominoesGame game){
        this.game = game;

        world = new GameWorld(game);
        renderer = new WorldRenderer(world);
        world.setRenderer(renderer);
        handler = new InputHandler(this);
        Gdx.input.setInputProcessor(handler);
    }

    public void update(float delta){
        world.update(delta);
    }

    public void draw(float delta){
        renderer.render(delta);
    }

    @Override
    public void render(float delta){
        update(delta);
        draw(delta);
    }

    @Override
    public void pause(){
        super.pause();
    }

    @Override
    public void resume(){
        super.resume();
    }

    @Override
    public void dispose(){
        super.dispose();
    }

    public GameWorld getWorld() {
        return world;
    }

    public PolyominoesGame getGame() {
        return game;
    }

    public WorldRenderer getRenderer() {
        return renderer;
    }
}
