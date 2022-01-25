package ru.geekbrains.patternBuilder;

public class NewPerson implements PersonBuilder{
    private String firstName;
    private String lastName;
    private String middleName;
    private String country;
    private String address;
    private String phone;
    private int age;
    private String gender;

    public NewPerson() {
        super();
    }

    @Override
    public PersonBuilder addFirstName() {
        System.out.println("Добавление имени");
        this.firstName = "Сергей";
        return this;
    }

    @Override
    public PersonBuilder addLastName() {
        System.out.println("добавление фамилии");
        this.lastName = "Петров";
        return this;
    }

    @Override
    public PersonBuilder addMiddleName() {
        System.out.println("добавление отчество");
        this.middleName = "Викторович";
        return this;
    }

    @Override
    public PersonBuilder addCountry() {
        System.out.println("добавление страны");
        this.country = "Россия";
        return this;
    }

    @Override
    public PersonBuilder addAddress() {
        System.out.println("Добавление адреса");
        this.address = "Москва";
        return this;
    }

    @Override
    public PersonBuilder addPhone() {
        System.out.println("Добавление телефона");
        this.phone = "911";
        return this;
    }

    @Override
    public PersonBuilder addAge() {
        System.out.println("Добавление возраста");
        this.age = 3;
        return this;
    }

    @Override
    public PersonBuilder addGender() {
        System.out.println("Добавление пола");
        this.gender = "М";
        return this;
    }

    @Override
    public Person build() {
        Person person = new Person(firstName, lastName, middleName, country, address, phone, age, gender);
        if (person.doCheck()) {
            return person;
        } else {
            System.out.println("Допущены ошибки при добавлении");
        }
        return null;
    }
}
