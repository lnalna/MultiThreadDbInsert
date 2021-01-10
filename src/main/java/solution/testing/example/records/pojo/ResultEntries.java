package main.java.solution.testing.example.records.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "entries")
public class ResultEntries implements Serializable {
    private List<ResultEntry> entries;

    public List<ResultEntry> getEntries() {
        return entries;
    }

    @XmlElement(name = "entry")
    public void setEntries(List<ResultEntry> entries) {
        this.entries = entries;
    }
}
