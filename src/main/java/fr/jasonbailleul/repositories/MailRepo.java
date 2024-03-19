package fr.jasonbailleul.repositories;

import fr.jasonbailleul.entities.MailEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class MailRepo implements PanacheRepositoryBase <MailEntity, Integer> {

    public long getUsageCount(int idApiKey) {
        return count("apiKeyEntity.id", idApiKey);
    }




}
