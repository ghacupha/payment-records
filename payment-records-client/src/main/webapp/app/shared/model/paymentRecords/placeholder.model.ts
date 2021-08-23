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
