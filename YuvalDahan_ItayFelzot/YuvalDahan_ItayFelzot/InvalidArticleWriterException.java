package YuvalDahan_ItayFelzot;

public class InvalidArticleWriterException extends CollegeException {
    public InvalidArticleWriterException() {
        super("Only doctors and professors can write articles.");
    }
}
