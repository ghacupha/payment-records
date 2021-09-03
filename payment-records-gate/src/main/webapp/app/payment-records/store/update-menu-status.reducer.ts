import {IPayment, Payment} from "app/shared/model/paymentRecords/payment.model";
import {createReducer, on} from "@ngrx/store";
import {
  newPaymentButtonClicked,
  paymentCopyButtonClicked,
  paymentCopyInitiated,
  paymentEditInitiated, paymentSaveButtonClicked, paymentUpdateButtonClicked
} from "app/payment-records/store/update-menu-status.actions";

export interface State {
  selectedPayment: IPayment,
  paymentUpdateState: UpdateState,
}

export interface UpdateState {
  weAreCopying: boolean
  weAreEditing: boolean
  weAreCreating: boolean
}

export const initialUpdateState: UpdateState = {
  weAreCopying: false,
  weAreEditing: false,
  weAreCreating: false,
}

export const initialState: State = {
  selectedPayment: new Payment(),
  paymentUpdateState: initialUpdateState,
}

const _paymentUpdateStateReducer = createReducer(
  initialState,

  on(paymentCopyInitiated, (state, {copiedPayment}) => ({
    ...state,
    selectedPayment: copiedPayment,
    paymentUpdateState: {
      ...state.paymentUpdateState,
      weAreCopying: true
    }
  })),

  on(paymentCopyButtonClicked, (state) => ({
    ...state,
    selectedPayment: {},
    paymentUpdateState: {
      ...state.paymentUpdateState,
      weAreCopying: false
    }
  })),

  on(paymentEditInitiated, (state, {editPayment}) => ({
    ...state,
    selectedPayment: editPayment,
    paymentUpdateState: {
      ...state.paymentUpdateState,
      weAreEditing: true
    }
  })),

  on(paymentUpdateButtonClicked, state => ({
    ...state,
    selectedPayment: {},
    paymentUpdateState: {
      ...state.paymentUpdateState,
      weAreEditing: false
    }
  })),

  on(newPaymentButtonClicked, state => ({...state})),
  on(paymentSaveButtonClicked, state => ({...state})),
);
