package ababilo.pwd.server.service;

import ababilo.pwd.server.service.model.Password;

import java.util.List;

/**
 * Created by ababilo on 08.11.16.
 */
public interface ProtocolService {

    List<Password> expandBackupRequest(byte[] data, byte[] secret);
    byte[] packBackup(List<Password> passwords, byte[] secret);
}
