package model;

import Exceptions.TermNotFoundException;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import visualization.CourseNode;

import java.util.*;

public class GeneralManager {

    private ArrayList<Term> terms;
    private HashMap<String, List<Course>> neededCourses = new HashMap<>();

    static final String BORDER = "------------";
    static final String INSTRUCTIONS = "1) view all your courses \n" +
            "2) add a term \n" +
            "3) remove a term \n" +
            "4) add a course \n" +
            "5) remove a course \n" +
            "6) quit";
//    private CourseManager cm;
    Scanner scanner = new Scanner(System. in);

    public GeneralManager() {
        terms = new ArrayList<>();
    }

    public void runProgram() {

        System.out.println("Welcome to Courseboard: a virtual course planner");

        boolean isRunning = true;
        while (isRunning) {
            System.out.println(BORDER);
            System.out.println(INSTRUCTIONS);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    printAll();
                    break;
                case "2":
                    addTerm();
                    break;
                case "3":
                    removeTerm();
                case "4":
                    addCourseOld();
                    break;
                case "5":
                    removeCourse();
                    break;
                case "6":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid Input");
            }
        }
        System.out.println("Exited Program");
        System.out.println("Hope to see you again!");
    }

    private void addTerm() {
        System.out.println("Term Name");
        Term t = new Term(scanner.nextLine());
        terms.add(t);
    }

    private void removeTerm() {
        System.out.println("Term Name to remove: ");
        terms.remove(findTerm(scanner.nextLine()));
    }

    public void addCourseOld() {
        try {
            System.out.println(BORDER);
            Term t = getTerm();

            System.out.println("Course Code: ");
            String cc = scanner.nextLine();
            System.out.println("Description: ");
            String d = scanner.nextLine();
            System.out.println("Credits: ");
            int credits = Integer.parseInt(scanner.nextLine());
            System.out.println("Pre-requisites (on one line separated with spaces): ");

            String pInput = scanner.nextLine();
            LinkedList<String> prereqs = new LinkedList<>();
            String[] pCourses= pInput.split(" ");
            Collections.addAll(prereqs, pCourses);

            System.out.println("Co-requisites (again, on one line separated with spaces): ");
            String cInput = scanner.nextLine();
            LinkedList<String> coreqs = new LinkedList<>();
            String[] cCourses = cInput.split(" ");
            Collections.addAll(coreqs, cCourses);

            if (t.addCourse(new Course(cc, d, credits, prereqs, coreqs))) {
                System.out.println(cc + " has been added");
            } else {
                System.out.println(cc + " already exists");
            }

        } catch (Exception e) {
            System.out.println("input error occurred");
        }
    }

    private Term getTerm() throws TermNotFoundException {
        System.out.println("Term to add Course to: ");
        Term t = findTerm(scanner.nextLine());

        if (t == null) {
            System.out.println("Term name not valid");
            throw new TermNotFoundException();
        }
        return t;
    }

    public void removeCourse() {
        System.out.println(BORDER);
        Term t = null;
        try {
            t = getTerm();
        } catch (TermNotFoundException e) {
            System.out.println("input error occurred");
        }
        System.out.println("Removing Course");
        System.out.println("Course Code: ");
        String code = scanner.nextLine();
        if (t.removeCourse(t.findCourse(code))) {
            System.out.println(code + " has been removed");
        } else {
            System.out.println(code + " does not exist");
        }
    }
    public void printAll() {
        for (Term t: terms) {
            System.out.println(t.printCourses());
        }
    }

    public Term findTerm(String name) {
        for (Term t : terms) {
            if (t.getName().equals(name)) return t;
        }
        return null;
    }

//    todo make this also affect course list
//    todo is it better to have two sets: one where the course info is stored in the courseNode, and one set where
//    the course manager has a set of courses
//    OK so each term has a set of courses.
//    so like component, branch and leaf
//    courseNode is the leaf, and term is the branch
    public boolean addCourse(Pane pane, MouseEvent event) {
        Course newCourse = new Course("", "", 0, null, null);
        CourseNode cn = new CourseNode(newCourse);
        cn.relocate(event.getX(), event.getY());
        pane.getChildren().add(cn);
        return true;
    }

//    need to order the terms in a solid way
//    need some way to add stuff in order
//    public List<Term> getInvalidCourses() {
//        for (Course c : neededCourses) {
//            System.out.println(c);
//        }
//    }

}
