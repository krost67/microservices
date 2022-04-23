package com.podlasenko.customer;

import com.podlasenko.clients.fraud.FraudCheckResponse;
import com.podlasenko.clients.fraud.FraudClient;
import com.podlasenko.clients.notification.NotificationClient;
import com.podlasenko.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo check if email is valid
        // todo check of email is not taken
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse != null && fraudCheckResponse.isFraudster()) {
            throw new IllegalArgumentException("This user is fraudster!");
        }

        // todo: make it async. i.e add to queue
        notificationClient.sendNotification(new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hello %s and welcome to this test system!",
                        customer.getFirstName()))
        );
    }
}
