package YuvalDahan_ItayFelzot;

public class ArticleNotFoundException extends CollegeException {
    public ArticleNotFoundException() {
        super("Article was not found for this lecturer.");
    }
}
