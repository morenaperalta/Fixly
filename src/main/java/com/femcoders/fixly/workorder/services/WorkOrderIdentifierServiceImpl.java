package com.femcoders.fixly.workorder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkOrderIdentifierServiceImpl implements WorkOrderIdentifierService{
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateIdentifier() {
        LocalDateTime now = LocalDateTime.now();
        String monthYear = String.format("%02d%d", now.getMonthValue(), now.getYear());

        String randomNumber = generateRandomNumber();

        return "WO-" + randomNumber + "-" + monthYear;
    }

    public String generateRandomNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(secureRandom.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
