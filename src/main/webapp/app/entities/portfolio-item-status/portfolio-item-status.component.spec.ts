import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioItemStatusComponent } from './portfolio-item-status.component';

describe('PortfolioItemStatusComponent', () => {
  let component: PortfolioItemStatusComponent;
  let fixture: ComponentFixture<PortfolioItemStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PortfolioItemStatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PortfolioItemStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
