import { element, by, ElementFinder } from 'protractor';

export class PaymentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payment div table .btn-danger'));
  title = element.all(by.css('jhi-payment div h2#page-heading span')).first();
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

export class PaymentUpdatePage {
  pageTitle = element(by.id('jhi-payment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  paymentsCategoryInput = element(by.id('field_paymentsCategory'));
  transactionNumberInput = element(by.id('field_transactionNumber'));
  transactionDateInput = element(by.id('field_transactionDate'));
  transactionCurrencyInput = element(by.id('field_transactionCurrency'));
  transactionAmountInput = element(by.id('field_transactionAmount'));
  beneficiaryInput = element(by.id('field_beneficiary'));

  placeholderSelect = element(by.id('field_placeholder'));
  paymentLabelSelect = element(by.id('field_paymentLabel'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setPaymentsCategoryInput(paymentsCategory: string): Promise<void> {
    await this.paymentsCategoryInput.sendKeys(paymentsCategory);
  }

  async getPaymentsCategoryInput(): Promise<string> {
    return await this.paymentsCategoryInput.getAttribute('value');
  }

  async setTransactionNumberInput(transactionNumber: string): Promise<void> {
    await this.transactionNumberInput.sendKeys(transactionNumber);
  }

  async getTransactionNumberInput(): Promise<string> {
    return await this.transactionNumberInput.getAttribute('value');
  }

  async setTransactionDateInput(transactionDate: string): Promise<void> {
    await this.transactionDateInput.sendKeys(transactionDate);
  }

  async getTransactionDateInput(): Promise<string> {
    return await this.transactionDateInput.getAttribute('value');
  }

  async setTransactionCurrencyInput(transactionCurrency: string): Promise<void> {
    await this.transactionCurrencyInput.sendKeys(transactionCurrency);
  }

  async getTransactionCurrencyInput(): Promise<string> {
    return await this.transactionCurrencyInput.getAttribute('value');
  }

  async setTransactionAmountInput(transactionAmount: string): Promise<void> {
    await this.transactionAmountInput.sendKeys(transactionAmount);
  }

  async getTransactionAmountInput(): Promise<string> {
    return await this.transactionAmountInput.getAttribute('value');
  }

  async setBeneficiaryInput(beneficiary: string): Promise<void> {
    await this.beneficiaryInput.sendKeys(beneficiary);
  }

  async getBeneficiaryInput(): Promise<string> {
    return await this.beneficiaryInput.getAttribute('value');
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

  async paymentLabelSelectLastOption(): Promise<void> {
    await this.paymentLabelSelect.all(by.tagName('option')).last().click();
  }

  async paymentLabelSelectOption(option: string): Promise<void> {
    await this.paymentLabelSelect.sendKeys(option);
  }

  getPaymentLabelSelect(): ElementFinder {
    return this.paymentLabelSelect;
  }

  async getPaymentLabelSelectedOption(): Promise<string> {
    return await this.paymentLabelSelect.element(by.css('option:checked')).getText();
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

export class PaymentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-payment-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-payment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
