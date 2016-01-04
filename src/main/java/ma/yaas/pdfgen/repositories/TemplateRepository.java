package ma.yaas.pdfgen.repositories;

import ma.yaas.pdfgen.models.TemplateModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by abdellah AHERBIL on 21/11/15.
 */
public interface TemplateRepository extends MongoRepository<TemplateModel, String> {
    // add new methods
}
