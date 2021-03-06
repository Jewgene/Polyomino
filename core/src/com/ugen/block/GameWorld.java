package com.ugen.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by eugen_000 on 9/3/2016.
 */
public class GameWorld {
    final PolyominoesGame theGame;

    private Preferences prefs;

    WorldRenderer renderer;

    Polyomino first;

    private ArrayList<Polyomino> currentGeneration;
    private ArrayList<Polyomino> nextGeneration;
    private ArrayList<Color> colors;

    long timeElapsed;

    float red;
    float green;
    float blue;

    int posx = 50, posy = 200;

    int generationCap;

    public GameWorld(PolyominoesGame game){
        this.theGame = game;

        timeElapsed = System.currentTimeMillis();

        currentGeneration = new ArrayList<Polyomino>();
        nextGeneration = new ArrayList<Polyomino>();
        colors = new ArrayList<Color>();
        first = new Polyomino();
        prefs = Gdx.app.getPreferences("UgenPrefs");

        if(prefs.contains("degree")){
            generationCap = prefs.getInteger("degree") - 1;
        }
        else
            generationCap = theGame.getGameScreen().getGenerationCap();

        generatePolyominoes();

        timeElapsed = System.currentTimeMillis() - timeElapsed;

        for(int i = 0; i < currentGeneration.size(); i++){
            red = (float) Math.sin((float)i * 6 / currentGeneration.size()) * 0.5f + 0.5f;
            green = (float) Math.sin((float)i * 6 / currentGeneration.size() + 1) * 0.5f + 0.5f;
            blue = (float) Math.sin((float)i * 6 /  currentGeneration.size() + 3) * 0.5f + 0.5f;

            currentGeneration.get(i).setColorIndex(i);
            colors.add(new Color(red, green, blue, 1.0f));
        }

        prefs.putInteger("degree", generationCap);
    }

    public void generatePolyominoes(){
        currentGeneration.clear();

        currentGeneration.add(first);

        for(int ii = 0; ii < generationCap; ii++) {
            for (int i = 0; i < currentGeneration.size(); i++){
                currentGeneration.get(i).rotate();
                currentGeneration.get(i).giveBirth();
                for(int j = 0; j < currentGeneration.get(i).getChildren().size(); j++)
                    nextGeneration.add(currentGeneration.get(i).getChildren().get(j));
            }

            currentGeneration.clear();
            for(int i = 0; i < nextGeneration.size(); i++)
                currentGeneration.add(nextGeneration.get(i));
            nextGeneration.clear();
        }
        removeDuplicates(currentGeneration);
    }

    public void removeDuplicates(ArrayList<Polyomino> children){
        for(int i = 0; i < children.size(); i++){
            for(int j = 0; j < 4; j++){
                for(int ii = children.size() - 1; ii > i; ii--){
                    if(Arrays.deepEquals(children.get(i).getGrid().getData(), children.get(ii).getGrid().getData()) & i != ii)
                        children.remove(ii);
                }
                children.get(i).rotate();
            }
        }
    }

    public void update(float delta){
    }

    public void setGenerationCap(int generationCap){
        this.generationCap = generationCap;
    }

    public ArrayList<Polyomino> getCurrentGeneration() {
        return currentGeneration;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setRenderer(WorldRenderer renderer) {
        this.renderer = renderer;
    }

    public PolyominoesGame getGame(){return theGame;}

    public int getGenerationCap() {
        return generationCap;
    }

    public Preferences getPrefs(){
        return prefs;
    }
}
