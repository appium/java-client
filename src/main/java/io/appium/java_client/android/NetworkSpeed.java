package io.appium.java_client.android;

/** Emulated network speed profiles. */
public enum NetworkSpeed {
    /** GSM/CSD (up: 14.4, down: 14.4 kbps). */
    GSM,
    /** HSCSD (up: 14.4, down: 43.2 kbps). */
    SCSD,
    /** GPRS (up: 28.8, down: 57.6 kbps). */
    GPRS,
    /** EDGE/EGPRS (up: 473.6, down: 473.6 kbps). */
    EDGE,
    /** UMTS/3G (up: 384.0, down: 384.0 kbps). */
    UMTS,
    /** HSDPA (up: 5760.0, down: 13,980.0 kbps). */
    HSDPA,
    /** LTE (up: 58,000, down: 173,000 kbps). */
    LTE,
    /** EVDO (up: 75,000, down: 280,000 kbps). */
    EVDO,
    /** No limit, the default. */
    FULL
}
