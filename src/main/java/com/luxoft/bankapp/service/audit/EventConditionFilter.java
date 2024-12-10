package com.luxoft.bankapp.service.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// TASK 4.2: Add a component that will be used to filter events.
@Component
public class EventConditionFilter {
    @Value("${audit.amount.deposit}")
    public double depositLimit;

    @Value("${audit.amount.withdrawal}")
    public double withdrawalLimit;
}
