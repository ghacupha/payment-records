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
