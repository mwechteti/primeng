import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IDashboard } from './dashboard.model';
import { DashboardService } from './service/dashboard.service';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  isLoading = false;

  constructor(protected dashboardService: DashboardService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.dashboardService.query().subscribe(
      (): void => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }
}
