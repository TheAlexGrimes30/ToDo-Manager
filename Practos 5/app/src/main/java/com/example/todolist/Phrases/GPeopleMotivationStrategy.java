package com.example.todolist.Phrases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GPeopleMotivationStrategy implements MotivationStrategy{

    /*
        Класс, содержащий мотивационные фразы великих личностей.
         Реализует паттерн "Стратегия"
     */

    private static final List<String> phrases = new ArrayList<>();

    static {
        phrases.add("Вы никогда не сумеете решить возникшую проблему, если сохраните то же " +
                "мышление и тот же подход, который привел вас к этой проблеме. Альберт Эйнштейн");

        phrases.add("Мы – рабы своих привычек. Измени свои привычки," +
                " изменится твоя жизнь. Роберт Кийосаки");

        phrases.add("Столкнувшись с трудностями, нельзя сдаваться, бежать." +
                " Вы должны оценивать ситуацию," +
                " искать решения и верить в то," +
                " что все делается к лучшему. Терпение – вот ключ к победе. Ник Вуйчич");

        phrases.add("Неудача – это просто возможность начать снова," +
                " но уже более мудро. Генри Форд");

        phrases.add("Не ошибается тот, кто ничего не делает! " +
                "Не бойтесь ошибаться – бойтесь повторять ошибки! Теодор Рузвельт");

        phrases.add("Когда вы вводите какие-либо новшества будьте готовы к тому," +
                " что вас назовут сумасшедшим. Ларри Эллисон");

        phrases.add("Ты никогда не переплывешь океан," +
                " если будешь бояться потерять берег из виду. Христофор Колумб");
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
