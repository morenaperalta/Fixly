package com.femcoders.fixly.user.services;

import com.femcoders.fixly.shared.exception.EntityNotFoundException;
import com.femcoders.fixly.shared.security.CustomUserDetails;
import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {
    private static final String USERNAME_FIELD = "username";
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username).map(CustomUserDetails::new).orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), USERNAME_FIELD, username));
    }

    public User getAuthenticatedUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = userDetails.getUsername();

        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), USERNAME_FIELD, username));
    }

    public String extractRole(Authentication auth) {
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("NO_ROLE");
    }

    public boolean isAdmin(String role) {
        return "ROLE_ADMIN".equals(role);
    }

    public boolean isSupervisor(String role) {
        return "ROLE_SUPERVISOR".equals(role);
    }
    public boolean isClient(String role) {
        return "ROLE_CLIENT".equals(role);
    }
    public boolean isTechnician(String role) {
        return "ROLE_TECHNICIAN".equals(role);
    }
}
