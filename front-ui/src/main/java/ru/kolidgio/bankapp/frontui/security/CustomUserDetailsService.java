package ru.kolidgio.bankapp.frontui.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.frontui.client.AccountClient;
import ru.kolidgio.bankapp.frontui.dto.UserAuthDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountClient accountClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            UserAuthDto user = accountClient.getUserAuthByLogin(username);
            return new User(user.login(),
                    user.password(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }catch(Exception e){
            throw new UsernameNotFoundException("Пользователь не найден: " + username, e);
        }
    }



}
