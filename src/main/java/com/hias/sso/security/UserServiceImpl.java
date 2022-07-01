package com.hias.sso.security;

import com.hias.entity.sso.User;
import com.hias.repository.sso.UserRepository;
import com.hias.security.dto.UserDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if (!optionalUser.isPresent()) {
            log.info("[loadUserByUsername] User with username : {} not found in the system.", username);
            throw new UsernameNotFoundException("User not found in the system.");
        }
        User user = optionalUser.get();
        log.info("[loadUserByUsername] User with username : {} found in the system.", username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getUserRoleList().forEach(userRole -> {
            authorities.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName()));
        });
        return new UserDetail(user.getUserName(), user.getPassword(), authorities);
    }
}
