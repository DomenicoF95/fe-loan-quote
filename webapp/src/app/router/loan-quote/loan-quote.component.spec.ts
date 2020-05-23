import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanQuoteComponent } from './loan-quote.component';

describe('LoanQuoteComponent', () => {
  let component: LoanQuoteComponent;
  let fixture: ComponentFixture<LoanQuoteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanQuoteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanQuoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
