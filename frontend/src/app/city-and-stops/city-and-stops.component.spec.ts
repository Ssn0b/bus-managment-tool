import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CityAndStopsComponent } from './city-and-stops.component';

describe('CityAndStopsComponent', () => {
  let component: CityAndStopsComponent;
  let fixture: ComponentFixture<CityAndStopsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CityAndStopsComponent]
    });
    fixture = TestBed.createComponent(CityAndStopsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
