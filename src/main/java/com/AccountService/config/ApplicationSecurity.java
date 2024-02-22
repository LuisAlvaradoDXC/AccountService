package com.AccountService.config;

import com.AccountService.jwt.JwtTokenFilter;
import com.AccountService.model.ERole;
import com.AccountService.model.User;
import com.AccountService.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class ApplicationSecurity {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationSecurity.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                List<User> listaRole = new ArrayList<>();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                User gestor = new User(1, "gestor@gmail.com", "password", ERole.GESTOR);
                String enc_password_gestor = passwordEncoder.encode(gestor.getPassword());
                gestor.setPassword(enc_password_gestor);

                User cliente = new User(2, "cliente@gmail.com", "password", ERole.CLIENTE);
                String enc_password_cliente = passwordEncoder.encode(cliente.getPassword());
                gestor.setPassword(enc_password_cliente);

                listaRole.add(gestor);
                listaRole.add(cliente);

                User userSuccessfull = new User();
                for (User usuario : listaRole) {
                    if (email.equals(usuario.getEmail())) {
                        userSuccessfull = usuario;
                        break;
                    }else {
                        throw new UsernameNotFoundException("Email no encontrado");
                    }
                }
                return userSuccessfull;
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        logger.info("Entra authenticationManager!!!!");
        return authConfig.getAuthenticationManager();
    }

/*    @Bean
    public InMemoryUserDetailsManager userDetailsServiceCliente() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("cliente@gmail.com")
                .password("password")
                .roles(String.valueOf(ERole.CLIENTE))
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsServiceGestor() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("gestor@gmail.com")
                .password("password")
                .roles(String.valueOf(ERole.GESTOR))
                .build();
        return new InMemoryUserDetailsManager(user);
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/cuentas/**").hasAnyAuthority(ERole.GESTOR.name())//Para acceder a productos debe ser GESTOR
                        .antMatchers("/cuentas/addMoney/**", "/cuentas/withdrawMoney/**").hasAnyAuthority(ERole.CLIENTE.name()) //cliente puede sacar y retirar dinero
                        .antMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated()
                );

        httpSecurity.exceptionHandling((exception) -> exception.authenticationEntryPoint(
                (request, response, ex) -> {
                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            ex.getMessage()
                    );
                }
        ));

        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }
}
