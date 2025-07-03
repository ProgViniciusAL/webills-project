package com.practice.authentication_project.domain.models.bill.controller;

import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.bill.payment.BillPayment;
import com.practice.authentication_project.domain.models.bill.payment.service.BillPaymentService;
import com.practice.authentication_project.domain.models.bill.service.BillService;
import com.practice.authentication_project.shared.dto.bills.BillPaymentDTO;
import com.practice.authentication_project.shared.dto.bills.BillsDTO;
import com.practice.authentication_project.shared.dto.bills.BillsUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillPaymentService billPaymentService;

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody BillsDTO billDTO, HttpServletRequest request) {
        Bill createdBill = billService.createBill(billDTO, request);
        return ResponseEntity.ok(createdBill);
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<BillPaymentDTO> payBill(@PathVariable UUID id, @RequestBody BillPaymentDTO billPaymentDTO, HttpServletRequest request) {
        BillPayment createdBill = billService.payBill(id, billPaymentDTO, request);
        return ResponseEntity.ok(new BillPaymentDTO(createdBill));
    }

    @GetMapping
    public ResponseEntity<List<BillsDTO>> getAllBills(HttpServletRequest request) {
        List<Bill> bills = billService.getAllBills(request);
        return ResponseEntity.ok(bills.stream().map(BillsDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillsDTO> getBillById(@PathVariable UUID id, HttpServletRequest request) {
        Bill bill = billService.getBillById(id, request);
        return ResponseEntity.ok(new BillsDTO(bill));
    }

    @PutMapping
    public ResponseEntity<BillsDTO> updateBill(@RequestBody BillsUpdateDTO updatedBill, HttpServletRequest request) {
        Bill bill = billService.updateBill(updatedBill, request);
        return ResponseEntity.ok(new BillsDTO(bill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable UUID id, HttpServletRequest request) {
        billService.deleteBill(id, request);
        return ResponseEntity.noContent().build();
    }

}

