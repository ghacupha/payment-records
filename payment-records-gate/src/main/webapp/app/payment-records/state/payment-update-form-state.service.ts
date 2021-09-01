import {Injectable} from "@angular/core";
import {StateService} from "app/payment-records/state/state.service";
import {Observable} from "rxjs";
import {IPayment, Payment} from "app/shared/model/paymentRecords/payment.model";

interface PaymentUpdateFormState {
  selectedPayment: IPayment,
  paymentUpdateState: UpdateState,
}

interface UpdateState {
  weAreCopying: boolean
  weAreEditing: boolean
  weAreCreating: boolean
}

const initialUpdateState: UpdateState = {
  weAreCopying: false,
  weAreEditing: false,
  weAreCreating: false,
}

export class EditingUpdateState implements UpdateState {
  constructor(
    public weAreCopying: boolean = false,
    public weAreEditing: boolean = true,
    public weAreCreating: boolean = false,
  ) {
  }
}

export class CreatingUpdateState implements UpdateState {
  constructor(
    public weAreCopying: boolean = false,
    public weAreEditing: boolean = false,
    public weAreCreating: boolean = true,
  ) {
  }
}

export class CopyingUpdateState implements UpdateState {
  constructor(
    public weAreCopying: boolean = true,
    public weAreEditing: boolean = false,
    public weAreCreating: boolean = false,
  ) {
  }
}

export class PaymentUpdateResetState implements UpdateState {
  constructor(
    public weAreCopying: boolean = false,
    public weAreEditing: boolean = false,
    public weAreCreating: boolean = false,
  ) {
  }
}

const initialState: PaymentUpdateFormState = {
  selectedPayment: new Payment(),
  paymentUpdateState: initialUpdateState,
}

/**
 * This state management service maintains the state for the payments entity.
 * This is a very simple state-management approach that is temporary use only.
 */
@Injectable({providedIn: "root"})
export class PaymentUpdateFormStateService extends StateService<PaymentUpdateFormState>{

  // TODO create DIY selectors
  weAreCopyingState$: Observable<boolean> = this.select(state => state.paymentUpdateState.weAreCopying);
  weAreEditingState$: Observable<boolean> = this.select(state => state.paymentUpdateState.weAreEditing);
  weAreCreatingState$: Observable<boolean> = this.select(state => state.paymentUpdateState.weAreCreating);
  selectedPayment$: Observable<IPayment> = this.select(state => state.selectedPayment);

  constructor() {
    super(initialState);
  }

  paymentCopied(): void {
    this.setState({paymentUpdateState: { ...new CopyingUpdateState()}});
  }

  paymentEdited(): void {
    this.setState({paymentUpdateState: { ...new EditingUpdateState()}});
  }

  paymentCreated(): void {
    this.setState({paymentUpdateState: { ...new CreatingUpdateState()}});
  }

  paymentSavedSuccessfully(): void {
    this.setState({paymentUpdateState: { ...new PaymentUpdateResetState()}})
  }

  paymentUpdateFormErrored(): void {
    this.setState({paymentUpdateState: { ...new PaymentUpdateResetState()}});
  }

  paymentCopiedSuccessfully(): void {
    this.setState({paymentUpdateState: { ...new PaymentUpdateResetState()}});
  }

  paymentCreatedSuccessfully(): void {
    this.setState({paymentUpdateState: { ...new PaymentUpdateResetState()}});
  }

  paymentEditedSuccessfully(): void {
    this.setState({paymentUpdateState: { ...new PaymentUpdateResetState()}});
  }

  paymentSelected(payment: IPayment): void {
    this.setState({selectedPayment: payment})
  }
}
