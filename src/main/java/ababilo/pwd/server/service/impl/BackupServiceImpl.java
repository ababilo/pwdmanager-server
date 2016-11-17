package ababilo.pwd.server.service.impl;

import ababilo.pwd.server.api.model.EncryptedPackage;
import ababilo.pwd.server.repository.BackupRepository;
import ababilo.pwd.server.repository.ClientRepository;
import ababilo.pwd.server.repository.model.BackupEntity;
import ababilo.pwd.server.repository.model.ClientEntity;
import ababilo.pwd.server.service.BackupService;
import ababilo.pwd.server.service.ProtocolService;
import ababilo.pwd.server.service.model.Password;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by ababilo on 08.11.16.
 */
@Component
public class BackupServiceImpl implements BackupService {

    private final BackupRepository backupRepository;
    private final ClientRepository clientRepository;
    private final ProtocolService protocolService;

    @Autowired
    public BackupServiceImpl(BackupRepository backupRepository,
                             ClientRepository clientRepository,
                             ProtocolService protocolService) {
        this.backupRepository = backupRepository;
        this.clientRepository = clientRepository;
        this.protocolService = protocolService;
    }

    @Override
    public String backup(EncryptedPackage request, String clientId) {
        ClientEntity client = clientRepository.findOne(new ObjectId(clientId));
        if (null == client) {
            throw new RuntimeException("Client not found");
        }

        List<Password> backup = protocolService.expandBackupRequest(request.getData(), client.getClientSecret());

        BackupEntity entity = new BackupEntity();
        entity.setClient(client);
        entity.setPasswords(backup);
        entity.setCreated(OffsetDateTime.now());
        backupRepository.save(entity);

        return entity.getId().toHexString();
    }

    @Override
    public EncryptedPackage restore(String clientId) {
        BackupEntity backup = backupRepository.findOneByClientId(clientId);
        if (null == backup) {
            throw new RuntimeException("Backup not found");
        }

        return new EncryptedPackage(protocolService.packBackup(backup.getPasswords(), backup.getClient().getClientSecret()));
    }
}
