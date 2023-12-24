package com.example.todolist.Phrases;

import java.util.Random;

public class MotivationGenerator implements IMotivationGenerator{

    /*
        Класс-генератор выбора мотивационных фраз.
         Работает с паттерном "Стратегия"
         Наследует интерфейс IMotivationGenerator
     */

    private MotivationStrategy strategy;

    public MotivationGenerator() {

            //Конструктор класса MotivationGenerator

        Random random = new Random();
        int classNumber = random.nextInt(3) + 1;
        switch (classNumber) {
            case 1:
                strategy = new GPeopleMotivationStrategy();
                break;
            case 2:
                strategy = new CasualMotivationStrategy();
                break;
            case 3:
                strategy = new HumorMotivationStrategy();
                break;
        }
    }

    @Override
    public String generateMotivation() {

        // Данный метод возвращает мотивационную фразу

        return strategy.getMotivationalPhrase();
    }


}
