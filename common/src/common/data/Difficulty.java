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
}
