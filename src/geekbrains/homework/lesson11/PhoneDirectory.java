package geekbrains.homework.lesson11;

import java.util.*;


public class PhoneDirectory {

    private static final HashMap<String, List<Integer>> surnameAndNumber = new HashMap<>();

    public void addPerson(String name, Integer number) {
        getNumber(name);
        surnameAndNumber.get(name).add(number);
    }

    private void getNumber(String name) {
        surnameAndNumber.putIfAbsent(name, new ArrayList<>());
    }

    public static List<Integer> info(String name) {
        return surnameAndNumber.get(name);
    }
}
