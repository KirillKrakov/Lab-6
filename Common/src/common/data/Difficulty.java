package common.data;

/**
 * Класс, представляющий сложность Лабораторной работы
 */
public enum Difficulty {
    VERY_EASY ("Очень легкая"),
    HOPELESS ("Безнадёжная"),
    TERRIBLE ("Ужасающая");

    private String title;

    Difficulty(String title) {
        this.title = title;
    }

    /**
     * Метод, возвращающий название сложности на русском языке
     * @return title
     */
    public String getTitle() {
        return title;
    }
    public static Difficulty outOfString(String arg) {
        for (Difficulty x : new Difficulty[]{Difficulty.HOPELESS, Difficulty.TERRIBLE, Difficulty.VERY_EASY}) {
            if (x.getTitle().equals(arg)) {
                return x;
            }
        }
        return null;
    }
}
