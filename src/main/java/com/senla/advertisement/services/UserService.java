package com.senla.advertisement.services;

import com.senla.advertisement.dao.MessageDAO;
import com.senla.advertisement.dao.PurchaseDAO;
import com.senla.advertisement.dao.RoleDAO;
import com.senla.advertisement.dao.UserDAO;
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
import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.entities.Role;
import com.senla.advertisement.entities.User;
import com.senla.advertisement.security.jwt.JwtProvider;
import com.senla.advertisement.services.interfaces.IUserService;
import com.senla.advertisement.utils.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService implements IUserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PurchaseDAO purchaseDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        return new UserDTO(userDAO.findByUsername(username));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByToken(String token) {
        String username = jwtProvider.getUsername(token);
        return new UserDTO(userDAO.findByUsername(username));
    }

    @Override
    @Transactional
    public boolean deleteByUsername(String token) {
        String username = jwtProvider.getUsername(token);

        userDAO.deleteByUsername(username);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getList() {
        return userDAO.findAll().stream().map(UserDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public int count() {
        return (int) userDAO.count();
    }

    @Override
    @Transactional
    public UserDTO register(RegistrationDTO registrationData) {
        Role role = roleDAO.findByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setEmail(registrationData.getEmail());
        user.setUsername(registrationData.getUsername());
        user.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        user.setRoles(roles);
        user.setMoney(0);
        user.setRegisteredSince(LocalDateTime.now());
        user.setStatus(Status.ALIVE);

        return new UserDTO(userDAO.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationData) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationData.getUsername(), authenticationData.getPassword()));
        User user = userDAO.findByUsername(authenticationData.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("User with " + authenticationData.getUsername() + " username not found");
        }

        String token = jwtProvider.createToken(authenticationData.getUsername(), new ArrayList<>(user.getRoles()));

        return new AuthenticationResponseDTO(user.getId(), user.getUsername(), token);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getSalesHistoryByUsername(String username, String token) {
        User user = userDAO.findByUsername(username);
        User requestedUser = userDAO.findByUsername(jwtProvider.getUsername(token));
        List<PurchaseDTO> purchases = null;
        if (user == requestedUser ||
                requestedUser.getRoles().stream().map(role -> role.getName()).toList().contains("MODERATOR") ||
                requestedUser.getRoles().stream().map(role -> role.getName()).toList().contains("ADMIN"))
             purchases = purchaseDAO.getSalesHistoryById(user.getId()).stream().map(PurchaseDTO::new).toList();
        return purchases;
    }


    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getRolesByUsername(String username) {
        return userDAO.findByUsername(username).getRoles().stream().map(role -> new RoleDTO(role.getName())).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementResponseDTO> getAdvertisementsByUsername(String username) {
        User user = userDAO.findByUsername(username);
        return user.getAdvertisements().stream().map(AdvertisementResponseDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getPurchasesByUsername(String username, String token) {
        User user = userDAO.findByUsername(username);
        User requestedUser = userDAO.findByUsername(jwtProvider.getUsername(token));
        if (user == requestedUser ||
                requestedUser.getRoles().stream().map(Role::getName).toList().contains("ADMIN")) {
            return user.getPurchases().stream().map(PurchaseDTO::new).toList();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDTO> getChatWith(String username, String token) {
        User anotherUser = userDAO.findByUsername(username);
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        return messageDAO.getChat(user.getId(), anotherUser.getId()).stream().map(MessageDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByUsername(String username) {
        User user = userDAO.findByUsername(username);
        return user.getComments().stream().map(CommentDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementRateResponseDTO> getRatesByUsername(String username, String token) {
        String requestedUsername = jwtProvider.getUsername(token);
        User user = userDAO.findByUsername(username);
        User requestedUser = userDAO.findByUsername(requestedUsername);
        if (user == requestedUser ||
                requestedUser.getRoles().stream().map(role -> role.getName()).toList().contains("MODERATOR") ||
                requestedUser.getRoles().stream().map(role -> role.getName()).toList().contains("ADMIN")) {
            return user.getRates().stream().map(AdvertisementRateResponseDTO::new).toList();
        }
        return null;
    }

    @Override
    @Transactional
    public boolean updateByUsername(ChangedUserDTO changedUser, String token) {
        String username = jwtProvider.getUsername(token);
        User user = userDAO.findByUsername(username);

        String changedUsername = changedUser.getUsername();
        if (changedUsername != null && !changedUsername.equals(user.getUsername())) {
            user.setUsername(changedUsername);
        }

        String changedName = changedUser.getName();
        if (changedName != null && !changedName.equals(user.getName()))
            user.setName(changedUser.getName());

        String phone = changedUser.getPhone();
        if (phone != null && !phone.equals(user.getPhone()))
            user.setPhone(changedUser.getPhone());

        String email = changedUser.getEmail();
        if (email != null && !email.equals(user.getEmail()))
            user.setEmail(changedUser.getEmail());

        String about = changedUser.getAbout();
        if (about != null && !about.equals(user.getAbout()))
            user.setAbout(changedUser.getAbout());

        return true;
    }


    @Override
    @Transactional
    public boolean changePasswordByUsername(String oldPassword, String password, String token) {
        String username = jwtProvider.getUsername(token);
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        User user = userDAO.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return true;
    }

    @Override
    @Transactional
    public UserDTO changeStatusByUsername(String username, Status status) {
        User user = userDAO.findByUsername(username);
        user.setStatus(status);
        for (Advertisement advertisement : user.getAdvertisements()) {
            advertisement.setStatus(status);
        }
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public Set<Role> addRoleByUsername(String username, String roleName) {
        User user = userDAO.findByUsername(username);
        Role role = roleDAO.findByName(roleName);
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        return roles;
    }

    @Override
    @Transactional
    public Set<Role> deleteRoleByUsername(String username, String roleName) {
        User user = userDAO.findByUsername(username);
        Role role = roleDAO.findByName(roleName);
        Set<Role> roles = user.getRoles();

        if (roles.contains(role)) {
            roles.remove(role);
            user.setRoles(roles);
        }

        return roles;
    }
}
