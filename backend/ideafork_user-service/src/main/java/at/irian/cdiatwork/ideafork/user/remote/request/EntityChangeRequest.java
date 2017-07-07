package at.irian.cdiatwork.ideafork.user.remote.request;

public class EntityChangeRequest {
    private String entityAsJson;

    private String id;

    private long version = 0;

    private final long creationTimestamp = System.currentTimeMillis();

    /*
     * generated
     */

    public String getEntityAsJson() {
        return entityAsJson;
    }

    public void setEntityAsJson(String entityAsJson) {
        this.entityAsJson = entityAsJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }
}
