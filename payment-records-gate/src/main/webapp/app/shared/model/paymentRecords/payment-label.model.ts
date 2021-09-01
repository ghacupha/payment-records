import { Moment } from 'moment';
import { IPayment } from 'app/shared/model/paymentRecords/payment.model';

export interface IPaymentLabel {
  id?: number;
  description?: string;
  comments?: string;
  schedule?: Moment;
  payments?: IPayment[];
}

export class PaymentLabel implements IPaymentLabel {
  constructor(
    public id?: number,
    public description?: string,
    public comments?: string,
    public schedule?: Moment,
    public payments?: IPayment[]
  ) {}
}
