package ababilo.pwd.server.service.impl;

import ababilo.pwd.server.service.KeystoreService;
import ababilo.pwd.server.util.KeystoreUtil;
import org.springframework.stereotype.Component;

/**
 * Created by ababilo on 08.11.16.
 */
@Component
public class KeystoreServiceImpl implements KeystoreService {

    @Override
    public byte[] getServerKey() {
        return KeystoreUtil.getKey();
    }
}
