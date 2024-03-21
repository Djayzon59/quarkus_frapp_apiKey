package fr.jasonbailleul.services;

import Security.Argon2;
import fr.jasonbailleul.dto.ApiKeyDto;
import fr.jasonbailleul.entities.ApiKeyEntity;
import fr.jasonbailleul.repositories.ApiKeyRepo;
import fr.jasonbailleul.repositories.MailRepo;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Base64;

@Path("/Clients/")
@Tag(name = "Client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientService {

    @Inject
    private ApiKeyRepo apiKeyRepo;
    @Inject
    private MailRepo mailRepo;


    @POST
    @Path("/new/")
    @Transactional
    @APIResponse(responseCode = "200", description = "OK !")
    public Response insert(ApiKeyDto apiKeyDto) {

        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setNom(apiKeyDto.getNom());
        byte[] salt = Argon2.generateFixedSalt16Byte();
        byte[] hashKey = Argon2.generateArgon2idMinimal(apiKeyDto.getNom(), salt);
        String key = Base64.getEncoder().encodeToString(hashKey);
        apiKeyEntity.setClef(key);
        apiKeyEntity.setEmail(apiKeyDto.getMail());
        apiKeyEntity.setQuota(apiKeyDto.getQuota());
        apiKeyRepo.persist(apiKeyEntity);

        return Response.ok(apiKeyEntity.getId()).status(200, "Ressource créée !").build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @APIResponse(responseCode = "200", description = "OK !")
    public Response delete(@PathParam("id") int id) {
        ApiKeyEntity apiKeyEntity = apiKeyRepo.findById(id);
        if (apiKeyEntity == null)
            return Response.status(404, "Ressource non trouvée").build();
        apiKeyRepo.deleteById(id);
        return Response.status(200, "Ressource supprimée !").build();
    }

    @GET
    @APIResponse(responseCode = "200", description = "OK !")
    public Response getAll(){
        return Response.ok(ApiKeyDto.toDtoList(apiKeyRepo.listAll())).build();
    }


}
