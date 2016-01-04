package ma.yaas.pdfgen.api.generated;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;


public final class DefaultGenerateResourceTest extends ma.yaas.pdfgen.api.generated.AbstractResourceTest
{
	/**
	 * Server side root resource /generate,
	 * evaluated with some default value(s).
	 */
	private static final String ROOT_RESOURCE_PATH = "/generate";

	/* post(entity) /generate */
	@Test
	public void testPostWithTemplateData()
	{
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("");
		final TemplateData entityBody =
		new TemplateData();
		final javax.ws.rs.client.Entity<TemplateData> entity =
		javax.ws.rs.client.Entity.entity(entityBody,"application/json");

		final Response response = target.request().post(entity);

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(), response.getStatus());
	}

	@Override
	protected ResourceConfig configureApplication()
	{
		final ResourceConfig application = new ResourceConfig();
		application.register(DefaultGenerateResource.class);
		return application;
	}
}
