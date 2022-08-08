package com.hias.controller;

import com.hias.constant.ErrorMessageCode;
import com.hias.entity.auth.Role;
import com.hias.entity.auth.User;
import com.hias.entity.auth.UserRole;
import com.hias.model.request.LoginRequestDTO;
import com.hias.model.request.UserRequestDTO;
import com.hias.model.response.TokenResponseDTO;
import com.hias.repository.auth.RoleRepository;
import com.hias.repository.auth.UserRepository;
import com.hias.repository.auth.UserRoleRepository;
import com.hias.security.JwtTokenProvider;
import com.hias.security.dto.UserDetail;
import com.hias.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/auth/")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        } catch (Exception exception) {
            log.error("Username or password is incorrect");
            return new ResponseEntity<>(MessageUtils.get().getMessage(ErrorMessageCode.USERNAME_OR_PASSWORD_INCORRECT),
                    HttpStatus.FORBIDDEN);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        TokenResponseDTO tokenResponseDTO = jwtTokenProvider.getTokenResponseDTO(userDetail);
        return new ResponseEntity<>(tokenResponseDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("create-user")
    public ResponseEntity<String> create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        Optional<Role> optionalRole = roleRepository.findByRoleNameAndIsDeletedIsFalse(userRequestDTO.getRoleName());
        User user = userRepository.save(User.builder()
                .userName(userRequestDTO.getUsername())
                .password(encoder.encode(userRequestDTO.getPassword()))
                .employeeNo(userRequestDTO.getEmployeeNo())
                .memberNo(userRequestDTO.getMemberNo())
                .clientNo(userRequestDTO.getClientNo())
                .serviceProviderNo(userRequestDTO.getServiceProviderNo())
                .build());
        userRoleRepository.save(UserRole.builder()
                .user(user)
                .role(optionalRole.get())
                .build());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
