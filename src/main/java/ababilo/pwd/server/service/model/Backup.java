package ababilo.pwd.server.service.model;

import java.util.List;

/**
 * Created by ababilo on 08.11.16.
 */
public class Backup {

    private String clientId;
    private List<Password> passwords;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
    }
}
