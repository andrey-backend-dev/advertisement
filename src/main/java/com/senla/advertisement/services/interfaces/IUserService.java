package com.senla.advertisement.services.interfaces;

import com.senla.advertisement.dto.AdvertisementRateResponseDTO;
import com.senla.advertisement.dto.AdvertisementResponseDTO;
import com.senla.advertisement.dto.AuthenticationRequestDTO;
import com.senla.advertisement.dto.AuthenticationResponseDTO;
import com.senla.advertisement.dto.ChangedUserDTO;
import com.senla.advertisement.dto.CommentDTO;
import com.senla.advertisement.dto.MessageDTO;
import com.senla.advertisement.dto.PurchaseDTO;
import com.senla.advertisement.dto.RegistrationDTO;
import com.senla.advertisement.dto.RoleDTO;
import com.senla.advertisement.dto.UserDTO;
import com.senla.advertisement.entities.Role;
import com.senla.advertisement.utils.enums.Status;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserDTO findByUsername(String username);
    UserDTO findByToken(String token);
    boolean deleteByUsername(String token);
    List<UserDTO> getList();
    int count();
    UserDTO register(RegistrationDTO registrationData);
    AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationData);
    List<PurchaseDTO> getSalesHistoryByUsername(String username, String token);
    List<RoleDTO> getRolesByUsername(String username);
    List<AdvertisementResponseDTO> getAdvertisementsByUsername(String username);
    List<PurchaseDTO> getPurchasesByUsername(String username, String token);
    List<MessageDTO> getChatWith(String username, String token);
    List<CommentDTO> getCommentsByUsername(String username);
    List<AdvertisementRateResponseDTO> getRatesByUsername(String username, String token);
    boolean updateByUsername(ChangedUserDTO changedUserDTO, String token);
    boolean changePasswordByUsername(String oldPassword, String password, String token);
    UserDTO changeStatusByUsername(String username, Status status);
    Set<Role> addRoleByUsername(String username, String roleName);
    Set<Role> deleteRoleByUsername(String username, String roleName);
}
