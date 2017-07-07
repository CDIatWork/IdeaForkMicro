package at.irian.cdiatwork.ideafork.test;

import at.irian.cdiatwork.ideafork.jwt.impl.TokenExpirationManager;

import javax.enterprise.inject.Specializes;

@Specializes
public class TestTokenExpirationManager extends TokenExpirationManager {
    @Override
    public long getExpirationTimeInMilliSeconds() {
        expirationTimeInMilliSeconds = globalExpirationTimeInMilliSeconds;
        return super.getExpirationTimeInMilliSeconds();
    }

    public static int replaceExpirationTimeInMilliSeconds(int expirationTimeInMilliSeconds) {
        int oldValue = globalExpirationTimeInMilliSeconds;
        globalExpirationTimeInMilliSeconds = expirationTimeInMilliSeconds;
        initTokenRenewTimeframe();
        return oldValue;
    }
}
