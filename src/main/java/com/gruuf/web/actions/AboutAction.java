package com.gruuf.web.actions;

import com.gruuf.GruufConstants;
import com.gruuf.auth.Anonymous;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Anonymous
@Result(name = SUCCESS, location = "about")
public class AboutAction extends BaseAction implements ServletResponseAware {

    private static final Logger LOG = LogManager.getLogger(AboutAction.class);

    private boolean devMode;
    private String hostUrl;
    private HttpServletResponse response;

    @Override
    public String execute() throws Exception {
        Cookie cookie = createCookie();

        LOG.debug("Adds cookie: {}", cookie);
        response.addCookie(cookie);

        return SUCCESS;
    }

    private Cookie createCookie() {
        Cookie cookie = new Cookie(GruufConstants.FIRST_TIME_COOKIE, Boolean.FALSE.toString());
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setSecure(!devMode);
        cookie.setPath("/");
        int begin = hostUrl.lastIndexOf("://") + 3;
        int end = hostUrl.substring(begin).lastIndexOf(':');
        String domain = hostUrl.substring(begin, end == -1 ? hostUrl.length() - 1 : end);
        cookie.setDomain(domain);
        return cookie;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject(GruufConstants.HOST_URL)
    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    @Inject(StrutsConstants.STRUTS_DEVMODE)
    public void setDevMode(String devMode) {
        this.devMode = Boolean.parseBoolean(devMode);
    }
}
