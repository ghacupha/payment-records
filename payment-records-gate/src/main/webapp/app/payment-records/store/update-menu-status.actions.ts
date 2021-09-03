import {createAction, props} from "@ngrx/store";
import {IPayment} from "app/shared/model/paymentRecords/payment.model";

export const paymentCopyInitiated = createAction(
  '[Payments Page] payment copy initiated',
  props<{copiedPayment: IPayment}>()
);

export const paymentCopyButtonClicked = createAction(
  '[Payment Update Form: Copy Button] payment copy button clicked'
);

export const paymentEditInitiated = createAction(
  '[Payments Page] payment edit initiated',
  props<{editPayment: IPayment}>()
);

export const paymentUpdateButtonClicked = createAction(
  '[Payment Edit Form: Update Button] payment update button clicked'
);

export const newPaymentButtonClicked = createAction(
  '[Payments New Button] new payment button clicked',
);

export const paymentSaveButtonClicked = createAction(
  '[Payment Update Form: Save Button] payment save button clicked'
);
