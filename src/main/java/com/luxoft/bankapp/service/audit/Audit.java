package com.luxoft.bankapp.service.audit;

import com.luxoft.bankapp.service.audit.events.BalanceEvent;
import com.luxoft.bankapp.service.audit.events.DepositEvent;
import com.luxoft.bankapp.service.audit.events.WithdrawEvent;
import org.springframework.context.event.EventListener;

public interface Audit {

    @EventListener void auditOperation(DepositEvent event);

    @EventListener void auditOperation(WithdrawEvent event);

    @EventListener void auditOperation(BalanceEvent event);
}

