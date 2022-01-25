package ru.geekbrains.patternBuilder;

public interface PersonBuilder {

    public PersonBuilder addFirstName();
    public PersonBuilder addLastName();
    public PersonBuilder addMiddleName();
    public PersonBuilder addCountry();
    public PersonBuilder addAddress();
    public PersonBuilder addPhone();
    public PersonBuilder addAge();
    public PersonBuilder addGender();

    public Person build();

}
