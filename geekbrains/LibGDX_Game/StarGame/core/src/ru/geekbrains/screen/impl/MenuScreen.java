package ru.geekbrains.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.screen.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 positionStart;
    private int xStartPosition = 20;
    private int yStartPosition = 20;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        touch = new Vector2();
        positionStart = new Vector2(xStartPosition, yStartPosition);
        v = new Vector2(1,1);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
//        batch.draw(img, touch.x, touch.y);
        batch.draw(img, positionStart.x, positionStart.y);

        if ((touch.x != positionStart.x || touch.y != positionStart.y) && (positionStart.x <= touch.x && positionStart.y <= touch.y)) {
//            touch.add(v);
            positionStart.add(v);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }


}
