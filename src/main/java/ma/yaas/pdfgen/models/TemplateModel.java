package ma.yaas.pdfgen.models;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Created by abdellah AHERBIL on 21/11/15.
 */
@Document(collection = "templates")
public class TemplateModel {

    @Id
    private String id;
    private String title;
    private String description;
    private Date createdAt;

    public TemplateModel() {
    }

    public TemplateModel(String title,String description) {
        this.title = title;
        this.description = description;
        this.createdAt=new Date();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
