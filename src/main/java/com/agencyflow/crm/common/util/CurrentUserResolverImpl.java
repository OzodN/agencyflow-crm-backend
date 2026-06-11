package com.agencyflow.crm.common.util;

import com.agencyflow.crm.common.exception.BusinessException;
import com.agencyflow.crm.common.exception.EntityNotFoundException;
import com.agencyflow.crm.user.model.User;
import com.agencyflow.crm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserResolverImpl implements CurrentUserResolver {

    private final UserRepository userRepository;

    @Override
    public @NonNull User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new BusinessException("No authenticated user found");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException("Authenticated user not found")
                );
    }
}
