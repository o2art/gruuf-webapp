package com.gruuf.web.actions.biker;

import com.gruuf.model.User;
import com.gruuf.model.UserLocale;
import com.gruuf.web.actions.BaseLoginAction;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.gruuf.web.actions.biker.ProfileAction.REDIRECT_TO_FORM;
import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(name = INPUT, location = "biker/profile"),
        @Result(name = REDIRECT_TO_FORM, type = "redirectAction", location = "profile")
})
@InterceptorRef("defaultWithMessages")
public class ProfileAction extends BaseLoginAction implements Preparable, Validateable {

    private static final Logger LOG = LogManager.getLogger(ProfileAction.class);

    public static final String REDIRECT_TO_FORM = "redirect-to-form";

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("profile-submit")
    public String profileSubmit() {
        User.UserCreator user = User.clone(currentUser)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withNotify(notify)
                .withUserLocale(userLocale);

        if (!currentUser.getEmail().equals(email)) {
            LOG.debug("New email has been defined: {}", email);
            addActionMessage(getText("biker.newEmailHasBeenSet"));
            user = user.withEmail(email);
        }

        if (StringUtils.isNotBlank(password1) && !currentUser.getPassword().equals(password1)) {
            LOG.debug("New password has been defined");
            addActionMessage(getText("biker.newPasswordHasBeenSet"));
            user = user.withPassword(password1);
        }

        currentUser = userStore.put(user.build());
        markSessionAsLoggedIn(currentUser);

        LOG.debug("User profiles has been updated {}", currentUser);

        return REDIRECT_TO_FORM;
    }

    public void validateProfileSubmit() {
        if (!currentUser.getEmail().equals(email)) {
            if (userStore.findBy(User.EMAIL, email.trim()).size() > 0) {
                email = currentUser.getEmail();
                addFieldError(User.EMAIL, getText("biker.emailAddressAlreadyRegistered"));
            }
        }

        if (isPasswordDifferent()) {
            if (!password1.equals(password2)) {
                addFieldError("password1", getText("biker.passwordDoNotMatch"));
            }
        }
    }

    public boolean isPasswordDifferent() {
        return (StringUtils.isNotBlank(password1) && !currentUser.getPassword().equals(password1))
                || (StringUtils.isNotBlank(password2) && !currentUser.getPassword().equals(password2));
    }

    @Override
    public void prepare() throws Exception {
        email = currentUser.getEmail();
        firstName = currentUser.getFirstName();
        lastName = currentUser.getLastName();
        userLocale = currentUser.getUserLocale();
        notify = currentUser.isNotify();
    }

    private String email;
    private String firstName;
    private String lastName;
    private UserLocale userLocale;
    private boolean notify;
    private String password1;
    private String password2;

    public String getEmail() {
        return email;
    }

    @RequiredStringValidator(key = "biker.emailIsRequired")
    @EmailValidator(key = "biker.wrongEmail")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserLocale getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(UserLocale userLocale) {
        this.userLocale = userLocale;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
