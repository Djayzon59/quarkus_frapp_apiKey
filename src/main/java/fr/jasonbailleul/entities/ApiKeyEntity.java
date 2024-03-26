package fr.jasonbailleul.entities;

import Security.Argon2;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="ApiKey", schema = "dbo")
public class ApiKeyEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String clef;

    @Column
    private String email;

    @Column
    private String nom;

    @Column
    private int quota;

    @OneToMany(mappedBy = "apiKeyEntity")
    private List<MailEntity> mailEntities;


    @Override
    public String toString() {
        return id + clef ;
    }
}
