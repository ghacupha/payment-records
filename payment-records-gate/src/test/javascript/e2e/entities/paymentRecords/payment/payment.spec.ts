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

import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { PaymentComponentsPage, PaymentDeleteDialog, PaymentUpdatePage } from './payment.page-object';

const expect = chai.expect;

describe('Payment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentComponentsPage: PaymentComponentsPage;
  let paymentUpdatePage: PaymentUpdatePage;
  let paymentDeleteDialog: PaymentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Payments', async () => {
    await navBarPage.goToEntity('payment');
    paymentComponentsPage = new PaymentComponentsPage();
    await browser.wait(ec.visibilityOf(paymentComponentsPage.title), 5000);
    expect(await paymentComponentsPage.getTitle()).to.eq('Payments');
    await browser.wait(ec.or(ec.visibilityOf(paymentComponentsPage.entities), ec.visibilityOf(paymentComponentsPage.noResult)), 1000);
  });

  it('should load create Payment page', async () => {
    await paymentComponentsPage.clickOnCreateButton();
    paymentUpdatePage = new PaymentUpdatePage();
    expect(await paymentUpdatePage.getPageTitle()).to.eq('Create or edit a Payment');
    await paymentUpdatePage.cancel();
  });

  it('should create and save Payments', async () => {
    const nbButtonsBeforeCreate = await paymentComponentsPage.countDeleteButtons();

    await paymentComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentUpdatePage.setPaymentsCategoryInput('paymentsCategory'),
      paymentUpdatePage.setTransactionNumberInput('transactionNumber'),
      paymentUpdatePage.setTransactionDateInput('2000-12-31'),
      paymentUpdatePage.setTransactionCurrencyInput('transactionCurrency'),
      paymentUpdatePage.setTransactionAmountInput('5'),
      paymentUpdatePage.setBeneficiaryInput('beneficiary'),
      // paymentUpdatePage.placeholderSelectLastOption(),
      // paymentUpdatePage.paymentLabelSelectLastOption(),
    ]);

    expect(await paymentUpdatePage.getPaymentsCategoryInput()).to.eq(
      'paymentsCategory',
      'Expected PaymentsCategory value to be equals to paymentsCategory'
    );
    expect(await paymentUpdatePage.getTransactionNumberInput()).to.eq(
      'transactionNumber',
      'Expected TransactionNumber value to be equals to transactionNumber'
    );
    expect(await paymentUpdatePage.getTransactionDateInput()).to.eq(
      '2000-12-31',
      'Expected transactionDate value to be equals to 2000-12-31'
    );
    expect(await paymentUpdatePage.getTransactionCurrencyInput()).to.eq(
      'transactionCurrency',
      'Expected TransactionCurrency value to be equals to transactionCurrency'
    );
    expect(await paymentUpdatePage.getTransactionAmountInput()).to.eq('5', 'Expected transactionAmount value to be equals to 5');
    expect(await paymentUpdatePage.getBeneficiaryInput()).to.eq('beneficiary', 'Expected Beneficiary value to be equals to beneficiary');

    await paymentUpdatePage.save();
    expect(await paymentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Payment', async () => {
    const nbButtonsBeforeDelete = await paymentComponentsPage.countDeleteButtons();
    await paymentComponentsPage.clickOnLastDeleteButton();

    paymentDeleteDialog = new PaymentDeleteDialog();
    expect(await paymentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Payment?');
    await paymentDeleteDialog.clickOnConfirmButton();

    expect(await paymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
