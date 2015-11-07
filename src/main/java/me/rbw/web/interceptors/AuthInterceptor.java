package me.rbw.web.interceptors;

import com.google.apphosting.client.serviceapp.AuthService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;
import me.rbw.RbwServices;
import me.rbw.auth.AuthChecker;
import me.rbw.model.User;
import me.rbw.services.UserStore;
import me.rbw.web.RbwActions;
import me.rbw.web.RbwAuth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthInterceptor extends AbstractInterceptor {

    private static Logger LOG = LogManager.getLogger(AuthInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        User currentUser = readCurrentUser(invocation);
        if (currentUser == null) {
            LOG.info("Current User is null, assuming not-logged-in");
            return invocation.invoke();
        } else {
            if (AuthChecker.isAllowedUser(currentUser, invocation.getAction())) {
                return invocation.invoke();
            } else {
                return RbwActions.HOME;
            }
        }
    }

    protected User readCurrentUser(ActionInvocation invocation) {
        String authToken = (String) invocation.getInvocationContext().getSession().get(RbwAuth.AUTH_TOKEN);
        UserStore userStore = (UserStore) invocation.getInvocationContext().getApplication().get(RbwServices.USER_REGISTER);
        if (authToken == null) {
            LOG.debug("User not logged-in yet!");
            return null;
        }
        return userStore.get(authToken);
    }
}