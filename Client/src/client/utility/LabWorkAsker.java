package client.utility;

import client.ClientApp;
import common.data.*;
import common.exceptions.LowerThanMinValueException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotEnumElementException;
import common.exceptions.WrongAnswerFormatException;
import common.exceptions.IncorrectInputInScriptException;

import common.utility.Outputer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.io.BufferedReader;

/**
 * Класс, запрашивающий у пользователя ввод значений элементов объекта - Лабораторной работы
 */
public class LabWorkAsker {
    private BufferedReader reader;
    private boolean fileMode;

    public LabWorkAsker(BufferedReader reader) {
        this.reader = reader;
        fileMode = false;
    }

    /**
     * Sets marine asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets marine asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the marine's name.
     *
     * @return Marine's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Outputer.println("Введите название:");
                Outputer.print(ClientApp.PS2);
                name = reader.readLine().trim();
                if (fileMode) Outputer.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Название не должно быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks a user the marine's X coordinate.
     *
     * @return Marine's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Double askX() throws IncorrectInputInScriptException {
        String strX;
        Double x;
        while (true) {
            try {
                Outputer.println("Введите координату X:");
                Outputer.print(ClientApp.PS2);
                strX = reader.readLine().trim();
                if (fileMode) Outputer.println(strX);
                if (strX.equals("") || strX.equals(null)) throw new MustBeNotEmptyException();
                x = Double.parseDouble(strX);
                if (x <= -711) throw new LowerThanMinValueException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата X должна быть представлена числом формата Double!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Значение не может быть null!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (LowerThanMinValueException exception) {
                Outputer.printerror("Значение должно быть больше -711!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks a user the marine's Y coordinate.
     *
     * @return Marine's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Long askY() throws IncorrectInputInScriptException {
        String strY;
        Long y;
        while (true) {
            try {
                Outputer.println("Введите координату Y:");
                Outputer.print(ClientApp.PS2);
                strY = reader.readLine().trim();
                if (fileMode) Outputer.println(strY);
                if (strY.equals("") || strY.equals(null)) throw new MustBeNotEmptyException();
                y = Long.parseLong(strY);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата Y должна быть представлена числом формата Long!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Значение не может быть null!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }
        }
        return y;
    }

    /**
     * Asks a user the marine's coordinates.
     *
     * @return Marine's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        Double x;
        Long y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the marine's health.
     *
     * @return Marine's health.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public int askMinimalPoint() throws IncorrectInputInScriptException {
        String strMinPoint;
        int minPoint;
        while (true) {
            try {
                Outputer.println("Введите минимальный балл:");
                Outputer.print(ClientApp.PS2);
                strMinPoint = reader.readLine().trim();
                if (fileMode) Outputer.println(strMinPoint);
                minPoint = Integer.parseInt(strMinPoint);
                if (minPoint <= 0) throw new LowerThanMinValueException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Минимальный балл не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (LowerThanMinValueException exception) {
                Outputer.printerror("Минимальный балл должно быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Минимальный балл должно быть представлено числом формата int!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            }
        }
        return minPoint;
    }

    /**
     * Asks a user the marine's category.
     *
     * @return Marine's category.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Float askPersonalQualitiesMinimum() throws IncorrectInputInScriptException {
        String strPersQualMin;
        Float persQualMin;
        while (true) {
            try {
                Outputer.println("Введите необходимый минимум личных качеств:");
                Outputer.print(ClientApp.PS2);
                strPersQualMin = reader.readLine().trim();
                if (strPersQualMin.equals("") || strPersQualMin.equals(null)) throw new MustBeNotEmptyException();
                if (fileMode) Outputer.println(strPersQualMin);
                persQualMin = Float.parseFloat(strPersQualMin);
                if (persQualMin <= 0) throw new LowerThanMinValueException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Минимум персональных качеств не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            } catch (NumberFormatException exception) {
                Outputer.printerror("Минимум персональных качеств должно быть представлено числом формата Float!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Значение не может быть null!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (LowerThanMinValueException e) {
                Outputer.printerror("Значение должно быть больше 0!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }
        }
        return persQualMin;
    }

    /**
     * Asks a user the marine's weapon type.
     *
     * @return Marine's weapon type.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askAveragePoint() throws IncorrectInputInScriptException {
        String strAverPoint;
        long averPoint;
        while (true) {
            try {
                Outputer.println("Введите средний балл:");
                Outputer.print(ClientApp.PS2);
                strAverPoint = reader.readLine().trim();
                if (fileMode) Outputer.println(strAverPoint);
                averPoint = Long.parseLong(strAverPoint);
                if (averPoint <= 0) throw new LowerThanMinValueException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Средний балл не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            } catch (NumberFormatException exception) {
                Outputer.printerror("Средний балл должно быть представлено числом формата long!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (LowerThanMinValueException e) {
                Outputer.printerror("Значение должно быть больше 0!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }
        }
        return averPoint;
    }

    /**
     * Asks a user the marine's melee weapon.
     *
     * @return Marine's melee weapon.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askPersonalName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Outputer.println("Введите имя автора");
                Outputer.print(ClientApp.PS2);
                name = reader.readLine().trim();
                if (name.equals("") || name.equals(null)) throw new MustBeNotEmptyException();
                if (fileMode) Outputer.println(name);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Имя не должно быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }
        }
        return name;
    }

    /**
     * Asks a user the marine chapter's name.
     *
     * @return Chapter's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public LocalDateTime askPersonBirthday() throws IncorrectInputInScriptException {
        String strDate;
        LocalDateTime date;
        while (true) {
            try {
                Outputer.println("Введите дату рождения автора:");
                Outputer.print(ClientApp.PS2);
                strDate = reader.readLine().trim();
                if (strDate.equals("") || strDate.equals(null)) throw new MustBeNotEmptyException();
                if (fileMode) Outputer.println(strDate);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                date = LocalDateTime.parse(strDate, formatter);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Дата рождения не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Поле даты рождения не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            } catch (DateTimeParseException exception) {
                Outputer.printerror("Возникла ошибка! Проверьте правильность формата ввода: YYYY-MM-DDThh:mm:ss");
                if (fileMode) throw new IncorrectInputInScriptException();
            }
        }
        return date;
    }

    /**
     * Asks a user the marine chapter's number of soldiers.
     *
     * @return Number of soldiers.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askPersonPassportID() throws IncorrectInputInScriptException {
        String passport;
        while (true) {
            try {
                Outputer.println("Введите паспортные данные автора:");
                Outputer.print(ClientApp.PS2);
                passport = reader.readLine().trim();
                if (fileMode) Outputer.println(passport);
                if (passport.equals("") || passport.equals(null)) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Количество солдат в ордене не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (MustBeNotEmptyException e) {
                Outputer.printerror("Имя не должно быть пустым!");
            }
        }
        return passport;
    }

    /**
     * Метод запрашивает у пользователя автора Лабораторной работы
     * @return Person
     */
    public Person askAuthor() throws IncorrectInputInScriptException {
        String name;
        LocalDateTime birthday;
        String passportID;
        name = askPersonalName();
        birthday = askPersonBirthday();
        passportID = askPersonPassportID();
        return new Person(name,birthday,passportID);
    }

