package com.medicore.medicore.account;

import com.medicore.medicore.account.dto.AuthResponse;
import com.medicore.medicore.account.dto.LoginRequest;
import com.medicore.medicore.account.dto.RegisterRequest;
import com.medicore.medicore.account.entity.Role;
import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.account.security.CustomUserDetails;
import com.medicore.medicore.account.security.JwtService;
import com.medicore.medicore.comman.exception.custome.UnauthorizeAccessException;
import com.medicore.medicore.comman.exception.custome.UserAlreadyExistsException;
import com.medicore.medicore.comman.utils.UserUtils;
import com.medicore.medicore.hospital.HospitalProfile;
import com.medicore.medicore.hospital.HospitalRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserUtils userUtils;
    private final HospitalRepo hospitalRepo;


    public AuthResponse register(RegisterRequest request) {

        if (userRepo.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .email(request.email())
                .fullName(request.fullName())
                .role(request.role())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User savedUser = userRepo.save(user);
        String token = jwtService.generateToken(new CustomUserDetails(savedUser));

        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }


    public AuthResponse login(LoginRequest request) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        CustomUserDetails principal = (CustomUserDetails) authenticate.getPrincipal();
        User user = principal.getUser();
        String token = jwtService.generateToken(principal);
        return new AuthResponse(
                token,
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public AuthResponse registerDoctor(RegisterRequest request) {
        User admin = userUtils.getCurrentUser();
        if (admin.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizeAccessException("Only admin can register hospitalProfile");
        }
        if (userRepo.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }


        log.info("email is {}", request.email());
        log.info("full name is {}", request.fullName());
        log.info("password is {}", request.password());
        log.info("role is {}", request.role());
        User hospital = User.builder()
                .email(request.email())
                .fullName(request.fullName())
                .role(Role.ROLE_HOSPITAL)
                .password(passwordEncoder.encode(request.password()))
                .build();

        User savedHospital = userRepo.save(hospital);

        HospitalProfile hospitalProfile = new HospitalProfile();
        hospitalProfile.setRegisteredBy(admin);
        hospitalProfile.setHospitalName(savedHospital.getFullName());
        hospitalProfile.setHospitalAdministrator(savedHospital);
        hospitalRepo.save(hospitalProfile);

        String token = jwtService.generateToken(new CustomUserDetails(hospital));
        return new AuthResponse(
                token,
                savedHospital.getId(),
                savedHospital.getFullName(),
                savedHospital.getEmail(),
                savedHospital.getRole()
        );

    }
}
