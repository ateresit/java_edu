package ru.geekbrains.screen.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.screen.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 position;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        position = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, position.x, position.y, 0.5f, 0.5f);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return super.touchDown(screenX, screenY, pointer, button);
    }


}
