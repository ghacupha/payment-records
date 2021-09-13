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
import { IPaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';

export interface IPayment {
  id?: number;
  paymentsCategory?: string;
  transactionNumber?: string;
  transactionDate?: Moment;
  transactionCurrency?: string;
  transactionAmount?: number;
  beneficiary?: string;
  placeholders?: IPlaceholder[];
  paymentLabels?: IPaymentLabel[];
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
    public placeholders?: IPlaceholder[],
    public paymentLabels?: IPaymentLabel[]
  ) {}
}
