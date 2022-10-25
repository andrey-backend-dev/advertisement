package com.senla.advertisement.controllers;

import com.senla.advertisement.dto.AdvertisementRateResponseDTO;
import com.senla.advertisement.dto.AdvertisementResponseDTO;
import com.senla.advertisement.dto.AuthenticationRequestDTO;
import com.senla.advertisement.dto.AuthenticationResponseDTO;
import com.senla.advertisement.dto.ChangedUserDTO;
import com.senla.advertisement.dto.CommentDTO;
import com.senla.advertisement.dto.MessageDTO;
import com.senla.advertisement.dto.PasswordDTO;
import com.senla.advertisement.dto.PurchaseDTO;
import com.senla.advertisement.dto.RegistrationDTO;
import com.senla.advertisement.dto.RoleDTO;
import com.senla.advertisement.dto.StatusDTO;
import com.senla.advertisement.dto.UserDTO;
import com.senla.advertisement.entities.Role;
import com.senla.advertisement.services.interfaces.IUserService;
import com.senla.advertisement.utils.wrappers.BooleanWrapper;
import com.senla.advertisement.utils.wrappers.NumWrapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private Logger logger;

// hibernate validation
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findByUsername(@PathVariable("username") String username) {
        logger.info("Method 'findByUsername' with parameters (username: {}) is called from User Controller at {}",
                username, LocalDateTime.now());
        return userService.findByUsername(username);
    }

    @GetMapping(value = "/my-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'findByToken' is called from User Controller at {}", LocalDateTime.now());
        return userService.findByToken(token);
    }

    @DeleteMapping(value = "/my-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooleanWrapper deleteByUsername(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'deleteByUsername' is called from User Controller at {}", LocalDateTime.now());
        return new BooleanWrapper(userService.deleteByUsername(token));
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getList() {
        logger.info("Method 'getList' is called from User Controller at {}", LocalDateTime.now());
        return userService.getList();
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public NumWrapper count() {
        logger.info("Method 'count' is called from User Controller at {}", LocalDateTime.now());
        return new NumWrapper(userService.count());
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO register(@RequestBody RegistrationDTO registrationData) {
        logger.info("Method 'register' with parameters (username: {}, email: {}) is called from User Controller at {}",
                registrationData.getUsername(), registrationData.getEmail(), LocalDateTime.now());
        return userService.register(registrationData);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationResponseDTO login(@RequestBody AuthenticationRequestDTO authenticationData) {
        logger.info("Method 'login' with parameters (username: {}) is called from User Controller at {}",
                authenticationData.getUsername(), LocalDateTime.now());
        return userService.login(authenticationData);
    }

    @GetMapping(value = "/{username}/sales-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseDTO> getSalesHistoryById(@PathVariable("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'getSalesHistoryById' with parameters (username: {}) is called from User Controller at {}",
                username, LocalDateTime.now());
        return userService.getSalesHistoryByUsername(username, token);
    }

    @GetMapping(value = "/{username}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> getRolesByUsername(@PathVariable("username") String username) {
        logger.info("Method 'getRolesByUsername' with parameters (username: {}) is called from User Controller at {}",
                username, LocalDateTime.now());
        return userService.getRolesByUsername(username);
    }

    @GetMapping(value = "/{username}/advertisements", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdvertisementResponseDTO> getAdvertisementsByUsername(@PathVariable("username") String username) {
        logger.info("Method 'getAdvertisementsByUsername' with parameters (username: {}) is called from User Controller at {}",
                username, LocalDateTime.now());
        return userService.getAdvertisementsByUsername(username);
    }

    @GetMapping(value = "/{username}/purchases", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseDTO> getPurchasesByUsername(@PathVariable("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'getPurchasesByUsername' with parameters (username: {}) is called from User Controller at {}",
                username, LocalDateTime.now());
        return userService.getPurchasesByUsername(username, token);
    }

    @GetMapping(value = "/chat/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDTO> getChatWith(@PathVariable("username") String username,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'getChatWith' with parameters (username: {}) is called from User Controller at {}",
                username, LocalDateTime.now());
        return userService.getChatWith(username, token);
    }

    @GetMapping(value = "/{username}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentDTO> getCommentsByUsername(@PathVariable("username") String username) {
        logger.info("Method 'getCommentsByUsername' with parameters (username: {}) is called from User Controller at {}",
                username, LocalDateTime.now());
        return userService.getCommentsByUsername(username);
    }

    @GetMapping(value = "/{username}/rates", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdvertisementRateResponseDTO> getRatesByUsername(@PathVariable("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        return userService.getRatesByUsername(username, token);
    }

    @PutMapping(value = "/my-account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BooleanWrapper updateByUsername(@RequestBody ChangedUserDTO changedUser, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'updateByUsername' with parameters (username: {}, name: {}, phone: {}, email: {}, about: {})" +
                        " is called from User Controller at {}",
                changedUser.getUsername(), changedUser.getName(), changedUser.getPhone(), changedUser.getEmail(),
                changedUser.getEmail(), LocalDateTime.now());
        return new BooleanWrapper(userService.updateByUsername(changedUser, token));
    }

    @PatchMapping(value = "/my-account/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooleanWrapper changePasswordByUsername(@RequestBody PasswordDTO passwordDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'changePasswordByUsername' is called from User Controller at {}", LocalDateTime.now());
        return new BooleanWrapper(userService.changePasswordByUsername(passwordDTO.getOldPassword(), passwordDTO.getPassword(), token));
    }

    @PatchMapping(value = "/{username}/change-status", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO changeStatusByUsername(@PathVariable("username") String username, @RequestBody StatusDTO statusDTO) {
        logger.info("Method 'changeStatusByUsername' with parameters (username: {}, status: {}) is called from User Controller at {}",
                username, statusDTO.getStatus(), LocalDateTime.now());
        return userService.changeStatusByUsername(username, statusDTO.getStatus());
    }

    @PatchMapping(value = "/{username}/add-role", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Role> addRoleByUsername(@PathVariable("username") String username, @RequestBody RoleDTO roleDTO) {
        logger.info("Method 'addRoleByUsername' with parameters (username: {}, role: {}) is called from User Controller at {}",
                username, roleDTO.getName(), LocalDateTime.now());
        return userService.addRoleByUsername(username, roleDTO.getName());
    }

    @DeleteMapping(value = "/{username}/delete-role", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Role> deleteRoleByUsername(@PathVariable("username") String username, @RequestBody RoleDTO roleDTO) {
        logger.info("Method 'deleteRoleByUsername' with parameters (username: {}, role: {}) is called from User Controller at {}",
                username, roleDTO.getName(), LocalDateTime.now());
        return userService.deleteRoleByUsername(username, roleDTO.getName());
    }
}
