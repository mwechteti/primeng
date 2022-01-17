import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortfolioItemDetailComponent } from './portfolio-item-detail.component';

describe('PortfolioItem Management Detail Component', () => {
  let comp: PortfolioItemDetailComponent;
  let fixture: ComponentFixture<PortfolioItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PortfolioItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ portfolioItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PortfolioItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PortfolioItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load portfolioItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.portfolioItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
