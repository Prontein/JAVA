package geekbrains.homework.lesson13;

public class Homework {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {

        float[] myArray = new float[size];
        System.out.println("Первый метод: ");
        fillArray (myArray);
        System.out.println("Второй метод: ");
        newFillArray(myArray);
    }

    public static void fillArray(float[] myArray) {

        long a = System.currentTimeMillis();

//        System.out.println("Начало отсчета = " + a);

        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = 1;
        }

        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = (float)(myArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));;
        }

        long b = System.currentTimeMillis();

//        System.out.println("Конец отсчета = " + b);
        System.out.println("Время работы = " + (b - a));
    }

    public static void newFillArray(float[] myArray) {

       float[] firstArray = new float[h];
       float[] secondArray = new float[h];

       long a = System.currentTimeMillis();

//       System.out.println("Начало отсчета  = " + a);

       System.arraycopy(myArray, 0, firstArray, 0, h);
       System.arraycopy(myArray, h, secondArray, 0, h);

       new Thread() {
           public void run () {
               for (int i = 0; i < firstArray.length; i++) {
                   firstArray[i] = 1;
               }
               for (int i = 0; i < firstArray.length; i++) {
                   firstArray[i] = (float)(firstArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));;
               }
           }
       }.start();

        new Thread() {
            public void run () {
                for (int i = 0; i < secondArray.length; i++) {
                    secondArray[i] = 1;
                }
                for (int i = 0; i < secondArray.length; i++) {
                    secondArray[i] = (float)(secondArray[i] * Math.sin(0.2f + ((i + h) / 5)) * Math.cos(0.2f + ((i + h) / 5)) * Math.cos(0.4f + ((i + h) / 2)));;
                }
            }
        }.start();

        System.arraycopy(firstArray, 0, myArray, 0, h);
        System.arraycopy(secondArray, 0, myArray, h, h);

        long b = System.currentTimeMillis();

//        System.out.println("Конец отсчета = " + b);
        System.out.println("Время работы = " + (b - a));
    }

}
