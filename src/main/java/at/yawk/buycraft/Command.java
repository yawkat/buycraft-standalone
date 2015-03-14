/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package at.yawk.buycraft;

/**
 * @author yawkat
 */
public class Command {
    private final int id;
    private final String username;
    private final String uuid;
    private final String command;
    private final boolean requireOnline;
    private final int requireInventorySlot;
    private final long delay;

    Command(int id, String username, String uuid, String command, boolean requireOnline,
            int requireInventorySlot, long delay) {
        this.id = id;
        this.username = username;
        this.uuid = uuid;
        this.command = command;
        this.requireOnline = requireOnline;
        this.requireInventorySlot = requireInventorySlot;
        this.delay = delay;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCommand() {
        return command;
    }

    public boolean isRequireOnline() {
        return requireOnline;
    }

    public int getRequireInventorySlot() {
        return requireInventorySlot;
    }

    public long getDelay() {
        return delay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Command command1 = (Command) o;

        if (delay != command1.delay) {
            return false;
        }
        if (id != command1.id) {
            return false;
        }
        if (requireInventorySlot != command1.requireInventorySlot) {
            return false;
        }
        if (requireOnline != command1.requireOnline) {
            return false;
        }
        if (!command.equals(command1.command)) {
            return false;
        }
        if (!username.equals(command1.username)) {
            return false;
        }
        if (!uuid.equals(command1.uuid)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + username.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + command.hashCode();
        result = 31 * result + (requireOnline ? 1 : 0);
        result = 31 * result + requireInventorySlot;
        result = 31 * result + (int) (delay ^ (delay >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Command{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", uuid='" + uuid + '\'' +
               ", command='" + command + '\'' +
               ", requireOnline=" + requireOnline +
               ", requireInventorySlot=" + requireInventorySlot +
               ", delay=" + delay +
               '}';
    }
}
