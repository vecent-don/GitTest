import lombok.Data;

@Data
public class Issue {
    int id;
    String title;
    String description;
    int issueNumber;
    String level1;
    String level2;

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
    public Issue(int id, String title, String description,int issueNumber,String  level) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.issueNumber = issueNumber;
        this.level2 = level;
    }
}
