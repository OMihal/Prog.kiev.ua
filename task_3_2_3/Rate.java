package ua.kiev.prog.omihal.homework.task_3_2_3;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rate")
public class Rate {
    @XmlAttribute
    private String id;
    @XmlElement
    private String Name;
    @XmlElement
    private Double Rate;
    @XmlElement
    private String Date;
    @XmlElement
    private String Time;
    @XmlElement
    private String Ask;
    @XmlElement
    private String Bid;

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public Double getRate() {
        return Rate;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getAsk() {
        return Ask;
    }

    public String getBid() {
        return Bid;
    }
}
