package ua.kiev.prog.omihal.homework.task_3_1_3;


import java.io.Serializable;

public class MyClass implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Save
    private String strValue;

    @Save
    private int index;

    private int counter; // exclude from serialization


    public void setStrValue(String strValue)
    {
        this.strValue = strValue;
    }

    public String getStrValue()
    {
        return strValue;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }
    public int getCounter()
    {
        return counter;
    }

    @Override
    public String toString()
    {
        return strValue + ", " + index + ", " + counter;
    }
}
