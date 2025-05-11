package com.practice.authentication_project.domain.models.user.service;

import com.practice.authentication_project.domain.models.user.Role;
import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.repository.UserRepository;
import com.practice.authentication_project.shared.dto.RegisterDTO;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// Importar BCryptPasswordEncoder se for usar para senhas
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity createUser(RegisterDTO registerDTO) {

        UserEntity newUser = new UserEntity();

        newUser.setName(registerDTO.username());
        newUser.setEmail(registerDTO.email());
        newUser.setPasswordHash(passwordEncoder.encode(registerDTO.password()));
        newUser.setRole(Role.valueOf(registerDTO.role()));

        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Transactional
    public UserEntity updateUser(Long id, UserEntity userDetails) {
        UserEntity existingUser = getUserById(id);
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        // Não atualize a senha diretamente aqui, crie um metodo específico para mudança de senha.
        // existingUser.setPasswordHash(userDetails.getPasswordHash()); // Evitar
        // Atualizar tenants (Set<Tenant>) requer lógica específica
        return userRepository.save(existingUser);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
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
