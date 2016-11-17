package ababilo.pwd.server.repository;

import ababilo.pwd.server.repository.model.ClientEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ababilo on 17.11.16.
 */
public interface ClientRepository extends MongoRepository<ClientEntity, ObjectId> {
}
