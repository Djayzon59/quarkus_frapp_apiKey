package fr.jasonbailleul.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.jasonbailleul.entities.MailEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class MailDto {

    @Schema(readOnly = true)
    @JsonProperty(index = 1)
    private int id;

    @JsonProperty(index = 2)
    private String subject;

    @JsonProperty(index = 3)
    private String sendTo;

    @JsonProperty(index = 4)
    private ApiKeyDto apiKeyDto;
    private String texte;


    public MailDto(MailEntity mailEntity){
        this.id = mailEntity.getIdMail();
        this.subject = mailEntity.getSubject();
        this.sendTo = mailEntity.getSendTo();
        this.apiKeyDto = new ApiKeyDto();
    }

    public MailDto(String subject, String sendTo, String texte) {
        this.subject = subject;
        this.sendTo = sendTo;
        this.texte = texte;
    }

    public static List<MailDto> toDtoList(List <MailEntity> mailEntities){
        List <MailDto> mailDtoList = new ArrayList<>();
        for(MailEntity mailEntity : mailEntities){
            mailDtoList.add(new MailDto(mailEntity));
        }
        return mailDtoList;
    }
}
