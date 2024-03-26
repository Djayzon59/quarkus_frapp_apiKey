package fr.jasonbailleul.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.jasonbailleul.entities.ApiKeyEntity;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import outils.HateOAS;
import outils.Link;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ApiKeyDto {

    @Schema(readOnly = true)
    @JsonProperty(index = 1)
    private int id;

    @JsonProperty(index = 2)
    private String nom;


    @Schema(readOnly = true)
    @JsonProperty(index = 3)
    private String clef;

    @Schema(example = "issamElBouziani@gmail.com")
    @JsonProperty(index = 4)
    private String mail;

    @Schema(example = "500")
    @JsonProperty(index = 5)
    private int quota;

    @Schema(readOnly = true)
    private HateOAS hateOAS;


    public ApiKeyDto(ApiKeyEntity apiKeyEntity){
       this.id = apiKeyEntity.getId();
       this.nom = apiKeyEntity.getNom();
       this.clef = apiKeyEntity.getClef();
       this.mail = apiKeyEntity.getEmail();
       this.quota = apiKeyEntity.getQuota();
    }

    public ApiKeyDto(ApiKeyEntity apiKeyEntity, UriBuilder uriBuilder){
        this.id = apiKeyEntity.getId();
        this.nom = apiKeyEntity.getNom();
        this.clef = apiKeyEntity.getClef();
        this.mail = apiKeyEntity.getEmail();
        this.quota = apiKeyEntity.getQuota();
        hateOAS = new HateOAS();

        UriBuilder deleteUriBuilder = UriBuilder.fromUri(uriBuilder.build());
        URI deleteUri = deleteUriBuilder.path("apikey/client/{id}").build(apiKeyEntity.getId());
        hateOAS.addLink(new Link("delete", HttpMethod.DELETE, deleteUri));

        UriBuilder updateQuotaUriBuilder = UriBuilder.fromUri(uriBuilder.build());
        URI updateUri = updateQuotaUriBuilder.path("apikey/{id}").build(apiKeyEntity.getId());
        hateOAS.addLink(new Link("update quota", HttpMethod.PUT, updateUri));
    }


    public static List<ApiKeyDto> toDtoList(List <ApiKeyEntity> apiKeyEntities, UriBuilder uriBuilder ){
        List <ApiKeyDto> apiKeyDtoList = new ArrayList<>();
        for(ApiKeyEntity apiKeyEntity : apiKeyEntities){
            UriBuilder uriBuilder1 = UriBuilder.fromUri(uriBuilder.build());
            apiKeyDtoList.add(new ApiKeyDto(apiKeyEntity, uriBuilder1));
        }
        return apiKeyDtoList;
    }



}
