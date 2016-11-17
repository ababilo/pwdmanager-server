package ababilo.pwd.server.repository;

import ababilo.pwd.server.repository.model.BackupEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ababilo on 08.11.16.
 */
public interface BackupRepository extends MongoRepository<BackupEntity, ObjectId> {

    BackupEntity findOneByClientId(String clientId);
}
