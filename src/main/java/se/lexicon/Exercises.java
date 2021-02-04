package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static se.lexicon.model.Gender.MALE;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message){
        System.out.println(message);
        Predicate<Person> firstNameCondition = person -> person.getFirstName().equalsIgnoreCase("Erik");
        List<Person> personsListWithErikName = storage.findMany(firstNameCondition);
        personsListWithErikName.forEach(person -> System.out.println(person));

        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message){
        System.out.println(message);
        Predicate<Person> femaleCondition = person -> person.getGender() == Gender.FEMALE;
        List<Person> personsListWithFemale = storage.findMany(femaleCondition);
        personsListWithFemale.forEach(person -> System.out.println(person));

        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message){
        System.out.println(message);

        Predicate<Person> bornAfterCondition = person -> person.getBirthDate().isAfter(LocalDate.parse("1999-12-31"));
        List<Person> personListWithBornAfter = storage.findMany(bornAfterCondition);
        personListWithBornAfter.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message){
        System.out.println(message);
        Predicate<Person> findOnePredicate = person -> person.getId() == 123;
        Person theOne = storage.findOne(findOnePredicate);
        System.out.println("theOne = " + theOne);

        System.out.println("----------------------");

    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message){
        System.out.println(message);
        Predicate<Person> findOnePredicate = person -> person.getId() == 456;
        Function<Person, String> personToString = person -> "Name: " + person.getFirstName() + " " + person.getLastName() + " born " + person.getBirthDate();
        String findNisseNilsson = storage.findOneAndMapToString(findOnePredicate, personToString);
        System.out.println(findNisseNilsson);


        System.out.println("----------------------");
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message){
        System.out.println(message);

        Predicate<Person> namesStartWithEAndMalePredicate = person -> person.getFirstName().startsWith("E")
                && person.getGender() == Gender.MALE;
        Function<Person, String> personToStringFunction = person -> person.getFirstName() + " " + person.getLastName();
        List<String> findAllMaleThatStartsWith_E = storage.findManyAndMapEachToString(namesStartWithEAndMalePredicate, personToStringFunction);
        findAllMaleThatStartsWith_E.forEach(s -> System.out.println(s));

        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message){
        System.out.println(message);
        Predicate<Person> allPersonBelowAge10 = person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() < 10;
        Function<Person, String> personToString = person -> person.getFirstName() + " "
                + person.getLastName() + " "
                + Period.between(person.getBirthDate(), LocalDate.now()).getYears() + " years";
        List<String> listAllPersonsBelowAge10 = storage.findManyAndMapEachToString(allPersonBelowAge10, personToString);
        listAllPersonsBelowAge10.forEach(System.out::println);


        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message){
        System.out.println(message);
        Predicate<Person> findUlf = person -> person.getFirstName().equalsIgnoreCase("Ulf");
        Consumer<Person> consumeUlf = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        storage.findAndDo(findUlf,consumeUlf);

        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message){
        System.out.println(message);
        Predicate<Person> findLastNameContainFirstNamePredicate = person -> person.getLastName().startsWith(person.getFirstName());
        Consumer<Person> findLastNameContainFirstNameConsumer = person -> System.out.println(person.getFirstName()
                +" "+person.getLastName());
        storage.findAndDo(findLastNameContainFirstNamePredicate, findLastNameContainFirstNameConsumer);

        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message){
        System.out.println(message);
        Predicate<Person> personFirstNameIsPalindrome = person -> new StringBuilder(person.getFirstName())
                .reverse().toString().equalsIgnoreCase(person.getFirstName());
        Consumer<Person> printer = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        storage.findAndDo(personFirstNameIsPalindrome, printer);

        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message){
        System.out.println(message);
        Predicate<Person> findPersonFirstNameA = person -> person.getFirstName().startsWith("A");
        Comparator<Person> findPersonComparator = Comparator.comparing(Person::getBirthDate);
        List<Person> findAndSort_findPersonFirstNameASortedBirthdate = storage.findAndSort(findPersonFirstNameA, findPersonComparator);
        findAndSort_findPersonFirstNameASortedBirthdate.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message){
        System.out.println(message);
        Predicate<Person> findPersonBefore1950 = person -> person.getBirthDate().isBefore(LocalDate.parse("1950-01-01"));
        Comparator<Person> findPersonComparator = Comparator.comparing(Person::getBirthDate).reversed();
        List<Person> findAndSort_findPersonFirstNameASortedBirthdate = storage.findAndSort(findPersonBefore1950, findPersonComparator);
        findAndSort_findPersonFirstNameASortedBirthdate.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message){
        System.out.println(message);
        Comparator<Person> findAllPersonAndSortComparator = Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName).thenComparing(Person::getBirthDate);
        List<Person> Sort_FindAllPersons = storage.findAndSort(findAllPersonAndSortComparator);
        Sort_FindAllPersons.forEach(System.out::println);

        System.out.println("----------------------");
    }
}
