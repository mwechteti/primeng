import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { HealthService } from './health.service';
import { Health, HealthDetails, HealthStatus } from './health.model';
import { HealthModalComponent } from './modal/health-modal.component';
import { NextObserver } from 'rxjs/internal/types';

@Component({
  selector: 'jhi-health',
  templateUrl: './health.component.html',
})
export class HealthComponent implements OnInit {
  health?: Health;

  constructor(private modalService: NgbModal, private healthService: HealthService) { }

  ngOnInit(): void {
    this.refresh();
  }

  getBadgeClass(statusState: HealthStatus): string {
    if (statusState === 'UP') {
      return 'badge-success';
    } else {
      return 'badge-danger';
    }
  }

  refresh(): void {
    this.healthService.checkHealth().subscribe({
      next: x => this.health = x,
      error: (err: HttpErrorResponse) => {
        if (err.status === 503) {
          this.health = err.error;
        }
      }
    });
  }

  showHealth(health: { key: string; value: HealthDetails }): void {
    const modalRef = this.modalService.open(HealthModalComponent);
    modalRef.componentInstance.health = health;
  }
}
