
package ma.yaas.pdfgen.api.generated;

import javax.annotation.Resource;
import javax.inject.Singleton;


import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ma.yaas.pdfgen.services.TemplateService;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

/**
* Resource class containing the custom logic. Please put your logic here!
*/
@Component("apiTemplatesResource")
@Singleton
public class DefaultTemplatesResource implements TemplatesResource
{
	Logger LOG = Logger.getLogger(DefaultTemplatesResource.class);
	@Context
	private UriInfo uriInfo;
	@Context
	private ContainerRequest request;
	@Resource
	private TemplateService templateService;


	/* GET / */
	@Override
	public Response get(final YaasAwareParameters yaasAware)
	{
		return Response.ok()
			.entity(templateService.findAll()).build();
	}


	/* POST / */
	@Override
	public Response post(final YaasAwareParameters yaasAware, final Template template)
	{
		// place some logic here
		Template createdTemplate =   templateService.create(template);
		URI createdLocation = uriInfo.getRequestUriBuilder().path("/" + createdTemplate.getId()).build();
		return Response.created(createdLocation).build();
	}

	/* GET /{templateId} */
	@Override
	public Response getByTemplateId(final YaasAwareParameters yaasAware, final String templateId)
	{
		// place some logic here
		return Response.ok()
			.entity(templateService.findById(templateId)).build();
	}

	/* PUT /{templateId} */
	@Override
	public Response putByTemplateId(final YaasAwareParameters yaasAware, final String templateId, final Template template)
	{
		// place some logic here
		template.setId(templateId);
		templateService.save(template);

		return Response.ok()
			.build();
	}

	/* POST /{templateId} */
	@Override
	public Response postByTemplateId(final YaasAwareParameters yaasAware, final String templateId) {
		// place some logic here
		final FormDataMultiPart part = request.readEntity(FormDataMultiPart.class);
		final InputStream file = part.getField("file").getValueAs(InputStream.class);
		templateService.storeTemplateFile(file,templateId);
		System.out.println("My Form: text=" + templateId);
		return Response.created(uriInfo.getAbsolutePath())
				.build();
	}

	/* DELETE /{templateId} */
	@Override
	public Response deleteByTemplateId(final YaasAwareParameters yaasAware, final String templateId)
	{
		// place some logic here
		return Response.noContent()
			.build();
	}

}
