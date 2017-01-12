package ua.kiev.prog.omihal.homework.task_3_1_3;


import java.io.Serializable;

public class MyClass implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Save
    private String strValue;

    @Save
    private int index;

    private int couner; // exclude from serialization


    public void setStrValue(String strValue)
    {
        this.strValue = strValue;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setCouner(int couner)
    {
        this.couner = couner;
    }

    public String getStrValue()
    {
        return strValue;
    }

    public int getIndex()
    {
        return index;
    }

    public int getCouner()
    {
        return couner;
    }

    @Override
    public String toString()
    {
        return strValue + ", " + index + ", " + couner;
    }
}
