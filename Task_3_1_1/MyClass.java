package ua.kiev.prog.omihal.homework.task_3_1_1;

public class MyClass
{
    // static method example
    @Test(a = 2, b = 5)
    public static void Test1(int a, int b)
    {
        System.out.println("Method result = " + (a + b));
    }

    // non-static method example
    @Test(a = 20, b = 50)
    public void Test2(int a, int b)
    {
        Test1(a, b);
    }

}
