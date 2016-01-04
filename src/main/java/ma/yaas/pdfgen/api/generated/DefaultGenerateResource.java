
package ma.yaas.pdfgen.api.generated;


import ma.yaas.pdfgen.services.PdfGeneratorService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.core.*;

/**
* Resource class containing the custom logic. Please put your logic here!
*/
@Component("apiGenerateResource")
@Singleton
public class DefaultGenerateResource implements ma.yaas.pdfgen.api.generated.GenerateResource
{
	@Context
	private UriInfo uriInfo;

	@Resource
	private PdfGeneratorService pdfGeneratorService;

	/* POST / */
	@Override
	public Response post(final YaasAwareParameters yaasAware, final TemplateData templateData)
	{
		StreamingOutput stream = pdfGeneratorService.generate(templateData);
		return Response.ok(stream, MediaType.APPLICATION_OCTET_STREAM)
			.header("content-disposition","attachment; filename = "+templateData.getId()+".pdf")
			.build();
	}

}
