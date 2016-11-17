package ababilo.pwd.server.repository.model;

import ababilo.pwd.server.service.model.Password;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by ababilo on 08.11.16.
 */
public class BackupEntity {

    @Id
    private ObjectId id;
    @DBRef
    private ClientEntity client;
    private List<Password> passwords;
    private OffsetDateTime created;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }
}
