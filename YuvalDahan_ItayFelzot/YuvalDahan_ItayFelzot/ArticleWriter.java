package YuvalDahan_ItayFelzot;

import java.util.ArrayList;

public class ArticleWriter extends Lecturer {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> articleNames = new ArrayList<String>();

    public ArticleWriter(String name, String id, Degree degree, String degreeName, float salary) throws CollegeException {
        super(name, id, degree, degreeName, salary);
    }

    public ArrayList<String> getArticleNames() {
        return articleNames;
    }

    public int getAmountOfArticles() {
        return articleNames.size();
    }

    public boolean doesArticleExist(String articleName) {
        if (articleName == null || articleName.equals("")) {
            return false;
        }
        return articleNames.contains(articleName);
    }

    public void addArticle(String articleName) throws CollegeException {
        if (articleName == null || articleName.equals("")) {
            throw new InvalidArticleException();
        }
        if (doesArticleExist(articleName)) {
            throw new DuplicateArticleException();
        }
        articleNames.add(articleName);
    }

    public void removeArticle(String articleName) throws CollegeException {
        if (articleName == null || articleName.equals("")) {
            throw new InvalidArticleException();
        }
        if (articleNames.remove(articleName) == false) {
            throw new ArticleNotFoundException();
        }
    }

    public int compareArticles(ArticleWriter other) throws InvalidArticleWriterException {
        if (other == null) {
            throw new InvalidArticleWriterException();
        }
        return Integer.compare(articleNames.size(), other.getAmountOfArticles());
    }

    @Override
    public boolean equals(Object other) {
        if ((other instanceof ArticleWriter) == false) {
            return false;
        }
        ArticleWriter otherWriter = (ArticleWriter) other;
        return articleNames.size() == otherWriter.getAmountOfArticles();
    }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer(super.toString());
        info.append("\nAmount of articles: ").append(articleNames.size());
        if (articleNames.size() > 0) {
            info.append("\nArticles: ");
            for (int i = 0; i < articleNames.size(); i++) {
                info.append(articleNames.get(i)).append(", ");
            }
        }
        return info.toString();
    }
}
