package ma.yaas.pdfgen.services.impl;

import com.mongodb.gridfs.GridFSDBFile;
import ma.yaas.pdfgen.api.generated.TemplateContext;
import ma.yaas.pdfgen.api.generated.TemplateData;
import ma.yaas.pdfgen.api.generated.TemplateMap;
import ma.yaas.pdfgen.services.PdfGeneratorService;
import ma.yaas.pdfgen.services.TemplateService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by abdellah AHERBIL on 22/11/15.
 */
public class DefaultPdfGeneratorService implements PdfGeneratorService {
    private TemplateService templateService;

    /* TODO  :
        - decouple the service from raml schema definitions
        - remove the compilation phase here
    */

    /**
     * generating a pdf output stream from a template
     * @param templateData
     */
    @Override
    public StreamingOutput generate(TemplateData templateData) {

        GridFSDBFile file = templateService.findTemplateFile(templateData.getId());
        if (file==null){
            //return or throw !!
            return null;
        }
        return  generate(file.getInputStream(),templateData.getContext());
    }

    /**
     * generate a pdf from an input jrxml stream and a context
     * @param stream jrxml stream
     * @param context template context
     * @return
     */
    @Override
    public StreamingOutput generate(InputStream stream, TemplateContext context) {
        return  (output-> {
            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(stream);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, buildParameters(context), buildDataBean(context));
                JasperExportManager.exportReportToPdfStream(jasperPrint,output);
            } catch (JRException e) {
                throw new IOException(e);
            }
        });
    }

    /**
     * building parameters from context
     * @param context
     * @return
     */
    private Map<String,Object> buildParameters(TemplateContext context){
        if(context==null||context.getParameters()==null ||context.getParameters().getAdditionalProperties()==null) return new HashMap<>();
        return context.getParameters().getAdditionalProperties();
    }


    /**
     * building the data bean form template context
     * @param context
     * @return
     */
    private JRRewindableDataSource buildDataBean(TemplateContext context){
        if(context==null||context.getFields()==null) return new JREmptyDataSource();
        Collection<Map<String,?>> col =context.getFields().stream().map( TemplateMap::getAdditionalProperties).collect(Collectors.toList());
        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(col);
        return dataSource;
    }

    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }
}
