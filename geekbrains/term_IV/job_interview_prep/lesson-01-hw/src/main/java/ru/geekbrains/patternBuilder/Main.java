package ru.geekbrains.patternBuilder;

public class Main {
    public static void main(String[] args) {
        PersonBuilder builder = new NewPerson();
        PersonCreator creator = new PersonCreator(builder);
        Person person = creator.createPerson();

        if (person != null) {
            System.out.println("Персона создана");
            System.out.println(person);
        }
    }
}
