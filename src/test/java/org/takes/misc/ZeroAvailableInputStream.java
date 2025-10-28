/*
 * SPDX-FileCopyrightText: Copyright (c) 2014-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.takes.misc;

import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream decorator that always returns 0 for available().
 * This simulates a slow network connection or other scenarios where
 * available() is not a reliable indicator of data presence.
 *
 * @since 2.0
 */
public final class ZeroAvailableInputStream extends InputStream {

    /**
     * Original InputStream.
     */
    private final InputStream origin;

    /**
     * Constructor.
     *
     * @param stream InputStream to decorate
     */
    public ZeroAvailableInputStream(final InputStream stream) {
        super();
        this.origin = stream;
    }

    @Override
    public int read() throws IOException {
        return this.origin.read();
    }

    @Override
    public int read(final byte[] buf) throws IOException {
        return this.origin.read(buf);
    }

    @Override
    public int read(final byte[] buf, final int off, final int len) throws
        IOException {
        return this.origin.read(buf, off, len);
    }

    @Override
    public long skip(final long num) throws IOException {
        return this.origin.skip(num);
    }

    @Override
    public int available() throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {
        this.origin.close();
    }

    @Override
    public void mark(final int readlimit) {
        this.origin.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        this.origin.reset();
    }

    @Override
    public boolean markSupported() {
        return this.origin.markSupported();
    }
}
