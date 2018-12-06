package com.ydt.security;

import com.ydt.dao.UserDAO;
import com.ydt.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ydt.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
    UserRepository userRepository;
	@Autowired
	UserDAO userDAO;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user = null;
		try {
		user = userRepository.findByUserName(username);
		}catch (Exception e) {
			new UsernameNotFoundException("User not found with username or email : " + username);
		}
        return UserPrincipal.create(user);
	}

	@Transactional
    public UserDetails loadUserById(int id) {
		Users user = null;
		try {
			user = userDAO.getUserById(id);
		}catch (Exception e){
			new UsernameNotFoundException("User not found with id : " + id);
		}
        return UserPrincipal.create(user);
    }

}
