package todoapp.web.model;

/**
 * 사이트 정보 모델
 *
 * @author springrunner.kr@gmail.com
 */
public class SiteProperties {

    private String author = "unknown";
    private String description = "TodoApp templates for Server-side";

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
