import lombok.Data;

@Data
public class Issue {
    int id;
    String title;
    String description;
    int issueNumber;

    public Issue(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    public Issue(int id, String title, String description,int issueNumber) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.issueNumber = issueNumber;
    }
}
