package com.practice.authentication_project.domain.models.bill.service;

import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.bill.Status;
import com.practice.authentication_project.domain.models.bill.payment.BillPayment;
import com.practice.authentication_project.domain.models.bill.payment.repository.BillPaymentRepository;
import com.practice.authentication_project.domain.models.bill.repository.BillRepository;
import com.practice.authentication_project.domain.models.category.Category;
import com.practice.authentication_project.domain.models.category.repository.CategoryRepository;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.repository.UserRepository;
import com.practice.authentication_project.security.config.AuthorizationService;
import com.practice.authentication_project.security.config.jwt.JWTService;
import com.practice.authentication_project.shared.dto.bills.BillPaymentDTO;
import com.practice.authentication_project.shared.dto.bills.BillsDTO;
import com.practice.authentication_project.shared.dto.bills.BillsUpdateDTO;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import com.practice.authentication_project.shared.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillPaymentRepository billPaymentRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionUtil sessionUtil;

    @Transactional
    public Bill createBill(BillsDTO bill, HttpServletRequest request) {

        Tenant tenant = sessionUtil.getSessionTenant(request);

        Category category = categoryRepository.findById(bill.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + bill.categoryId()));

        tenant.validateCategoryOwnership(category);

        Bill newBill = new Bill(bill, category, tenant);

        return billRepository.save(newBill);
    }

    @Transactional(readOnly = true)
    public List<Bill> getAllBills(HttpServletRequest request) {

        Tenant tenant = sessionUtil.getSessionTenant(request);
        return billRepository.findByTenantId(tenant.getId());

    }

    @Transactional(readOnly = true)
    public Bill getBillById(UUID id, HttpServletRequest request) {
        Tenant tenant = sessionUtil.getSessionTenant(request);
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));

        tenant.validateBillOwnership(bill);

        return bill;
    }

    @Transactional
    public BillPayment payBill(UUID billId, BillPaymentDTO paymentDTO, HttpServletRequest request) {
        Tenant tenant = sessionUtil.getSessionTenant(request);
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billId));

        tenant.validateBillOwnership(bill);

        BillPayment billPayment = new BillPayment(bill, tenant, paymentDTO.amount(), paymentDTO.paymentDate());

        return billPaymentRepository.save(billPayment);

    }

    @Transactional
    public Bill updateBill(BillsUpdateDTO updatedBill, HttpServletRequest request) {
        Tenant tenant = sessionUtil.getSessionTenant(request);

        Bill existingBill = billRepository.findById(updatedBill.id())
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + updatedBill.id()));

        Category newCategory = categoryRepository.findById(updatedBill.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + updatedBill.categoryId()));

        if (tenant.validateBillOwnership(existingBill)) {
            existingBill.updateFrom(updatedBill, newCategory);
            return billRepository.save(existingBill);
        }

        return null;
    }


    @Transactional
    public void deleteBill(UUID id, HttpServletRequest request) {

        Tenant tenant = sessionUtil.getSessionTenant(request);

        Bill existingBill = billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found"));

        if (!billRepository.existsById(id) || !tenant.validateBillOwnership(existingBill)) {
            throw new ResourceNotFoundException("Bill not found with id: " + id);
        }
        billRepository.deleteById(id);
    }
}
