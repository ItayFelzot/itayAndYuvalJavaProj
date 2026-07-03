package YuvalDahan_ItayFelzot;

public class Doctor extends ArticleWriter {
    private String[] articleNames = new String[1];
    private int amountOfArticles = 0;
    public Doctor(String name, String id, Degree degree, String degreeName, float salary) {
        super(name, id, degree, degreeName, salary);
    }

}
