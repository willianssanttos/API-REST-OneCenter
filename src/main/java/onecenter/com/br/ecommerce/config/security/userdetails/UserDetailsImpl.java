package onecenter.com.br.ecommerce.config.security.userdetails;

import onecenter.com.br.ecommerce.pessoa.entity.PessoaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    @Autowired
    private PessoaEntity login;

    public UserDetailsImpl(PessoaEntity login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return login.getNomeRole()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNomeRole().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return login.getSenha();
    }

    @Override
    public String getUsername() {
        return login.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
