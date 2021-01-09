package solution.testing.example.records.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.util.Objects;

public class ResultEntry implements Serializable {
    private Integer field;

    public Integer getField() {
        return field;
    }

    @XmlAttribute(name = "field")
    public void setField(Integer field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "ResultEntry{" +
               "field=" + field +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultEntry that = (ResultEntry) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }
}
