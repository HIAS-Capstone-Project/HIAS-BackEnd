package com.hias.service.auth;

import com.hias.entity.auth.User;
import com.hias.repository.auth.UserRepository;
import com.hias.security.dto.UserDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private static List<String> fieldsHasLongValue = Stream
            .of("clientNo", "memberNo", "employeeNo", "serviceProviderNo")
            .collect(Collectors.toList());

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
        Long primaryKey = getEntityPrimaryKey(user);
        return new UserDetail(user.getUserName(), user.getPassword(), authorities, primaryKey);
    }

    private Long getEntityPrimaryKey(User user) {
        Long primaryKey = null;
        for (String fieldName : fieldsHasLongValue) {
            Field field;
            try {
                field = user.getClass().getDeclaredField(fieldName);
                field.setAccessible(Boolean.TRUE);
                Object value = field.get(user);
                if (value != null) {
                    primaryKey = (Long) value;
                    break;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return primaryKey;
    }
}
