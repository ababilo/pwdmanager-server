package ababilo.pwd.server.service.model;

import java.util.Map;

/**
 * Created by ababilo on 08.11.16.
 */
public class Password {

    private short id;
    private byte[] password;
    private Map<String, String> metadata;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
