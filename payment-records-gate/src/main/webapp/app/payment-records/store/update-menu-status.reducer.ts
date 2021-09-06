import {IPayment} from "app/shared/model/paymentRecords/payment.model";
import {Action, createReducer, on} from "@ngrx/store";
import {
  newPaymentButtonClicked,
  paymentCopyButtonClicked,
  paymentCopyInitiated,
  paymentEditInitiated, paymentSaveButtonClicked, paymentUpdateButtonClicked
} from "app/payment-records/store/update-menu-status.actions";
import {initialState, State} from "app/payment-records/store/global-store.definition";

export const paymentUpdateFormStateSelector = "paymentUpdateForm"

export interface PaymentsFormState {
  selectedPayment: IPayment,
  weAreCopying: boolean
  weAreEditing: boolean
  weAreCreating: boolean
}

const _paymentUpdateStateReducer = createReducer(
  initialState,

  on(paymentCopyInitiated, (state, {copiedPayment}) => ({
    ...state,
    paymentsFormState: {
      ...state.paymentsFormState,
      selectedPayment: copiedPayment,
      weAreCopying: true
    }
  })),

  on(paymentCopyButtonClicked, (state) => ({
    ...state,
    paymentsFormState: {
      ...state.paymentsFormState,
      weAreCopying: false
    }
  })),

  on(paymentEditInitiated, (state, {editPayment}) => ({
    ...state,
    paymentsFormState: {
      ...state.paymentsFormState,
      selectedPayment: editPayment,
      weAreEditing: true
    }
  })),

  on(paymentUpdateButtonClicked, state => ({
    ...state,
    paymentsFormState: {
      ...state.paymentsFormState,
      selectedPayment: {},
      weAreEditing: false
    }
  })),

  on(newPaymentButtonClicked, (state, {newPayment}) => ({
      ...state,
      paymentsFormState: {
        ...state.paymentsFormState,
        selectedPayment: newPayment,
        weAreCreating: true
      }
    }
  )),

  on(paymentSaveButtonClicked, state => ({
      ...state,
      paymentsFormState: {
        ...state.paymentsFormState,
        selectedPayment: {},
        weAreCreating: false
      }
    }
  )),
);

export function paymentUpdateStateReducer(state: State = initialState, action: Action): State {

  return _paymentUpdateStateReducer(state, action);
}
