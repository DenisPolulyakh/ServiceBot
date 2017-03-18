import {Component, OnInit, Input, ViewChild} from '@angular/core';
import {DictionaryService} from '../../../shared/dictionary.service';
import {DictionaryList} from '../../../model/dictionary.model';
import * as _ from 'lodash'
import {ReplaceletterPipe} from '../../../utils';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'dictionary-list',
  templateUrl: './dictionary-list.html',
  providers: [DictionaryService, NotificationsService],
  styles: [require('./slyle.scss')]
})

export class DictionaryListComponent implements OnInit {
  @Input() title:string;

  private dictionaryList = []
  private itemSnapshot = []
  private loadingList:boolean = false;

  public currentPage:number = 1;
  public totalItems:number = 0; // total numbar of page not items
  public maxSize:number = 100; // max page size

  public options = {
    timeOut: 5000,
    lastOnBottom: true,
    clickToClose: true,
    maxLength: 0,
    maxStack: 7,
    showProgressBar: true,
    pauseOnHover: true,
    preventDuplicates: false,
    preventLastDuplicates: 'visible',
    rtl: false,
    animate: 'scale',
    position: ['right', 'bottom']
  };

  constructor(private dictionaryService:DictionaryService,
              private _notificationsService:NotificationsService) {
  }

  ngOnInit() {
    this.loadDictionary()
  }

  loadDictionary() {
    this.loadingList = true

    this.dictionaryService.getDictionaryList(this.currentPage, this.maxSize)
      .subscribe(
        res => {

          this.dictionaryList = _.map(res.rows, (muDto:any) => {
            return new DictionaryList(muDto);
          });

          this.totalItems = res.total
          this.loadingList = false;
        },
        err => {
          this.loadingList = false;
          this._notificationsService.error(
            'Ошибка',
            'Упс что-то пошло не так',
            this.options
          )
        }
      );
  }

  actionOnSubmit() {

  }

  actionOnClose() {

  }

  pageChanged(page) {
    this.currentPage = page;
    this.loadDictionary()
  }

  edit(item:DictionaryList) {
    if (item.getIsEdit()) return null;
    this.itemSnapshot[item.getId()] = _.cloneDeep(item);
    item.setIsEdit(true)
  }

  save(item:DictionaryList) {
    item.getIsNew()
      ? this.dictionaryService.saveDictionaryItem(item)
      .subscribe(
        res => {
          item.setIsEdit(false);
        },
        err => {
          this.loadingList = false;
        }
      )
      : this.dictionaryService.updateDictionaryItem(item)
      .subscribe(
        res => {
          item.setIsEdit(false);
        },
        err => {
          this.loadingList = false;
        }
      )
  }

  cancel(item:DictionaryList) {
    let index = _.indexOf(this.dictionaryList, _.find(this.dictionaryList, {'_id': item.getId()}));
    this.dictionaryList.splice(index, 1, this.itemSnapshot[item.getId()]);
    // _.remove(this.itemSnapshot, i => (
    //   i._id == item.getId()
    // ))
  }

  add() {
    this.dictionaryList.unshift(
      new DictionaryList({"isEdit": true, "isNew": true})
    )
  }

  remove(item:DictionaryList) {
    this.dictionaryService.removeDictionaryItem(item)
      .subscribe(
        res => {
          item.setIsEdit(false);
          _.remove(this.dictionaryList, i => (
            i._id == item.getId
          ))
        },
        err => {
          debugger
          this.loadingList = false;
          this._notificationsService.error(
            'Ошибка',
            'Упс что-то пошло не так',
            this.options
          )
        }
      );

  }

}
