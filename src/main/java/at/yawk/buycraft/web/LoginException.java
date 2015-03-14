/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package at.yawk.buycraft.web;

/**
 * @author yawkat
 */
public class LoginException extends Exception {
    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class BadCredentialsException extends LoginException {
        public BadCredentialsException(String message) {
            super(message);
        }

        public BadCredentialsException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
