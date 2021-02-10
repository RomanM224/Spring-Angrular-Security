package com.maistruk.springangular.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maistruk.springangular.model.User;
import com.maistruk.springangular.repo.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("userDetailsServiceImpl")
public class UserDetailsServicceImpl implements UserDetailsService{
    
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        UserDetails fromUser = SecurityUser.fromUser(user);
        
        return SecurityUser.fromUser(user);
    }
}
