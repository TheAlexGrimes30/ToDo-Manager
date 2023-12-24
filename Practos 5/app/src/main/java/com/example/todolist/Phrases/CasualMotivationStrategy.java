package com.example.todolist.Phrases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CasualMotivationStrategy implements MotivationStrategy{

    /*
        Класс, содержащий повседневные мотивационные фразы.
         Реализует паттерн "Стратегия"
     */

    private static final List<String> phrases = new ArrayList<>();

    static {
        phrases.add("Будущее зависит от того, что вы делаете сегодня.");

        phrases.add("Неважно, как медленно вы идете, главное — не останавливаться.");

        phrases.add("Усердие — это мать удачи.");

        phrases.add("Мечтайте больше, чем другие думают, что вы способны.");

        phrases.add("Рост начинается там, где заканчивается комфорт.");

        phrases.add("Вы сильнее, чем вы думаете.");

        phrases.add("Поверьте, что вы можете, и вы уже на полпути к успеху.");

        phrases.add("Поверь в себя и во все, что ты есть. Знай, что внутри " +
                "тебя есть что-то больше любого препятствия.");
    }

    @Override
    public String getMotivationalPhrase() {

        /*
            Метод, который возвращает случайную
             мотивационную фразу из списка phrase
         */

        Random random = new Random();
        int index = random.nextInt(phrases.size());
        return phrases.get(index);
    }
}
