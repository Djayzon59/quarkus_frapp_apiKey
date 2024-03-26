package fr.jasonbailleul.services;

import fr.jasonbailleul.dto.MailDto;
import fr.jasonbailleul.entities.ApiKeyEntity;
import fr.jasonbailleul.entities.MailEntity;
import fr.jasonbailleul.repositories.ApiKeyRepo;
import fr.jasonbailleul.repositories.MailRepo;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;



@Path("/apiKey/")
@Tag(name = "API Key")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiKeyService {
    @Inject
    private ApiKeyRepo apiKeyRepo;
    @Inject
    private MailRepo mailRepo;


    @PUT
    @Path("{id}")
    @Transactional
    @APIResponse(responseCode = "200", description = "OK !")
    @APIResponse(responseCode = "404", description = "Ressource non trouvée !")
    public Response updateQuota(@PathParam("id") int id, @QueryParam("quota") int quota) {

        ApiKeyEntity apiKeyEntity = apiKeyRepo.findById(id);
        if (apiKeyEntity == null) {
            return Response.status(404, "Ressource non trouvée").build();
        }

        apiKeyEntity.setQuota(quota);
        apiKeyRepo.persist(apiKeyEntity);
        return Response.status(200, "Quota mis à jour !").build();
    }


    @GET
    @Operation(hidden = true)
    @APIResponse(responseCode = "200", description = "OK !")
    @APIResponse(responseCode = "250", description = "Clef invalide !")
    @APIResponse(responseCode = "251", description = "Quota dépassé !")
    public Response chekUsage(@HeaderParam("clef") String clef) {

        ApiKeyEntity apiKeyEntity = apiKeyRepo.findByKey(clef);

        if (apiKeyEntity == null)
            return Response.status(250, "Clef invalide !").build();

        if (apiKeyEntity.getQuota() != 0) {
            long usageCount = mailRepo.getUsageCount(apiKeyEntity.getId());
            if (usageCount >= apiKeyEntity.getQuota()) {
                return Response.status(251, "Quota dépassé !").build();
            }
        }
        return Response.status(200).build();
    }


    @Transactional
    @POST
    @Operation(hidden = true)
    @APIResponse(responseCode = "200", description = "OK !")
    public Response saveMail(MailDto mailDto, @HeaderParam("clef") String clef) {
        ApiKeyEntity apiKeyEntity = apiKeyRepo.findByKey(clef);
        MailEntity mailEntity = new MailEntity();
        mailEntity.setSubject(mailDto.getSubject());
        mailEntity.setSendTo(mailDto.getSendTo());
        mailEntity.setSubject(mailDto.getSubject());
        mailEntity.setApiKeyEntity(apiKeyEntity);
        mailRepo.persist(mailEntity);

        return Response.status(200).build();
    }

}
