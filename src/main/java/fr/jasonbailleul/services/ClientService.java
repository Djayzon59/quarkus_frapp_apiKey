package fr.jasonbailleul.services;

import Security.Argon2;
import fr.jasonbailleul.dto.ApiKeyDto;
import fr.jasonbailleul.dto.MailDto;
import fr.jasonbailleul.entities.ApiKeyEntity;
import fr.jasonbailleul.repositories.ApiKeyRepo;
import fr.jasonbailleul.repositories.MailRepo;
import fr.jasonbailleul.restCLient.MailClient;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import outils.Link;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;

@Path("/client/")
@Tag(name = "client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientService {

    @Inject
    private ApiKeyRepo apiKeyRepo;
    @Inject
    private HateOas hateOas;
    @Inject
    private UriInfo uriInfo;
    @RestClient
    private MailClient mailClient;
    @Inject
    private MailRepo mailRepo;



    @POST
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
        MailDto mailDto = new MailDto("Voici votre clef d'API", apiKeyEntity.getEmail(), String.format("CLEF = %s", apiKeyEntity.getClef()) );
        mailClient.sendMailWithoutKey(mailDto);
        apiKeyRepo.persist(apiKeyEntity);
        return Response.ok(apiKeyEntity.getId()).status(200, "Ressource créée !").build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @APIResponse(responseCode = "200", description = "OK !")
    @APIResponse(responseCode = "404", description = "Identifiant incorrect !")
    public Response delete(@PathParam("id") int id) {
        ApiKeyEntity apiKeyEntity = apiKeyRepo.findById(id);
        if (apiKeyEntity == null)
            return Response.status(404, "Ressource non trouvée").build();

        mailRepo.deleteByIdApiKey(id);
        apiKeyRepo.deleteById(id);
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();

        hateOas.addLink(new Link("Créer nouvlle ressource",HttpMethod.POST,uriBuilder.path("apikey/client").build()));
        return Response.ok(hateOas).status(200, "Ressource supprimée !").build();
    }


    @GET
    @APIResponse(responseCode = "200", description = "OK !")
    public Response getAll() {
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        ArrayList<ApiKeyDto> listeApiKeys = new ArrayList<>(ApiKeyDto.toDtoList(apiKeyRepo.listAll(),uriBuilder));
        return Response.ok(listeApiKeys).build();
    }
}
