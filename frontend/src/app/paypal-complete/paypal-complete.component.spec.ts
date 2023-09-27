import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaypalCompleteComponent } from './paypal-complete.component';

describe('PaypalCompleteComponent', () => {
  let component: PaypalCompleteComponent;
  let fixture: ComponentFixture<PaypalCompleteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaypalCompleteComponent]
    });
    fixture = TestBed.createComponent(PaypalCompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
