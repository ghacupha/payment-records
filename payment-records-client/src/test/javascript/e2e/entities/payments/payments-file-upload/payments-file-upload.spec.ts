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
  PaymentsFileUploadComponentsPage,
  PaymentsFileUploadDeleteDialog,
  PaymentsFileUploadUpdatePage,
} from './payments-file-upload.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('PaymentsFileUpload e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentsFileUploadComponentsPage: PaymentsFileUploadComponentsPage;
  let paymentsFileUploadUpdatePage: PaymentsFileUploadUpdatePage;
  let paymentsFileUploadDeleteDialog: PaymentsFileUploadDeleteDialog;
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

  it('should load PaymentsFileUploads', async () => {
    await navBarPage.goToEntity('payments-file-upload');
    paymentsFileUploadComponentsPage = new PaymentsFileUploadComponentsPage();
    await browser.wait(ec.visibilityOf(paymentsFileUploadComponentsPage.title), 5000);
    expect(await paymentsFileUploadComponentsPage.getTitle()).to.eq('Payments File Uploads');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentsFileUploadComponentsPage.entities), ec.visibilityOf(paymentsFileUploadComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentsFileUpload page', async () => {
    await paymentsFileUploadComponentsPage.clickOnCreateButton();
    paymentsFileUploadUpdatePage = new PaymentsFileUploadUpdatePage();
    expect(await paymentsFileUploadUpdatePage.getPageTitle()).to.eq('Create or edit a Payments File Upload');
    await paymentsFileUploadUpdatePage.cancel();
  });

  it('should create and save PaymentsFileUploads', async () => {
    const nbButtonsBeforeCreate = await paymentsFileUploadComponentsPage.countDeleteButtons();

    await paymentsFileUploadComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentsFileUploadUpdatePage.setDescriptionInput('description'),
      paymentsFileUploadUpdatePage.setFileNameInput('fileName'),
      paymentsFileUploadUpdatePage.setPeriodFromInput('2000-12-31'),
      paymentsFileUploadUpdatePage.setPeriodToInput('2000-12-31'),
      paymentsFileUploadUpdatePage.setPaymentsFileTypeIdInput('5'),
      paymentsFileUploadUpdatePage.setDataFileInput(absolutePath),
      paymentsFileUploadUpdatePage.setUploadTokenInput('uploadToken'),
      // paymentsFileUploadUpdatePage.placeholderSelectLastOption(),
    ]);

    expect(await paymentsFileUploadUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await paymentsFileUploadUpdatePage.getFileNameInput()).to.eq('fileName', 'Expected FileName value to be equals to fileName');
    expect(await paymentsFileUploadUpdatePage.getPeriodFromInput()).to.eq(
      '2000-12-31',
      'Expected periodFrom value to be equals to 2000-12-31'
    );
    expect(await paymentsFileUploadUpdatePage.getPeriodToInput()).to.eq('2000-12-31', 'Expected periodTo value to be equals to 2000-12-31');
    expect(await paymentsFileUploadUpdatePage.getPaymentsFileTypeIdInput()).to.eq(
      '5',
      'Expected paymentsFileTypeId value to be equals to 5'
    );
    expect(await paymentsFileUploadUpdatePage.getDataFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected DataFile value to be end with ' + fileNameToUpload
    );
    const selectedUploadSuccessful = paymentsFileUploadUpdatePage.getUploadSuccessfulInput();
    if (await selectedUploadSuccessful.isSelected()) {
      await paymentsFileUploadUpdatePage.getUploadSuccessfulInput().click();
      expect(await paymentsFileUploadUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful not to be selected').to
        .be.false;
    } else {
      await paymentsFileUploadUpdatePage.getUploadSuccessfulInput().click();
      expect(await paymentsFileUploadUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful to be selected').to.be
        .true;
    }
    const selectedUploadProcessed = paymentsFileUploadUpdatePage.getUploadProcessedInput();
    if (await selectedUploadProcessed.isSelected()) {
      await paymentsFileUploadUpdatePage.getUploadProcessedInput().click();
      expect(await paymentsFileUploadUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed not to be selected').to.be
        .false;
    } else {
      await paymentsFileUploadUpdatePage.getUploadProcessedInput().click();
      expect(await paymentsFileUploadUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed to be selected').to.be
        .true;
    }
    expect(await paymentsFileUploadUpdatePage.getUploadTokenInput()).to.eq(
      'uploadToken',
      'Expected UploadToken value to be equals to uploadToken'
    );

    await paymentsFileUploadUpdatePage.save();
    expect(await paymentsFileUploadUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentsFileUploadComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentsFileUpload', async () => {
    const nbButtonsBeforeDelete = await paymentsFileUploadComponentsPage.countDeleteButtons();
    await paymentsFileUploadComponentsPage.clickOnLastDeleteButton();

    paymentsFileUploadDeleteDialog = new PaymentsFileUploadDeleteDialog();
    expect(await paymentsFileUploadDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Payments File Upload?');
    await paymentsFileUploadDeleteDialog.clickOnConfirmButton();

    expect(await paymentsFileUploadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