    /**
     * Метод запрашивает у пользователя сложность Лабораторной работы
     * @return diff
     */
    public Difficulty askDifficulty() throws IncorrectInputInScriptException {
        String strDiff;
        Difficulty diff;
        while (true) {
            try {
                System.out.println("Введите уровень сложности работы: HOPELESS, TERRIBLE, VERY_EASY");
                Outputer.print(ClientApp.PS2);
                strDiff = reader.readLine().trim();
                if (strDiff.equals("") || strDiff.equals(null)) throw new MustBeNotEmptyException();
                if (fileMode) Outputer.println(strDiff);
                strDiff = strDiff.toUpperCase();
                if (!(strDiff.equals("VERY_EASY") || strDiff.equals("HOPELESS") || strDiff.equals("TERRIBLE"))) throw new NotEnumElementException();
                diff = Difficulty.valueOf(strDiff);
                break;
            } catch(IOException ex){
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            } catch (MustBeNotEmptyException ex){
                Outputer.printerror("Имя не должно быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotEnumElementException ex){
                Outputer.printerror("Введенное значение некорректно! Проверьте, что вы ввели один из 3 вариантов: HOPELESS, TERRIBLE, VERY_EASY");
                if (fileMode) throw new IncorrectInputInScriptException();
            }
        }
        return diff;
    }
    /**
     * Asks a user a question.
     *
     * @param argument A question.
     * @return Answer (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String argument) throws IncorrectInputInScriptException {
        String question = argument + " (+/-):";
        String answer;
        boolean x;
        while (true) {
            try {
                Outputer.println(question);
                Outputer.print(ClientApp.PS2);
                answer = reader.readLine().trim();
                if (fileMode) Outputer.println(answer);
                if (answer.equals("+")) x = true;
                else if(answer.equals("-")) x = false;
                else throw new WrongAnswerFormatException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (WrongAnswerFormatException exception) {
                Outputer.printerror("Ответ должен быть '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IOException exception) {
                Outputer.printerror("Операция ввода была прервана!");
                System.exit(0);
            }
        }
        return x;
    }
}

