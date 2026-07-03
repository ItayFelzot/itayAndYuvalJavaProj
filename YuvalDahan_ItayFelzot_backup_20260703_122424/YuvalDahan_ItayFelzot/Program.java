package YuvalDahan_ItayFelzot;

/*
 Submitters:
 1. Full Name: Itay Felzot  ID: 331017764
 2. Full Name: Yuval Dahan  ID: 331694596
*/
import java.util.Scanner;
public class Program {
    public static void main(String[] args) {
        System.out.println("Submitters:\n1. Itay Felzot - ID: 331017764\n2. Yuval Dahan - ID: 331694596");
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the name of the college: ");
        String collegeName = s.nextLine();
        College c = new College(collegeName);

        int input;
        do {
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
            System.out.println("12. Add a article to a lecturer");
            input = s.nextInt();
            s.nextLine();
            System.out.println("");

            switch (input) {
                case 1: {
                    System.out.println("Enter the name: ");
                    String name = s.nextLine();
                    System.out.println("Enter the degree (1 for First, 2 for Second, 3 for Doctor, 4 for Professor): ");
                    int degreeInput = s.nextInt();
                    s.nextLine();
                    System.out.println("Enter the degree name: ");
                    String degreeName = s.nextLine();
                    System.out.println("Enter the salary: ");
                    float salary = s.nextFloat();
                    s.nextLine();
                    do {
                    System.out.println("Enter the id: ");
                    String id = s.nextLine();
                    boolean added = c.inputLecturer(name, id, degreeInput, degreeName, salary);
                    if (added)
                        System.out.println("Lecturer added successfully.");
                    else
                        System.out.println("Invalid input or lecturer already exists. Lecturer not added.");
                    break;
                    } while (false);
                    break;
                }
                case 2: {
                    System.out.println("Enter the name of the committee: ");
                    String committeeName = s.nextLine();
                    System.out.println("Enter the name of the head of the committee: ");
                    String headName = s.nextLine();
                    int result = c.inputComittee(committeeName, headName);
                    if (result == 0)
                        System.out.println("Committee already exists in the college. Committee not added.");
                    else if (result == 1)
                        System.out.println("Head of committee is not valid. Committee not added.");
                    else if (result == 2)
                        System.out.println("Committee added successfully.");
                    break;
                }
                case 3: {
                    System.out.println("Enter the name of the lecturer: ");
                    String lecturerName = s.nextLine();
                    System.out.println("Enter the name of the committee: ");
                    String committeeName = s.nextLine();
                    int result = c.ltoC(lecturerName, committeeName);
                    if (result == 0)
                        System.out.println("Committee not found in the college.");
                    else if (result == 1)
                        System.out.println("Lecturer not found in the college.");
                    else if (result == 2)
                        System.out.println("Lecturer added to committee successfully.");
                    else if (result == 3)
                        System.out.println("Lecturer is already in the committee or is the head of the committee.");
                    break;
                }
                case 4: {
                    System.out.println("Enter the name of the committee: ");
                    String committeeName = s.nextLine();
                    System.out.println("Enter the name of the new head of the committee: ");
                    String lecturerName = s.nextLine();
                    int result = c.changeHeadOfComittee(committeeName, lecturerName);
                    if (result == 0)
                        System.out.println("Committee not found in the college.");
                    else if (result == 1)
                        System.out.println("Lecturer not found in the college.");
                    else if (result == 2)
                        System.out.println("Head of committee changed successfully.");
                    else if (result == 3)
                        System.out.println("Lecturer does not have the required degree to be head of committee.");
                    break;
                }
                case 5: {
                    System.out.println("Enter the name of the committee: ");
                    String committeeName = s.nextLine();
                    System.out.println("Enter the name of the lecturer: ");
                    String lecturerName = s.nextLine();
                    int result = c.removeLecturerFromComittee(committeeName, lecturerName);
                    if (result == 0)
                        System.out.println("Committee not found in the college.");
                    else if (result == 1)
                        System.out.println("Lecturer not found in the college.");
                    else if (result == 2)
                        System.out.println("Lecturer removed from committee successfully.");
                    else if (result == -1)
                        System.out.println("Lecturer is not in the committee.");
                    break;
                }
                case 6: {
                    do {
                    System.out.println("Enter the name of the department: ");
                    String departmentName = s.nextLine();
                    boolean added = c.inputDepartment(departmentName);
                    if (added)
                        System.out.println("Department added successfully.");
                    else
                        System.out.println("Department already exists in the college.");
                    break;
                    } while (false);
                    break;
                }
                case 7: {
                    System.out.println("Enter the name of the lecturer: ");
                    String lecturerName = s.nextLine();
                    System.out.println("Enter the name of the department: ");
                    String departmentName = s.nextLine();
                    int result = c.addLecturerToDepartment(lecturerName, departmentName);
                    if (result == 0)
                        System.out.println("Department not found in the college or lecturer already in the department.");
                    else if (result == 1)
                        System.out.println("Lecturer not found in the college.");
                    else if (result == 2)
                        System.out.println("Lecturer added to department successfully.");
                    break;
                }
                case 8: {
                    System.out.println("Average salary of lecturers in the college: " + c.showAverageSalaryOfLecturersInCollege());
                    break;
                }
                case 9: {
                    System.out.println("Enter the name of the department: ");
                    String departmentName = s.nextLine();
                    float sal = c.showAverageSalaryOfLecturersInDepartment(departmentName);
                    if (sal == -1)
                        System.out.println("Department not found in the college.");
                    else
                        System.out.println("Average salary of lecturers in the department: " + sal);
                    break;
                }
                case 10: {
                    System.out.println("Information of all lecturers in the college: " + c.showInformationOfAllLecturersInCollege());
                    break;
                }
                case 11: {
                    System.out.println("Information of all committees in the college: " + c.showInformationOfAllComitteesInCollege());
                    break;
                }
                case 12: {
                    System.out.println("Enter the name of the lecturer: ");
                    String lecturerName = s.nextLine();
                    System.out.println("Enter the name of the article: ");
                    String articleName = s.nextLine();
                    int result = c.addArticleToLecturer(lecturerName, articleName);
                    if (result == 0)
                        System.out.println("Lecturer not found in the college.");
                    else if (result == 1)
                        System.out.println("Article already exists for the lecturer.");
                    else
                        System.out.println("Article added to lecturer successfully.");
                }
                case 0:
                    System.out.println("Exiting the college menu.");
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number from 0 to 11.");
                    break;
            }
            System.out.println("");
        } while (input != 0);

        s.close();
    }
}