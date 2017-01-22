package ma.yaas.pdfgen.api.generated;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;


public final class DefaultTemplatesResourceTest extends ma.yaas.pdfgen.api.generated.AbstractResourceTest
{
	/**
	 * Server side root resource /templates,
	 * evaluated with some default value(s).
	 */
	private static final String ROOT_RESOURCE_PATH = "/templates";

	/* get() /templates */
	@Test
	public void testGet()
	{
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("");

		final Response response = target.request().get();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(), response.getStatus());
	}

	/* post(entity) /templates */
	@Test
	public void testPostWithTemplate()
	{
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("");
		final Template entityBody =
		new Template();
		final javax.ws.rs.client.Entity<Template> entity =
		javax.ws.rs.client.Entity.entity(entityBody,"application/json");

		final Response response = target.request().post(entity);

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.CREATED.getStatusCode(), response.getStatus());
	}

	/* get() /templates/templateId */
	@Test
	public void testGetByTemplateId()
	{
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("/templateId");

		final Response response = target.request().get();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(), response.getStatus());
	}

	/* put(entity) /templates/templateId */
	@Test
	public void testPutByTemplateIdWithTemplate()
	{
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("/templateId");
		final Template entityBody =
		new Template();
		final javax.ws.rs.client.Entity<Template> entity =
		javax.ws.rs.client.Entity.entity(entityBody,"application/json");

		final Response response = target.request().put(entity);

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(), response.getStatus());
	}

	/* delete() /templates/templateId */
	@Test
	public void testDeleteByTemplateId()
	{
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("/templateId");

		final Response response = target.request().delete();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Override
	protected ResourceConfig configureApplication()
	{
		final ResourceConfig application = new ResourceConfig();
		application.register(DefaultTemplatesResource.class);
		return application;
	}
}
