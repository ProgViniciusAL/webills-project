package com.practice.authentication_project.domain.models.bill.payment.service;

import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.bill.payment.BillPayment;
import com.practice.authentication_project.domain.models.bill.payment.repository.BillPaymentRepository;
import com.practice.authentication_project.domain.models.bill.repository.BillRepository;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BillPaymentService {

    @Autowired
    private BillPaymentRepository billPaymentRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Transactional
    public BillPayment createBillPayment(BillPayment billPayment, UUID billId, UUID tenantId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billId));
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        billPayment.setBill(bill);
        billPayment.setTenant(tenant);
        // Lógica adicional: verificar se o pagamento excede o valor da conta, atualizar status da conta, etc.
        return billPaymentRepository.save(billPayment);
    }

    @Transactional(readOnly = true)
    public List<BillPayment> getAllBillPayments() {
        return billPaymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public BillPayment getBillPaymentById(UUID id) {
        return billPaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BillPayment not found with id: " + id));
    }

    // Pagamentos, assim como transações, são muitas vezes imutáveis.
    @Transactional
    public BillPayment updateBillPayment(UUID id, BillPayment paymentDetails) {
        BillPayment existingPayment = getBillPaymentById(id);
        existingPayment.setPaymentDate(paymentDetails.getPaymentDate());
        existingPayment.setAmountPaid(paymentDetails.getAmountPaid());
        existingPayment.setFullPayment(paymentDetails.isFullPayment());
        // Mudar a 'bill' associada ou 'tenant' é uma operação muito complexa e incomum.
        return billPaymentRepository.save(existingPayment);
    }

    @Transactional
    public void deleteBillPayment(UUID id) {
        if (!billPaymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("BillPayment not found with id: " + id);
        }
        // Lógica de estorno se necessário
        billPaymentRepository.deleteById(id);
    }
}
