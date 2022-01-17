import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ILegalEntityType } from 'app/entities/legal-entity-type/legal-entity-type.model';
import { LegalEntityTypeService } from 'app/entities/legal-entity-type/service/legal-entity-type.service';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { ILegalEntity, LegalEntity } from '../legal-entity.model';
import { LegalEntityService } from '../service/legal-entity.service';


@Component({
  selector: 'jhi-legal-entity-update',
  templateUrl: './legal-entity-update.component.html',
})
export class LegalEntityUpdateComponent implements OnInit {
  isSaving = false;

  typesCollection: ILegalEntityType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    type: [],
  });

  constructor(
    protected legalEntityService: LegalEntityService,
    protected legalEntityTypeService: LegalEntityTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((legalEntity: ILegalEntity) => {
      // this is necessary to extract the entity from the wrapping object sent by the route resolve
      const entityToUpdate: ILegalEntity = Object(legalEntity)["legalEntity"];

      this.updateForm(entityToUpdate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const legalEntity = this.createFromForm();
    if (legalEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.legalEntityService.update(legalEntity));
    } else {
      this.subscribeToSaveResponse(this.legalEntityService.create(legalEntity));
    }
  }

  trackLegalEntityTypeById(index: number, item: ILegalEntityType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILegalEntity>>): void {
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

  protected updateForm(legalEntity: ILegalEntity): void {
    this.editForm.patchValue({
      id: legalEntity.id,
      name: legalEntity.name,
      type: legalEntity.type,
    });

    this.typesCollection = this.legalEntityTypeService.addLegalEntityTypeToCollectionIfMissing(this.typesCollection, legalEntity.type);
  }

  protected loadRelationshipsOptions(): void {
    this.legalEntityTypeService
      .query({ filter: 'legalentity-is-null' })
      .pipe(map((res: HttpResponse<ILegalEntityType[]>) => res.body ?? []))
      .pipe(
        map((legalEntityTypes: ILegalEntityType[]) =>
          this.legalEntityTypeService.addLegalEntityTypeToCollectionIfMissing(legalEntityTypes, this.editForm.get('type')!.value as ILegalEntityType)
        )
      )
      .subscribe((legalEntityTypes: ILegalEntityType[]) => (this.typesCollection = legalEntityTypes));
  }

  protected createFromForm(): ILegalEntity {
    return {
      ...new LegalEntity(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }
}
