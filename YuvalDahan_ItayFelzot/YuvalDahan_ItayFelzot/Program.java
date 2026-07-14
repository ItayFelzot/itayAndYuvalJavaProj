package YuvalDahan_ItayFelzot;

/*
 Submitters:
 1. Full Name: Itay Felzot  ID: 331017764
 2. Full Name: Yuval Dahan  ID: 331694596
*/
import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    private static final String DATA_FILE = "college.dat";

    public static void main(String[] args) {
        System.out.println("Submitters:\n1. Itay Felzot - ID: 331017764\n2. Yuval Dahan - ID: 331694596");
        Scanner s = new Scanner(System.in);

        College c = loadCollege(s);
        String collegeName = c.getName();

        int input;
        do {
            printMenu(collegeName);
            input = readInt(s);
            s.nextLine();
            System.out.println("");

            switch (input) {
                case 1:
                    addLecturer(s, c);
                    break;
                case 2:
                    addCommittee(s, c);
                    break;
                case 3:
                    addLecturerToCommittee(s, c);
                    break;
                case 4:
                    changeHeadOfCommittee(s, c);
                    break;
                case 5:
                    removeLecturerFromCommittee(s, c);
                    break;
                case 6:
                    addDepartment(s, c);
                    break;
                case 7:
                    addLecturerToDepartment(s, c);
                    break;
                case 8:
                    System.out.println("Average salary of lecturers in the college: " + c.showAverageSalaryOfLecturersInCollege());
                    break;
                case 9:
                    showDepartmentAverage(s, c);
                    break;
                case 10:
                    System.out.println("Information of all lecturers in the college: " + c.showInformationOfAllLecturersInCollege());
                    break;
                case 11:
                    System.out.println("Information of all committees in the college: " + c.showInformationOfAllCommitteesInCollege());
                    break;
                case 12:
                    addArticle(s, c);
                    break;
                case 13:
                    compareArticleWriters(s, c);
                    break;
                case 14:
                    compareCommittees(s, c);
                    break;
                case 15:
                    cloneCommittee(s, c);
                    break;
                case 0:
                    System.out.println("Exiting the college menu.");
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number from 0 to 15.");
                    break;
            }
            System.out.println("");
        } while (input != 0);

        try {
            c.saveToFile(DATA_FILE);
            System.out.println("College data saved successfully.");
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }

        s.close();
    }

    private static College loadCollege(Scanner s) {
        try {
            College loadedCollege = College.loadFromFile(DATA_FILE);
            if (loadedCollege != null) {
                System.out.println("College data loaded successfully.");
                return loadedCollege;
            }
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Enter the name of the college: ");
        String collegeName = s.nextLine();
        return new College(collegeName);
    }

    private static void printMenu(String collegeName) {
        System.out.println("Please choose an option to perform in the college " + collegeName + ": ");
        System.out.println("0. Exit");
        System.out.println("1. Add a lecturer to the college");
        System.out.println("2. Add a committee to the college, and assign a head of committee to it");
        System.out.println("3. Add a lecturer to a committee");
        System.out.println("4. Change the head of a committee");
        System.out.println("5. Remove a lecturer from a committee");
        System.out.println("6. Add a department to the college");
        System.out.println("7. Add a lecturer to a department");
        System.out.println("8. Show the average salary of the lecturers in the college");
        System.out.println("9. Show the average salary of the lecturers in a department");
        System.out.println("10. Show the information of all the lecturers in the college");
        System.out.println("11. Show the information of all the committees in the college");
        System.out.println("12. Add an article to a lecturer");
        System.out.println("13. Compare doctors or professors by article amount");
        System.out.println("14. Compare committees");
        System.out.println("15. Clone a committee");
    }

    private static int readInt(Scanner s) {
        while (true) {
            try {
                return s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                s.nextLine();
            }
        }
    }

    private static float readFloat(Scanner s) {
        while (true) {
            try {
                return s.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                s.nextLine();
            }
        }
    }

    private static void addLecturer(Scanner s, College c) {
        System.out.println("Enter the name: ");
        String name = s.nextLine();
        System.out.println("Enter the degree (1 for First, 2 for Second, 3 for Doctor, 4 for Professor): ");
        int degreeInput = readInt(s);
        s.nextLine();
        System.out.println("Enter the degree name: ");
        String degreeName = s.nextLine();
        System.out.println("Enter the salary: ");
        float salary = readFloat(s);
        s.nextLine();
        String university = "";
        if (degreeInput == 4) {
            System.out.println("Enter the professorship granting university: ");
            university = s.nextLine();
        }

        boolean finished = false;
        while (finished == false) {
            System.out.println("Enter the id: ");
            String id = s.nextLine();
            try {
                c.inputLecturer(name, id, degreeInput, degreeName, salary, university);
                System.out.println("Lecturer added successfully.");
                finished = true;
            } catch (InvalidIdException e) {
                System.out.println(e.getMessage());
            } catch (CollegeException e) {
                System.out.println(e.getMessage());
                finished = true;
            }
        }
    }

    private static void addCommittee(Scanner s, College c) {
        String committeeName = "";
        String headName = "";
        int memberType = 0;
        try {
            System.out.println("Enter the name of the committee: ");
            committeeName = s.nextLine();
            System.out.println("Enter the name of the head of the committee: ");
            headName = s.nextLine();
            System.out.println("Enter committee member type (1 for First degree, 2 for Second degree, 3 for Doctor, 4 for Professor): ");
            memberType = readInt(s);
            s.nextLine();
            c.inputCommittee(committeeName, headName, memberType);
            System.out.println("Committee added successfully.");
        } catch (MultipleLecturersWithSameNameException e) {
            try {
                System.out.println(e.getMessage());
                String headId = s.nextLine();
                c.inputCommittee(committeeName, headName, headId, memberType);
                System.out.println("Committee added successfully.");
            } catch (CollegeException secondException) {
                System.out.println(secondException.getMessage());
            }
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addLecturerToCommittee(Scanner s, College c) {
        String committeeName = "";
        try {
            System.out.println("Enter the name of the lecturer: ");
            String lecturerName = s.nextLine();
            System.out.println("Enter the name of the committee: ");
            committeeName = s.nextLine();
            c.addLecturerToCommittee(lecturerName, committeeName);
            System.out.println("Lecturer added to committee successfully.");
        } catch (MultipleLecturersWithSameNameException e) {
            try {
                System.out.println(e.getMessage());
                String lecturerId = s.nextLine();
                c.addLecturerToCommittee(e.getLecturerName(), lecturerId, committeeName);
                System.out.println("Lecturer added to committee successfully.");
            } catch (CollegeException secondException) {
                System.out.println(secondException.getMessage());
            }
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void changeHeadOfCommittee(Scanner s, College c) {
        String committeeName = "";
        try {
            System.out.println("Enter the name of the committee: ");
            committeeName = s.nextLine();
            System.out.println("Enter the name of the new head of the committee: ");
            String lecturerName = s.nextLine();
            c.changeHeadOfCommittee(committeeName, lecturerName);
            System.out.println("Head of committee changed successfully.");
        } catch (MultipleLecturersWithSameNameException e) {
            try {
                System.out.println(e.getMessage());
                String lecturerId = s.nextLine();
                c.changeHeadOfCommittee(committeeName, e.getLecturerName(), lecturerId);
                System.out.println("Head of committee changed successfully.");
            } catch (CollegeException secondException) {
                System.out.println(secondException.getMessage());
            }
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeLecturerFromCommittee(Scanner s, College c) {
        String committeeName = "";
        try {
            System.out.println("Enter the name of the committee: ");
            committeeName = s.nextLine();
            System.out.println("Enter the name of the lecturer: ");
            String lecturerName = s.nextLine();
            c.removeLecturerFromCommittee(committeeName, lecturerName);
            System.out.println("Lecturer removed from committee successfully.");
        } catch (MultipleLecturersWithSameNameException e) {
            try {
                System.out.println(e.getMessage());
                String lecturerId = s.nextLine();
                c.removeLecturerFromCommittee(committeeName, e.getLecturerName(), lecturerId);
                System.out.println("Lecturer removed from committee successfully.");
            } catch (CollegeException secondException) {
                System.out.println(secondException.getMessage());
            }
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addDepartment(Scanner s, College c) {
        boolean finished = false;
        while (finished == false) {
            try {
                System.out.println("Enter the name of the department: ");
                String departmentName = s.nextLine();
                c.inputDepartment(departmentName);
                System.out.println("Department added successfully.");
                finished = true;
            } catch (CollegeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void addLecturerToDepartment(Scanner s, College c) {
        System.out.println("Enter the name of the lecturer: ");
        String lecturerName = s.nextLine();
        String departmentName = "";
        boolean finished = false;
        while (finished == false) {
            try {
                System.out.println("Enter the name of the department: ");
                departmentName = s.nextLine();
                c.addLecturerToDepartment(lecturerName, departmentName);
                System.out.println("Lecturer added to department successfully.");
                finished = true;
            } catch (MultipleLecturersWithSameNameException e) {
                try {
                    System.out.println(e.getMessage());
                    String lecturerId = s.nextLine();
                    c.addLecturerToDepartment(e.getLecturerName(), lecturerId, departmentName);
                    System.out.println("Lecturer added to department successfully.");
                    finished = true;
                } catch (DepartmentNotFoundException secondException) {
                    System.out.println(secondException.getMessage());
                } catch (CollegeException secondException) {
                    System.out.println(secondException.getMessage());
                    finished = true;
                }
            } catch (DepartmentNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (CollegeException e) {
                System.out.println(e.getMessage());
                finished = true;
            }
        }
    }

    private static void showDepartmentAverage(Scanner s, College c) {
        boolean finished = false;
        while (finished == false) {
            try {
                System.out.println("Enter the name of the department: ");
                String departmentName = s.nextLine();
                System.out.println("Average salary of lecturers in the department: " + c.showAverageSalaryOfLecturersInDepartment(departmentName));
                finished = true;
            } catch (DepartmentNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (CollegeException e) {
                System.out.println(e.getMessage());
                finished = true;
            }
        }
    }

    private static void addArticle(Scanner s, College c) {
        String articleName = "";
        try {
            System.out.println("Enter the name of the lecturer: ");
            String lecturerName = s.nextLine();
            System.out.println("Enter the name of the article: ");
            articleName = s.nextLine();
            c.addArticleToLecturer(lecturerName, articleName);
            System.out.println("Article added to lecturer successfully.");
        } catch (MultipleLecturersWithSameNameException e) {
            try {
                System.out.println(e.getMessage());
                String lecturerId = s.nextLine();
                c.addArticleToLecturer(e.getLecturerName(), lecturerId, articleName);
                System.out.println("Article added to lecturer successfully.");
            } catch (CollegeException secondException) {
                System.out.println(secondException.getMessage());
            }
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void compareArticleWriters(Scanner s, College c) {
        System.out.println("Enter the name of the first doctor or professor: ");
        String firstName = s.nextLine();
        System.out.println("Enter the name of the second doctor or professor: ");
        String secondName = s.nextLine();
        String firstId = "";
        String secondId = "";
        boolean finished = false;
        while (finished == false) {
            try {
                System.out.println(c.compareArticleWriters(firstName, firstId, secondName, secondId));
                finished = true;
            } catch (MultipleLecturersWithSameNameException e) {
                System.out.println(e.getMessage());
                String id = s.nextLine();
                if (e.getLecturerName().equals(firstName)) {
                    firstId = id;
                } else {
                    secondId = id;
                }
            } catch (CollegeException e) {
                System.out.println(e.getMessage());
                finished = true;
            }
        }
    }

    private static void compareCommittees(Scanner s, College c) {
        int compareType = chooseCommitteeCompareType(s);
        if (compareType == 0) {
            return;
        }
        try {
            System.out.println("Enter the name of the first committee: ");
            String firstName = s.nextLine();
            System.out.println("Enter the name of the second committee: ");
            String secondName = s.nextLine();
            System.out.println(c.compareCommittees(firstName, secondName, compareType));
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int chooseCommitteeCompareType(Scanner s) {
        while (true) {
            try {
                System.out.println("Choose comparison type:");
                System.out.println("1. Compare by number of lecturers");
                System.out.println("2. Compare by total number of articles");
                System.out.println("0. Cancel");
                int compareType = s.nextInt();
                s.nextLine();
                if (compareType == 0 || compareType == 1 || compareType == 2) {
                    return compareType;
                }
                throw new InvalidCompareTypeException();
            } catch (InputMismatchException e) {
                System.out.println("Invalid comparison type.");
                s.nextLine();
            } catch (InvalidCompareTypeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void cloneCommittee(Scanner s, College c) {
        try {
            System.out.println("Enter the name of the committee to clone: ");
            String committeeName = s.nextLine();
            c.cloneCommitteeByName(committeeName);
            System.out.println("Committee cloned successfully.");
        } catch (CollegeException e) {
            System.out.println(e.getMessage());
        }
    }
}



