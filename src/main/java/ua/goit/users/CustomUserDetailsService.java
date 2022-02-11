package ua.goit.users;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

//public class CustomUserDetailsService implements UserDetailsService {
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @Autowired
//  private PasswordEncoder passwordEncoder;
//
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user = userRepository.findUserByName(username);
//    if (user == null) {
//      throw new UsernameNotFoundException(
//          "User with that combination username and password was not found");
//    }
//    return new MyUserDetails(user, passwordEncoder);
//  }
//
//  public static class MyUserDetails implements UserDetails {
//
//    private final User user;
//    PasswordEncoder passwordEncoder;
//
//    public MyUserDetails(User user, PasswordEncoder passwordEncoder) {
//      this.user = user;
//      this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//      return user.getRoles().stream()
//          .map(role -> new SimpleGrantedAuthority(role.getName()))
//          .collect(Collectors.toList());
//    }
//
////      @Override
////      public String getPassword() {
////          return passwordEncoder.encode(this.user.getPassword());
////      }
//
//    @Override
//    public String getPassword() {
//      return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//      return this.user.getUserName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//      return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//      return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//      return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//      return true;
//    }
//  }
//}