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

export class PaymentsFileTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payments-file-type div table .btn-danger'));
  title = element.all(by.css('jhi-payments-file-type div h2#page-heading span')).first();
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

export class PaymentsFileTypeUpdatePage {
  pageTitle = element(by.id('jhi-payments-file-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  paymentsFileTypeNameInput = element(by.id('field_paymentsFileTypeName'));
  paymentsFileMediumTypeSelect = element(by.id('field_paymentsFileMediumType'));
  descriptionInput = element(by.id('field_description'));
  fileTemplateInput = element(by.id('file_fileTemplate'));
  paymentsfileTypeSelect = element(by.id('field_paymentsfileType'));

  placeholderSelect = element(by.id('field_placeholder'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setPaymentsFileTypeNameInput(paymentsFileTypeName: string): Promise<void> {
    await this.paymentsFileTypeNameInput.sendKeys(paymentsFileTypeName);
  }

  async getPaymentsFileTypeNameInput(): Promise<string> {
    return await this.paymentsFileTypeNameInput.getAttribute('value');
  }

  async setPaymentsFileMediumTypeSelect(paymentsFileMediumType: string): Promise<void> {
    await this.paymentsFileMediumTypeSelect.sendKeys(paymentsFileMediumType);
  }

  async getPaymentsFileMediumTypeSelect(): Promise<string> {
    return await this.paymentsFileMediumTypeSelect.element(by.css('option:checked')).getText();
  }

  async paymentsFileMediumTypeSelectLastOption(): Promise<void> {
    await this.paymentsFileMediumTypeSelect.all(by.tagName('option')).last().click();
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setFileTemplateInput(fileTemplate: string): Promise<void> {
    await this.fileTemplateInput.sendKeys(fileTemplate);
  }

  async getFileTemplateInput(): Promise<string> {
    return await this.fileTemplateInput.getAttribute('value');
  }

  async setPaymentsfileTypeSelect(paymentsfileType: string): Promise<void> {
    await this.paymentsfileTypeSelect.sendKeys(paymentsfileType);
  }

  async getPaymentsfileTypeSelect(): Promise<string> {
    return await this.paymentsfileTypeSelect.element(by.css('option:checked')).getText();
  }

  async paymentsfileTypeSelectLastOption(): Promise<void> {
    await this.paymentsfileTypeSelect.all(by.tagName('option')).last().click();
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

export class PaymentsFileTypeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paymentsFileType-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paymentsFileType'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
