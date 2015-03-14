/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package at.yawk.buycraft.web;

import java.io.OutputStream;

/**
 * @author yawkat
 */
class DiscardingOutputStream extends OutputStream {
    public static final OutputStream instance = new DiscardingOutputStream();

    private DiscardingOutputStream() {}

    @Override
    public void write(int b) {}
}
