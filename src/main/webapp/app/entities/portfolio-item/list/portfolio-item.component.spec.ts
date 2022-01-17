import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PortfolioItemService } from '../service/portfolio-item.service';

import { PortfolioItemComponent } from './portfolio-item.component';

describe('PortfolioItem Management Component', () => {
  let comp: PortfolioItemComponent;
  let fixture: ComponentFixture<PortfolioItemComponent>;
  let service: PortfolioItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PortfolioItemComponent],
    })
      .overrideTemplate(PortfolioItemComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PortfolioItemComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PortfolioItemService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.portfolioItems?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
