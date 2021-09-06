import {createFeatureSelector, createSelector} from "@ngrx/store";
import {PaymentsFormState, paymentUpdateFormStateSelector} from "app/payment-records/store/update-menu-status.reducer";

export const paymentStatusFeatureSelector = createFeatureSelector<PaymentsFormState>(paymentUpdateFormStateSelector);

export const updateSelectedPayment = createSelector(
  paymentStatusFeatureSelector,
  state => state.selectedPayment
);

export const editingPaymentStatus = createSelector(
  paymentStatusFeatureSelector,
  state => state.paymentUpdateState.weAreEditing
);

export const creatingPaymentStatus = createSelector(
  paymentStatusFeatureSelector,
  state => state.paymentUpdateState.weAreCreating
);

export const copyingPaymentStatus = createSelector(
  paymentStatusFeatureSelector,
  state => state.paymentUpdateState.weAreCopying
);
