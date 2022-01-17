import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPortfolio, Portfolio } from '../portfolio.model';
import { PortfolioService } from '../service/portfolio.service';

@Component({
  selector: 'jhi-portfolio-update',
  templateUrl: './portfolio-update.component.html',
})
export class PortfolioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected portfolioService: PortfolioService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((portfolio: IPortfolio) => {
      this.updateForm(portfolio);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const portfolio = this.createFromForm();
    if (portfolio.id !== undefined) {
      this.subscribeToSaveResponse(this.portfolioService.update(portfolio));
    } else {
      this.subscribeToSaveResponse(this.portfolioService.create(portfolio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPortfolio>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(portfolio: IPortfolio): void {
    this.editForm.patchValue({
      id: portfolio.id,
    });
  }

  protected createFromForm(): IPortfolio {
    return {
      ...new Portfolio(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
