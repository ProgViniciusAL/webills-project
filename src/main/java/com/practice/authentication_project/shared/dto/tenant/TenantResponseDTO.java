package com.practice.authentication_project.shared.dto.tenant;

import com.practice.authentication_project.shared.dto.bills.BillsDTO;
import com.practice.authentication_project.shared.dto.user.UserResponseDTO;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public record TenantResponseDTO(String name, OffsetDateTime createdAt, List<UserResponseDTO> users, List<BillsDTO> bills) {

}
