import {initialUpdateState, PaymentsFormState} from "app/payment-records/store/update-menu-status.reducer";
import {Payment} from "app/shared/model/paymentRecords/payment.model";

export interface State {
  paymentsFormState: PaymentsFormState
}

export const initialState: State = {
  paymentsFormState: {
    selectedPayment: new Payment(),
    paymentUpdateState: initialUpdateState,
  }
}
