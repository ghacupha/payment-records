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

export class PaymentsFileUploadComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payments-file-upload div table .btn-danger'));
  title = element.all(by.css('jhi-payments-file-upload div h2#page-heading span')).first();
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

export class PaymentsFileUploadUpdatePage {
  pageTitle = element(by.id('jhi-payments-file-upload-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  descriptionInput = element(by.id('field_description'));
  fileNameInput = element(by.id('field_fileName'));
  periodFromInput = element(by.id('field_periodFrom'));
  periodToInput = element(by.id('field_periodTo'));
  paymentsFileTypeIdInput = element(by.id('field_paymentsFileTypeId'));
  dataFileInput = element(by.id('file_dataFile'));
  uploadSuccessfulInput = element(by.id('field_uploadSuccessful'));
  uploadProcessedInput = element(by.id('field_uploadProcessed'));
  uploadTokenInput = element(by.id('field_uploadToken'));

  placeholderSelect = element(by.id('field_placeholder'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setFileNameInput(fileName: string): Promise<void> {
    await this.fileNameInput.sendKeys(fileName);
  }

  async getFileNameInput(): Promise<string> {
    return await this.fileNameInput.getAttribute('value');
  }

  async setPeriodFromInput(periodFrom: string): Promise<void> {
    await this.periodFromInput.sendKeys(periodFrom);
  }

  async getPeriodFromInput(): Promise<string> {
    return await this.periodFromInput.getAttribute('value');
  }

  async setPeriodToInput(periodTo: string): Promise<void> {
    await this.periodToInput.sendKeys(periodTo);
  }

  async getPeriodToInput(): Promise<string> {
    return await this.periodToInput.getAttribute('value');
  }

  async setPaymentsFileTypeIdInput(paymentsFileTypeId: string): Promise<void> {
    await this.paymentsFileTypeIdInput.sendKeys(paymentsFileTypeId);
  }

  async getPaymentsFileTypeIdInput(): Promise<string> {
    return await this.paymentsFileTypeIdInput.getAttribute('value');
  }

  async setDataFileInput(dataFile: string): Promise<void> {
    await this.dataFileInput.sendKeys(dataFile);
  }

  async getDataFileInput(): Promise<string> {
    return await this.dataFileInput.getAttribute('value');
  }

  getUploadSuccessfulInput(): ElementFinder {
    return this.uploadSuccessfulInput;
  }

  getUploadProcessedInput(): ElementFinder {
    return this.uploadProcessedInput;
  }

  async setUploadTokenInput(uploadToken: string): Promise<void> {
    await this.uploadTokenInput.sendKeys(uploadToken);
  }

  async getUploadTokenInput(): Promise<string> {
    return await this.uploadTokenInput.getAttribute('value');
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

export class PaymentsFileUploadDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paymentsFileUpload-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paymentsFileUpload'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
