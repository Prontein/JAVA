package geekbrains.homework.lesson10;

public class Homework {
    public static final int ARRAY_SIZE = 4;

    public static void main(String[] args) {
        String[][] arrays = new String[4][4];

        arrays[0][0] = "1";
        arrays[0][1] = "2";
        arrays[0][2] = "3";
        arrays[0][3] = "4";
        arrays[1][0] = "5";
        arrays[1][1] = "6";
        arrays[1][2] = "7";
        arrays[1][3] = "ф8";
        arrays[2][0] = "9";
        arrays[2][1] = "10";
        arrays[2][2] = "11";
        arrays[2][3] = "12";
        arrays[3][0] = "13";
        arrays[3][1] = "g4";
        arrays[3][2] = "15";
        arrays[3][3] = "16";

        try {
            System.out.println("Результат суммирования = " + doExceptions (arrays));

        } catch (MyException.MyArraySizeException e) {
            e.printStackTrace();
        }
    }

    public static int doExceptions (String [][] arrays) throws MyException.MyArraySizeException {
        if (arrays[0].length != ARRAY_SIZE || arrays.length != ARRAY_SIZE)
            throw new MyException.MyArraySizeException("Задан некорректный размер массива");

        int[][] arraysInt = new int[ARRAY_SIZE][ARRAY_SIZE];
        int sum = 0;
        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays.length; j++) {
                try {
                    arraysInt[i][j] = Integer.parseInt(arrays[i][j]);
                } catch (NumberFormatException e) {
//                    e.printStackTrace();
                    try {
                        throw new MyException.MyArrayDataException("В ячейке с индексом [" + i + "][" + j + "] лежит символ или текст вместо числа!" + "\t" +
                                " arrays[" + i + "][" + j + "] = " + arrays[i][j]);
                    } catch (MyException.MyArrayDataException p) {
                        p.printStackTrace();
                    }
                }
                sum = sum + arraysInt[i][j];
            }
        }
    return sum;
    }

}
