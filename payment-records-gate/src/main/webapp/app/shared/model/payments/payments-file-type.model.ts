///
/// Payment Records - Payment records is part of the ERP System
/// Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
///
/// This program is free software: you can redistribute it and/or modify
/// it under the terms of the GNU General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or
/// (at your option) any later version.
///
/// This program is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU General Public License for more details.
///
/// You should have received a copy of the GNU General Public License
/// along with this program. If not, see <http://www.gnu.org/licenses/>.
///

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
