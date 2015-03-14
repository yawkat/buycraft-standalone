/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package at.yawk.buycraft;

/**
 * @author yawkat
 */
class BuycraftClientImpl implements BuycraftClient {
    private final byte[] apiKey;
    private final BuycraftApiImpl api;

    public BuycraftClientImpl(byte[] apiKey) {
        this.apiKey = apiKey;
        api = new BuycraftApiImpl(this);
    }

    @Override
    public BuycraftApi getApi() {
        return api;
    }

    byte[] getApiKey() {
        return apiKey;
    }
}
