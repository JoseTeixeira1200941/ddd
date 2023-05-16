
package eapli.base.app.backoffice.console.presentation.Course;

import eapli.framework.actions.Action;

public class ListCourseAction implements Action {

    @Override
    public boolean execute() {
        return new ShowAllCoursesUI().show();
    }
}
