package application;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.time.util.CurrentTimeCalendars;

/**
 * User Management Service. Provides the typical application use cases for
 * managing {@link SystemUser}, e.g., adding, deactivating, listing, searching.
 *
 * @author Paulo Gandra de Sousa
 */
@Component
public class UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final PasswordPolicy policy;

    /**
     *
     * @param userRepo
     * @param encoder
     * @param policy
     */
    @Autowired
    public UserManagementService(final UserRepository userRepo, final PasswordPolicy policy,
                                 final PasswordEncoder encoder) {
        userRepository = userRepo;
        this.policy = policy;
        this.encoder = encoder;
    }

    /**
     * Registers a new user in the system allowing to specify when the user account
     * was created.
     *
     * @param username
     * @param rawPassword
     * @param firstName
     * @param lastName
     * @param email
     * @param roles
     * @param createdOn
     * @return the new user
     */
    @Transactional
    public SystemUser registerNewUser(final String username, final String rawPassword, final String firstName,
                                      final String lastName, final String email, final Set<Role> roles, final Calendar createdOn) {
        final var userBuilder = new SystemUserBuilder(policy, encoder);
        userBuilder.with(username, rawPassword, firstName, lastName, email).createdOn(createdOn).withRoles(roles);
        final var newUser = userBuilder.build();
        return userRepository.save(newUser);
    }

    /**
     * Registers a new user in the system.
     *
     * @param username
     * @param rawPassword
     * @param firstName
     * @param lastName
     * @param email
     * @param roles
     * @return the new user
     */
    @Transactional
    public SystemUser registerNewUser(final String username, final String rawPassword, final String firstName,
                                      final String lastName, final String email, final Set<Role> roles) {
        return registerNewUser(username, rawPassword, firstName, lastName, email, roles, CurrentTimeCalendars.now());
    }

    /**
     * Registers a new user in the system. Mostly useful for two-step
     * signup/registration process where the domain objects were already created by
     * another process, e.g., signup.
     *
     * @param username
     * @param password
     * @param name
     * @param email
     * @param roles
     * @return the enw user
     */
    @Transactional
    public SystemUser registerUser(final Username username, final Password password, final Name name,
                                   final EmailAddress email, final Set<Role> roles) {
        final var userBuilder = new SystemUserBuilder(policy, encoder);
        userBuilder.with(username, password, name, email).withRoles(roles);
        final var newUser = userBuilder.build();
        return userRepository.save(newUser);
    }

    /**
     *
     * @return all active users
     */
    public Iterable<SystemUser> activeUsers() {
        return userRepository.findByActive(true);
    }

    /**
     *
     * @return all deactivated users
     */
    public Iterable<SystemUser> deactivatedUsers() {
        return userRepository.findByActive(false);
    }

    /**
     *
     * @return all users no matter their status
     */
    public Iterable<SystemUser> allUsers() {
        return userRepository.findAll();
    }

    /**
     * Looks up a user by its username.
     *
     * @param id
     * @return an Optional which value is the user with the desired identify. an
     *         empty Optional if there is no user with that username
     */
    public Optional<SystemUser> userOfIdentity(final Username id) {
        return userRepository.ofIdentity(id);
    }

    /**
     * Deactivates a user. Client code must not reference the input parameter after
     * calling this method and must use the return object instead.
     *
     * @param user
     * @return the updated user.
     */
    @Transactional
    public SystemUser deactivateUser(final SystemUser user) {
        user.deactivate(CurrentTimeCalendars.now());
        return userRepository.save(user);
    }
}
