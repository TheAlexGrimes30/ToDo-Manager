package com.example.todolist.Phrases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HumorMotivationStrategy implements MotivationStrategy{

    /*
        Класс, содержащий пацанские мотивационные фразы.
         Реализует паттерн "Стратегия"
     */

    private static final List<String> phrases = new ArrayList<>();

    static {
        phrases.add("Прав не тот кто прав а тот кто лев");

        phrases.add("Легко вставать, когда ты не ложился.");

        phrases.add("Не нервничай! Не восстанавливаются не только нервные клетки," +
                " но и коренные зубы!");

        phrases.add("Твой путь — путь волка, но путь не волк, если в тебе не ты.");

        phrases.add("В этой жизни ты либо волк, либо НЕ волк.");

        phrases.add("Даже если нет шансов, всегда есть шанс.");

        phrases.add("Будь мотивирован, как сокол, охотись славно. Будь великолепен, как леопард," +
                " сражайся, чтобы победить. Проводите меньше времени с соловьями и павлинами. " +
                "Одна сплошная болтовня, другая только цвет");
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
