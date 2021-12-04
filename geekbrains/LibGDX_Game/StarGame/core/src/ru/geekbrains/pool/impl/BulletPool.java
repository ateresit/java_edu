package ru.geekbrains.pool.impl;

import ru.geekbrains.pool.SpritesPool;
import ru.geekbrains.sprite.impl.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
