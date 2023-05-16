package eapli.base.coursemanagement.domain;
import eapli.framework.domain.model.AggregateRoot;

public class Course implements AggregateRoot<Long> {

    private Name name;
    private Capacity capacity;
    private Description description;
    private Title title;
    private Code code;

    public Course(Name name, Capacity capacity, Description description, Title title, Code code) {
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.title = title;
        this.code = code;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public Code getCode() {
        return code;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Title getTitle() {
        return title;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public Long identity() {
        return null;
    }
}
