import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';
import { PaymentsFileMediumTypes } from 'app/shared/model/enumerations/payments-file-medium-types.model';
import { PaymentsFileModelType } from 'app/shared/model/enumerations/payments-file-model-type.model';

export interface IPaymentsFileType {
  id?: number;
  paymentsFileTypeName?: string;
  paymentsFileMediumType?: PaymentsFileMediumTypes;
  description?: string;
  fileTemplateContentType?: string;
  fileTemplate?: any;
  paymentsfileType?: PaymentsFileModelType;
  placeholders?: IPlaceholder[];
}

export class PaymentsFileType implements IPaymentsFileType {
  constructor(
    public id?: number,
    public paymentsFileTypeName?: string,
    public paymentsFileMediumType?: PaymentsFileMediumTypes,
    public description?: string,
    public fileTemplateContentType?: string,
    public fileTemplate?: any,
    public paymentsfileType?: PaymentsFileModelType,
    public placeholders?: IPlaceholder[]
  ) {}
}
