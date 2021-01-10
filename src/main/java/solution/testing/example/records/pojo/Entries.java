package main.java.solution.testing.example.records.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "entries")
public class Entries implements Serializable {
    private List<Entry> entries;

    public List<Entry> getEntries() {
        return entries;
    }

    @XmlElement(name = "entry")
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
