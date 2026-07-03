package YuvalDahan_ItayFelzot;

public class DuplicateArticleException extends CollegeException {
    public DuplicateArticleException() {
        super("Article already exists for this lecturer.");
    }
}
