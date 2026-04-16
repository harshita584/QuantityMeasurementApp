package com.example.userservice.service;

import com.example.userservice.model.QuantityMeasurementEntity;
import com.example.userservice.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private QuantityMeasurementRepository historyRepository;

    public QuantityMeasurementEntity saveHistory(Long userId, QuantityMeasurementEntity history) {
        history.setUserId(userId);
        return historyRepository.save(history);
    }

    public List<QuantityMeasurementEntity> getHistory(Long userId) {
        return historyRepository.findByUserId(userId);
    }

    public List<QuantityMeasurementEntity> getAllHistory() {
        return historyRepository.findAll();
    }

    public List<QuantityMeasurementEntity> getHistoryByOperation(String operation) {
        return historyRepository.findByOperation(operation);
    }
}
