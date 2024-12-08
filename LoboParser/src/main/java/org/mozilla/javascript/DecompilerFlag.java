/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

public enum DecompilerFlag {
    /**
     * Flag to indicate that the decompilation should omit the function header and trailing brace.
     */
    ONLY_BODY,

    /** Flag to indicate that the decompilation generates toSource result. */
    TO_SOURCE;
}
