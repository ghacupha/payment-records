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

import {
  PaymentsMessageTokenComponentsPage,
  PaymentsMessageTokenDeleteDialog,
  PaymentsMessageTokenUpdatePage,
} from './payments-message-token.page-object';

const expect = chai.expect;

describe('PaymentsMessageToken e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentsMessageTokenComponentsPage: PaymentsMessageTokenComponentsPage;
  let paymentsMessageTokenUpdatePage: PaymentsMessageTokenUpdatePage;
  let paymentsMessageTokenDeleteDialog: PaymentsMessageTokenDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentsMessageTokens', async () => {
    await navBarPage.goToEntity('payments-message-token');
    paymentsMessageTokenComponentsPage = new PaymentsMessageTokenComponentsPage();
    await browser.wait(ec.visibilityOf(paymentsMessageTokenComponentsPage.title), 5000);
    expect(await paymentsMessageTokenComponentsPage.getTitle()).to.eq('Payments Message Tokens');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentsMessageTokenComponentsPage.entities), ec.visibilityOf(paymentsMessageTokenComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentsMessageToken page', async () => {
    await paymentsMessageTokenComponentsPage.clickOnCreateButton();
    paymentsMessageTokenUpdatePage = new PaymentsMessageTokenUpdatePage();
    expect(await paymentsMessageTokenUpdatePage.getPageTitle()).to.eq('Create or edit a Payments Message Token');
    await paymentsMessageTokenUpdatePage.cancel();
  });

  it('should create and save PaymentsMessageTokens', async () => {
    const nbButtonsBeforeCreate = await paymentsMessageTokenComponentsPage.countDeleteButtons();

    await paymentsMessageTokenComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentsMessageTokenUpdatePage.setDescriptionInput('description'),
      paymentsMessageTokenUpdatePage.setTimeSentInput('5'),
      paymentsMessageTokenUpdatePage.setTokenValueInput('tokenValue'),
    ]);

    expect(await paymentsMessageTokenUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await paymentsMessageTokenUpdatePage.getTimeSentInput()).to.eq('5', 'Expected timeSent value to be equals to 5');
    expect(await paymentsMessageTokenUpdatePage.getTokenValueInput()).to.eq(
      'tokenValue',
      'Expected TokenValue value to be equals to tokenValue'
    );
    const selectedReceived = paymentsMessageTokenUpdatePage.getReceivedInput();
    if (await selectedReceived.isSelected()) {
      await paymentsMessageTokenUpdatePage.getReceivedInput().click();
      expect(await paymentsMessageTokenUpdatePage.getReceivedInput().isSelected(), 'Expected received not to be selected').to.be.false;
    } else {
      await paymentsMessageTokenUpdatePage.getReceivedInput().click();
      expect(await paymentsMessageTokenUpdatePage.getReceivedInput().isSelected(), 'Expected received to be selected').to.be.true;
    }
    const selectedActioned = paymentsMessageTokenUpdatePage.getActionedInput();
    if (await selectedActioned.isSelected()) {
      await paymentsMessageTokenUpdatePage.getActionedInput().click();
      expect(await paymentsMessageTokenUpdatePage.getActionedInput().isSelected(), 'Expected actioned not to be selected').to.be.false;
    } else {
      await paymentsMessageTokenUpdatePage.getActionedInput().click();
      expect(await paymentsMessageTokenUpdatePage.getActionedInput().isSelected(), 'Expected actioned to be selected').to.be.true;
    }
    const selectedContentFullyEnqueued = paymentsMessageTokenUpdatePage.getContentFullyEnqueuedInput();
    if (await selectedContentFullyEnqueued.isSelected()) {
      await paymentsMessageTokenUpdatePage.getContentFullyEnqueuedInput().click();
      expect(
        await paymentsMessageTokenUpdatePage.getContentFullyEnqueuedInput().isSelected(),
        'Expected contentFullyEnqueued not to be selected'
      ).to.be.false;
    } else {
      await paymentsMessageTokenUpdatePage.getContentFullyEnqueuedInput().click();
      expect(
        await paymentsMessageTokenUpdatePage.getContentFullyEnqueuedInput().isSelected(),
        'Expected contentFullyEnqueued to be selected'
      ).to.be.true;
    }

    await paymentsMessageTokenUpdatePage.save();
    expect(await paymentsMessageTokenUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentsMessageTokenComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentsMessageToken', async () => {
    const nbButtonsBeforeDelete = await paymentsMessageTokenComponentsPage.countDeleteButtons();
    await paymentsMessageTokenComponentsPage.clickOnLastDeleteButton();

    paymentsMessageTokenDeleteDialog = new PaymentsMessageTokenDeleteDialog();
    expect(await paymentsMessageTokenDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Payments Message Token?');
    await paymentsMessageTokenDeleteDialog.clickOnConfirmButton();

    expect(await paymentsMessageTokenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
