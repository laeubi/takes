/*
 * SPDX-FileCopyrightText: Copyright (c) 2014-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.takes.rs;

import java.io.ByteArrayInputStream;
import org.cactoos.bytes.BytesOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.takes.misc.ZeroAvailableInputStream;

/**
 * Test case for {@link RsBody.Stream} with streams where available() returns 0.
 *
 * @since 2.0
 */
final class RsBodyStreamZeroAvailableTest {

    @Test
    void returnsCorrectInputWithZeroAvailableStream() throws Exception {
        final byte[] bytes =
            new BytesOf("Stream with zero available!").asBytes();
        final RsBody.Stream body = new RsBody.Stream(
            new ZeroAvailableInputStream(
                new ByteArrayInputStream(bytes)
            )
        );
        MatcherAssert.assertThat(
            "Body content must be correct even when available() returns 0",
            new BytesOf(body).asBytes(),
            new IsEqual<>(bytes)
        );
    }

    @Test
    void returnsCorrectLengthWithZeroAvailableStream() throws Exception {
        final byte[] bytes =
            new BytesOf("Stream length test!").asBytes();
        final RsBody.Stream body = new RsBody.Stream(
            new ZeroAvailableInputStream(
                new ByteArrayInputStream(bytes)
            )
        );
        MatcherAssert.assertThat(
            "Body length must be correct even when available() returns 0",
            new LengthOf(body).value(),
            new IsEqual<>((long) bytes.length)
        );
    }

    @Test
    void handlesEmptyStreamWithZeroAvailable() throws Exception {
        final byte[] bytes = new byte[0];
        final RsBody.Stream body = new RsBody.Stream(
            new ZeroAvailableInputStream(
                new ByteArrayInputStream(bytes)
            )
        );
        MatcherAssert.assertThat(
            "Empty stream must have zero length even when available() returns 0",
            new LengthOf(body).value(),
            new IsEqual<>(0L)
        );
    }
}
