package ua.kiev.prog.omihal.homework.task_3_1_2;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface SaveTo
{
    String path();
}
