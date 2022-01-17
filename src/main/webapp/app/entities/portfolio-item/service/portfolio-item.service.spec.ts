import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPortfolioItem, PortfolioItem } from '../portfolio-item.model';

import { PortfolioItemService } from './portfolio-item.service';

describe('PortfolioItem Service', () => {
  let service: PortfolioItemService;
  let httpMock: HttpTestingController;
  let elemDefault: IPortfolioItem;
  let expectedResult: IPortfolioItem | IPortfolioItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PortfolioItemService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PortfolioItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PortfolioItem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PortfolioItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PortfolioItem', () => {
      const patchObject = Object.assign({}, new PortfolioItem());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PortfolioItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PortfolioItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPortfolioItemToCollectionIfMissing', () => {
      it('should add a PortfolioItem to an empty array', () => {
        const portfolioItem: IPortfolioItem = { id: 123 };
        expectedResult = service.addPortfolioItemToCollectionIfMissing([], portfolioItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(portfolioItem);
      });

      it('should not add a PortfolioItem to an array that contains it', () => {
        const portfolioItem: IPortfolioItem = { id: 123 };
        const portfolioItemCollection: IPortfolioItem[] = [
          {
            ...portfolioItem,
          },
          { id: 456 },
        ];
        expectedResult = service.addPortfolioItemToCollectionIfMissing(portfolioItemCollection, portfolioItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PortfolioItem to an array that doesn't contain it", () => {
        const portfolioItem: IPortfolioItem = { id: 123 };
        const portfolioItemCollection: IPortfolioItem[] = [{ id: 456 }];
        expectedResult = service.addPortfolioItemToCollectionIfMissing(portfolioItemCollection, portfolioItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(portfolioItem);
      });

      it('should add only unique PortfolioItem to an array', () => {
        const portfolioItemArray: IPortfolioItem[] = [{ id: 123 }, { id: 456 }, { id: 45884 }];
        const portfolioItemCollection: IPortfolioItem[] = [{ id: 123 }];
        expectedResult = service.addPortfolioItemToCollectionIfMissing(portfolioItemCollection, ...portfolioItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const portfolioItem: IPortfolioItem = { id: 123 };
        const portfolioItem2: IPortfolioItem = { id: 456 };
        expectedResult = service.addPortfolioItemToCollectionIfMissing([], portfolioItem, portfolioItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(portfolioItem);
        expect(expectedResult).toContain(portfolioItem2);
      });

      it('should accept null and undefined values', () => {
        const portfolioItem: IPortfolioItem = { id: 123 };
        expectedResult = service.addPortfolioItemToCollectionIfMissing([], null, portfolioItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(portfolioItem);
      });

      it('should return initial array if no PortfolioItem is added', () => {
        const portfolioItemCollection: IPortfolioItem[] = [{ id: 123 }];
        expectedResult = service.addPortfolioItemToCollectionIfMissing(portfolioItemCollection, undefined, null);
        expect(expectedResult).toEqual(portfolioItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
