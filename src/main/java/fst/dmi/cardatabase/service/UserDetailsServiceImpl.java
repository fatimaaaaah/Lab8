package fst.dmi.cardatabase.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import fst.dmi.cardatabase.domain.AppUser;
import fst.dmi.cardatabase.domain.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository repository;

    public UserDetailsServiceImpl(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = repository.findByUsername(username);
        if (user.isPresent()) {
            AppUser currentUser = user.get();
            return User.withUsername(username)
                    .password(currentUser.getPassword())
                    .roles(currentUser.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé.");
        }
    }
}
