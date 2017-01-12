package ua.kiev.prog.omihal.homework.task_3_1_2;

import java.lang.reflect.Method;

public class SaveProcessor
{
    public static void save(Object obj) throws Exception
    {
        Class<?> cls = obj.getClass();
        System.out.println("Class name = " + cls.getName());

        if (!cls.isAnnotationPresent(SaveTo.class))
        {
            System.err.println("Annotation is absent");
            return;
        }
        System.out.println("Annotation present");
        SaveTo saveTo = cls.getAnnotation(SaveTo.class);
        String path = saveTo.path();
        System.out.println("Path = " + path);

        Method[] methods = cls.getMethods();
        for (Method method : methods)
        {
            if (method.isAnnotationPresent(Saver.class))
            {
                System.out.println("Method founded");
                method.invoke(obj, path);
            }
        }
    }
}
