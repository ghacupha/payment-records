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

import { element, by, ElementFinder } from 'protractor';

export class CurrencyTableComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-currency-table div table .btn-danger'));
  title = element.all(by.css('jhi-currency-table div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class CurrencyTableUpdatePage {
  pageTitle = element(by.id('jhi-currency-table-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  currencyCodeInput = element(by.id('field_currencyCode'));
  localitySelect = element(by.id('field_locality'));
  currencyNameInput = element(by.id('field_currencyName'));
  countryInput = element(by.id('field_country'));

  placeholderSelect = element(by.id('field_placeholder'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setCurrencyCodeInput(currencyCode: string): Promise<void> {
    await this.currencyCodeInput.sendKeys(currencyCode);
  }

  async getCurrencyCodeInput(): Promise<string> {
    return await this.currencyCodeInput.getAttribute('value');
  }

  async setLocalitySelect(locality: string): Promise<void> {
    await this.localitySelect.sendKeys(locality);
  }

  async getLocalitySelect(): Promise<string> {
    return await this.localitySelect.element(by.css('option:checked')).getText();
  }

  async localitySelectLastOption(): Promise<void> {
    await this.localitySelect.all(by.tagName('option')).last().click();
  }

  async setCurrencyNameInput(currencyName: string): Promise<void> {
    await this.currencyNameInput.sendKeys(currencyName);
  }

  async getCurrencyNameInput(): Promise<string> {
    return await this.currencyNameInput.getAttribute('value');
  }

  async setCountryInput(country: string): Promise<void> {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput(): Promise<string> {
    return await this.countryInput.getAttribute('value');
  }

  async placeholderSelectLastOption(): Promise<void> {
    await this.placeholderSelect.all(by.tagName('option')).last().click();
  }

  async placeholderSelectOption(option: string): Promise<void> {
    await this.placeholderSelect.sendKeys(option);
  }

  getPlaceholderSelect(): ElementFinder {
    return this.placeholderSelect;
  }

  async getPlaceholderSelectedOption(): Promise<string> {
    return await this.placeholderSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CurrencyTableDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-currencyTable-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-currencyTable'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
