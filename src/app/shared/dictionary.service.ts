import {Injectable} from '@angular/core';
import {Http, RequestOptions, RequestOptionsArgs, URLSearchParams} from '@angular/http';
import {API_URL} from '../config';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {DictionaryList} from '../model/dictionary.model'

@Injectable()
export class DictionaryService {
  constructor(private http:Http) {
    this.http = http;
  }


  public getDictionaryList(page, maxSize):Observable<any> {
    let offset = 0;
    if (page > 1) {
      offset = (page - 1) * maxSize
    }

    let params:URLSearchParams = new URLSearchParams();
    params.set('offset', offset.toString());
    params.set('records', maxSize);

    return this.http.get(API_URL + '/list', new RequestOptions({
        search: params,
      }))
      .map(res => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'))
  }

  public saveDictionaryItem(item:DictionaryList):Observable<any> {
    return this.http.post(API_URL + '/create', {
        tags: item.getTags(),
        message: item.getMessage(),
      })
      .map(res => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'))
  }

  public updateDictionaryItem(item:DictionaryList):Observable<any> {
    return this.http.put(API_URL + '/update', {
        tags: item.getTags(),
        message: item.getMessage(),
      })
      .map(res => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'))
  }

  public removeDictionaryItem(item:DictionaryList):Observable<any> {
    return this.http.post(API_URL + '/delete', {
        id: item.getId(),
      })
      .map(res => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'))

  }


}
