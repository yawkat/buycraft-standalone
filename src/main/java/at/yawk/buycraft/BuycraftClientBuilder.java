package at.yawk.buycraft;

import java.util.Objects;
import javax.xml.bind.DatatypeConverter;

/**
 * @author yawkat
 */
public class BuycraftClientBuilder {
    private byte[] apiKey;

    public BuycraftClientBuilder apiKey(byte[] key) {
        Objects.requireNonNull(key);
        this.apiKey = key;
        return this;
    }

    public BuycraftClientBuilder apiKey(String key) {
        Objects.requireNonNull(key);
        return apiKey(DatatypeConverter.parseHexBinary(key));
    }

    public BuycraftClient build() {
        Objects.requireNonNull(apiKey);

        return new BuycraftClientImpl(apiKey);
    }
}
