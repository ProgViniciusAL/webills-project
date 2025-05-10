package com.practice.authentication_project.domain.models.account.service;

import com.practice.authentication_project.domain.models.account.Account;
import com.practice.authentication_project.domain.models.account.repository.AccountRepository;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TenantRepository tenantRepository; // Para associar a um tenant

    @Transactional
    public Account createAccount(Account account, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for account creation: " + tenantId));
        account.setTenant(tenant);
        // O saldo (balance) geralmente é inicializado ou gerenciado por transações, não diretamente no CRUD.
        // account.setBalance(BigDecimal.ZERO); // Exemplo de inicialização
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Poderia ter um metodo para buscar contas de um tenant específico
    // public List<Account> getAccountsByTenantId(UUID tenantId) { ... }

    @Transactional(readOnly = true)
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    @Transactional
    public Account updateAccount(UUID id, Account accountDetails) {
        Account existingAccount = getAccountById(id);
        existingAccount.setName(accountDetails.getName());
        existingAccount.setType(accountDetails.getType());
        // Atualizar o 'balance' diretamente aqui pode ser perigoso.
        // Geralmente o saldo é resultado de transações.
        // existingAccount.setBalance(accountDetails.getBalance());
        // Alterar o tenant de uma conta também é uma operação delicada.
        return accountRepository.save(existingAccount);
    }

    @Transactional
    public void deleteAccount(UUID id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}
