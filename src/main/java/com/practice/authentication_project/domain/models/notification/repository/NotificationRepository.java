package com.practice.authentication_project.domain.models.notification.repository;

import com.practice.authentication_project.domain.models.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
