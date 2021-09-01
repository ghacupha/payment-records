import {Injectable} from "@angular/core";
import {StateService} from "app/payment-records/state/state.service";
import {Observable} from "rxjs";
import {IPayment} from "app/shared/model/paymentRecords/payment.model";

interface PaymentUpdateFormState {
  selectedPaymentId: number,
  weAreCopying: boolean
}

const initialState: PaymentUpdateFormState = {
  selectedPaymentId: 0,
  weAreCopying: false
}

@Injectable({providedIn: "root"})
export class PaymentUpdateFormStateService extends StateService<PaymentUpdateFormState>{

  weAreCopyingState$: Observable<boolean> = this.select(state => state.weAreCopying);

  selectedPaymentId$: Observable<number> = this.select(state => state.selectedPaymentId);

  constructor() {
    super(initialState);
  }

  copyPayment(): void {
    this.setState({weAreCopying: true})
  }

  completePaymentCopy(): void {
    this.setState({weAreCopying: false})
  }

  selectPayment(payment: IPayment): void {
    this.setState({selectedPaymentId: payment.id})
  }
}
