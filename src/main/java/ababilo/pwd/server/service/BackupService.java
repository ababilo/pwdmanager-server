package ababilo.pwd.server.service;

import ababilo.pwd.server.api.model.EncryptedPackage;

/**
 * Created by ababilo on 08.11.16.
 */
public interface BackupService {

    String backup(EncryptedPackage request, String clientId);
    EncryptedPackage restore(String encryptedPackage);
}
