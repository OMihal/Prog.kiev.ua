package ua.kiev.prog.omihal.homework.task_3_2_3;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="results")
public class Results {
    @XmlElement
    private Rate[] rate;

    public Rate[] getRate() {
        return rate;
    }
}
