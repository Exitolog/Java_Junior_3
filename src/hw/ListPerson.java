package hw;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import task2.ToDo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListPerson {

    public static final String FILE_JSON = "persons.json";

    private static final ObjectMapper objectMapper = new ObjectMapper();


    //метод добавления нового Person в список
    public static void addNewPerson(Scanner scanner, List<Person> personList) {
        System.out.println("Введите имя нового Person: ");
        String newName = scanner.nextLine();
        System.out.println("Введите возраст нового Person (строго 18+ и меньше 70) : ");
        int newAge = Integer.parseInt(scanner.nextLine());
        if (!validPerson(newName, newAge)) {
            addNewPerson(scanner, personList);
        } else {
            personList.add(new Person(newName, newAge));
            savePersonToFile(FILE_JSON, personList);
            System.out.println("Новый Person " + newName + " добавлен в список");
        }
    }

    //метод выгрузки данных из бд со списком Person
    public static List<Person> loadPersonsFromFile(String fileName) {
        List<Person> personsList = new ArrayList<>();

        File file = new File(fileName);
        if (file.exists()) {
            try {
                if (fileName.endsWith(".json"))
                    personsList = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return personsList;
    }

    //метод добавления Person в список
    public static void savePersonToFile(String fileName, List<Person> personList) {
        try {
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(fileName), personList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод проверки Person на валидность данных
    public static boolean validPerson(String newName, int newAge) {
        boolean valid = true;
        if(newName.isEmpty()){
            System.out.println("Ввели пустое поле имя");
            return false;
        }
        if (newAge < 18 || newAge > 70) {
            System.out.println("Некорректный ввод возраста " + newAge + ". Повторите попытку ввода: ");
            return false;

        }
        if (!newName.matches("^[a-zA-Zа-яА-Я]*")) {
            System.out.println("Некорректное имя " + newName + ". Повторите попытку ввода: ");
            return false;
        }
        return valid;
    }

    //метод удаления Person из бд
    public static void deletePerson(Scanner scanner, List<Person> personsList) {
        System.out.println("Введите номер Person, который хотите удалить из списка");
        String inputNumber = scanner.nextLine();

        try {
            int numberPerson = Integer.parseInt(inputNumber) - 1;
            if (numberPerson >= 0 && numberPerson < personsList.size()) {
                System.out.println("Person " + personsList.get(numberPerson).getName() + " удален из списка");
                personsList.remove(personsList.get(numberPerson));
                savePersonToFile(FILE_JSON, personsList);
            } else {
                System.out.println("Person под номером " + numberPerson
                        + "не найден в списке.\n Попробуйте снова");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Попробуйте еще раз.");
        }
    }

    //метод обновления Person
    public static void changeInfoPerson(Scanner scanner, List<Person> personsList) {
        System.out.println("Введите номер Person, информацию о котором хотите изменить");
        String inputNumber = scanner.nextLine();

        try {
            int numberPerson = Integer.parseInt(inputNumber) - 1;
            if (numberPerson >= 0 && numberPerson < personsList.size()) {
                System.out.println("Введите имя: ");
                String newName = scanner.nextLine();
                System.out.println("Введите возраст нового Person (строго 18+ и меньше 70) : ");
                int newAge = Integer.parseInt(scanner.nextLine());
                if (!validPerson(newName, newAge)) {
                    System.out.println("Некорректный ввод данных. Попробуйте снова.");
                } else {
                    personsList.get(numberPerson).setName(newName);
                    personsList.get(numberPerson).setAge(newAge);
                    System.out.println("Информация обновлена.");
                    savePersonToFile(FILE_JSON, personsList);
                }
            } else {
                System.out.println("Person под номером " + numberPerson
                        + "не найден в списке.\n Попробуйте снова");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Попробуйте еще раз.");
        }
    }

    //метод вывода списка Person на консоль
    public static void printPersonsList(List<Person> personsList) {
        System.out.println("Список Person: ");
        for (int i = 0; i < personsList.size(); i++) {
            Person person = personsList.get(i);
            System.out.println((i + 1) + ". " + person.getName() + " " + person.getAge());
        }
    }
}
