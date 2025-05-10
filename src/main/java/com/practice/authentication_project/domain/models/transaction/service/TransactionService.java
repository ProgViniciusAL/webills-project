package com.practice.authentication_project.domain.models.transaction.service;

import com.practice.authentication_project.domain.models.account.Account;
import com.practice.authentication_project.domain.models.account.repository.AccountRepository;
import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.bill.repository.BillRepository;
import com.practice.authentication_project.domain.models.category.Category;
import com.practice.authentication_project.domain.models.category.repository.CategoryRepository;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.domain.models.transaction.Transaction;
import com.practice.authentication_project.domain.models.transaction.repository.TransactionRepository;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Transactional
    public Transaction createTransaction(Transaction transaction, UUID accountId, Long categoryId, UUID billId, UUID tenantId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        transaction.setAccount(account);
        transaction.setTenant(tenant);

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
            transaction.setCategory(category);
        }
        if (billId != null) {
            Bill bill = billRepository.findById(billId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billId));
            transaction.setBill(bill);
        }

        // Lógica de atualização de saldo da conta deveria ocorrer aqui
        account.setBalance(account.getBalance().add(transaction.getAmount()));
        accountRepository.save(account);

        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
    }

    // Transações são geralmente imutáveis. Um metodo de "update" pode não ser apropriado.

    @Transactional
    public void deleteTransaction(Long id) {
        Transaction transaction = getTransactionById(id); // Garante que existe antes de tentar deletar
        // Lógica de estorno de saldo da conta deveria ocorrer aqui
        // Account account = transaction.getAccount();
        // account.setBalance(account.getBalance().subtract(transaction.getAmount())); // Exemplo simplificado
        // accountRepository.save(account);
        transactionRepository.deleteById(id);
    }
}
