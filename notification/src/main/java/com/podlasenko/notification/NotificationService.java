package com.podlasenko.notification;

import com.podlasenko.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest request) {
        notificationRepository.save(Notification.builder()
                .message(request.message())
                .toCustomerId(request.toCustomerId())
                .toCustomerEmail(request.toCustomerEmail())
                .sentAt(LocalDateTime.now())
                .sender("Yevhen P.")
                .build());
    }
}
