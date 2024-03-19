package fr.jasonbailleul.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.jasonbailleul.entities.ApiKeyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

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

    public ApiKeyDto(ApiKeyEntity apiKeyEntity){
       this.id = apiKeyEntity.getId();
       this.nom = apiKeyEntity.getNom();
       this.clef = apiKeyEntity.getClef();
       this.mail = apiKeyEntity.getEmail();
       this.quota = apiKeyEntity.getQuota();
    }


    public static List<ApiKeyDto> toDtoList(List <ApiKeyEntity> apiKeyEntities){
        List <ApiKeyDto> apiKeyDtoList = new ArrayList<>();
        for(ApiKeyEntity apiKeyEntity : apiKeyEntities){
            apiKeyDtoList.add(new ApiKeyDto(apiKeyEntity));
        }
        return apiKeyDtoList;
    }




}
