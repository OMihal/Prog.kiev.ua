package ua.kiev.prog.omihal.homework.task_3_1_3;

import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            final String path = "d:\\MyClass_3_1_3.dat";

            MyClass myClass1 = new MyClass();
            myClass1.setStrValue("TestStringValue");
            myClass1.setIndex(3);
            myClass1.setCounter(10);

            System.out.println("Before serialization : " + myClass1.toString());

            // write
            Saver.write(myClass1, path);

            // read
            MyClass myClass2 = (MyClass)Saver.read(path);

            System.out.println("After deserialization: " + myClass2.toString());

            if (myClass2.getCounter() != 0)// counter must be zero
            {
                System.out.println("Test failed");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
