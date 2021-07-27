package ru.geekbrains;

import java.util.concurrent.ThreadLocalRandom;

public class RandomValueGen {
    int newRandom = 0;

    /**
     * Генерация псевдо-случайных значений в заданных пределах и с заданным шагом
     *
     * @param step шаг генерации произвольного значения
     * @param step2 смещение для произвольного значения
     * @param min минимальное начальное значение (вкл)
     * @param max максимальное конечное значение (вкл)
     * @return произвольное значение в заданных пределах
     */
    public int randomValueWithStep(int step, int step2, int min, int max) {
        int rndValue = ThreadLocalRandom.current().nextInt(min/step,(max+step)/step);
        int rndValuePlus = ThreadLocalRandom.current().nextInt(step2);
        newRandom = (rndValue * step) + (rndValuePlus * step2);

        if (newRandom > max) {
            rndValue = ThreadLocalRandom.current().nextInt(min/step,(max+step)/step);
            newRandom = (rndValue * step);
        }

        return newRandom;
    }
}
