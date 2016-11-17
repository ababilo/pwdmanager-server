package ababilo.pwd.server.repository.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Created by ababilo on 17.11.16.
 */
public class ClientEntity {

    @Id
    private ObjectId id;
    private byte[] clientSecret;
    // todo a few fields like name etc


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public byte[] getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(byte[] clientSecret) {
        this.clientSecret = clientSecret;
    }
}
