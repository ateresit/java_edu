package ru.geekbrains.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.screen.BaseScreen;

public class MenuScreen extends BaseScreen {

    private final float V_LENGTH = 5f;
    private Texture img;
    private Vector2 position;
    private Vector2 touch;
    private Vector2 v;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        position = new Vector2();
        touch = new Vector2();
        v = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, position.x, position.y);
        batch.end();

        if (touch.dst(position) >= V_LENGTH) {
            position.add(v);
        } else {
            position.set(touch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        v.set(touch.cpy().sub(position).setLength(V_LENGTH));
        return super.touchDown(screenX, screenY, pointer, button);
    }


}
