class Article implements Comparable<Article> {
    private String title;
    private int size;

    public Article(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public String getTitle() {
        return this.title;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public int compareTo(Article otherArticle) {
        java.util.Comparator<Article> articleComparator = java.util.Comparator.comparing(Article::getSize).thenComparing(Article::getTitle);
        return articleComparator.compare(this, otherArticle);
    }
}