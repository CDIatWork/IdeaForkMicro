package at.irian.cdiatwork.ideafork.ui.jsf.util;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("jsf")
@ApplicationScoped
public class JsfUtils {
    public String getContextPath() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }
}
