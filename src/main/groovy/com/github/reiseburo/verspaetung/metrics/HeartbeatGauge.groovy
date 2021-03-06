package com.github.reiseburo.verspaetung.metrics

import com.codahale.metrics.Gauge

/**
 * A simple gauge that will always just return 1 indicating that the process is
 * alive
 */
class HeartbeatGauge implements Gauge<Integer> {
    @Override
    Integer getValue() {
        return 1
    }
}
