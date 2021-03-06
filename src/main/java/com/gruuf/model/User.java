package com.gruuf.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.auth.Token;
import com.gruuf.web.GruufAuth;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    public static final String EMAIL = "email";

    @Id
    private String id;
    @Index
    private String email;
    private String password;
    @Index
    private Set<Token> tokens;

    private String firstName;
    private String lastName;
    private UserLocale userLocale;
    private boolean notify;
    @Index
    private String facebookId;

    private Date timestamp;

    private Set<PolicyType> acceptedPolicies;

    private User() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public boolean hasAnyToken(Token[] tokens) {
        for (Token token : tokens) {
            if (this.tokens.contains(token)) {
                return true;
            }
        }
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserLocale getUserLocale() {
        return userLocale != null ? userLocale : UserLocale.EN;
    }

    public boolean isNotify() {
        return notify;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Set<PolicyType> getAcceptedPolicies() {
        return acceptedPolicies;
    }

    public String getFullName() {
        if (firstName == null && lastName == null) {
            return email;
        }
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", tokens=" + tokens +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userLocale=" + userLocale +
                ", notify=" + notify +
                ", facebookId='" + facebookId + '\'' +
                ", timestamp=" + timestamp +
                ", acceptedPolicies=" + acceptedPolicies +
                '}';
    }

    public static UserCreator create() {
        return new UserCreator(GruufAuth.generateUUID());
    }

    public static UserCreator clone(User user) {
        return new UserCreator(user.getId())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withUserLocale(user.getUserLocale())
                .withTokens(user.getTokens())
                .withAcceptedPolicies(user.getAcceptedPolicies())
                .withFacebookId(user.getFacebookId());
    }

    public boolean isPrivacyPolicyAccepted() {
        return acceptedPolicies != null && acceptedPolicies.contains(PolicyType.PRIVACY_POLICY);
    }

    public static class UserCreator {

        private User target;

        private UserCreator(String id) {
            target = new User();
            target.id = id;
            target.userLocale = UserLocale.EN;
            target.notify = true;
            target.timestamp = new Date();
        }

        public UserCreator withEmail(String email) {
            target.email = email;
            return this;
        }

        public UserCreator withPassword(String password) {
            target.password = password;
            return this;
        }

        public UserCreator withToken(Token token) {
            if (target.tokens == null) {
                target.tokens = new HashSet<>();
            }
            target.tokens.add(token);
            return this;
        }

        public UserCreator withTokens(Set<Token> tokens) {
            if (target.tokens == null) {
                target.tokens = new HashSet<>();
            }
            target.tokens.addAll(tokens);
            return this;
        }

        public UserCreator withFirstName(String firstName) {
            target.firstName = firstName;
            return this;
        }

        public UserCreator withLastName(String lastName) {
            target.lastName = lastName;
            return this;
        }

        public UserCreator replaceTokens(Set<Token> tokens) {
            target.tokens = tokens;
            return this;
        }

        public UserCreator withUserLocale(UserLocale locale) {
            target.userLocale = locale;
            return this;
        }

        public UserCreator withNotify(boolean notify) {
            target.notify = notify;
            return this;
        }

        public UserCreator withFacebookId(String facebookId) {
            target.facebookId = facebookId;
            return this;
        }

        public UserCreator withAcceptedPolicies(Set<PolicyType> acceptedPolicies) {
            target.acceptedPolicies = acceptedPolicies;
            return this;
        }

        public User build() {
            return target;
        }
    }
}
