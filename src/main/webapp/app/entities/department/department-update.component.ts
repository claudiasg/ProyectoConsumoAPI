import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDepartment, Department } from 'app/shared/model/department.model';
import { DepartmentService } from './department.service';

@Component({
  selector: 'jhi-department-update',
  templateUrl: './department-update.component.html'
})
export class DepartmentUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    departmentID: [null, [Validators.required]],
    departmentName: []
  });

  constructor(protected departmentService: DepartmentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ department }) => {
      this.updateForm(department);
    });
  }

  updateForm(department: IDepartment) {
    this.editForm.patchValue({
      id: department.id,
      departmentID: department.departmentID,
      departmentName: department.departmentName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const department = this.createFromForm();
    if (department.id !== undefined) {
      this.subscribeToSaveResponse(this.departmentService.update(this.department.id,department));
    } else {
      this.subscribeToSaveResponse(this.departmentService.create(department));
    }
  }

  private createFromForm(): IDepartment {
    return {
      ...new Department(),
      id: this.editForm.get(['id']).value,
      departmentID: this.editForm.get(['departmentID']).value,
      departmentName: this.editForm.get(['departmentName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartment>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
