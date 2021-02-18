package geekbrains.homework.lesson9;

// Добрый вечер. На первом курсе Java на занятиях и в практических заданиях мы всегда создавали классы отдельно друг от друга,
// на прошлом занятии вы показывали примеры создавая множество классов в одном. Я ради нового опыта попробовал сделать так же,
// но все таки хотелось бы уточнить сколько допускается создавать таких классов внутри одного, чтобы это было правильно с точки зрения
// грамотности написания кода? И правильно ли будет создавать все классы по отдельности или это допустимо только для новичков на этапе
// обучения?
// Не разобрался как можно связть 3 отдельных класса в один массив участников, поэтому создал новые самостоятельные массивы
// с участникками и препятсвиями. Надеюсь не сильно отклонился от условий задания.

public class Homework {

    public static void main (String[] args) {

//        Cat cat = new Cat("Борис", 50, 2, DISTANCE, WALL_HEIGHT);
//        Human human = new Human("Вова", 150, 3, DISTANCE, WALL_HEIGHT);
//        Robot robot = new Robot("Железяка", 1000, 1, DISTANCE, WALL_HEIGHT);

//        if (cat.run() == true) {
//            System.out.println("Участник " + cat.getName() + " успешно пробежал дистанцию");
//        } else {
//            System.out.println("Участник " + cat.getName() + " не справился с дистанцией");
//        }
//
//        if (cat.jump() == true) {
//            System.out.println("Участник " + cat.getName() + " успешно перепрыгнул препятствие");
//        } else {
//            System.out.println("Участник " + cat.getName() + " не смог перепрыгнуть препятствие");
//        }


        Participant[] participant = new Participant[6];

        participant[0] = new Participant ("Cat","Борис", 50, 4);
        participant[1] = new Participant ("Cat","Барсик", 150, 10);
        participant[2] = new Participant ("Human","Вова", 150, 3);
        participant[3] = new Participant ("Human","Серега", 1000, 1);
        participant[4] = new Participant ("Robot","Железяка", 1000, 1);
        participant[5] = new Participant ("Robot","Громозека", 200, 10);

        Obstacles[] obstacles = new Obstacles[3];
        obstacles[0] = new Obstacles(30, 5);
        obstacles[1] = new Obstacles(40, 1);
        obstacles[2] = new Obstacles(100, 4);


        for (int i = 0; i < obstacles.length; i++) {
            int counter = 0;
            System.out.println("Начинается полоса препятствий № " + (i + 1));

            for (int j = 0; j < participant.length; j++) {

                if (participant[j] != null ) {
                    if (participant[j].run(obstacles[i].Length)) {
                        System.out.println("Участник " + participant[j].getType() + " под именем " + participant[j].getName() + " успешно пробежал дистанцию! ");
                    } else {
                        System.out.println("Участник " + participant[j].getType() + " под именем " + participant[j].getName() + " не справился с дистанцией и выбывает из соревнований ");
                        participant[j] = null;
                    }
                    if (participant[j] != null ) {
                        if (participant[j].jump(obstacles[i].Height)) {
                            System.out.println("Участник " + participant[j].getType() + " под именем " + participant[j].getName() + " успешно перепрыгнул препятствие! ");
                        } else {
                            System.out.println("Участник " + participant[j].getType() + " под именем " + participant[j].getName() + " не смог перепрыгнуть препятствие и выбывает из соревнований! ");
                            participant[j] = null;
                        }
                    }
                } else {
                    counter ++; // В случае если участник выбывает из соревнований счетчик увеличивается на 1
                }
                if (counter == participant.length) {
                    System.out.println("Все участники выбыли из соревнований");
                    return;
                }
            }
        }

    }

    public static class Obstacles {
        private int Length;
        private int Height;

        Obstacles(int Length, int Height) {
            this.Length = Length;
            this.Height = Height;
        }

        public int getLength () {
            return Length;
        }

        public int getHeight () {
            return Height;
        }
    }

    public static class Participant implements Treadmill, Wall {

        private String type;
        private String name;
        private int maxLength;
        private int maxHeight;

        Participant (String type, String name, int maxLength, int maxHeight) {
            this.type = type;
            this.name = name;
            this.maxLength = maxLength;
            this.maxHeight = maxHeight;
        }

        public String getType () {
            return type;
        }

        public String getName () {
            return name;
        }

        public int getMaxLength () {
            return maxLength;
        }

        public int getMaxHeight () {
            return maxHeight;
        }

        @Override
        public boolean run (int distance) {
            return  (maxLength > distance);
        }

        @Override
        public boolean jump (int wallHeight) {
            return (maxHeight > wallHeight);
        }
    }




}
