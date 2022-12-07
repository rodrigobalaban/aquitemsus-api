package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.TokenCloudMessageUser;
import br.ufpr.aquitemsus.service.CloudMessageUserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cloud-message")
public class CloudMessageUserController {

    private final CloudMessageUserService _cloudMessageUserService;

    public CloudMessageUserController(CloudMessageUserService cloudMessageUserService) {
        _cloudMessageUserService = cloudMessageUserService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenCloudMessageUser saveToken(@RequestBody TokenCloudMessageUser tokenCloudMessageUser) {
        return _cloudMessageUserService.saveToken(tokenCloudMessageUser);
    }

    @GetMapping("notify")
    public void sendNotification() throws FirebaseMessagingException {
        _cloudMessageUserService.sendScheduleReminder();
    }
}
