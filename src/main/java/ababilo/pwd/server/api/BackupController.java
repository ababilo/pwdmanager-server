package ababilo.pwd.server.api;

import ababilo.pwd.server.api.model.Confirmation;
import ababilo.pwd.server.api.model.EncryptedPackage;
import ababilo.pwd.server.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ababilo on 08.11.16.
 */
@RestController
public class BackupController {

    private final BackupService backupService;

    @Autowired
    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @RequestMapping(value = "/backup", method = RequestMethod.POST)
    public Confirmation backup(@RequestBody EncryptedPackage request, @RequestParam("clientId") String clientId) {
        return new Confirmation<>(backupService.backup(request, clientId));
    }

    @RequestMapping(value = "/backup", method = RequestMethod.GET)
    public Confirmation restore(@RequestParam("clientId") String clientId) {
        return new Confirmation<>(backupService.restore(clientId));
    }
}
