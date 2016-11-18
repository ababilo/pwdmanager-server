package ababilo.pwd.server;

import ababilo.pwd.server.creator.ClientPassword;
import ababilo.pwd.server.creator.Database;
import ababilo.pwd.server.repository.BackupRepository;
import ababilo.pwd.server.repository.ClientRepository;
import ababilo.pwd.server.repository.model.BackupEntity;
import ababilo.pwd.server.repository.model.ClientEntity;
import ababilo.pwd.server.service.model.Password;
import ababilo.pwd.server.util.AesUtil;
import ababilo.pwd.server.util.HashingUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by ababilo on 18.11.16.
 */
@SpringBootApplication
public class DatabaseCreator implements CommandLineRunner {

    private static final String SALT = "sbs-101-o";

    private final ClientRepository clientRepository;
    private final BackupRepository backupRepository;
    private final ObjectMapper mapper;

    @Autowired
    public DatabaseCreator(ClientRepository clientRepository, BackupRepository backupRepository, ObjectMapper mapper) {
        this.clientRepository = clientRepository;
        this.backupRepository = backupRepository;
        this.mapper = mapper;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DatabaseCreator.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ClientEntity client = new ClientEntity();
        client.setClientSecret(AesUtil.generateSecureBytes(32));
        clientRepository.save(client);

        BackupEntity backup = new BackupEntity();
        backup.setClient(client);
        backup.setCreated(OffsetDateTime.now());

        Password password = new Password();
        password.setId((short) 1);
        password.setPassword("password".getBytes(Charsets.US_ASCII));
        password.setMetadata(Collections.singletonMap("title", "My first password"));
        backup.setPasswords(Collections.singletonList(password));
        backupRepository.save(backup);

        saveDatabase(buildDatabase(backup));
        System.exit(0);
    }

    private Database buildDatabase(BackupEntity backup) {
        ClientEntity client = backup.getClient();
        return new Database(
                client.getId().toHexString(), client.getClientSecret(),
                AesUtil.generateSecureBytes(32), AesUtil.generateSecureBytes(32),
                backup.getPasswords().stream()
                        .map(password -> new ClientPassword(password.getId(), password.getMetadata().get("title"), password.getPassword()))
                        .collect(Collectors.toList())
        );
    }

    private void saveDatabase(Database database) throws Exception {
        String json = mapper.writeValueAsString(database);
        byte[] data = AesUtil.encrypt(json.getBytes(Charsets.US_ASCII), HashingUtil.PBKDF2("password".toCharArray(), SALT.getBytes(Charsets.US_ASCII)));

        try (OutputStream out = new FileOutputStream("database")) {
            IOUtils.write(data, out);
            out.flush();
        }
    }
}
