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
