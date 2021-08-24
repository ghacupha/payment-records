import { ICurrencyTable } from 'app/shared/model/payments/currency-table.model';
import { IPayment } from 'app/shared/model/paymentRecords/payment.model';
import { IPaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';
import { IPaymentsFileUpload } from 'app/shared/model/payments/payments-file-upload.model';

export interface IPlaceholder {
  id?: number;
  description?: string;
  token?: string;
  currencyTables?: ICurrencyTable[];
  payments?: IPayment[];
  paymentsFileTypes?: IPaymentsFileType[];
  paymentsFileUploads?: IPaymentsFileUpload[];
}

export class Placeholder implements IPlaceholder {
  constructor(
    public id?: number,
    public description?: string,
    public token?: string,
    public currencyTables?: ICurrencyTable[],
    public payments?: IPayment[],
    public paymentsFileTypes?: IPaymentsFileType[],
    public paymentsFileUploads?: IPaymentsFileUpload[]
  ) {}
}
