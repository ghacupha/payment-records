package io.github.erp.internal.model;

/*-
 * Payment Records - Payment records is part of the ERP System
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Payment excel view model
 */
public class PaymentEVM implements Serializable {

    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String paymentsCategory;

    @ExcelCell(1)
    private String transactionNumber;

    @ExcelCell(2)
    private String transactionDate;

    @ExcelCell(3)
    private String transactionCurrency;

    @NotNull
    @ExcelCell(4)
    private double transactionAmount;

    @ExcelCell(5)
    private String beneficiary;

    public PaymentEVM(int rowIndex, String paymentsCategory, String transactionNumber, String transactionDate, String transactionCurrency, @NotNull double transactionAmount, String beneficiary) {
        this.rowIndex = rowIndex;
        this.paymentsCategory = paymentsCategory;
        this.transactionNumber = transactionNumber;
        this.transactionDate = transactionDate;
        this.transactionCurrency = transactionCurrency;
        this.transactionAmount = transactionAmount;
        this.beneficiary = beneficiary;
    }

    public PaymentEVM() {
    }

    public static PaymentEVMBuilder builder() {
        return new PaymentEVMBuilder();
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public String getPaymentsCategory() {
        return this.paymentsCategory;
    }

    public String getTransactionNumber() {
        return this.transactionNumber;
    }

    public String getTransactionDate() {
        return this.transactionDate;
    }

    public String getTransactionCurrency() {
        return this.transactionCurrency;
    }

    public @NotNull double getTransactionAmount() {
        return this.transactionAmount;
    }

    public String getBeneficiary() {
        return this.beneficiary;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setPaymentsCategory(String paymentsCategory) {
        this.paymentsCategory = paymentsCategory;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public void setTransactionAmount(@NotNull double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PaymentEVM)) return false;
        final PaymentEVM other = (PaymentEVM) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getRowIndex() != other.getRowIndex()) return false;
        final Object this$paymentsCategory = this.getPaymentsCategory();
        final Object other$paymentsCategory = other.getPaymentsCategory();
        if (this$paymentsCategory == null ? other$paymentsCategory != null : !this$paymentsCategory.equals(other$paymentsCategory))
            return false;
        final Object this$transactionNumber = this.getTransactionNumber();
        final Object other$transactionNumber = other.getTransactionNumber();
        if (this$transactionNumber == null ? other$transactionNumber != null : !this$transactionNumber.equals(other$transactionNumber))
            return false;
        final Object this$transactionDate = this.getTransactionDate();
        final Object other$transactionDate = other.getTransactionDate();
        if (this$transactionDate == null ? other$transactionDate != null : !this$transactionDate.equals(other$transactionDate))
            return false;
        final Object this$transactionCurrency = this.getTransactionCurrency();
        final Object other$transactionCurrency = other.getTransactionCurrency();
        if (this$transactionCurrency == null ? other$transactionCurrency != null : !this$transactionCurrency.equals(other$transactionCurrency))
            return false;
        if (Double.compare(this.getTransactionAmount(), other.getTransactionAmount()) != 0) return false;
        final Object this$beneficiary = this.getBeneficiary();
        final Object other$beneficiary = other.getBeneficiary();
        if (this$beneficiary == null ? other$beneficiary != null : !this$beneficiary.equals(other$beneficiary))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PaymentEVM;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getRowIndex();
        final Object $paymentsCategory = this.getPaymentsCategory();
        result = result * PRIME + ($paymentsCategory == null ? 43 : $paymentsCategory.hashCode());
        final Object $transactionNumber = this.getTransactionNumber();
        result = result * PRIME + ($transactionNumber == null ? 43 : $transactionNumber.hashCode());
        final Object $transactionDate = this.getTransactionDate();
        result = result * PRIME + ($transactionDate == null ? 43 : $transactionDate.hashCode());
        final Object $transactionCurrency = this.getTransactionCurrency();
        result = result * PRIME + ($transactionCurrency == null ? 43 : $transactionCurrency.hashCode());
        final long $transactionAmount = Double.doubleToLongBits(this.getTransactionAmount());
        result = result * PRIME + (int) ($transactionAmount >>> 32 ^ $transactionAmount);
        final Object $beneficiary = this.getBeneficiary();
        result = result * PRIME + ($beneficiary == null ? 43 : $beneficiary.hashCode());
        return result;
    }

    public String toString() {
        return "PaymentEVM(rowIndex=" + this.getRowIndex() + ", paymentsCategory=" + this.getPaymentsCategory() + ", transactionNumber=" + this.getTransactionNumber() + ", transactionDate=" + this.getTransactionDate() + ", transactionCurrency=" + this.getTransactionCurrency() + ", transactionAmount=" + this.getTransactionAmount() + ", beneficiary=" + this.getBeneficiary() + ")";
    }

    public static class PaymentEVMBuilder {
        private int rowIndex;
        private String paymentsCategory;
        private String transactionNumber;
        private String transactionDate;
        private String transactionCurrency;
        private @NotNull double transactionAmount;
        private String beneficiary;

        PaymentEVMBuilder() {
        }

        public PaymentEVMBuilder rowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
            return this;
        }

        public PaymentEVMBuilder paymentsCategory(String paymentsCategory) {
            this.paymentsCategory = paymentsCategory;
            return this;
        }

        public PaymentEVMBuilder transactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
            return this;
        }

        public PaymentEVMBuilder transactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public PaymentEVMBuilder transactionCurrency(String transactionCurrency) {
            this.transactionCurrency = transactionCurrency;
            return this;
        }

        public PaymentEVMBuilder transactionAmount(@NotNull double transactionAmount) {
            this.transactionAmount = transactionAmount;
            return this;
        }

        public PaymentEVMBuilder beneficiary(String beneficiary) {
            this.beneficiary = beneficiary;
            return this;
        }

        public PaymentEVM build() {
            return new PaymentEVM(rowIndex, paymentsCategory, transactionNumber, transactionDate, transactionCurrency, transactionAmount, beneficiary);
        }

        public String toString() {
            return "PaymentEVM.PaymentEVMBuilder(rowIndex=" + this.rowIndex + ", paymentsCategory=" + this.paymentsCategory + ", transactionNumber=" + this.transactionNumber + ", transactionDate=" + this.transactionDate + ", transactionCurrency=" + this.transactionCurrency + ", transactionAmount=" + this.transactionAmount + ", beneficiary=" + this.beneficiary + ")";
        }
    }
}
