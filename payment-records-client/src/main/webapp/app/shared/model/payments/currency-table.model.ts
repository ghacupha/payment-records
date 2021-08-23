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
import { CurrencyLocality } from 'app/shared/model/enumerations/currency-locality.model';

export interface ICurrencyTable {
  id?: number;
  currencyCode?: string;
  locality?: CurrencyLocality;
  currencyName?: string;
  country?: string;
  placeholders?: IPlaceholder[];
}

export class CurrencyTable implements ICurrencyTable {
  constructor(
    public id?: number,
    public currencyCode?: string,
    public locality?: CurrencyLocality,
    public currencyName?: string,
    public country?: string,
    public placeholders?: IPlaceholder[]
  ) {}
}
