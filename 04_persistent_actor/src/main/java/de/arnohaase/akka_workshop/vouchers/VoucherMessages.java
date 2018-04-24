package de.arnohaase.akka_workshop.vouchers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.io.Serializable;
import java.util.List;

public class VoucherMessages {
    @JsonSerialize
    @Value.Immutable
    public interface AddVouchers {
        @Value.Parameter List<String> vouchers();
    }

    @JsonSerialize
    @Value.Immutable(singleton = true)
    public interface GetVoucher {
    }

    @JsonSerialize
    @Value.Immutable
    public interface GetVoucherResponse {
        @Value.Parameter String voucher();
    }

    @JsonSerialize
    @Value.Immutable(singleton = true)
    public interface NoVoucherResponse {
    }

    @Value.Immutable
    interface VoucherCodesAdded extends Serializable {
        @Value.Parameter List<String> vouchers();
    }

    @Value.Immutable
    interface VoucherAssigned extends Serializable {
        @Value.Parameter String voucher();
    }

    @Value.Immutable
    interface Snapshot extends Serializable {
        @Value.Parameter List<String> vouchers();
    }
}
