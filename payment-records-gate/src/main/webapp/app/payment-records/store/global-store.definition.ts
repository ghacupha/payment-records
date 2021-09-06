import {
  PaymentsFormState,
} from "app/payment-records/store/update-menu-status.reducer";

export interface State {
  paymentsFormState: PaymentsFormState
}

export const initialState: State = {
  paymentsFormState: {
    selectedPayment: {},
    weAreCopying: false,
    weAreEditing: false,
    weAreCreating: false,
  }
}
