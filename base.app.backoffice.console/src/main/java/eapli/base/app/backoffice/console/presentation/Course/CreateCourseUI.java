package eapli.base.app.backoffice.console.presentation.Course;

import eapli.base.coursemanagement.eventhandler.CreateCourseController;
import eapli.base.coursemanagement.domain.*;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;
import eapli.framework.io.util.Console;


public class CreateCourseUI extends AbstractListUI<Course> {

    private final CreateCourseController controller;

    public CreateCourseUI() {
        this.controller = new CreateCourseController();
    }

    @Override
    protected Iterable<Course> elements() {
        return controller.listCourses();
    }

    @Override
    protected String elementName() {
        return "course";
    }

    @Override
    protected String listHeader() {
        return "List of courses:";
    }

    @Override
    protected String emptyMessage() {
        return "No courses available";
    }

    @Override
    protected boolean doShow() {
        return true;
    }

    @Override
    public String headline() {
        return "Create a new course";
    }

    public void showContent() {
        final String    name = Console.readLine("Enter the course name: ");
        final String    capacity = Console.readLine("Enter the course capacity: ");
        final String    description = Console.readLine("Enter the course description: ");
        final String    title = Console.readLine("Enter the course title: ");	
        final String    code = Console.readLine("Enter the course code: ");
        //controller.createCourse(new Name(name), new Capacity(Integer.parseInt(capacity)), new Description(description), new Title(title), new Code(code), State.ACTIVE);
        System.out.println("Course created successfully.");
    }

    @Override
    protected Visitor<Course> elementPrinter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'elementPrinter'");
    }
}
