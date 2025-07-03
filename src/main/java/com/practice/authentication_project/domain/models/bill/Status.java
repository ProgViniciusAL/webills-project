package com.practice.authentication_project.domain.models.bill;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public enum Status {

    PENDENTE,     // Criada, mas ainda não paga
    PAGO,        // Totalmente paga
    VENCIDO,     // Venceu, mas ainda não foi paga
    CANCELADO;  // Cancelada, não será paga

    public void updateStatus(Bill bill) {
        if (bill.getStatus() != Status.PAGO && bill.getDueDate().isBefore(LocalDate.now())) {
            bill.setStatus(Status.VENCIDO);
        }
    }

}
