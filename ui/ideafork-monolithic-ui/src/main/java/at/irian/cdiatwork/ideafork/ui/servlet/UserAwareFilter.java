package at.irian.cdiatwork.ideafork.ui.servlet;

import at.irian.cdiatwork.ideafork.ui.jsf.view.JsfIdentityHolder;
import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigDescriptor;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/pages/import/*", "/idea/import", "/public/idea/export/all"})
public class UserAwareFilter implements Filter {
    @Inject
    private JsfIdentityHolder identityHolder;

    @Inject
    private ViewConfigResolver viewConfigResolver;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //windowContext.activateWindow(request.getParameter("dswid"));

        if (identityHolder.isAuthenticated()) { //lazy check: valid user + no session-timeout
            chain.doFilter(request, response);
        } else {
            ViewConfigDescriptor viewConfigDescriptor = viewConfigResolver.getViewConfigDescriptor(Pages.User.Login.class);
            request.getRequestDispatcher(viewConfigDescriptor.getViewId()).forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
