package com.practice.authentication_project.domain.models.user.service;

import com.practice.authentication_project.domain.models.user.User;
import com.practice.authentication_project.domain.models.user.repository.UserRepository;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// Importar BCryptPasswordEncoder se for usar para senhas
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    // private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        // Exemplo: Encriptar senha antes de salvar
        // user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        // Verificar se email já existe, etc.
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User existingUser = getUserById(id);
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        // Não atualize a senha diretamente aqui, crie um metodo específico para mudança de senha.
        // existingUser.setPasswordHash(userDetails.getPasswordHash()); // Evitar
        // Atualizar tenants (Set<Tenant>) requer lógica específica
        return userRepository.save(existingUser);
    }

    // Exemplo de metodo para atualizar senha (mais seguro)
    /*
    @Transactional
    public User updateUserPassword(Long id, String newPassword) {
        User existingUser = getUserById(id);
        existingUser.setPasswordHash(passwordEncoder.encode(newPassword));
        return userRepository.save(existingUser);
    }
    */

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
