package org.eternity.phone.contract.rateplan.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.eternity.phone.shared.monetary.Money;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "additional_plan_type")
@NoArgsConstructor
public abstract class AdditionalRatePlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public AdditionalRatePlan(AdditionalRatePlan next) {
        this.next = next;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="NEXT_PLAN")
    private AdditionalRatePlan next;

    public Money calculate(Money fee) {
        if (next == null) {
            return apply(fee);
        }

        return next.calculate(apply(fee));
    }

    protected abstract Money apply(Money fee);
}
