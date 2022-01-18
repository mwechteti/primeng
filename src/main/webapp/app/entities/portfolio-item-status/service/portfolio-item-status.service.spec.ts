import { TestBed } from '@angular/core/testing';

import { PortfolioItemStatusService } from './portfolio-item-status.service';

describe('PortfolioItemStatusService', () => {
  let service: PortfolioItemStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PortfolioItemStatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
