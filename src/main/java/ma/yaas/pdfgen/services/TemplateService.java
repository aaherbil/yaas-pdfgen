package ma.yaas.pdfgen.services;

import com.mongodb.gridfs.GridFSDBFile;
import ma.yaas.pdfgen.api.generated.Template;

import java.io.InputStream;
import java.util.List;

/**
 * Created by laptop on 21/11/15.
 */
public interface TemplateService {

    public Template create(Template template);
    public Template findById(String id);
    public List<Template> findAll();
    public void save(Template template);
    public void delete(Template template);
    public void storeTemplateFile(InputStream file, String templateId);
    public GridFSDBFile findTemplateFile(String templateId);
}
