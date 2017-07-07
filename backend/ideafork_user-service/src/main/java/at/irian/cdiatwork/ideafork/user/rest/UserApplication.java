package at.irian.cdiatwork.ideafork.user.rest;

import javax.enterprise.context.Dependent;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Dependent
@ApplicationPath("/v1/")
public class UserApplication extends Application {
}
