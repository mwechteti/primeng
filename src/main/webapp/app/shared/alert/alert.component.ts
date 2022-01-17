import { Component, OnDestroy, OnInit } from '@angular/core';

import { AlertService, Alert } from 'app/core/util/alert.service';

@Component({
  selector: 'jhi-alert',
  templateUrl: './alert.component.html',
})
export class AlertComponent implements OnInit, OnDestroy {
  alerts: Alert[] = [];

  constructor(private alertService: AlertService) { }

  ngOnInit(): void {
    this.alerts = this.alertService.get();
  }



  ngOnDestroy(): void {
    this.alertService.clear();
  }


}
