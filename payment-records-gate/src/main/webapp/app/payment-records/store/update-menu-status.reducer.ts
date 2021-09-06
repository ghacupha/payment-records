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
  paymentUpdateState: UpdateActionState,
}

export interface UpdateActionState {
  weAreCopying: boolean
  weAreEditing: boolean
  weAreCreating: boolean
}

export const initialUpdateState: UpdateActionState = {
  weAreCopying: false,
  weAreEditing: false,
  weAreCreating: false,
}

const _paymentUpdateStateReducer = createReducer(
  initialState,

  on(paymentCopyInitiated, (state, {copiedPayment}) => ({
    ...state,
    selectedPayment: copiedPayment,
    paymentUpdateState: {
      ...state.paymentsFormState.paymentUpdateState,
      weAreCopying: true
    }
  })),

  on(paymentCopyButtonClicked, (state) => ({
    ...state,
    selectedPayment: {},
    paymentUpdateState: {
      ...state.paymentsFormState.paymentUpdateState,
      weAreCopying: false
    }
  })),

  on(paymentEditInitiated, (state, {editPayment}) => ({
    ...state,
    selectedPayment: editPayment,
    paymentUpdateState: {
      ...state.paymentsFormState.paymentUpdateState,
      weAreEditing: true
    }
  })),

  on(paymentUpdateButtonClicked, state => ({
    ...state,
    selectedPayment: {},
    paymentUpdateState: {
      ...state.paymentsFormState.paymentUpdateState,
      weAreEditing: false
    }
  })),

  on(newPaymentButtonClicked, state => ({
      ...state,
      selectedPayment: {},
      paymentUpdateState: {
        ...state.paymentsFormState.paymentUpdateState,
        weAreCreating: true
      }
    }
  )),

  on(paymentSaveButtonClicked, state => ({
      ...state,
      selectedPayment: {},
      paymentUpdateState: {
        ...state.paymentsFormState.paymentUpdateState,
        weAreCreating: false,
      }
    }
  )),
);

export function paymentUpdateStateReducer(state: State = initialState, action: Action): State {

  return _paymentUpdateStateReducer(state, action);
}
