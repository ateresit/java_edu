package ru.geekbrains.sprite.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Sprite;

public class Logo extends Sprite {

    private static final float HEIGHT = 0.15f;
    private static final float V_LEN = 0.01f;

    private final Vector2 touch;
    private final Vector2 v;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        this.touch = new Vector2();
        this.v = new Vector2();
    }

    // позиционируем картинку и задаём размер
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
    }

    // движение картинки
    @Override
    public void update(float delta) {
        super.update(delta);
        if (pos.dst(touch) > V_LEN){
            pos.add(v);
        } else {
            pos.set(touch);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }
}
