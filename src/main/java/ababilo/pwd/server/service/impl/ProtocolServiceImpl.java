package ababilo.pwd.server.service.impl;

import ababilo.pwd.server.creator.ClientPassword;
import ababilo.pwd.server.service.ProtocolService;
import ababilo.pwd.server.service.model.Password;
import ababilo.pwd.server.util.AesUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ababilo on 08.11.16.
 */
@Component
public class ProtocolServiceImpl implements ProtocolService {

    private final ObjectMapper mapper;

    @Autowired
    public ProtocolServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Password> expandBackupRequest(byte[] data, byte[] secret) {
        byte[] decrypted = AesUtil.decrypt(data, secret);
        try {
            return mapper.readValue(decrypted, new TypeReference<List<Password>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Can't parse data", e);
        }
    }

    @Override
    public byte[] packBackup(List<Password> passwords, byte[] secret) {
        try {
            String json = mapper.writeValueAsString(passwords.stream()
                    .map(password -> new ClientPassword(password.getId(), password.getMetadata().get("title"), password.getPassword()))
                    .collect(Collectors.toList()));
            return AesUtil.encrypt(json.getBytes(Charsets.US_ASCII), secret);
        } catch (Exception e) {
            throw new RuntimeException("Can't pack data", e);
        }
    }
}
