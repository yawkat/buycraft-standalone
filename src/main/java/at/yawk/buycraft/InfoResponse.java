/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package at.yawk.buycraft;

/**
 * @author yawkat
 */
public class InfoResponse {
    private final double latestPluginVersion;
    private final String latestPluginDownload;
    private final int serverId;
    private final String currency;
    private final String serverName;
    private final String serverStoreUrl;
    private final int updateUsernameInterval;
    private final boolean onlineMode;

    InfoResponse(double latestPluginVersion, String latestPluginDownload, int serverId, String currency,
                 String serverName, String serverStoreUrl, int updateUsernameInterval, boolean onlineMode) {
        this.latestPluginVersion = latestPluginVersion;
        this.latestPluginDownload = latestPluginDownload;
        this.serverId = serverId;
        this.currency = currency;
        this.serverName = serverName;
        this.serverStoreUrl = serverStoreUrl;
        this.updateUsernameInterval = updateUsernameInterval;
        this.onlineMode = onlineMode;
    }

    public double getLatestPluginVersion() {
        return latestPluginVersion;
    }

    public String getLatestPluginDownload() {
        return latestPluginDownload;
    }

    public int getServerId() {
        return serverId;
    }

    public String getCurrency() {
        return currency;
    }

    public String getServerName() {
        return serverName;
    }

    public String getServerStoreUrl() {
        return serverStoreUrl;
    }

    public int getUpdateUsernameInterval() {
        return updateUsernameInterval;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InfoResponse that = (InfoResponse) o;

        if (Double.compare(that.latestPluginVersion, latestPluginVersion) != 0) {
            return false;
        }
        if (onlineMode != that.onlineMode) {
            return false;
        }
        if (serverId != that.serverId) {
            return false;
        }
        if (updateUsernameInterval != that.updateUsernameInterval) {
            return false;
        }
        if (!currency.equals(that.currency)) {
            return false;
        }
        if (!latestPluginDownload.equals(that.latestPluginDownload)) {
            return false;
        }
        if (!serverName.equals(that.serverName)) {
            return false;
        }
        if (!serverStoreUrl.equals(that.serverStoreUrl)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latestPluginVersion);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + latestPluginDownload.hashCode();
        result = 31 * result + serverId;
        result = 31 * result + currency.hashCode();
        result = 31 * result + serverName.hashCode();
        result = 31 * result + serverStoreUrl.hashCode();
        result = 31 * result + updateUsernameInterval;
        result = 31 * result + (onlineMode ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InfoResponse{" +
               "latestPluginVersion=" + latestPluginVersion +
               ", latestPluginDownload='" + latestPluginDownload + '\'' +
               ", serverId=" + serverId +
               ", currency='" + currency + '\'' +
               ", serverName='" + serverName + '\'' +
               ", serverStoreUrl='" + serverStoreUrl + '\'' +
               ", updateUsernameInterval=" + updateUsernameInterval +
               ", onlineMode=" + onlineMode +
               '}';
    }
}
