package YuvalDahan_ItayFelzot;

public class ArticleWriter extends Lecturer {
    private String[] articleNames = new String[1];
    private int amountOfArticles = 0;
    public ArticleWriter(String name, String id, Degree degree, String degreeName, float salary) {
        super(name, id, degree, degreeName, salary);
    }
public boolean addArticle(String articleName) {
        if (doesArticleExist(articleName) == true)
            return false;
        String[] newArticleNames;
        if (this.articleNames.length == this.amountOfArticles)
            newArticleNames = new String[Math.max(1, this.amountOfArticles*2)];
        else
            newArticleNames = new String[this.articleNames.length];
        for (int i = 0; i < this.amountOfArticles; i++) {
            newArticleNames[i] = this.articleNames[i];
        }
        newArticleNames[this.amountOfArticles] = articleName;
        this.articleNames = newArticleNames;
        this.amountOfArticles++;
        return true;
    }
public boolean doesArticleExist(String articleName) {
        if (articleName == null || articleName.equals(""))
            return false;
        for (int i = 0; i < this.amountOfArticles; i++) {
            if (this.articleNames[i] != null && this.articleNames[i].equals(articleName))
                return true;
        }
        return false;
    }
public boolean removeArticle(String articleName) {
        if (articleName == null || articleName.equals(""))
            return false;
        String[] newArticleNames = new String[Math.max(1, this.amountOfArticles)];
        int j = 0;
        for (int i = 0; i < this.amountOfArticles; i++) {
            if (this.articleNames[i] == null) continue;
            if (!this.articleNames[i].equals(articleName)) {
                newArticleNames[j] = this.articleNames[i];
                j++;
            }
        }
        this.articleNames = newArticleNames;
        this.amountOfArticles = j;
        return true;
    }
}
