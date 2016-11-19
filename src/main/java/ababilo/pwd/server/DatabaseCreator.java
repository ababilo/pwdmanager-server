package ababilo.pwd.server;

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

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by ababilo on 18.11.16.
 */
//@SpringBootApplication
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
        //backup.setCreated(new OffsetDateTime(java.time.OffsetDateTime.now()));

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
        byte[] btKey = AesUtil.generateSecureBytes(32);
        byte[] hbtKey = AesUtil.generateSecureBytes(32);
        System.out.println("CLIENTID: " + client.getId().toHexString());
        System.out.println("BTKEY: " + Arrays.toString(btKey));
        System.out.println("HBTKEY: " + Arrays.toString(hbtKey));
        return new Database(
                client.getId().toHexString(), client.getClientSecret(),
                btKey, hbtKey,
                Collections.emptyList()
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
