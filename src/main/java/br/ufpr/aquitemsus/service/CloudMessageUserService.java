package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.Schedule;
import br.ufpr.aquitemsus.model.TokenCloudMessageUser;
import br.ufpr.aquitemsus.model.enums.ScheduleStatus;
import br.ufpr.aquitemsus.repository.TokenCloudMessageUserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class CloudMessageUserService {

    private final FirebaseMessaging _firebaseMessaging;
    private final ScheduleService _scheduleService;
    private final TokenCloudMessageUserRepository _tokenCloudMessageUserRepository;

    public CloudMessageUserService(
            FirebaseMessaging firebaseMessaging,
            ScheduleService scheduleService,
            TokenCloudMessageUserRepository tokenCloudMessageUserRepository
    ) {
        _firebaseMessaging = firebaseMessaging;
        _scheduleService = scheduleService;
        _tokenCloudMessageUserRepository = tokenCloudMessageUserRepository;
    }

    public TokenCloudMessageUser findByUserSusId(Long userId) {
        return _tokenCloudMessageUserRepository.findByUserSusId(userId);
    }

    public TokenCloudMessageUser saveToken(TokenCloudMessageUser tokenCloudMessageUser) {
        var savedToken = this.findByUserSusId(tokenCloudMessageUser.getUserSus().getId());

        if (savedToken != null) {
            tokenCloudMessageUser.setId(savedToken.getId());
        }

        return _tokenCloudMessageUserRepository.save(tokenCloudMessageUser);
    }

    public void sendScheduleReminder() throws FirebaseMessagingException {
        var calendar = Calendar.getInstance();

        calendar.add(Calendar.HOUR, 12);
        var startDate = calendar.getTime();

        calendar.add(Calendar.HOUR, 1);
        var endDate = calendar.getTime();

        var schedules = _scheduleService.findSchedulesBetween(startDate, endDate);

        for (Schedule schedule : schedules) {
            TokenCloudMessageUser tokenUser = this.findByUserSusId(schedule.getUserSus().getId());

            if (tokenUser == null || schedule.getStatus() != ScheduleStatus.Confirmed) {
                continue;
            }

            calendar.setTime(schedule.getDate());
            String dayMessage = "amanhã";

            if (calendar.get(Calendar.HOUR_OF_DAY) >= 12) {
                dayMessage = "hoje";
            }

            String format = "HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

            Notification notification = Notification
                    .builder()
                    .setTitle("Aqui tem SUS")
                    .setBody("Olá, você tem um atendimento agendado " + dayMessage + " às " + simpleDateFormat.format(schedule.getDate()) + "h.")
                    .build();

            Message message = Message
                    .builder()
                    .setToken(tokenUser.getToken())
                    .setNotification(notification)
                    .build();

            _firebaseMessaging.send(message);
        }
    }
}
