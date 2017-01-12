package ua.kiev.prog.omihal.homework.task_3_1_3;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Saver
{
    public static void write(Object obj, String path) throws Exception
    {
        try (OutputStream os = new FileOutputStream(path))
        {
            ObjectOutputStream oos = new ObjectOutputStream(os);

            Class<?> cls = obj.getClass();
            oos.writeObject(cls);

            Field[] fields = cls.getDeclaredFields();
            for (Field fld : fields)
            {
                if (fld.isAnnotationPresent(Save.class))
                {
                    fld.setAccessible(true);
                    Object value = fld.get(obj);
                    oos.writeObject(value);
                }
            }
        }
    }
    public static Object read(String path) throws Exception
    {
        Object obj;

        try (InputStream is = new FileInputStream(path))
        {
            ObjectInputStream ois = new ObjectInputStream(is);
            Class<?> cls = (Class<?>)ois.readObject();

            Constructor<?> ctr = cls.getConstructor();
            obj = ctr.newInstance();

            Field[] fields = cls.getDeclaredFields();
            for (Field fld : fields)
            {
                if (fld.isAnnotationPresent(Save.class))
                {
                    fld.setAccessible(true);
                    Object value = ois.readObject();
                    fld.set(obj, value);
                }
            }
        }
        return obj;
    }
}
