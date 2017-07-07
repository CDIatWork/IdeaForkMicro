package at.irian.cdiatwork.ideafork.notification.rest;

import javax.enterprise.context.Dependent;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Dependent
@ApplicationPath("/v1/")
public class NotificationApplication extends Application {
}
