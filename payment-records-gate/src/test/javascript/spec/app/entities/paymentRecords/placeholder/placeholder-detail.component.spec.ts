import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PlaceholderDetailComponent } from 'app/entities/paymentRecords/placeholder/placeholder-detail.component';
import { Placeholder } from 'app/shared/model/paymentRecords/placeholder.model';

describe('Component Tests', () => {
  describe('Placeholder Management Detail Component', () => {
    let comp: PlaceholderDetailComponent;
    let fixture: ComponentFixture<PlaceholderDetailComponent>;
    const route = ({ data: of({ placeholder: new Placeholder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PlaceholderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlaceholderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlaceholderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load placeholder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.placeholder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
