import {Component, OnInit, Input, ViewChild} from '@angular/core';
import {ImportService} from '../../../shared/import.service';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'import-form',
  templateUrl: './import-form.html',
  providers: [ImportService, NotificationsService],
})

export class ImportComponent implements OnInit {
  @Input() title:string;

  private loadingFile:boolean = false
  private formData:FormData = new FormData();

  constructor(private importService:ImportService,
              private _notificationsService:NotificationsService) {
  }

  ngOnInit() {
  }

  fileChange(event) {
    this.formData = null;
    let fileList:FileList = event.target.files;
    if (fileList.length > 0) {
      let file:File = fileList[0];
      this.formData.append('uploadFile', file, file.name);
    }


  }

  importFile() {
    this.importService.importFile(this.formData)
      .subscribe(
        res => {

        },
        err => {

          this.loadingFile = false;

        }
      );
  }
}
