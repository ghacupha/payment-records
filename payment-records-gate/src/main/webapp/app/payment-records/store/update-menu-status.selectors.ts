import {createFeatureSelector, createSelector} from "@ngrx/store";
import {paymentUpdateFormStateSelector} from "app/payment-records/store/update-menu-status.reducer";
import {State} from "app/payment-records/store/global-store.definition";

export const paymentStatusFeatureSelector = createFeatureSelector<State>(paymentUpdateFormStateSelector);

export const updateSelectedPayment = createSelector(
  paymentStatusFeatureSelector,
  state => state.paymentsFormState.selectedPayment
);

export const editingPaymentStatus = createSelector(
  paymentStatusFeatureSelector,
  state => state.paymentsFormState.weAreEditing
);

export const creatingPaymentStatus = createSelector(
  paymentStatusFeatureSelector,
  state => state.paymentsFormState.weAreCreating
);

export const copyingPaymentStatus = createSelector(
  paymentStatusFeatureSelector,
  state => state.paymentsFormState.weAreCopying
);
