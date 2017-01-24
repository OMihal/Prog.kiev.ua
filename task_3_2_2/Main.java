package ua.kiev.prog.omihal.homework.task_3_2_2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main
{
    static final String jsn = "{" +
        "\"name\": \"Ivan\"," +
        "\"surname\": \"Petrenko\"," +
        "\"phones\": [\"044-256-78-90\",\"066-123-45-67\"]," +
        "\"sites\": [\"http://site1.com\", \"http://site2.com\"]," +
        "\"address\": {" +
        "\"country\": \"Ukraine\"," +
        "\"city\": \"Kyiv\"," +
        "\"street\": \"Bazhana ave.\"" +
        "}" +
        "}";

    public static void main(String[] args)
    {
        try
        {
            Gson json = new GsonBuilder().create();
            Person person = (Person)json.fromJson(jsn, Person.class);

            System.out.println(String.format("Person: %s %s",
                person.getName(), person.getSurname()));

            System.out.println("Phones: ");
            for (String phone : person.getPhones()){
                System.out.println(phone);
            }

            System.out.println("Sites: ");
            for (String site : person.getSites()){
                System.out.println(site);
            }

            System.out.println("Address: " + person.getAddress());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
