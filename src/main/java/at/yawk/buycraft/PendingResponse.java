package at.yawk.buycraft;

import java.util.Collection;

/**
 * @author yawkat
 */
public class PendingResponse {
    private final Collection<String> pendingUsernames;
    private final boolean offlineCommands;

    PendingResponse(Collection<String> pendingUsernames, boolean offlineCommands) {
        this.pendingUsernames = pendingUsernames;
        this.offlineCommands = offlineCommands;
    }

    public Collection<String> getPendingUsernames() {
        return pendingUsernames;
    }

    public boolean isOfflineCommands() {
        return offlineCommands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PendingResponse that = (PendingResponse) o;

        if (offlineCommands != that.offlineCommands) {
            return false;
        }
        if (!pendingUsernames.equals(that.pendingUsernames)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = pendingUsernames.hashCode();
        result = 31 * result + (offlineCommands ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PendingResponse{" +
               "pendingUsernames=" + pendingUsernames +
               ", offlineCommands=" + offlineCommands +
               '}';
    }
}
