package fr.jasonbailleul.restCLient;

import fr.jasonbailleul.dto.MailDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/mails")
public interface MailClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendEmail (MailDto mailDto, @HeaderParam("ApiKey") String apiKey);

}
