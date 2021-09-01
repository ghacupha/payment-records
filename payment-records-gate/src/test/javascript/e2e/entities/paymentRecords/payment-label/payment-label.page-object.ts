import { element, by, ElementFinder } from 'protractor';

export class PaymentLabelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payment-label div table .btn-danger'));
  title = element.all(by.css('jhi-payment-label div h2#page-heading span')).first();
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

export class PaymentLabelUpdatePage {
  pageTitle = element(by.id('jhi-payment-label-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  descriptionInput = element(by.id('field_description'));
  commentsInput = element(by.id('field_comments'));
  scheduleInput = element(by.id('field_schedule'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
  }

  async setScheduleInput(schedule: string): Promise<void> {
    await this.scheduleInput.sendKeys(schedule);
  }

  async getScheduleInput(): Promise<string> {
    return await this.scheduleInput.getAttribute('value');
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

export class PaymentLabelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paymentLabel-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paymentLabel'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
