package geekbrains.homework.lesson10;

import java.awt.event.FocusEvent;

public class MyException extends Exception {
    public static class MyArraySizeException extends Exception {
        public MyArraySizeException (String message) {
            super (message);
        }
    }

    public static class MyArrayDataException extends NumberFormatException {
        public MyArrayDataException (String message) {
            super (message);
        }
    }
}