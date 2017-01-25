package ua.kiev.prog.omihal.homework.task_3_2_3;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="query")
public class Query {
    @XmlElement
    private Results results;

    public Results getResults() {
        return results;
    }
}
