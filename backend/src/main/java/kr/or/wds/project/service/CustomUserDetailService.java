package kr.or.wds.project.service;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.or.wds.project.common.UserRole;
import kr.or.wds.project.entity.UserEntity;
import kr.or.wds.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity member = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        GrantedAuthority authority = createAuthority(member.getRole());
        return new User(member.getEmail(), member.getPassword(), Collections.singletonList(authority));
    }

    private GrantedAuthority createAuthority(UserRole role) {
        return new SimpleGrantedAuthority(role.getValue());
    }
}
