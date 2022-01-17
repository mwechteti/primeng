import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { ILegalEntityType, LegalEntityType } from '../legal-entity-type.model';
import { LegalEntityTypeService } from '../service/legal-entity-type.service';


@Component({
  selector: 'jhi-legal-entity-type-update',
  templateUrl: './legal-entity-type-update.component.html',
})
export class LegalEntityTypeUpdateComponent implements OnInit {
  isSaving = false;
  editForm: FormGroup;

  constructor(
    protected legalEntityTypeService: LegalEntityTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      id: [],
      label: [null, [Validators.required]],
    });

  }

  ngOnInit(): void {

    // 1st way to retrieve entity data: using the legalEntityTypeService to query the backend
    // const idParameter: number = +(this.activatedRoute.snapshot.paramMap.get('id') ?? 0);
    // if (idParameter !== 0) {
    //   this.legalEntityTypeService.find(idParameter).subscribe({
    //     next: (res: HttpResponse<ILegalEntityType>) => {
    //       if (res.body) {
    //         this.updateForm(res.body);
    //       }
    //     }
    //   });
    // }

    // 2nd way to retrieve entity data: using the activated route through the resolver which queries the backend before initiating the component
    this.activatedRoute.data.subscribe(
      res => {
        const entityToUpdate: ILegalEntityType = Object(res)["legalEntityType"];
        this.updateForm(entityToUpdate);
      });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const legalEntityType = this.createFromForm();
    if (legalEntityType.id !== undefined) {
      this.subscribeToSaveResponse(this.legalEntityTypeService.update(legalEntityType));
    } else {
      this.subscribeToSaveResponse(this.legalEntityTypeService.create(legalEntityType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILegalEntityType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError()
    });
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

  protected updateForm(entity: ILegalEntityType): void {
    this.editForm.patchValue({
      id: entity.id ?? 0,
      label: entity.label ?? 'no value',
    });
  }

  protected createFromForm(): ILegalEntityType {
    return {
      ...new LegalEntityType(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
    };
  }
}
