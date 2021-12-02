package ru.geekbrains.screen.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.BaseScreen;
import ru.geekbrains.sprite.impl.Background;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;

    @Override
    public void show() {
        super.show();
        bg = new Texture("dart_vaider_one.jpeg");
        background = new Background(bg);
        draw();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {

    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        batch.end();

    }

}
