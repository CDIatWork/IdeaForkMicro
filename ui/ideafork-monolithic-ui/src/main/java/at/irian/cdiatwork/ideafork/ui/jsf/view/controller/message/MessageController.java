package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.message;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;

@Model
public class MessageController {
    public boolean isGlobalMessageAvailable() {
        return !FacesContext.getCurrentInstance().getMessageList(null).isEmpty();
    }
}
