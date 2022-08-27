import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { LogModel } from '../models/logs.model';

@Injectable()
export class DataService {
  private readonly API_URL_LOG = 'http://localhost:8080/logs';


  dataLog: BehaviorSubject<LogModel[]> = new BehaviorSubject<LogModel[]>([]);

  dialogData: any;

  constructor (private httpClient: HttpClient, public toastr: ToastrService) {}

  get data(): LogModel[] {
    return this.dataLog.value;
  }

  getDialogData() {
    return this.dialogData;
  }


  getAllLogs(): void {
    this.httpClient.get<LogModel[]>(this.API_URL_LOG).subscribe(data => {
        this.dataLog.next(data);
      },
      (error: HttpErrorResponse) => {
        this.toastr.error('Error: ' + error.name + ' ' + error.message);
      });
  }
  
  addLog(log: LogModel): void {
    this.httpClient.post(this.API_URL_LOG, log).subscribe(data => {
      this.dialogData = data;
       this.toastr.success('Salvo com sucesso!');
       window.location.reload()
      },
      (err: HttpErrorResponse) => {
        this.toastr.error('Error: ' + err.name + ' ' + err.message);
    });
  }
}




