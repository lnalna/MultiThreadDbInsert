package main.java.solution.testing.example.records.pojo;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Objects;

public class Entry implements Serializable {
    private Integer field;

    public Integer getField() {
        return field;
    }

    @XmlElement(name = "field")
    public void setField(Integer field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "Entry{" +
               "field=" + field +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(field, entry.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }
}
