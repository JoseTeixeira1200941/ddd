package application;

import java.util.Optional;

import eapli.framework.infrastructure.authz.application.Authenticator;
import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.infrastructure.authz.application.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.validations.Preconditions;

/**
 * Authentication service to handle login and password change/reset.
 *
 * @author Paulo Gandra Sousa
 *
 */
@Component
public class AuthenticationService implements Authenticator {

    private final UserRepository repo;
    private final application.AuthorizationService authz;
    private final PasswordPolicy policy;
    private final PasswordEncoder encoder;

    /**
     *
     * @param repo
     *            a user repository
     * @param authz
     *            an authentication service
     * @param policy
     * @param encoder
     */
    @Autowired
    public AuthenticationService(final UserRepository repo, final AuthorizationService authz,
                                 final PasswordPolicy policy, final PasswordEncoder encoder) {
        Preconditions.noneNull(repo, authz, encoder);
        this.repo = repo;
        this.authz = authz;
        this.policy = policy;
        this.encoder = encoder;
    }

    /**
     * Allows a user to perform login. Checks if a user can be authenticated
     * with the username/password pair and if so creates a user session for it.
     *
     * @param username
     * @param rawPassword
     * @param requiredRoles
     * @return the authenticated user session or an empty optional otherwise
     * @throws IllegalArgumentException
     *             if the username or password cannot be constructed from the
     *             passed string arguments
     */
    @Override
    public Optional<UserSession> authenticate(final String username, final String rawPassword,
                                              final Role... requiredRoles) {
        Preconditions.nonEmpty(username, "a username must be provided");
        Preconditions.nonEmpty(rawPassword, "a password must be provided");

        final SystemUser newSession = retrieveUser(username)
                .filter(u -> u.passwordMatches(rawPassword, encoder) && u.isActive()
                        && (noActionRightsToValidate(requiredRoles) || u.hasAny(requiredRoles)))
                .orElse(null);

        return authz.createSessionForUser(newSession);
    }

    private boolean noActionRightsToValidate(final Role... onlyWithThis) {
        return onlyWithThis.length == 0 || (onlyWithThis.length == 1 && onlyWithThis[0] == null);
    }

    private Optional<SystemUser> retrieveUser(final String username) {
        return repo.ofIdentity(Username.valueOf(username));
    }

    /**
     * Change the password of a certain user. If the operation succeeds, the
     * {@code user} object reference is no longer valid and subsequent code
     * should use the value of the returned {@code Optional} to work with the
     * user.
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     * @return An optional which value is the changed SystemUser object or an
     *         empty Optional if the password could not be changed
     */
    public Optional<SystemUser> changePassword(final SystemUser user, final String oldPassword,
                                               final String newPassword) {
        if (user.passwordMatches(oldPassword, encoder)) {
            return Password.encodedAndValid(newPassword, policy, encoder).map(p -> {
                user.changePassword(p);
                return repo.save(user);
            });
        }

        return Optional.empty();
    }

    /**
     * Change the password of the currently logged in user.
     *
     * @param oldPassword
     * @param newPassword
     * @return true if the password could be changed. false if there is no user
     *         session or the password could not be changed
     */
    public boolean changePassword(final String oldPassword, final String newPassword) {
        return authz.session()
                .map(u -> changePassword(u.authenticatedUser(), oldPassword, newPassword))
                .map(u -> true)
                .orElse(false);
    }

    /**
     * Initiates a password reset request. If the operation succeeds, the
     * {@code user} object reference is no longer valid and subsequent code
     * should NOT use it.
     *
     * @param user
     * @return the reset token to be provided back in order to reset the
     *         password
     */
    public String resetPassword(final SystemUser user) {
        final String token = user.resetPassword();
        repo.save(user);
        return token;
    }

    /**
     * Resets the password of the user. If the operation succeeds, the
     * {@code user} object reference is no longer valid and subsequent code
     * should use the value of the returned {@code Optional} to work with the
     * user.
     *
     * @param user
     * @param token
     * @param newPass
     * @return an Optional which value is the changed SystemUser or an empty
     *         optional otherwise
     */
    public Optional<SystemUser> confirmResetPassword(final SystemUser user, final String token,
                                                     final Password newPass) {
        if (user.confirmResetPassword(token, newPass)) {
            return Optional.of(repo.save(user));
        }
        return Optional.empty();
    }
}