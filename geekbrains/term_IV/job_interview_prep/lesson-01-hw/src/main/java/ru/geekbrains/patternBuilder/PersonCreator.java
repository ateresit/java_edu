package ru.geekbrains.patternBuilder;

public class PersonCreator {
    private PersonBuilder builder;

    public PersonCreator(PersonBuilder builder){
        super();
        this.builder = builder;
        if (this.builder == null) {
            throw new IllegalArgumentException("Не работает без билдера!");
        }
    }

    public Person createPerson(){
        return builder.addFirstName().addMiddleName().addLastName().addGender().addAge().addPhone().addAddress().addCountry().build();
    }
}
