package ua.kiev.prog.omihal.homework.task_3_1_3;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Saver
{
    static private final String classNode = "class";


    public static void write(Object obj, String path) throws Exception
    {
        try(OutputStream os = new FileOutputStream(path))
        {
            String data = serialize(obj);
            os.write(data.getBytes());
        }
    }

    public static Object read(String path) throws Exception
    {
        Object obj;
        try(InputStream is = new FileInputStream(path))
        {
            byte[] buffer = new byte[is.available()];
            if (is.read(buffer) != buffer.length)
                throw new IOException("Reading error");

            String data = new String(buffer);
            obj = deserialize(data);
        }
        return obj;
    }
    private static String serialize(Object obj) throws Exception
    {
        StringBuilder sb = new StringBuilder();

        Class<?> cls = obj.getClass();
        sb.append(classNode);
        sb.append("=");
        sb.append(cls.getName());

        Field[] fields = cls.getDeclaredFields();
        for (Field fld : fields)
        {
            if (fld.isAnnotationPresent(Save.class))
            {
                sb.append(";");

                if (Modifier.isPrivate(fld.getModifiers()))
                    fld.setAccessible(true);

                Object value = fld.get(obj);
                sb.append(fld.getName());
                sb.append("=");
                sb.append(value.toString());
            }
        }
        return sb.toString();
    }
    private static Object deserialize(String data) throws Exception
    {
        Object obj = null;
        Class<?> cls = null;

        String[] pairs = data.split(";");

        for (int i = 0; i < pairs.length; i++)
        {
            String[] vals = pairs[i].split("=");
            if (vals.length != 2)
                throw new IOException("Invalid data: " + pairs[i]);

            if (i == 0) // create object
            {
                if (!vals[0].equals(classNode))
                    throw new IOException("Invalid class node: " + pairs[0]);

                cls = Class.forName(vals[1]);
                Constructor<?> ctr = cls.getConstructor();
                obj = ctr.newInstance();
            }
            else
            {
                Field fld = cls.getDeclaredField(vals[0]);
                if (fld == null )
                    throw new IOException("Invalid field name: " + vals[0]);

                if (fld.isAnnotationPresent(Save.class))
                {
                    if (Modifier.isPrivate(fld.getModifiers()))
                        fld.setAccessible(true);

                    Object value = strToObj(fld.getType(), vals[1]);
                    fld.set(obj, value);
                }
            }
        }

        return obj;
    }

    private static Object strToObj(Class<?> cls, String value) throws IOException
    {
        if (cls == String.class)
        {
            return value;
        }
        else if (cls == int.class)
        {
            return Integer.parseInt(value);
        }
        else if (cls == long.class)
        {
            return Long.parseLong(value);
        }
        else
        {
            throw new IOException("Unsupported field type: " + cls.getName());
        }
    }
}
