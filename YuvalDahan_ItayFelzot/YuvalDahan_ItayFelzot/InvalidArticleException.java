package YuvalDahan_ItayFelzot;

public class InvalidArticleException extends CollegeException {
    public InvalidArticleException() {
        super("Article name cannot be empty.");
    }
}
