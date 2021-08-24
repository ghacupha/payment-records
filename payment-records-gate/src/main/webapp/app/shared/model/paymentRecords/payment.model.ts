import { Moment } from 'moment';
import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';

export interface IPayment {
  id?: number;
  paymentsCategory?: string;
  transactionNumber?: string;
  transactionDate?: Moment;
  transactionCurrency?: string;
  transactionAmount?: number;
  beneficiary?: string;
  placeholders?: IPlaceholder[];
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public paymentsCategory?: string,
    public transactionNumber?: string,
    public transactionDate?: Moment,
    public transactionCurrency?: string,
    public transactionAmount?: number,
    public beneficiary?: string,
    public placeholders?: IPlaceholder[]
  ) {}
}
