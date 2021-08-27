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

export class PaymentsMessageTokenComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payments-message-token div table .btn-danger'));
  title = element.all(by.css('jhi-payments-message-token div h2#page-heading span')).first();
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

export class PaymentsMessageTokenUpdatePage {
  pageTitle = element(by.id('jhi-payments-message-token-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  descriptionInput = element(by.id('field_description'));
  timeSentInput = element(by.id('field_timeSent'));
  tokenValueInput = element(by.id('field_tokenValue'));
  receivedInput = element(by.id('field_received'));
  actionedInput = element(by.id('field_actioned'));
  contentFullyEnqueuedInput = element(by.id('field_contentFullyEnqueued'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setTimeSentInput(timeSent: string): Promise<void> {
    await this.timeSentInput.sendKeys(timeSent);
  }

  async getTimeSentInput(): Promise<string> {
    return await this.timeSentInput.getAttribute('value');
  }

  async setTokenValueInput(tokenValue: string): Promise<void> {
    await this.tokenValueInput.sendKeys(tokenValue);
  }

  async getTokenValueInput(): Promise<string> {
    return await this.tokenValueInput.getAttribute('value');
  }

  getReceivedInput(): ElementFinder {
    return this.receivedInput;
  }

  getActionedInput(): ElementFinder {
    return this.actionedInput;
  }

  getContentFullyEnqueuedInput(): ElementFinder {
    return this.contentFullyEnqueuedInput;
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

export class PaymentsMessageTokenDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paymentsMessageToken-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paymentsMessageToken'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
