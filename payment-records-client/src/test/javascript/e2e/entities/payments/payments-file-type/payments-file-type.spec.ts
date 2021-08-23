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

import { PaymentsFileTypeComponentsPage, PaymentsFileTypeDeleteDialog, PaymentsFileTypeUpdatePage } from './payments-file-type.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('PaymentsFileType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentsFileTypeComponentsPage: PaymentsFileTypeComponentsPage;
  let paymentsFileTypeUpdatePage: PaymentsFileTypeUpdatePage;
  let paymentsFileTypeDeleteDialog: PaymentsFileTypeDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentsFileTypes', async () => {
    await navBarPage.goToEntity('payments-file-type');
    paymentsFileTypeComponentsPage = new PaymentsFileTypeComponentsPage();
    await browser.wait(ec.visibilityOf(paymentsFileTypeComponentsPage.title), 5000);
    expect(await paymentsFileTypeComponentsPage.getTitle()).to.eq('Payments File Types');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentsFileTypeComponentsPage.entities), ec.visibilityOf(paymentsFileTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentsFileType page', async () => {
    await paymentsFileTypeComponentsPage.clickOnCreateButton();
    paymentsFileTypeUpdatePage = new PaymentsFileTypeUpdatePage();
    expect(await paymentsFileTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Payments File Type');
    await paymentsFileTypeUpdatePage.cancel();
  });

  it('should create and save PaymentsFileTypes', async () => {
    const nbButtonsBeforeCreate = await paymentsFileTypeComponentsPage.countDeleteButtons();

    await paymentsFileTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentsFileTypeUpdatePage.setPaymentsFileTypeNameInput('paymentsFileTypeName'),
      paymentsFileTypeUpdatePage.paymentsFileMediumTypeSelectLastOption(),
      paymentsFileTypeUpdatePage.setDescriptionInput('description'),
      paymentsFileTypeUpdatePage.setFileTemplateInput(absolutePath),
      paymentsFileTypeUpdatePage.paymentsfileTypeSelectLastOption(),
      // paymentsFileTypeUpdatePage.placeholderSelectLastOption(),
    ]);

    expect(await paymentsFileTypeUpdatePage.getPaymentsFileTypeNameInput()).to.eq(
      'paymentsFileTypeName',
      'Expected PaymentsFileTypeName value to be equals to paymentsFileTypeName'
    );
    expect(await paymentsFileTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await paymentsFileTypeUpdatePage.getFileTemplateInput()).to.endsWith(
      fileNameToUpload,
      'Expected FileTemplate value to be end with ' + fileNameToUpload
    );

    await paymentsFileTypeUpdatePage.save();
    expect(await paymentsFileTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentsFileTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentsFileType', async () => {
    const nbButtonsBeforeDelete = await paymentsFileTypeComponentsPage.countDeleteButtons();
    await paymentsFileTypeComponentsPage.clickOnLastDeleteButton();

    paymentsFileTypeDeleteDialog = new PaymentsFileTypeDeleteDialog();
    expect(await paymentsFileTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Payments File Type?');
    await paymentsFileTypeDeleteDialog.clickOnConfirmButton();

    expect(await paymentsFileTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
