package eapli.base.app.backoffice.console.presentation.Course;

import eapli.base.coursemanagement.eventhandler.CreateCourseController;

public class ShowAllCoursesUI {

    CreateCourseController theController = new CreateCourseController();

    public boolean  show() {
        System.out.println("Courses:");
        theController.listCourses().forEach(System.out::println);
        return true;
    }
}
