package com.mcverse.jobify.auth.security;

import com.mcverse.jobify.auth.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Map<String, UserDetails> store = new ConcurrentHashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = store.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }

    public void save(String username, String encodedPassword, Role role) {
        if (store.containsKey(username)) {
            throw new IllegalArgumentException("Username already taken: " + username);
        }
        store.put(username, User.withUsername(username)
                .password(encodedPassword)
                .authorities(new SimpleGrantedAuthority("ROLE_" + role.name()))
                .build());
    }

    public boolean exists(String username) {
        return store.containsKey(username);
    }
}
