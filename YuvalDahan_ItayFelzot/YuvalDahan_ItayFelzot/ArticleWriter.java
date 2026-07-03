package YuvalDahan_ItayFelzot;

public class ArticleWriter extends Lecturer {
    private String[] articleNames = new String[1];
    private int amountOfArticles = 0;

    public ArticleWriter(String name, String id, Degree degree, String degreeName, float salary) throws CollegeException {
        super(name, id, degree, degreeName, salary);
    }

    public String[] getArticleNames() {
        return articleNames;
    }

    public int getAmountOfArticles() {
        return amountOfArticles;
    }

    public boolean doesArticleExist(String articleName) {
        if (articleName == null || articleName.equals("")) {
            return false;
        }
        for (int i = 0; i < this.amountOfArticles; i++) {
            if (this.articleNames[i] != null && this.articleNames[i].equals(articleName)) {
                return true;
            }
        }
        return false;
    }

    public void addArticle(String articleName) throws CollegeException {
        if (articleName == null || articleName.equals("")) {
            throw new InvalidArticleException();
        }
        if (doesArticleExist(articleName)) {
            throw new DuplicateArticleException();
        }
        String[] newArticleNames;
        if (this.articleNames.length == this.amountOfArticles) {
            newArticleNames = new String[Math.max(1, this.amountOfArticles * 2)];
        } else {
            newArticleNames = new String[this.articleNames.length];
        }
        for (int i = 0; i < this.amountOfArticles; i++) {
            newArticleNames[i] = this.articleNames[i];
        }
        newArticleNames[this.amountOfArticles] = articleName;
        this.articleNames = newArticleNames;
        this.amountOfArticles++;
    }

    public void removeArticle(String articleName) throws CollegeException {
        if (articleName == null || articleName.equals("")) {
            throw new InvalidArticleException();
        }
        if (!doesArticleExist(articleName)) {
            throw new ArticleNotFoundException();
        }
        String[] newArticleNames = new String[Math.max(1, this.amountOfArticles)];
        int j = 0;
        for (int i = 0; i < this.amountOfArticles; i++) {
            if (this.articleNames[i] == null) {
                continue;
            }
            if (!this.articleNames[i].equals(articleName)) {
                newArticleNames[j] = this.articleNames[i];
                j++;
            }
        }
        this.articleNames = newArticleNames;
        this.amountOfArticles = j;
    }

    public int compareArticles(ArticleWriter other) throws InvalidArticleWriterException {
        if (other == null) {
            throw new InvalidArticleWriterException();
        }
        return Integer.compare(this.amountOfArticles, other.amountOfArticles);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ArticleWriter)) {
            return false;
        }
        ArticleWriter otherWriter = (ArticleWriter) other;
        return this.amountOfArticles == otherWriter.amountOfArticles;
    }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer(super.toString());
        info.append("\nAmount of articles: ").append(amountOfArticles);
        if (amountOfArticles > 0) {
            info.append("\nArticles: ");
            for (int i = 0; i < amountOfArticles; i++) {
                if (articleNames[i] != null) {
                    info.append(articleNames[i]).append(", ");
                }
            }
        }
        return info.toString();
    }
}
