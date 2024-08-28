package hw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static hw.ListPerson.FILE_JSON;
import static hw.ListPerson.loadPersonsFromFile;

public class HomeWork {
    public static void main(String[] args) {
        List<Person> personsList;
        File file = new File(FILE_JSON);
        if (file.exists() && !file.isDirectory()) {
            personsList = loadPersonsFromFile(FILE_JSON);
        } else {
            personsList = preparePersonsList();
        }

        ListPerson.savePersonToFile(FILE_JSON, personsList);


        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Выберите действие: ");
            System.out.println("1. Добавить нового Person");
            System.out.println("2. Удалить Person");
            System.out.println("3. Изменить информацию о Person");
            System.out.println("4. Вывести список Person");
            System.out.println("5. Выход");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    ListPerson.addNewPerson(scanner, personsList);
                    break;
                case "2":
                    ListPerson.deletePerson(scanner, personsList);
                    break;
                case "3":
                    ListPerson.changeInfoPerson(scanner, personsList);
                    break;
                case "4":
                    ListPerson.printPersonsList(personsList);
                    break;
                case "5":
                    ListPerson.savePersonToFile(FILE_JSON, personsList);
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
            //ListPerson.printPersonsList(personsList);
        }
    }

    private static List<Person> preparePersonsList() {
        List<Person> personsList = new ArrayList<>();
        personsList.add(new Person("Иван", 25));
        personsList.add(new Person("Геннадий", 31));
        personsList.add(new Person("Ольга", 19));
        personsList.add(new Person("Диана", 21));
        return personsList;
    }
}
