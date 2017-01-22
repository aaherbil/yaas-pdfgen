package ma.yaas.pdfgen.services;

import ma.yaas.pdfgen.api.generated.TemplateContext;
import ma.yaas.pdfgen.api.generated.TemplateData;

import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by laptop on 21/11/15.
 */
public interface PdfGeneratorService {

    StreamingOutput generate(TemplateData templateData);
    StreamingOutput generate(InputStream stream, TemplateContext context);
}
