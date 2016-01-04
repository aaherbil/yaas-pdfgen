package ma.yaas.pdfgen.services.impl;

import ch.qos.logback.core.util.ContentTypeUtil;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import ma.yaas.pdfgen.api.generated.Template;
import ma.yaas.pdfgen.models.TemplateModel;
import ma.yaas.pdfgen.repositories.TemplateRepository;
import ma.yaas.pdfgen.services.TemplateService;
import static  org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Query.*;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by abdellah AHERBIL on 21/11/15.
 */
public class DefaultTemplateService implements TemplateService {


    private TemplateRepository templateRepository;
    private GridFsTemplate gridFsTemplate;

    @Override
    public Template create(Template template) {
        TemplateModel templateModel = new TemplateModel(template.getTitle(),template.getDescription());
        templateRepository.insert(templateModel);
        return buildTemplate(templateModel);
    }

    @Override
    public Template findById(String id) {
        TemplateModel templateModel =  templateRepository.findOne(id);

        if(templateModel!=null){
            return buildTemplate(templateModel);
        }
        return null;
    }

    @Override
    public List<Template> findAll() {
        List<TemplateModel> templateModels= templateRepository.findAll();
        List<Template> templates = new ArrayList<Template>();
        for(TemplateModel templateModel :templateModels){
            templates.add(buildTemplate(templateModel));
        }
        return templates;
    }

    private Template buildTemplate(TemplateModel templateModel) {
       Template template =  new Template();
        template.setId(templateModel.getId());
        template.setTitle(templateModel.getTitle());
        template.setDescription(templateModel.getDescription());
        GridFSDBFile storedFile = findTemplateFile(templateModel.getId());
        if(storedFile!=null){
            template.setFile(storedFile.getId().toString());
        }
        template.setCreatedAt(templateModel.getCreatedAt());
        return template;
    }

    @Override
    public void save(Template template) {
       TemplateModel templateModel=templateRepository.findOne(template.getId());
        if(templateModel!=null){
            templateModel.setTitle(template.getTitle());
            templateModel.setDescription(template.getDescription());
            templateRepository.save(templateModel);
        }
    }

    @Override
    public void delete(Template template) {
        TemplateModel templateModel=templateRepository.findOne(template.getId());
        if(templateModel!=null){
            gridFsTemplate.delete(query(where("filename").is(templateModel.getId() + ".jrxml")));
            templateRepository.delete(templateModel);
        }
    }

    @Override
    public void storeTemplateFile(InputStream file, String templateId) {
        TemplateModel templateModel=templateRepository.findOne(templateId);

        if(templateModel!=null) {
            //delete existing files
            gridFsTemplate.delete(query(where("filename").is(templateId + ".jrxml")));
            //insert the new file
            gridFsTemplate.store(file, templateId + ".jrxml", "application/xml");
        }

    }

    @Override
    public GridFSDBFile findTemplateFile(String templateId) {
        return gridFsTemplate.findOne(query(where("filename").is(templateId + ".jrxml")));
    }

    public void setTemplateRepository(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public void setGridFsTemplate(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }
}
