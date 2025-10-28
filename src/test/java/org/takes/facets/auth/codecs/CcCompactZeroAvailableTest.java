/*
 * SPDX-FileCopyrightText: Copyright (c) 2014-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.takes.facets.auth.codecs;

import java.io.IOException;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.takes.facets.auth.Identity;

/**
 * Test case for {@link CcCompact} with streams where available() returns 0.
 * @since 2.0
 */
final class CcCompactZeroAvailableTest {

    @Test
    @SuppressWarnings("unchecked")
    void encodesAndDecodesWithMultipleProperties() {
        final String urn = "urn:test:zero-available";
        final Identity identity = new Identity.Simple(
            urn,
            new MapOf<>(
                new MapEntry<>("name", "Jeff Lebowski"),
                new MapEntry<>("email", "jeff@example.com"),
                new MapEntry<>("role", "admin")
            )
        );
        final byte[] bytes = new CcCompact().encode(identity);
        final Identity decoded = new CcCompact().decode(bytes);
        MatcherAssert.assertThat(
            "Round-trip compact encoding must preserve original identity URN",
            decoded.urn(),
            Matchers.equalTo(urn)
        );
        MatcherAssert.assertThat(
            "Round-trip compact encoding must preserve all properties",
            decoded.properties().size(),
            Matchers.equalTo(3)
        );
        MatcherAssert.assertThat(
            "Round-trip compact encoding must preserve property values",
            decoded.properties().get("name"),
            Matchers.equalTo("Jeff Lebowski")
        );
    }

    @Test
    void encodesAndDecodesEmptyProperties() {
        final String urn = "urn:test:empty";
        final Identity identity = new Identity.Simple(urn);
        final byte[] bytes = new CcCompact().encode(identity);
        final Identity decoded = new CcCompact().decode(bytes);
        MatcherAssert.assertThat(
            "Round-trip compact encoding must preserve URN with empty properties",
            decoded.urn(),
            Matchers.equalTo(urn)
        );
        MatcherAssert.assertThat(
            "Round-trip compact encoding must preserve empty properties",
            decoded.properties().isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test
    void decodesInvalidData() throws IOException {
        MatcherAssert.assertThat(
            "Invalid compact data must decode to anonymous identity",
            new CcSafe(new CcCompact()).decode(
                " % tjw".getBytes()
            ),
            Matchers.equalTo(Identity.ANONYMOUS)
        );
    }
}
