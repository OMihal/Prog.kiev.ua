package ua.kiev.prog.omihal.homework.task_3_1_2;

import java.io.FileWriter;
import java.io.IOException;

@SaveTo(path = "d:\\file.txt")
public class TextContainer
{
    private String value;

    public TextContainer(String value)
    {
        this.value = value;
    }

    @Saver
    public void save(String path) throws IOException
    {
        try (FileWriter fw = new FileWriter(path))
        {
            fw.write(value);
        }
    }
}
