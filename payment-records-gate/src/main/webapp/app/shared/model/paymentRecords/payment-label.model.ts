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
