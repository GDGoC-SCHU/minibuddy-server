package com.minibuddy.feature.notification;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public ApiFuture<String> send(String notificationToken, String title, String body) {
        if (notificationToken == null || notificationToken.isEmpty()) {
            return null;
        }
        Message message = Message.builder()
                .setToken(notificationToken)
                .setNotification(
                        Notification.builder().setTitle(title).setBody(body).build()
                ).build();
        return FirebaseMessaging.getInstance().sendAsync(message);
    }
}
