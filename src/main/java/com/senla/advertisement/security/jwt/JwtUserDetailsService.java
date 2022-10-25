package com.senla.advertisement.security.jwt;

import com.senla.advertisement.dao.UserDAO;
import com.senla.advertisement.entities.User;
import com.senla.advertisement.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException(username);

        return new JwtUser(user);
    }
}
