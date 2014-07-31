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
