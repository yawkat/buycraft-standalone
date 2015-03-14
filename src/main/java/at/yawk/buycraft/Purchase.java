/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package at.yawk.buycraft;

import java.util.Collection;

/**
 * @author yawkat
 */
public class Purchase {
    private final long time;
    private final String timeString;
    private final String username;
    private final String uuid;
    private final String price;
    private final String currency;
    private final Collection<Integer> packages;

    Purchase(long time, String timeString, String username, String uuid, String price, String currency,
             Collection<Integer> packages) {
        this.time = time;
        this.timeString = timeString;
        this.username = username;
        this.uuid = uuid;
        this.price = price;
        this.currency = currency;
        this.packages = packages;
    }

    public long getTime() {
        return time;
    }

    public String getTimeString() {
        return timeString;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public Collection<Integer> getPackages() {
        return packages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Purchase purchase = (Purchase) o;

        if (time != purchase.time) {
            return false;
        }
        if (!currency.equals(purchase.currency)) {
            return false;
        }
        if (!packages.equals(purchase.packages)) {
            return false;
        }
        if (!price.equals(purchase.price)) {
            return false;
        }
        if (!timeString.equals(purchase.timeString)) {
            return false;
        }
        if (!username.equals(purchase.username)) {
            return false;
        }
        if (!uuid.equals(purchase.uuid)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (time ^ (time >>> 32));
        result = 31 * result + timeString.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + packages.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Purchase{" +
               "time=" + time +
               ", timeString='" + timeString + '\'' +
               ", username='" + username + '\'' +
               ", uuid='" + uuid + '\'' +
               ", price='" + price + '\'' +
               ", currency='" + currency + '\'' +
               ", packages=" + packages +
               '}';
    }
}
