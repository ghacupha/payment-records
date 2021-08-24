import { Moment } from 'moment';
import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';

export interface IPaymentsFileUpload {
  id?: number;
  description?: string;
  fileName?: string;
  periodFrom?: Moment;
  periodTo?: Moment;
  paymentsFileTypeId?: number;
  dataFileContentType?: string;
  dataFile?: any;
  uploadSuccessful?: boolean;
  uploadProcessed?: boolean;
  uploadToken?: string;
  placeholders?: IPlaceholder[];
}

export class PaymentsFileUpload implements IPaymentsFileUpload {
  constructor(
    public id?: number,
    public description?: string,
    public fileName?: string,
    public periodFrom?: Moment,
    public periodTo?: Moment,
    public paymentsFileTypeId?: number,
    public dataFileContentType?: string,
    public dataFile?: any,
    public uploadSuccessful?: boolean,
    public uploadProcessed?: boolean,
    public uploadToken?: string,
    public placeholders?: IPlaceholder[]
  ) {
    this.uploadSuccessful = this.uploadSuccessful || false;
    this.uploadProcessed = this.uploadProcessed || false;
  }
}
