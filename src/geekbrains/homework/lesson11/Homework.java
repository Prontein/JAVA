package geekbrains.homework.lesson11;

import java.util.HashMap;
import java.util.Set;

public class Homework {
    public static void main(String[] args) {
//      Задание № 1
        String[] arrayWords = {"Кот", "Собака", "Жираф", "Бегемот", "Слон", "Кот", "Буйвол", "Змея", "Собака", "Кот", "Цапля", "Утка", "Жираф", "Леопард", "Гиена",
                "Волк", "Тигр", "Суслик", "Буйвол", "Утка"};

        HashMap <String, Integer> myMap = new HashMap<>();
        findWord(arrayWords, myMap);

        Set<String> keys = myMap.keySet();
        System.out.println("Уникальные слова: " + keys);
        System.out.println("Количество повторений: " + myMap);

//      Задание № 2
        PhoneDirectory phoneDirectory = new PhoneDirectory();
        phoneDirectory.addPerson("Ivanov", 796434);
        phoneDirectory.addPerson("Sidorov", 792337);
        phoneDirectory.addPerson("Ivanov", 7165465);
        phoneDirectory.addPerson("Ivanov", 72552235);
        phoneDirectory.addPerson("Sidorov", 2358768);
        phoneDirectory.addPerson("Petrov", 1654654);
        phoneDirectory.addPerson("Sidorov", 1650656);
        System.out.println("\nСправочник");
        System.out.println("phone Ivanov: " + PhoneDirectory.info("Ivanov"));
        System.out.println("phone Sidorov: " + PhoneDirectory.info("Sidorov"));
        System.out.println("phone Petrov: " + PhoneDirectory.info("Petrov"));
    }

    public static void findWord(String[] arrayWords, HashMap<String, Integer> myMap) {
        for (String arrayWord : arrayWords) {
            if (myMap.containsKey(arrayWord))
                myMap.put(arrayWord, myMap.get(arrayWord) + 1);
            else
                myMap.put(arrayWord, 1);
        }
    }
}
