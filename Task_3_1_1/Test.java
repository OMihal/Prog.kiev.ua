package ua.kiev.prog.omihal.homework.task_3_1_1;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Test
{
    int a();
    int b();
}
