package com.practice.authentication_project.domain.models.notification.service;

import com.practice.authentication_project.domain.models.notification.Notification;
import com.practice.authentication_project.domain.models.notification.repository.NotificationRepository;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.repository.UserRepository;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, TenantRepository tenantRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public Notification createNotification(Notification notification, Long userId, UUID tenantId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        notification.setUser(user);
        notification.setTenant(tenant);
        return notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Notification getNotificationById(UUID id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
    }

    @Transactional
    public Notification updateNotification(UUID id, Notification notificationDetails) {
        Notification existingNotification = getNotificationById(id);
        existingNotification.setTitle(notificationDetails.getTitle());
        existingNotification.setMessage(notificationDetails.getMessage());
        existingNotification.setRead(notificationDetails.isRead());
        // Mudar usuário ou tenant de uma notificação é incomum.
        return notificationRepository.save(existingNotification);
    }

    @Transactional
    public Notification markAsRead(UUID id) {
        Notification existingNotification = getNotificationById(id);
        existingNotification.setRead(true);
        return notificationRepository.save(existingNotification);
    }

    @Transactional
    public Notification markAsUnread(UUID id) {
        Notification existingNotification = getNotificationById(id);
        existingNotification.setRead(false);
        return notificationRepository.save(existingNotification);
    }

    @Transactional
    public void deleteNotification(UUID id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}