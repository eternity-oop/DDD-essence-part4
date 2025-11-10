package org.eternity.phone.contract.rateplan.domain;

import org.eternity.phone.contract.billing.domain.Call;
import org.eternity.phone.shared.monetary.Money;
import org.eternity.phone.shared.temporal.TimeInterval;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NightRateAndTaxablePlanTest {
    @Test
    public void 심야요금제_세금_요금_계산() {
        List<Call> calls = List.of(
                new Call("010-1111-2222", TimeInterval.of(LocalDateTime.of(2025, 1, 1, 18, 30, 0), LocalDateTime.of(2025, 1, 1, 18, 30, 10))),
                new Call("010-1111-2222", TimeInterval.of(LocalDateTime.of(2025, 1, 1, 23, 0, 20), LocalDateTime.of(2025, 1, 1, 23, 0, 30)))
        );

        NightRatePlan plan = new NightRatePlan(Money.won(10), Money.won(1), Duration.ofSeconds(10),
                                    new TaxableRatePlan(0.1));

        Money fee = plan.calculateFee(calls);
        assertThat(fee).isEqualTo(Money.won(12));
    }
}
