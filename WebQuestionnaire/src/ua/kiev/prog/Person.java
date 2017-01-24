package ua.kiev.prog;

public class Person {
    private String firstName;
    private String lastName;
    private int age;


    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj != null) && (obj.getClass() == Person.class)) {
            Person p = (Person) obj;
            return ((p.age == age) &&
                    (p.firstName.equalsIgnoreCase(firstName)) &&
                    (p.lastName.equalsIgnoreCase(lastName)));
        } else {
            return false;
        }
    }
}
