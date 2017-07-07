package at.irian.cdiatwork.ideafork.ui.jsf.view.error;

import at.irian.cdiatwork.ideafork.remote.api.UnexpectedServiceResultException;
import at.irian.cdiatwork.ideafork.ui.jsf.message.UserMessage;
import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import org.apache.deltaspike.core.api.config.view.DefaultErrorView;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.apache.deltaspike.jsf.api.listener.phase.BeforePhase;
import org.apache.deltaspike.jsf.api.listener.phase.JsfPhaseId;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

@RequestScoped
@ExceptionHandler
public class ErrorViewAwareExceptionHandler {
    private boolean exceptionDetected = false;

    public void onUnhandledException(@Handles(ordinal = Integer.MIN_VALUE) ExceptionEvent<IllegalStateException> exceptionEvent) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext == null) {
            return;
        }

        if (!exceptionEvent.isMarkedHandled()) {
            exceptionEvent.handled();
            exceptionDetected = true;
        }
    }

    public void onUnhandledRemoteException(@Handles(ordinal = Integer.MIN_VALUE) ExceptionEvent<UnexpectedServiceResultException> exceptionEvent,
                                           JsfMessage<UserMessage> failedRemoteCallMessage,
                                           ViewConfigResolver viewConfigResolver) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext == null) {
            return;
        }

        if (!exceptionEvent.isMarkedHandled()) {
            exceptionEvent.handled();

            if (viewConfigResolver.getViewConfigDescriptor(Pages.User.Login.class).getViewId().equals(facesContext.getViewRoot().getViewId())) {
                failedRemoteCallMessage.addError().loginFailed();
            } else {
                failedRemoteCallMessage.addError().unhandledFailure();
            }
        }
    }

    protected void navigateOnDetectedException(@Observes @BeforePhase(JsfPhaseId.RENDER_RESPONSE) PhaseEvent phaseEvent,
                                               ViewNavigationHandler viewNavigationHandler) {

        if (exceptionDetected) {
            viewNavigationHandler.navigateTo(DefaultErrorView.class);
        }
    }
}
