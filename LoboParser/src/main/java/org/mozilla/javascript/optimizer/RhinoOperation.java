package org.mozilla.javascript.optimizer;

import jdk.dynalink.Operation;

/**
 * A list of operation types used that are specific to Rhino, in addition to standard operations
 * like GET and SET...
 */
@SuppressWarnings("AndroidJdkLibsChecker")
public enum RhinoOperation implements Operation {
    BIND,
    GETNOWARN,
    GETWITHTHIS,
    GETWITHTHISOPTIONAL,
    GETELEMENT,
    GETINDEX,
    SETSTRICT,
    SETCONST,
    SETELEMENT,
    SETINDEX,
    ADD,
    EQ,
    SHALLOWEQ,
    COMPARE_GT,
    COMPARE_LT,
    COMPARE_GE,
    COMPARE_LE,
    TOBOOLEAN,
    TOINT32,
    TOUINT32,
    TONUMBER,
    TONUMERIC,
}
