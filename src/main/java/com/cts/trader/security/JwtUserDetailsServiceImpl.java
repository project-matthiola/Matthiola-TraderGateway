package com.cts.trader.security;

import com.cts.trader.model.Trader;
import com.cts.trader.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TraderRepository traderRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Trader trader = traderRepository.findTraderByTraderName(username);
        if(trader == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            List<String> list = new ArrayList<>();
            list.add(trader.getRole());
            return new JwtUser(trader.getTraderName(), trader.getPassword(), list.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
    }
}
