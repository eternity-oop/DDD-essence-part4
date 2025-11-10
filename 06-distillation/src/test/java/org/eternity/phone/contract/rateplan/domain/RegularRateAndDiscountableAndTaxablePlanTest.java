package org.eternity.phone.contract.rateplan.domain;

import org.eternity.phone.contract.billing.domain.Call;
import org.eternity.phone.shared.monetary.Money;
import org.eternity.phone.shared.temporal.TimeInterval;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RegularRateAndDiscountableAndTaxablePlanTest  {
    @Test
    public void 일반요금제_기본요금할인_세금_요금_계산() {
        List<Call> calls = List.of(
                new Call("010-1111-2222", TimeInterval.of(LocalDateTime.of(2025, 1, 1, 10, 0, 0), LocalDateTime.of(2025, 1, 1, 10, 0, 10))),
                new Call("010-1111-2222", TimeInterval.of(LocalDateTime.of(2025, 1, 1, 10, 0, 20), LocalDateTime.of(2025, 1, 1, 10, 0, 30)))
        );

        RegularRatePlan plan = new RegularRatePlan(Money.won(10), Duration.ofSeconds(10),
                                    new DiscountableRatePlan(Money.won(1),
                                            new TaxableRatePlan(0.1)));

        Money fee = plan.calculateFee(calls);
        assertThat(fee).isEqualTo(Money.won(21));
    }
}
