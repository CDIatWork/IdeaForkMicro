package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.user;

import at.irian.cdiatwork.ideafork.ui.jsf.view.controller.ViewController;
import at.irian.cdiatwork.ideafork.ui.remote.dto.UserAction;
import at.irian.cdiatwork.ideafork.ui.remote.UserActionService;
import at.irian.cdiatwork.ideafork.ui.remote.dto.ProfileActivity;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ViewController
public class ProfileViewCtrl implements Serializable {
    @Inject
    private UserActionService.UserStatsService statisticService;

    private List<UserAction> userActionList;
    private ProfileActivity profileActivity;

    @PostConstruct
    public void init() {
        profileActivity = statisticService.loadLatestActivities();
        userActionList = profileActivity.getUserActionEntryList();
    }

    public ProfileActivity getProfileActivity() {
        return profileActivity;
    }

    public List<UserAction> getUserActionList() {
        return userActionList;
    }
}
