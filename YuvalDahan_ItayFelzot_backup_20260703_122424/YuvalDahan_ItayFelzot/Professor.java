package YuvalDahan_ItayFelzot;

public class Professor extends ArticleWriter {
    private String[] articleNames = new String[1];
    private int amountOfArticles = 0;
    private String professorshipGrantingUniversity;
    public Professor(String name, String id, Degree degree, String degreeName, Float salary, String professorshipGrantingUniversity) {
        super(name, id, degree, degreeName, salary);
        setProfessorshipGrantingUniversity(professorshipGrantingUniversity);


    }
    public void setProfessorshipGrantingUniversity(String professorshipGrantingUniversity) {
        this.professorshipGrantingUniversity = professorshipGrantingUniversity;
    }
    public String getProfessorshipGrantingUniversity() {
        return this.professorshipGrantingUniversity;
    }

}
