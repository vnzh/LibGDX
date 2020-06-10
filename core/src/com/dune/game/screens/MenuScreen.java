package com.dune.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dune.game.core.Assets;
import com.dune.game.core.gui.GuiPlayerInfo;

public class MenuScreen extends AbstractScreen {
    public MenuScreen(SpriteBatch batch) {
        super(batch);

    }

//
//    private Stage stage;
//
//    public Stage getStage() {
//        return stage;
//    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void update(float dt) {
//        stage.act(dt);
        if (Gdx.input.justTouched()) {
            ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
        }
    }

    @Override
    public void dispose() {
    }


//    public void createGui() {
//        stage = new Stage(ScreenManager.getInstance().getViewport(), ScreenManager.getInstance().getBatch());
//        Gdx.input.setInputProcessor(new InputMultiplexer(stage));
//        Skin skin = new Skin();
////        skin.addRegions(Assets.getInstance().getAtlas());
//        skin.addRegions(Assets.getInstance().getAtlas());
//        BitmapFont font14 = Assets.getInstance().getAssetManager().get("fonts/font14.ttf");
//        TextButton.TextButtonStyle textButtonStyleGame = new TextButton.TextButtonStyle(
//                skin.getDrawable("mainGame"), null, null, font14);
//
//        final TextButton menuBtn = new TextButton("", textButtonStyleGame);
//        menuBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
//            }
//        });
//        TextButton.TextButtonStyle textButtonStyleExit = new TextButton.TextButtonStyle(
//                skin.getDrawable("mainExit"), null, null, font14);
//
//        final TextButton exitBtn = new TextButton("", textButtonStyleExit);
//        exitBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });
//        Group menuGroup = new Group();
//        menuBtn.setPosition(600, 500);
//        exitBtn.setPosition(600, 300);
//        menuGroup.addActor(menuBtn);
//        menuGroup.addActor(exitBtn);
//        menuGroup.setPosition(550, 550);
//
//        stage.addActor(menuGroup);
//        skin.dispose();
//    }

}