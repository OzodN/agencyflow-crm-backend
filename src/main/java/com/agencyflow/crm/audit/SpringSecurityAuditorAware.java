package com.agencyflow.crm.audit;

import com.agencyflow.crm.security.userdetails.CustomUserDetails;
import com.agencyflow.crm.user.model.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Override
    public @NonNull Optional<User> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(CustomUserDetails.class::isInstance)
                .map(CustomUserDetails.class::cast)
                .map(CustomUserDetails::getUser);
    }
}
