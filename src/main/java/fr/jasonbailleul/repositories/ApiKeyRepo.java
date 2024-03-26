package fr.jasonbailleul.repositories;

import fr.jasonbailleul.entities.ApiKeyEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;


@RequestScoped
public class ApiKeyRepo implements PanacheRepositoryBase <ApiKeyEntity, Integer> {

    public ApiKeyEntity findByKey(String clef) {
        ApiKeyEntity apiKeyEntity = find("clef", clef).firstResult();
        return apiKeyEntity;
    }





}
