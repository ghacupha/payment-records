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
  props<{newPayment: IPayment}>()
);

export const paymentSaveButtonClicked = createAction(
  '[Payment Update Form: Save Button] payment save button clicked'
);

export const paymentUpdateCancelButtonClicked = createAction(
  '[Payment Update Form: Cancel Button] payment update cancel button clicked'
);

export const paymentUpdateErrorHasOccurred = createAction(
  '[Payment Update Form: Error] Payment update error encountered'
);
