package com.medicore.medicore.comman.utils;

import com.medicore.medicore.account.entity.User;
import com.medicore.medicore.account.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        CustomUserDetails principal =
                (CustomUserDetails) authentication.getPrincipal();

        return principal.getUser();
    }
}
