package dnd.auction.domain.user.security;

import dnd.auction.domain.user.entity.AppUser;
import dnd.auction.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final AppUser appUser = userRepository.findByEmail(email);

        if (appUser == null) {
            throw new UsernameNotFoundException("email '" + email + "' not found");
        }

        return User//
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())//
                .authorities(appUser.getAppUserRoles())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}