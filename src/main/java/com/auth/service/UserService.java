package com.api.service;

import com.api.dto.CreateUserDTO;
import com.api.dto.LoginUserDTO;
import com.api.dto.RecoveryJwtTokenDTO;
import com.api.model.Role;
import com.api.model.RoleName;
import com.api.model.User;
import com.api.repository.UserRepository;
import com.api.security.JwtTokenService;
import com.api.security.UserDetailsImpl;
import com.api.security.config.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
//	@Autowired
//	private PasswordEncoder encoder;

//	public User saveUser(User user) {
//		String password = user.getPassword();
//		user.setPassword(encoder.encode(password));
//		return userRepository.save(user);
//	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private SecurityConfiguration securityConfiguration;

	//Autentica um usuárioe retorna um token JWT
	public RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDTO) {
		//Cria um objeto de autenticação com o username e o password fornecido pelo usuário
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(loginUserDTO.username(), loginUserDTO.password());

		//Autentica o usuário com as credenciais fornecidas
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		//Obtém o UserDetails com o usuário autenticado
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		//Gera um token JWT para o usuário autenticado
		return new RecoveryJwtTokenDTO(jwtTokenService.generationToken(userDetails));

	}

    public void createUser(CreateUserDTO createUserDTO) {
		User newUser = new User(createUserDTO.name(), createUserDTO.username(),
				createUserDTO.password(), List.of(new Role(RoleName.ROLE_COMMON_USER)));
		userRepository.save(newUser);

    }
}
