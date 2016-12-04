package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.gruuf.web.GruufActions;
import com.gruuf.web.GruufAuth;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.interceptor.I18nInterceptor;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@InterceptorRef("defaultWithMessages")
@Anonymous
public class LoginAction extends BaseLoginAction implements SessionAware {

    private Map<String, Object> session;
    private UserStore userStore;

    private String email;
    private String password;

    public String execute() {
        return SUCCESS;
    }

    @Action(value = "login-submit")
    public String submit() {
        User user = userStore.findUniqueBy("email", email.trim());
        if (user != null && GruufAuth.isPasswordValid(password, user.getPassword())) {
            markSessionAsLoggedIn(user);
            return GruufActions.GARAGE;
        } else {
            addActionError("Cannot login!");
            return GruufActions.LOGIN;
        }
    }

    @Action(value = "logout")
    public String logout() {
        session.put(GruufAuth.AUTH_TOKEN, null);
        return GruufActions.LOGIN;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Inject
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
